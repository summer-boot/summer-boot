package io.github.summer.boot.xdatabase;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.filter.Order;
import io.github.summer.boot.filter.Page;
import io.github.summer.boot.sql.Preconditions;
import io.github.summer.boot.sql.SqlParameter;
import io.github.summer.boot.sql.SqlParser;
import io.github.summer.boot.sql.SqlParserImpl;
import io.github.summer.boot.value.Value;
import io.github.summer.boot.value.ValueType;
import io.github.summer.boot.xdatabase.logger.LogTemplate;
import io.github.summer.boot.xdatabase.schema.TableSchema;
import io.github.summer.boot.xdatabase.value.DefaultValues;
import io.github.summer.boot.xdatabase.value.PlaceholderValues;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Template
 *
 * @author changebooks@qq.com
 */
public class Template {
    /**
     * SELECT COUNT(*)
     */
    private static final String AGGREGATE = "COUNT(*) AS aggregate";

    /**
     * the {@link Executor} instance
     */
    private final Executor executor;

    /**
     * the {@link SqlParser} instance
     */
    private final SqlParser sqlParser;

    public Template(Executor executor) {
        Preconditions.requireNonNull(executor, "executor must not be null");

        this.executor = executor;
        this.sqlParser = new SqlParserImpl();
    }

    public Template(Executor executor, SqlParser sqlParser) {
        Preconditions.requireNonNull(executor, "executor must not be null");
        Preconditions.requireNonNull(sqlParser, "sqlParser must not be null");

        this.executor = executor;
        this.sqlParser = sqlParser;
    }

    /**
     * SELECT LIST
     *
     * @param tableSchema the {@link TableSchema} instance
     * @param filters     [ the {@link BaseFilter} instance ]
     * @param orders      [ the {@link Order} instance ]
     * @param page        the {@link Page} instance
     * @return [ [ Column Name : Column Value ] ]
     */
    public List<Map<String, Value>> selectList(@NotNull TableSchema tableSchema,
                                               List<BaseFilter> filters, List<Order> orders, Page page) {
        SqlParameter sqlParameter = parseSelect(tableSchema, filters, orders, page);

        List<String> columnNames = tableSchema.getColumnNames();
        Map<String, Integer> valueTypes = tableSchema.getValueTypes();

        Executor executor = getExecutor();
        List<Map<String, Value>> result = executor.selectList(sqlParameter, columnNames, valueTypes);

        writeLogSelectList(tableSchema, filters, orders, page, result);
        return result;
    }

    /**
     * SELECT COUNT
     *
     * @param tableName FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @return AGGREGATE
     */
    public Long selectCount(@NotNull String tableName, List<BaseFilter> filters) {
        SqlParameter sqlParameter = parseSelect(tableName, AGGREGATE, filters, null, null);

        Executor executor = getExecutor();
        Value aggregate = executor.getOne(sqlParameter, ValueType.LONG.code);
        Long result = aggregate != null ? aggregate.getValueLong() : null;

        writeLogSelectCount(tableName, filters, result);
        return result;
    }

    /**
     * SELECT ONE
     *
     * @param tableSchema the {@link TableSchema} instance
     * @param filters     [ the {@link BaseFilter} instance ]
     * @param orders      [ the {@link Order} instance ]
     * @return [ Column Name : Column Value ]
     */
    public Map<String, Value> selectOne(@NotNull TableSchema tableSchema,
                                        List<BaseFilter> filters, List<Order> orders) {
        Page page = new Page();
        page.setLimit(1);

        SqlParameter sqlParameter = parseSelect(tableSchema, filters, orders, page);

        List<String> columnNames = tableSchema.getColumnNames();
        Map<String, Integer> valueTypes = tableSchema.getValueTypes();

        Executor executor = getExecutor();
        Map<String, Value> result = executor.selectOne(sqlParameter, columnNames, valueTypes);

        writeLogSelectOne(tableSchema, filters, orders, result);
        return result;
    }

    /**
     * CHECK EXIST
     *
     * @param tableName FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @return EXIST ? 1 : null
     */
    public Integer checkExist(@NotNull String tableName, List<BaseFilter> filters) {
        Page page = new Page();
        page.setLimit(1);

        SqlParameter sqlParameter = parseSelect(tableName, "1", filters, null, page);

        Executor executor = getExecutor();
        Value exist = executor.getOne(sqlParameter, ValueType.INTEGER.code);
        Integer result = exist != null ? exist.getValueInteger() : null;

        writeLogCheckExist(tableName, filters, result);
        return result;
    }

    /**
     * INSERT
     *
     * @param tableSchema the {@link TableSchema} instance
     * @param values      [ Column Name : Column Value ]
     * @return AFFECTED ROWS
     */
    public int insert(@NotNull TableSchema tableSchema, @NotNull Map<String, Value> values) {
        SqlParameter sqlParameter = new SqlParameter();

        String sql = parseInsert(tableSchema, 1);
        sqlParameter.setSql(sql);

        List<String> parameterNames = tableSchema.getColumnNamesOnInsert();
        sqlParameter.setParameterNames(parameterNames);

        Map<String, Value> parameters = setDefaultValuesOnInsert(tableSchema, values);
        sqlParameter.setParameters(parameters);

        Executor executor = getExecutor();
        int result = executor.update(sqlParameter);

        writeLogInsert(tableSchema, values, result);
        return result;
    }

    /**
     * BATCH INSERT
     *
     * @param tableSchema the {@link TableSchema} instance
     * @param list        [ [ Column Name : Column Value ] ]
     * @return AFFECTED ROWS
     */
    public int batchInsert(@NotNull TableSchema tableSchema, @NotNull List<Map<String, Value>> list) {
        List<Map<String, Value>> parametersList = setDefaultValuesOnInsert(tableSchema, list);
        int batchSize = parametersList.size();
        if (batchSize == 0) {
            return 0;
        }

        SqlParameter sqlParameter = new SqlParameter();

        String sql = parseInsert(tableSchema, batchSize);
        sqlParameter.setSql(sql);

        List<String> parameterNames = tableSchema.getColumnNamesOnInsert();
        sqlParameter.setParameterNames(parameterNames);

        sqlParameter.setParametersList(parametersList);

        Executor executor = getExecutor();
        int result = executor.updateList(sqlParameter);

        writeLogBatchInsert(tableSchema, list, result);
        return result;
    }

    /**
     * UPDATE
     *
     * @param tableName UPDATE table
     * @param sets      [ column = column + 1 ]
     * @param setNames  [ Set Name ]
     * @param setValues [ Set Name : Set Value ]
     * @param filters   [ the {@link BaseFilter} instance ]
     * @return AFFECTED ROWS
     */
    public int update(@NotNull String tableName,
                      List<String> sets, List<String> setNames, Map<String, Value> setValues,
                      List<BaseFilter> filters) {
        SqlParser sqlParser = getSqlParser();
        String joinedSets = joinSets(sets, setNames);
        SqlParameter sqlParameter = sqlParser.parseUpdate(tableName, joinedSets, filters);

        List<String> parameterNames = PlaceholderValues.concatNames(setNames, sqlParameter.getParameterNames());
        Map<String, Value> parameters = PlaceholderValues.concatParameters(setValues, sqlParameter.getParameters());

        sqlParameter.setParameterNames(parameterNames);
        sqlParameter.setParameters(parameters);

        Executor executor = getExecutor();
        int result = executor.update(sqlParameter);

        writeLogUpdate(tableName, sets, setNames, setValues, filters, result);
        return result;
    }

    /**
     * BATCH UPDATE, No Transactional
     *
     * @param tableSchema the {@link TableSchema} instance
     * @param sets        [ column = column + 1 ]
     * @param setNames    [ Set Name ]
     * @param list        [ [ Parameter Name : Parameter Value ] ]
     * @param keyName     Key Name, if null ? Primary Key
     * @return AFFECTED ROWS
     */
    public int[] batchUpdate(@NotNull TableSchema tableSchema,
                             List<String> sets, List<String> setNames,
                             List<Map<String, Value>> list,
                             @Nullable String keyName) {
        String tableName = tableSchema.getTableName();
        String idName = keyName != null ? keyName.trim() : tableSchema.getIdName();
        Preconditions.requireNonEmpty(idName, "idName must not be empty, tableName: " + tableName);

        if (setNames != null) {
            setNames.remove(idName);
        }

        List<String> parameterNames = setNames != null ? new ArrayList<>(setNames) : new ArrayList<>();
        parameterNames.add(idName);

        SqlParser sqlParser = getSqlParser();
        String joinedSets = joinSets(sets, setNames);
        List<BaseFilter> filters = KeyParser.parseList(idName);
        SqlParameter sqlParameter = sqlParser.parseUpdate(tableName, joinedSets, filters);

        sqlParameter.setParameterNames(parameterNames);
        sqlParameter.setParametersList(list);

        Executor executor = getExecutor();
        int[] result = executor.batchUpdate(sqlParameter);

        writeLogBatchUpdate(tableSchema, sets, setNames, list, keyName, result);
        return result;
    }

    /**
     * DELETE
     *
     * @param tableName DELETE FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @return AFFECTED ROWS
     */
    public int delete(@NotNull String tableName, List<BaseFilter> filters) {
        SqlParser sqlParser = getSqlParser();
        SqlParameter sqlParameter = sqlParser.parseDelete(tableName, filters);

        Executor executor = getExecutor();
        int result = executor.update(sqlParameter);

        writeLogDelete(tableName, filters, result);
        return result;
    }

    /**
     * Set Default Values On Insert
     *
     * @param tableSchema the {@link TableSchema} instance
     * @param list        [ [ Parameter Name : Parameter Value ] ]
     * @return [ [ Parameter Name : Parameter Value ] ]
     */
    @NotNull
    public List<Map<String, Value>> setDefaultValuesOnInsert(@NotNull TableSchema tableSchema, List<Map<String, Value>> list) {
        List<String> columnNames = tableSchema.getColumnNamesOnInsert();
        Map<String, Value> defaultValues = tableSchema.getDefaultValues();
        return DefaultValues.setDefaultValues(columnNames, defaultValues, list);
    }

    /**
     * Set Default Values On Insert
     *
     * @param tableSchema the {@link TableSchema} instance
     * @param values      [ Parameter Name : Parameter Value ]
     * @return [ Parameter Name : Parameter Value ]
     */
    @NotNull
    public Map<String, Value> setDefaultValuesOnInsert(@NotNull TableSchema tableSchema, Map<String, Value> values) {
        List<String> columnNames = tableSchema.getColumnNamesOnInsert();
        Map<String, Value> defaultValues = tableSchema.getDefaultValues();
        return DefaultValues.setDefaultValues(columnNames, defaultValues, values);
    }

    /**
     * Join Sets
     *
     * @param sets     [ column = column + 1 ]
     * @param setNames [ Set Name ]
     * @return column = column + 1, column = ?
     */
    @NotNull
    public String joinSets(List<String> sets, List<String> setNames) {
        List<String> result = sets != null ? new ArrayList<>(sets) : new ArrayList<>();

        List<String> setPlaceholders = PlaceholderValues.joinPlaceholder(setNames);
        if (setPlaceholders != null) {
            result.addAll(setPlaceholders);
        }

        return String.join(", ", result);
    }

    /**
     * SELECT column, column FROM table WHERE column = ? ORDER BY name ASC LIMIT offset, limit
     *
     * @param tableSchema the {@link TableSchema} instance
     * @param filters     [ the {@link BaseFilter} instance ]
     * @param orders      [ the {@link Order} instance ]
     * @param page        the {@link Page} instance
     * @return the {@link SqlParameter} instance
     */
    @NotNull
    public SqlParameter parseSelect(@NotNull TableSchema tableSchema,
                                    List<BaseFilter> filters, List<Order> orders, Page page) {
        String tableName = tableSchema.getTableName();
        String columns = tableSchema.getColumns();
        return parseSelect(tableName, columns, filters, orders, page);
    }

    /**
     * SELECT column, column FROM table WHERE column = ? ORDER BY name ASC LIMIT offset, limit
     *
     * @param tableName FROM table
     * @param columns   column, COUNT(*) AS aggregate, 1
     * @param filters   [ the {@link BaseFilter} instance ]
     * @param orders    [ the {@link Order} instance ]
     * @param page      the {@link Page} instance
     * @return the {@link SqlParameter} instance
     */
    @NotNull
    public SqlParameter parseSelect(@NotNull String tableName,
                                    @NotNull String columns,
                                    List<BaseFilter> filters, List<Order> orders, Page page) {
        SqlParser sqlParser = getSqlParser();
        return sqlParser.parseSelect(tableName, false, columns, filters, orders, page);
    }

    /**
     * Parse Insert
     *
     * @param tableSchema the {@link TableSchema} instance
     * @param batchSize   Batch Size
     * @return INSERT INTO table (column, column) VALUES (?, ?), (?, ?), (?, ?)
     */
    @NotNull
    public String parseInsert(@NotNull TableSchema tableSchema, int batchSize) {
        String tableName = tableSchema.getTableName();
        String columns = tableSchema.getColumnsOnInsert();
        String values = tableSchema.getValuesOnInsert();

        SqlParser sqlParser = getSqlParser();
        return sqlParser.parseInsert(tableName, columns, values, batchSize);
    }

    @NotNull
    public Executor getExecutor() {
        return executor;
    }

    @NotNull
    public SqlParser getSqlParser() {
        return sqlParser;
    }

    protected void writeLogSelectList(TableSchema tableSchema, List<BaseFilter> filters, List<Order> orders, Page page, List<Map<String, Value>> result) {
        try {
            LogTemplate logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectList(tableSchema, filters, orders, page, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogSelectCount(String tableName, List<BaseFilter> filters, Long result) {
        try {
            LogTemplate logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectCount(tableName, filters, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogSelectOne(TableSchema tableSchema, List<BaseFilter> filters, List<Order> orders, Map<String, Value> result) {
        try {
            LogTemplate logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectOne(tableSchema, filters, orders, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogCheckExist(String tableName, List<BaseFilter> filters, Integer result) {
        try {
            LogTemplate logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.checkExist(tableName, filters, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogInsert(TableSchema tableSchema, Map<String, Value> values, int result) {
        try {
            LogTemplate logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.insert(tableSchema, values, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogBatchInsert(TableSchema tableSchema, List<Map<String, Value>> list, int result) {
        try {
            LogTemplate logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.batchInsert(tableSchema, list, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogUpdate(String tableName, List<String> sets, List<String> setNames, Map<String, Value> setValues, List<BaseFilter> filters, int result) {
        try {
            LogTemplate logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.update(tableName, sets, setNames, setValues, filters, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogBatchUpdate(TableSchema tableSchema, List<String> sets, List<String> setNames, List<Map<String, Value>> list, String keyName, int[] result) {
        try {
            LogTemplate logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.batchUpdate(tableSchema, sets, setNames, list, keyName, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogDelete(String tableName, List<BaseFilter> filters, int result) {
        try {
            LogTemplate logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.delete(tableName, filters, result);
            }
        } catch (Throwable ignored) {
        }
    }

    /**
     * Log Writer
     *
     * @return the {@link LogTemplate} instance
     */
    @Nullable
    protected LogTemplate getLogWriter() {
        return LogWriter.getTemplate();
    }

}
