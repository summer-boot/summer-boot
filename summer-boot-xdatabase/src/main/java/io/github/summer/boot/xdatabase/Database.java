package io.github.summer.boot.xdatabase;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.filter.Order;
import io.github.summer.boot.filter.Page;
import io.github.summer.boot.value.Value;
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
        try {
            Schema tableSchema = getTableSchema(tableName);
            List<Map<String, Value>> result = template.selectList(tableSchema, filters, orders, page);

            writeLogSelectList(tableName, filters, orders, page, result);
            return result;
        } catch (Throwable ex) {
            writeLogSelectList(tableName, filters, orders, page, ex);
            throw new Database.SelectException(tableName, ex);
        }
    }

    /**
     * SELECT COUNT
     *
     * @param tableName FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @return AGGREGATE
     */
    public long selectCount(@NotNull String tableName, List<BaseFilter> filters) {
        try {
            Long aggregate = template.selectCount(tableName, filters);
            long result = aggregate != null ? aggregate : 0;

            writeLogSelectCount(tableName, filters, result);
            return result;
        } catch (Throwable ex) {
            writeLogSelectCount(tableName, filters, ex);
            throw new Database.SelectException(tableName, ex);
        }
    }

    /**
     * SELECT ONE
     *
     * @param tableName FROM table
     * @param keyValue  Key Value
     * @param keyName   Key Name, if null ? Primary Key
     * @return [ Column Name : Column Value ]
     */
    public Map<String, Value> selectOne(@NotNull String tableName,
                                        @NotNull Value keyValue, @Nullable String keyName) {
        try {
            Schema tableSchema = getTableSchema(tableName);
            List<BaseFilter> filters = parseKey(tableSchema, keyValue, keyName);
            Map<String, Value> result = template.selectOne(tableSchema, filters, null);

            writeLogSelectOne(tableName, keyValue, keyName, result);
            return result;
        } catch (Throwable ex) {
            writeLogSelectOne(tableName, keyValue, keyName, ex);
            throw new Database.SelectException(tableName, ex);
        }
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
        try {
            Schema tableSchema = getTableSchema(tableName);
            Map<String, Value> result = template.selectOne(tableSchema, filters, orders);

            writeLogSelectOne(tableName, filters, orders, result);
            return result;
        } catch (Throwable ex) {
            writeLogSelectOne(tableName, filters, orders, ex);
            throw new Database.SelectException(tableName, ex);
        }
    }

    /**
     * CHECK EXIST
     *
     * @param tableName FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @return EXIST ? true : false
     */
    public boolean checkExist(@NotNull String tableName, List<BaseFilter> filters) {
        try {
            Integer exist = template.checkExist(tableName, filters);
            boolean result = exist != null && exist == 1;

            writeLogCheckExist(tableName, filters, result);
            return result;
        } catch (Throwable ex) {
            writeLogCheckExist(tableName, filters, ex);
            throw new Database.SelectException(tableName, ex);
        }
    }

    /**
     * INSERT
     *
     * @param tableName INSERT INTO table
     * @param values    [ Column Name : Column Value ]
     * @return AFFECTED ROWS
     */
    public int insert(@NotNull String tableName, @NotNull Map<String, Value> values) {
        try {
            Schema tableSchema = getTableSchema(tableName);
            int result = template.insert(tableSchema, values);

            writeLogInsert(tableName, values, result);
            return result;
        } catch (Throwable ex) {
            writeLogInsert(tableName, values, ex);
            throw new Database.InsertException(tableName, ex);
        }
    }

    /**
     * BATCH INSERT
     *
     * @param tableName INSERT INTO table
     * @param list      [ [ Column Name : Column Value ] ]
     * @return AFFECTED ROWS
     */
    public int batchInsert(@NotNull String tableName, @NotNull List<Map<String, Value>> list) {
        try {
            Schema tableSchema = getTableSchema(tableName);
            int result = template.batchInsert(tableSchema, list);

            writeLogBatchInsert(tableName, list, result);
            return result;
        } catch (Throwable ex) {
            writeLogBatchInsert(tableName, list, ex);
            throw new Database.InsertException(tableName, ex);
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
     * @return AFFECTED ROWS
     */
    public int update(@NotNull String tableName,
                      @NotNull Value keyValue,
                      List<String> sets, Map<String, Value> setValues,
                      @Nullable String keyName) {
        try {
            List<BaseFilter> filters = parseKey(tableName, keyValue, keyName);
            int result = update(tableName, sets, setValues, filters);

            writeLogUpdate(tableName, keyValue, sets, setValues, keyName, result);
            return result;
        } catch (Throwable ex) {
            writeLogUpdate(tableName, keyValue, sets, setValues, keyName, ex);
            throw new Database.UpdateException(tableName, ex);
        }
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
        try {
            Schema tableSchema = getTableSchema(tableName);
            List<String> setNames = parseSetNames(tableSchema, setValues);
            int result = template.update(tableName, sets, setNames, setValues, filters);

            writeLogUpdate(tableName, sets, setValues, filters, result);
            return result;
        } catch (Throwable ex) {
            writeLogUpdate(tableName, sets, setValues, filters, ex);
            throw new Database.UpdateException(tableName, ex);
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
     * @return AFFECTED ROWS
     */
    public int[] batchUpdate(@NotNull String tableName,
                             List<String> sets, List<String> setNames,
                             List<Map<String, Value>> list,
                             @Nullable String keyName) {
        try {
            Schema tableSchema = getTableSchema(tableName);
            int[] result = template.batchUpdate(tableSchema, sets, setNames, list, keyName);

            writeLogBatchUpdate(tableName, sets, setNames, list, keyName, result);
            return result;
        } catch (Throwable ex) {
            writeLogBatchUpdate(tableName, sets, setNames, list, keyName, ex);
            throw new Database.UpdateException(tableName, ex);
        }
    }

    /**
     * DELETE
     *
     * @param tableName DELETE FROM table
     * @param keyValue  Key Value
     * @param keyName   Key Name, if null ? Primary Key
     * @return AFFECTED ROWS
     */
    public int delete(@NotNull String tableName, @NotNull Value keyValue, @Nullable String keyName) {
        try {
            List<BaseFilter> filters = parseKey(tableName, keyValue, keyName);
            int result = delete(tableName, filters);

            writeLogDelete(tableName, keyValue, keyName, result);
            return result;
        } catch (Throwable ex) {
            writeLogDelete(tableName, keyValue, keyName, ex);
            throw new Database.DeleteException(tableName, ex);
        }
    }

    /**
     * DELETE
     *
     * @param tableName DELETE FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @return AFFECTED ROWS
     */
    public int delete(@NotNull String tableName, List<BaseFilter> filters) {
        try {
            int result = template.delete(tableName, filters);

            writeLogDelete(tableName, filters, result);
            return result;
        } catch (Throwable ex) {
            writeLogDelete(tableName, filters, ex);
            throw new Database.DeleteException(tableName, ex);
        }
    }

    /**
     * Parse Set Names
     *
     * @param tableSchema the {@link Schema} instance
     * @param setValues   [ Set Name : Set Value ]
     * @return [ Set Name ]
     */
    @NotNull
    public List<String> parseSetNames(@NotNull Schema tableSchema, Map<String, Value> setValues) {
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
     * @return [ the {@link BaseFilter} instance ]
     */
    @NotNull
    public List<BaseFilter> parseKey(@NotNull String tableName, @NotNull Value keyValue, @Nullable String keyName) {
        Schema tableSchema = getTableSchema(tableName);
        return parseKey(tableSchema, keyValue, keyName);
    }

    /**
     * Key = :Key
     *
     * @param tableSchema the {@link Schema} instance
     * @param keyValue    Key Value
     * @param keyName     Key Name, if null ? Primary Key
     * @return [ the {@link BaseFilter} instance ]
     */
    @NotNull
    public List<BaseFilter> parseKey(@NotNull Schema tableSchema, @NotNull Value keyValue, @Nullable String keyName) {
        return KeyParser.parseList(tableSchema, keyValue, keyName);
    }

    /**
     * PRIMARY KEY
     *
     * @param tableName Table Name
     * @return Primary Key Name
     */
    @NotEmpty
    public String getIdName(@NotNull String tableName) {
        Schema tableSchema = getTableSchema(tableName);
        return getIdName(tableSchema);
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
     * SELECT LIST
     *
     * @param tableName FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @param orders    [ the {@link Order} instance ]
     * @param page      the {@link Page} instance
     * @param ex        the {@link Throwable} instance
     */
    protected void writeLogSelectList(String tableName, List<BaseFilter> filters, List<Order> orders, Page page, Throwable ex) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectList(tableName, filters, orders, page, ex);
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
     * SELECT COUNT
     *
     * @param tableName FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @param ex        the {@link Throwable} instance
     */
    protected void writeLogSelectCount(String tableName, List<BaseFilter> filters, Throwable ex) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectCount(tableName, filters, ex);
            }
        } catch (Throwable ignored) {
        }
    }

    /**
     * SELECT ONE
     *
     * @param tableName FROM table
     * @param keyValue  Key Value
     * @param keyName   Key Name
     * @param result    [ Column Name : Column Value ]
     */
    protected void writeLogSelectOne(String tableName, Value keyValue, String keyName, Map<String, Value> result) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectOne(tableName, keyValue, keyName, result);
            }
        } catch (Throwable ignored) {
        }
    }

    /**
     * SELECT ONE
     *
     * @param tableName FROM table
     * @param keyValue  Key Value
     * @param keyName   Key Name
     * @param ex        the {@link Throwable} instance
     */
    protected void writeLogSelectOne(String tableName, Value keyValue, String keyName, Throwable ex) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectOne(tableName, keyValue, keyName, ex);
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
     * SELECT ONE
     *
     * @param tableName FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @param orders    [ the {@link Order} instance ]
     * @param ex        the {@link Throwable} instance
     */
    protected void writeLogSelectOne(String tableName, List<BaseFilter> filters, List<Order> orders, Throwable ex) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectOne(tableName, filters, orders, ex);
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
     * CHECK EXIST
     *
     * @param tableName FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @param ex        the {@link Throwable} instance
     */
    protected void writeLogCheckExist(String tableName, List<BaseFilter> filters, Throwable ex) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.checkExist(tableName, filters, ex);
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
     * INSERT
     *
     * @param tableName INSERT INTO table
     * @param values    [ Column Name : Column Value ]
     * @param ex        the {@link Throwable} instance
     */
    protected void writeLogInsert(String tableName, Map<String, Value> values, Throwable ex) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.insert(tableName, values, ex);
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
     * BATCH INSERT
     *
     * @param tableName INSERT INTO table
     * @param list      [ [ Column Name : Column Value ] ]
     * @param ex        the {@link Throwable} instance
     */
    protected void writeLogBatchInsert(String tableName, List<Map<String, Value>> list, Throwable ex) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.batchInsert(tableName, list, ex);
            }
        } catch (Throwable ignored) {
        }
    }

    /**
     * UPDATE
     *
     * @param tableName UPDATE table
     * @param keyValue  Key Value
     * @param sets      [ column = column + 1 ]
     * @param setValues [ Set Name : Set Value ]
     * @param keyName   Key Name
     * @param result    AFFECTED ROWS
     */
    protected void writeLogUpdate(String tableName, Value keyValue, List<String> sets, Map<String, Value> setValues, String keyName, int result) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.update(tableName, keyValue, sets, setValues, keyName, result);
            }
        } catch (Throwable ignored) {
        }
    }

    /**
     * UPDATE
     *
     * @param tableName UPDATE table
     * @param keyValue  Key Value
     * @param sets      [ column = column + 1 ]
     * @param setValues [ Set Name : Set Value ]
     * @param keyName   Key Name
     * @param ex        the {@link Throwable} instance
     */
    protected void writeLogUpdate(String tableName, Value keyValue, List<String> sets, Map<String, Value> setValues, String keyName, Throwable ex) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.update(tableName, keyValue, sets, setValues, keyName, ex);
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
     * UPDATE
     *
     * @param tableName UPDATE table
     * @param sets      [ column = column + 1 ]
     * @param setValues [ Set Name : Set Value ]
     * @param filters   [ the {@link BaseFilter} instance ]
     * @param ex        the {@link Throwable} instance
     */
    protected void writeLogUpdate(String tableName, List<String> sets, Map<String, Value> setValues, List<BaseFilter> filters, Throwable ex) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.update(tableName, sets, setValues, filters, ex);
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
     * @param keyName   Key Name
     * @param result    AFFECTED ROWS
     */
    protected void writeLogBatchUpdate(String tableName, List<String> sets, List<String> setNames, List<Map<String, Value>> list, String keyName, int[] result) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.batchUpdate(tableName, sets, setNames, list, keyName, result);
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
     * @param keyName   Key Name
     * @param ex        the {@link Throwable} instance
     */
    protected void writeLogBatchUpdate(String tableName, List<String> sets, List<String> setNames, List<Map<String, Value>> list, String keyName, Throwable ex) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.batchUpdate(tableName, sets, setNames, list, keyName, ex);
            }
        } catch (Throwable ignored) {
        }
    }

    /**
     * DELETE
     *
     * @param tableName DELETE FROM table
     * @param keyValue  Key Value
     * @param keyName   Key Name
     * @param result    AFFECTED ROWS
     */
    protected void writeLogDelete(String tableName, Value keyValue, String keyName, int result) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.delete(tableName, keyValue, keyName, result);
            }
        } catch (Throwable ignored) {
        }
    }

    /**
     * DELETE
     *
     * @param tableName DELETE FROM table
     * @param keyValue  Key Value
     * @param keyName   Key Name
     * @param ex        the {@link Throwable} instance
     */
    protected void writeLogDelete(String tableName, Value keyValue, String keyName, Throwable ex) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.delete(tableName, keyValue, keyName, ex);
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
     * DELETE
     *
     * @param tableName DELETE FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @param ex        the {@link Throwable} instance
     */
    protected void writeLogDelete(String tableName, List<BaseFilter> filters, Throwable ex) {
        try {
            LogWriter.Database logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.delete(tableName, filters, ex);
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
