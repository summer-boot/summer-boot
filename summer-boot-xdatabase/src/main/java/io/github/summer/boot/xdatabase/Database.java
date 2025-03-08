package io.github.summer.boot.xdatabase;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.filter.ExpressionFilter;
import io.github.summer.boot.filter.Order;
import io.github.summer.boot.filter.Page;
import io.github.summer.boot.value.Value;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.*;

/**
 * Database
 *
 * @author changebooks@qq.com
 */
public class Database {
    /**
     * the {@link Template} instance
     */
    private final Template template;

    public Database(Template template) {
        Preconditions.requireNonNull(template, "template must not be null");

        this.template = template;
    }

    /**
     * SELECT LIST
     *
     * @param tableName FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @param orders    [ the {@link Order} instance ]
     * @param page      the {@link Page} instance
     * @return [ [ Column Name : Column Value ] ]
     */
    public List<Map<String, Value>> selectList(@NotNull String tableName,
                                               List<BaseFilter> filters, List<Order> orders, Page page) {
        Schema tableSchema = getTableSchema(tableName);
        List<Map<String, Value>> result = template.selectList(tableSchema, filters, orders, page);

        writeLogSelectList(tableName, filters, orders, page, result);
        return result;
    }

    /**
     * SELECT COUNT
     *
     * @param tableName FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @return AGGREGATE
     */
    public long selectCount(@NotNull String tableName, List<BaseFilter> filters) {
        Long aggregate = template.selectCount(tableName, filters);
        long result = aggregate != null ? aggregate : 0;

        writeLogSelectCount(tableName, filters, result);
        return result;
    }

    /**
     * SELECT ONE
     *
     * @param tableName FROM table
     * @param idValue   Primary Key Value
     * @return [ Column Name : Column Value ]
     */
    public Map<String, Value> selectOne(@NotNull String tableName, @NotNull Value idValue) {
        Schema tableSchema = getTableSchema(tableName);
        List<BaseFilter> filters = parseId(tableSchema, idValue);
        Map<String, Value> result = template.selectOne(tableSchema, filters, null);

        writeLogSelectOne(tableName, idValue, result);
        return result;
    }

    /**
     * SELECT ONE
     *
     * @param tableName FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @param orders    [ the {@link Order} instance ]
     * @return [ Column Name : Column Value ]
     */
    public Map<String, Value> selectOne(@NotNull String tableName,
                                        List<BaseFilter> filters, List<Order> orders) {
        Schema tableSchema = getTableSchema(tableName);
        Map<String, Value> result = template.selectOne(tableSchema, filters, orders);

        writeLogSelectOne(tableName, filters, orders, result);
        return result;
    }

    /**
     * CHECK EXIST
     *
     * @param tableName FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @return EXIST ? true : false
     */
    public boolean checkExist(@NotNull String tableName, List<BaseFilter> filters) {
        Integer exist = template.checkExist(tableName, filters);
        boolean result = exist != null && exist == 1;

        writeLogCheckExist(tableName, filters, result);
        return result;
    }

    /**
     * INSERT
     *
     * @param tableName INSERT INTO table
     * @param values    [ Column Name : Column Value ]
     * @return AFFECTED ROWS
     */
    public int insert(@NotNull String tableName, @NotNull Map<String, Value> values) {
        Schema tableSchema = getTableSchema(tableName);
        int result = template.insert(tableSchema, values);

        writeLogInsert(tableName, values, result);
        return result;
    }

    /**
     * BATCH INSERT
     *
     * @param tableName INSERT INTO table
     * @param list      [ [ Column Name : Column Value ] ]
     * @return AFFECTED ROWS
     */
    public int batchInsert(@NotNull String tableName, @NotNull List<Map<String, Value>> list) {
        Schema tableSchema = getTableSchema(tableName);
        int result = template.batchInsert(tableSchema, list);

        writeLogBatchInsert(tableName, list, result);
        return result;
    }

    /**
     * UPDATE
     *
     * @param tableName UPDATE table
     * @param idValue   Primary Key Value
     * @param sets      [ column = column + 1 ]
     * @param setValues [ Set Name : Set Value ]
     * @return AFFECTED ROWS
     */
    public int update(@NotNull String tableName,
                      @NotNull Value idValue,
                      List<String> sets, Map<String, Value> setValues) {
        Schema tableSchema = getTableSchema(tableName);
        List<BaseFilter> filters = parseId(tableSchema, idValue);
        int result = update(tableName, sets, setValues, filters);

        writeLogUpdate(tableName, idValue, sets, setValues, result);
        return result;
    }

    /**
     * UPDATE
     *
     * @param tableName UPDATE table
     * @param sets      [ column = column + 1 ]
     * @param setValues [ Set Name : Set Value ]
     * @param filters   [ the {@link BaseFilter} instance ]
     * @return AFFECTED ROWS
     */
    public int update(@NotNull String tableName,
                      List<String> sets, Map<String, Value> setValues,
                      List<BaseFilter> filters) {
        Schema tableSchema = getTableSchema(tableName);
        Set<String> columnNames = tableSchema.getColumnNamesOnUpdate();

        List<String> setNames = new ArrayList<>();
        if (setValues != null) {
            for (Map.Entry<String, Value> entry : setValues.entrySet()) {
                if (entry == null) {
                    continue;
                }

                String columnName = entry.getKey();
                if (columnName == null) {
                    continue;
                }

                Value value = entry.getValue();
                if (value == null) {
                    continue;
                }

                if (columnNames.contains(columnName)) {
                    setNames.add(columnName);
                }
            }
        }

        int result = template.update(tableName, sets, setNames, setValues, filters);

        writeLogUpdate(tableName, sets, setValues, filters, result);
        return result;
    }

    /**
     * BATCH UPDATE, No Transactional
     *
     * @param tableName UPDATE table
     * @param sets      [ column = column + 1 ]
     * @param setNames  [ Set Name ]
     * @param list      [ [ Parameter Name : Parameter Value ] ]
     * @return AFFECTED ROWS
     */
    public int[] batchUpdate(@NotNull String tableName,
                             List<String> sets, List<String> setNames,
                             List<Map<String, Value>> list) {
        Schema tableSchema = getTableSchema(tableName);
        int[] result = template.batchUpdate(tableSchema, sets, setNames, list);

        writeLogBatchUpdate(tableName, sets, setNames, list, result);
        return result;
    }

    /**
     * DELETE
     *
     * @param tableName DELETE FROM table
     * @param idValue   Primary Key Value
     * @return AFFECTED ROWS
     */
    public int delete(@NotNull String tableName, @NotNull Value idValue) {
        Schema tableSchema = getTableSchema(tableName);
        List<BaseFilter> filters = parseId(tableSchema, idValue);
        int result = delete(tableName, filters);

        writeLogDelete(tableName, idValue, result);
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
        int result = template.delete(tableName, filters);

        writeLogDelete(tableName, filters, result);
        return result;
    }

    /**
     * PARSE PRIMARY KEY
     *
     * @param tableSchema the {@link Schema} instance
     * @param idValue     Primary Key Value
     * @return [ the {@link BaseFilter} instance ]
     */
    @NotNull
    public List<BaseFilter> parseId(@NotNull Schema tableSchema, @NotNull Value idValue) {
        Template template = getTemplate();

        String idName = getIdName(tableSchema);
        ExpressionFilter filter = template.parseId(idName, idValue);

        return Collections.singletonList(filter);
    }

    /**
     * PARSE PRIMARY KEY
     *
     * @param tableSchema the {@link Schema} instance
     * @return [ the {@link BaseFilter} instance ]
     */
    @NotNull
    public List<BaseFilter> parseId(@NotNull Schema tableSchema) {
        Template template = getTemplate();

        String idName = getIdName(tableSchema);
        ExpressionFilter filter = template.parseId(idName);

        return Collections.singletonList(filter);
    }

    /**
     * PRIMARY KEY
     *
     * @param tableSchema the {@link Schema} instance
     * @return Primary Key Name
     */
    @NotEmpty
    public String getIdName(@NotNull Schema tableSchema) {
        String idName = tableSchema.getIdName();
        Preconditions.requireNonEmpty(idName, "idName must not be empty, tableName: " + tableSchema.getTableName());
        return idName;
    }

    /**
     * TABLE NAME to TABLE SCHEMA
     *
     * @param tableName Table Name
     * @return the {@link Schema} instance
     */
    @NotNull
    public Schema getTableSchema(@NotNull String tableName) {
        Schema tableSchema = SchemaRegistry.get(tableName);
        Preconditions.requireNonNull(tableSchema, "tableSchema must not be null, tableName: " + tableName);
        return tableSchema;
    }

    @NotNull
    public Template getTemplate() {
        return template;
    }

    /**
     * SELECT LIST
     *
     * @param tableName FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @param orders    [ the {@link Order} instance ]
     * @param page      the {@link Page} instance
     * @param result    [ [ Column Name : Column Value ] ]
     */
    protected void writeLogSelectList(String tableName, List<BaseFilter> filters, List<Order> orders, Page page, List<Map<String, Value>> result) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectList(tableName, filters, orders, page, result);
            }
        } catch (Throwable ignored) {
        }
    }

    /**
     * SELECT COUNT
     *
     * @param tableName FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @param result    AGGREGATE
     */
    protected void writeLogSelectCount(String tableName, List<BaseFilter> filters, long result) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectCount(tableName, filters, result);
            }
        } catch (Throwable ignored) {
        }
    }

    /**
     * SELECT ONE
     *
     * @param tableName FROM table
     * @param idValue   Primary Key Value
     * @param result    [ Column Name : Column Value ]
     */
    protected void writeLogSelectOne(String tableName, Value idValue, Map<String, Value> result) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectOne(tableName, idValue, result);
            }
        } catch (Throwable ignored) {
        }
    }

    /**
     * SELECT ONE
     *
     * @param tableName FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @param orders    [ the {@link Order} instance ]
     * @param result    [ Column Name : Column Value ]
     */
    protected void writeLogSelectOne(String tableName, List<BaseFilter> filters, List<Order> orders, Map<String, Value> result) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectOne(tableName, filters, orders, result);
            }
        } catch (Throwable ignored) {
        }
    }

    /**
     * CHECK EXIST
     *
     * @param tableName FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @param result    EXIST ? true : false
     */
    protected void writeLogCheckExist(String tableName, List<BaseFilter> filters, boolean result) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.checkExist(tableName, filters, result);
            }
        } catch (Throwable ignored) {
        }
    }

    /**
     * INSERT
     *
     * @param tableName INSERT INTO table
     * @param values    [ Column Name : Column Value ]
     * @param result    AFFECTED ROWS
     */
    protected void writeLogInsert(String tableName, Map<String, Value> values, int result) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.insert(tableName, values, result);
            }
        } catch (Throwable ignored) {
        }
    }

    /**
     * BATCH INSERT
     *
     * @param tableName INSERT INTO table
     * @param list      [ [ Column Name : Column Value ] ]
     * @param result    AFFECTED ROWS
     */
    protected void writeLogBatchInsert(String tableName, List<Map<String, Value>> list, int result) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.batchInsert(tableName, list, result);
            }
        } catch (Throwable ignored) {
        }
    }

    /**
     * UPDATE
     *
     * @param tableName UPDATE table
     * @param idValue   Primary Key Value
     * @param sets      [ column = column + 1 ]
     * @param setValues [ Set Name : Set Value ]
     * @param result    AFFECTED ROWS
     */
    protected void writeLogUpdate(String tableName, Value idValue, List<String> sets, Map<String, Value> setValues, int result) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.update(tableName, idValue, sets, setValues, result);
            }
        } catch (Throwable ignored) {
        }
    }

    /**
     * UPDATE
     *
     * @param tableName UPDATE table
     * @param sets      [ column = column + 1 ]
     * @param setValues [ Set Name : Set Value ]
     * @param filters   [ the {@link BaseFilter} instance ]
     * @param result    AFFECTED ROWS
     */
    protected void writeLogUpdate(String tableName, List<String> sets, Map<String, Value> setValues, List<BaseFilter> filters, int result) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.update(tableName, sets, setValues, filters, result);
            }
        } catch (Throwable ignored) {
        }
    }

    /**
     * BATCH UPDATE
     *
     * @param tableName UPDATE table
     * @param sets      [ column = column + 1 ]
     * @param setNames  [ Set Name ]
     * @param list      [ [ Parameter Name : Parameter Value ] ]
     * @param result    AFFECTED ROWS
     */
    protected void writeLogBatchUpdate(String tableName, List<String> sets, List<String> setNames, List<Map<String, Value>> list, int[] result) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.batchUpdate(tableName, sets, setNames, list, result);
            }
        } catch (Throwable ignored) {
        }
    }

    /**
     * DELETE
     *
     * @param tableName DELETE FROM table
     * @param idValue   Primary Key Value
     * @param result    AFFECTED ROWS
     */
    protected void writeLogDelete(String tableName, Value idValue, int result) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.delete(tableName, idValue, result);
            }
        } catch (Throwable ignored) {
        }
    }

    /**
     * DELETE
     *
     * @param tableName DELETE FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @param result    AFFECTED ROWS
     */
    protected void writeLogDelete(String tableName, List<BaseFilter> filters, int result) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.delete(tableName, filters, result);
            }
        } catch (Throwable ignored) {
        }
    }

    /**
     * Log Writer
     *
     * @return the {@link LogWriter.Database} instance
     */
    @Nullable
    protected LogWriter.Database getLogWriter() {
        return LogWriter.getDatabase();
    }

}
