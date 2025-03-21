package io.github.summer.boot.xdatabase;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.filter.Order;
import io.github.summer.boot.filter.Page;
import io.github.summer.boot.sql.Preconditions;
import io.github.summer.boot.value.Value;
import io.github.summer.boot.xdatabase.logger.LogDatabase;
import io.github.summer.boot.xdatabase.schema.TableSchema;
import io.github.summer.boot.xdatabase.schema.TableSchemaRegistry;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    /**
     * the {@link LogDatabase} instance
     */
    private LogDatabase logWriter;

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
     * @param tableNum  Sharding Table Num
     * @return [ [ Column Name : Column Value ] ]
     */
    public List<Map<String, Value>> selectList(@NotNull String tableName,
                                               List<BaseFilter> filters, List<Order> orders, Page page,
                                               @Nullable Integer tableNum) {
        try {
            TableSchema tableSchema = getTableSchema(tableName, tableNum);
            List<Map<String, Value>> result = template.selectList(tableSchema, filters, orders, page);

            writeLogSelectList(tableName, filters, orders, page, tableNum, result);
            return result;
        } catch (Throwable ex) {
            writeLogSelectList(tableName, filters, orders, page, tableNum, ex);
            throw new SelectException(tableName, ex);
        }
    }

    /**
     * SELECT COUNT
     *
     * @param tableName FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @param tableNum  Sharding Table Num
     * @return AGGREGATE
     */
    public long selectCount(@NotNull String tableName, List<BaseFilter> filters, @Nullable Integer tableNum) {
        try {
            String joinedTableName = joinTableName(tableName, tableNum);
            Long aggregate = template.selectCount(joinedTableName, filters);
            long result = aggregate != null ? aggregate : 0;

            writeLogSelectCount(tableName, filters, tableNum, result);
            return result;
        } catch (Throwable ex) {
            writeLogSelectCount(tableName, filters, tableNum, ex);
            throw new SelectException(tableName, ex);
        }
    }

    /**
     * SELECT ONE
     *
     * @param tableName FROM table
     * @param keyValue  Key Value
     * @param keyName   Key Name, if null ? Primary Key
     * @param tableNum  Sharding Table Num
     * @return [ Column Name : Column Value ]
     */
    public Map<String, Value> selectOne(@NotNull String tableName,
                                        @NotNull Value keyValue, @Nullable String keyName,
                                        @Nullable Integer tableNum) {
        try {
            TableSchema tableSchema = getTableSchema(tableName, tableNum);
            List<BaseFilter> filters = parseKey(tableSchema, keyValue, keyName);
            Map<String, Value> result = template.selectOne(tableSchema, filters, null);

            writeLogSelectOne(tableName, keyValue, keyName, tableNum, result);
            return result;
        } catch (Throwable ex) {
            writeLogSelectOne(tableName, keyValue, keyName, tableNum, ex);
            throw new SelectException(tableName, ex);
        }
    }

    /**
     * SELECT ONE
     *
     * @param tableName FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @param orders    [ the {@link Order} instance ]
     * @param tableNum  Sharding Table Num
     * @return [ Column Name : Column Value ]
     */
    public Map<String, Value> selectOne(@NotNull String tableName,
                                        List<BaseFilter> filters, List<Order> orders,
                                        @Nullable Integer tableNum) {
        try {
            TableSchema tableSchema = getTableSchema(tableName, tableNum);
            Map<String, Value> result = template.selectOne(tableSchema, filters, orders);

            writeLogSelectOne(tableName, filters, orders, tableNum, result);
            return result;
        } catch (Throwable ex) {
            writeLogSelectOne(tableName, filters, orders, tableNum, ex);
            throw new SelectException(tableName, ex);
        }
    }

    /**
     * CHECK EXIST
     *
     * @param tableName FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @param tableNum  Sharding Table Num
     * @return EXIST ? true : false
     */
    public boolean checkExist(@NotNull String tableName, List<BaseFilter> filters, @Nullable Integer tableNum) {
        try {
            String joinedTableName = joinTableName(tableName, tableNum);
            Integer exist = template.checkExist(joinedTableName, filters);
            boolean result = exist != null && exist == 1;

            writeLogCheckExist(tableName, filters, tableNum, result);
            return result;
        } catch (Throwable ex) {
            writeLogCheckExist(tableName, filters, tableNum, ex);
            throw new SelectException(tableName, ex);
        }
    }

    /**
     * INSERT
     *
     * @param tableName INSERT INTO table
     * @param values    [ Column Name : Column Value ]
     * @param tableNum  Sharding Table Num
     * @return AFFECTED ROWS
     */
    public int insert(@NotNull String tableName, @NotNull Map<String, Value> values, @Nullable Integer tableNum) {
        try {
            TableSchema tableSchema = getTableSchema(tableName, tableNum);
            int result = template.insert(tableSchema, values);

            writeLogInsert(tableName, values, tableNum, result);
            return result;
        } catch (Throwable ex) {
            writeLogInsert(tableName, values, tableNum, ex);
            throw new InsertException(tableName, ex);
        }
    }

    /**
     * BATCH INSERT
     *
     * @param tableName INSERT INTO table
     * @param list      [ [ Column Name : Column Value ] ]
     * @param tableNum  Sharding Table Num
     * @return AFFECTED ROWS
     */
    public int batchInsert(@NotNull String tableName, @NotNull List<Map<String, Value>> list, @Nullable Integer tableNum) {
        try {
            TableSchema tableSchema = getTableSchema(tableName, tableNum);
            int result = template.batchInsert(tableSchema, list);

            writeLogBatchInsert(tableName, list, tableNum, result);
            return result;
        } catch (Throwable ex) {
            writeLogBatchInsert(tableName, list, tableNum, ex);
            throw new InsertException(tableName, ex);
        }
    }

    /**
     * UPDATE
     *
     * @param tableName UPDATE table
     * @param keyValue  Key Value
     * @param sets      [ column = column + 1 ]
     * @param setValues [ Set Name : Set Value ]
     * @param keyName   Key Name, if null ? Primary Key
     * @param tableNum  Sharding Table Num
     * @return AFFECTED ROWS
     */
    public int update(@NotNull String tableName,
                      @NotNull Value keyValue,
                      List<String> sets, Map<String, Value> setValues,
                      @Nullable String keyName,
                      @Nullable Integer tableNum) {
        try {
            List<BaseFilter> filters = parseKey(tableName, keyValue, keyName, tableNum);
            int result = update(tableName, sets, setValues, filters, tableNum);

            writeLogUpdate(tableName, keyValue, sets, setValues, keyName, tableNum, result);
            return result;
        } catch (Throwable ex) {
            writeLogUpdate(tableName, keyValue, sets, setValues, keyName, tableNum, ex);
            throw new UpdateException(tableName, ex);
        }
    }

    /**
     * UPDATE
     *
     * @param tableName UPDATE table
     * @param sets      [ column = column + 1 ]
     * @param setValues [ Set Name : Set Value ]
     * @param filters   [ the {@link BaseFilter} instance ]
     * @param tableNum  Sharding Table Num
     * @return AFFECTED ROWS
     */
    public int update(@NotNull String tableName,
                      List<String> sets, Map<String, Value> setValues,
                      List<BaseFilter> filters,
                      @Nullable Integer tableNum) {
        try {
            TableSchema tableSchema = getTableSchema(tableName, tableNum);
            List<String> setNames = parseSetNames(tableSchema, setValues);
            int result = template.update(tableName, sets, setNames, setValues, filters);

            writeLogUpdate(tableName, sets, setValues, filters, tableNum, result);
            return result;
        } catch (Throwable ex) {
            writeLogUpdate(tableName, sets, setValues, filters, tableNum, ex);
            throw new UpdateException(tableName, ex);
        }
    }

    /**
     * BATCH UPDATE, No Transactional
     *
     * @param tableName UPDATE table
     * @param sets      [ column = column + 1 ]
     * @param setNames  [ Set Name ]
     * @param list      [ [ Parameter Name : Parameter Value ] ]
     * @param keyName   Key Name, if null ? Primary Key
     * @param tableNum  Sharding Table Num
     * @return AFFECTED ROWS
     */
    public int[] batchUpdate(@NotNull String tableName,
                             List<String> sets, List<String> setNames,
                             List<Map<String, Value>> list,
                             @Nullable String keyName,
                             @Nullable Integer tableNum) {
        try {
            TableSchema tableSchema = getTableSchema(tableName, tableNum);
            int[] result = template.batchUpdate(tableSchema, sets, setNames, list, keyName);

            writeLogBatchUpdate(tableName, sets, setNames, list, keyName, tableNum, result);
            return result;
        } catch (Throwable ex) {
            writeLogBatchUpdate(tableName, sets, setNames, list, keyName, tableNum, ex);
            throw new UpdateException(tableName, ex);
        }
    }

    /**
     * DELETE
     *
     * @param tableName DELETE FROM table
     * @param keyValue  Key Value
     * @param keyName   Key Name, if null ? Primary Key
     * @param tableNum  Sharding Table Num
     * @return AFFECTED ROWS
     */
    public int delete(@NotNull String tableName, @NotNull Value keyValue, @Nullable String keyName, @Nullable Integer tableNum) {
        try {
            List<BaseFilter> filters = parseKey(tableName, keyValue, keyName, tableNum);
            int result = delete(tableName, filters, tableNum);

            writeLogDelete(tableName, keyValue, keyName, tableNum, result);
            return result;
        } catch (Throwable ex) {
            writeLogDelete(tableName, keyValue, keyName, tableNum, ex);
            throw new DeleteException(tableName, ex);
        }
    }

    /**
     * DELETE
     *
     * @param tableName DELETE FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @param tableNum  Sharding Table Num
     * @return AFFECTED ROWS
     */
    public int delete(@NotNull String tableName, List<BaseFilter> filters, @Nullable Integer tableNum) {
        try {
            String joinedTableName = joinTableName(tableName, tableNum);
            int result = template.delete(joinedTableName, filters);

            writeLogDelete(tableName, filters, tableNum, result);
            return result;
        } catch (Throwable ex) {
            writeLogDelete(tableName, filters, tableNum, ex);
            throw new DeleteException(tableName, ex);
        }
    }

    /**
     * Parse Set Names
     *
     * @param tableSchema the {@link TableSchema} instance
     * @param setValues   [ Set Name : Set Value ]
     * @return [ Set Name ]
     */
    @NotNull
    public List<String> parseSetNames(@NotNull TableSchema tableSchema, Map<String, Value> setValues) {
        List<String> result = new ArrayList<>();
        if (setValues == null) {
            return result;
        }

        Set<String> columnNames = tableSchema.getColumnNamesOnUpdate();
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
                result.add(columnName);
            }
        }

        return result;
    }

    /**
     * Key = :Key
     *
     * @param tableName Table Name
     * @param keyValue  Key Value
     * @param keyName   Key Name, if null ? Primary Key
     * @param tableNum  Sharding Table Num
     * @return [ the {@link BaseFilter} instance ]
     */
    @NotNull
    public List<BaseFilter> parseKey(@NotNull String tableName, @NotNull Value keyValue, @Nullable String keyName, @Nullable Integer tableNum) {
        TableSchema tableSchema = getTableSchema(tableName, tableNum);
        return parseKey(tableSchema, keyValue, keyName);
    }

    /**
     * Key = :Key
     *
     * @param tableSchema the {@link TableSchema} instance
     * @param keyValue    Key Value
     * @param keyName     Key Name, if null ? Primary Key
     * @return [ the {@link BaseFilter} instance ]
     */
    @NotNull
    public List<BaseFilter> parseKey(@NotNull TableSchema tableSchema, @NotNull Value keyValue, @Nullable String keyName) {
        return KeyParser.parseList(tableSchema, keyValue, keyName);
    }

    /**
     * PRIMARY KEY
     *
     * @param tableName Table Name
     * @param tableNum  Sharding Table Num
     * @return Primary Key Name
     */
    @NotEmpty
    public String getIdName(@NotNull String tableName, @Nullable Integer tableNum) {
        TableSchema tableSchema = getTableSchema(tableName, tableNum);
        return getIdName(tableSchema);
    }

    /**
     * PRIMARY KEY
     *
     * @param tableSchema the {@link TableSchema} instance
     * @return Primary Key Name
     */
    @NotEmpty
    public String getIdName(@NotNull TableSchema tableSchema) {
        String idName = tableSchema.getIdName();
        Preconditions.requireNonEmpty(idName, "idName must not be empty, tableName: " + tableSchema.getTableName());
        return idName;
    }

    /**
     * TABLE NAME to TABLE SCHEMA
     *
     * @param tableName Table Name
     * @param tableNum  Sharding Table Num
     * @return the {@link TableSchema} instance
     */
    @NotNull
    public TableSchema getTableSchema(@NotNull String tableName, @Nullable Integer tableNum) {
        String joinedTableName = joinTableName(tableName, tableNum);
        TableSchema tableSchema = TableSchemaRegistry.get(joinedTableName);
        Preconditions.requireNonNull(tableSchema, "tableSchema must not be null, tableName: " + joinedTableName);
        return tableSchema;
    }

    /**
     * Join Table Name And Sharding Table Num
     *
     * @param tableName Table Name
     * @param tableNum  Sharding Table Num
     * @return the {@link TableSchema} instance
     */
    @NotNull
    public String joinTableName(@NotNull String tableName, @Nullable Integer tableNum) {
        if (tableNum == null) {
            return tableName;
        } else {
            return tableName + "_" + tableNum;
        }
    }

    @NotNull
    public Template getTemplate() {
        return template;
    }

    protected void writeLogSelectList(String tableName, List<BaseFilter> filters, List<Order> orders, Page page, Integer tableNum, List<Map<String, Value>> result) {
        try {
            LogDatabase logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectList(tableName, filters, orders, page, tableNum, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogSelectList(String tableName, List<BaseFilter> filters, List<Order> orders, Page page, Integer tableNum, Throwable ex) {
        try {
            LogDatabase logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectList(tableName, filters, orders, page, tableNum, ex);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogSelectCount(String tableName, List<BaseFilter> filters, Integer tableNum, long result) {
        try {
            LogDatabase logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectCount(tableName, filters, tableNum, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogSelectCount(String tableName, List<BaseFilter> filters, Integer tableNum, Throwable ex) {
        try {
            LogDatabase logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectCount(tableName, filters, tableNum, ex);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogSelectOne(String tableName, Value keyValue, String keyName, Integer tableNum, Map<String, Value> result) {
        try {
            LogDatabase logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectOne(tableName, keyValue, keyName, tableNum, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogSelectOne(String tableName, Value keyValue, String keyName, Integer tableNum, Throwable ex) {
        try {
            LogDatabase logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectOne(tableName, keyValue, keyName, tableNum, ex);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogSelectOne(String tableName, List<BaseFilter> filters, List<Order> orders, Integer tableNum, Map<String, Value> result) {
        try {
            LogDatabase logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectOne(tableName, filters, orders, tableNum, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogSelectOne(String tableName, List<BaseFilter> filters, List<Order> orders, Integer tableNum, Throwable ex) {
        try {
            LogDatabase logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectOne(tableName, filters, orders, tableNum, ex);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogCheckExist(String tableName, List<BaseFilter> filters, Integer tableNum, boolean result) {
        try {
            LogDatabase logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.checkExist(tableName, filters, tableNum, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogCheckExist(String tableName, List<BaseFilter> filters, Integer tableNum, Throwable ex) {
        try {
            LogDatabase logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.checkExist(tableName, filters, tableNum, ex);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogInsert(String tableName, Map<String, Value> values, Integer tableNum, int result) {
        try {
            LogDatabase logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.insert(tableName, values, tableNum, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogInsert(String tableName, Map<String, Value> values, Integer tableNum, Throwable ex) {
        try {
            LogDatabase logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.insert(tableName, values, tableNum, ex);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogBatchInsert(String tableName, List<Map<String, Value>> list, Integer tableNum, int result) {
        try {
            LogDatabase logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.batchInsert(tableName, list, tableNum, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogBatchInsert(String tableName, List<Map<String, Value>> list, Integer tableNum, Throwable ex) {
        try {
            LogDatabase logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.batchInsert(tableName, list, tableNum, ex);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogUpdate(String tableName, Value keyValue, List<String> sets, Map<String, Value> setValues, String keyName, Integer tableNum, int result) {
        try {
            LogDatabase logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.update(tableName, keyValue, sets, setValues, keyName, tableNum, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogUpdate(String tableName, Value keyValue, List<String> sets, Map<String, Value> setValues, String keyName, Integer tableNum, Throwable ex) {
        try {
            LogDatabase logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.update(tableName, keyValue, sets, setValues, keyName, tableNum, ex);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogUpdate(String tableName, List<String> sets, Map<String, Value> setValues, List<BaseFilter> filters, Integer tableNum, int result) {
        try {
            LogDatabase logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.update(tableName, sets, setValues, filters, tableNum, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogUpdate(String tableName, List<String> sets, Map<String, Value> setValues, List<BaseFilter> filters, Integer tableNum, Throwable ex) {
        try {
            LogDatabase logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.update(tableName, sets, setValues, filters, tableNum, ex);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogBatchUpdate(String tableName, List<String> sets, List<String> setNames, List<Map<String, Value>> list, String keyName, Integer tableNum, int[] result) {
        try {
            LogDatabase logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.batchUpdate(tableName, sets, setNames, list, keyName, tableNum, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogBatchUpdate(String tableName, List<String> sets, List<String> setNames, List<Map<String, Value>> list, String keyName, Integer tableNum, Throwable ex) {
        try {
            LogDatabase logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.batchUpdate(tableName, sets, setNames, list, keyName, tableNum, ex);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogDelete(String tableName, Value keyValue, String keyName, Integer tableNum, int result) {
        try {
            LogDatabase logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.delete(tableName, keyValue, keyName, tableNum, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogDelete(String tableName, Value keyValue, String keyName, Integer tableNum, Throwable ex) {
        try {
            LogDatabase logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.delete(tableName, keyValue, keyName, tableNum, ex);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogDelete(String tableName, List<BaseFilter> filters, Integer tableNum, int result) {
        try {
            LogDatabase logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.delete(tableName, filters, tableNum, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogDelete(String tableName, List<BaseFilter> filters, Integer tableNum, Throwable ex) {
        try {
            LogDatabase logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.delete(tableName, filters, tableNum, ex);
            }
        } catch (Throwable ignored) {
        }
    }

    @Nullable
    public LogDatabase getLogWriter() {
        return logWriter;
    }

    public void setLogWriter(@Nullable LogDatabase logWriter) {
        this.logWriter = logWriter;
    }

    /**
     * 查询异常
     */
    public static class SelectException extends RuntimeException {

        private final String tableName;

        public SelectException(String tableName, Throwable cause) {
            super(cause);
            this.tableName = tableName;
        }

        public String getTableName() {
            return tableName;
        }

    }

    /**
     * 新增异常
     */
    public static class InsertException extends RuntimeException {

        private final String tableName;

        public InsertException(String tableName, Throwable cause) {
            super(cause);
            this.tableName = tableName;
        }

        public String getTableName() {
            return tableName;
        }

    }

    /**
     * 修改异常
     */
    public static class UpdateException extends RuntimeException {

        private final String tableName;

        public UpdateException(String tableName, Throwable cause) {
            super(cause);
            this.tableName = tableName;
        }

        public String getTableName() {
            return tableName;
        }

    }

    /**
     * 删除异常
     */
    public static class DeleteException extends RuntimeException {

        private final String tableName;

        public DeleteException(String tableName, Throwable cause) {
            super(cause);
            this.tableName = tableName;
        }

        public String getTableName() {
            return tableName;
        }

    }

}
