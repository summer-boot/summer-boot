package io.github.summer.boot.xrepository;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.sql.Order;
import io.github.summer.boot.sql.Page;
import io.github.summer.boot.sql.Preconditions;
import io.github.summer.boot.value.Value;
import io.github.summer.boot.xdatabase.Database;
import io.github.summer.boot.xrepository.key.KeyRegistry;
import io.github.summer.boot.xrepository.logger.LogRepository;
import io.github.summer.boot.xrepository.sharding.ShardingRegistry;
import io.github.summer.boot.xrepository.sharding.ShardingValue;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

/**
 * Repository
 *
 * @author changebooks@qq.com
 */
public class Repository {
    /**
     * the {@link Database} instance
     */
    private final Database database;

    /**
     * the {@link Cache} instance
     */
    private final Cache cache;

    /**
     * the {@link LogRepository} instance
     */
    private LogRepository logWriter;

    public Repository(Database database, Cache cache) {
        Preconditions.requireNonNull(database, "database must not be null");
        Preconditions.requireNonNull(cache, "cache must not be null");

        this.database = database;
        this.cache = cache;
    }

    /**
     * SELECT LIST
     *
     * @param tableName     FROM table
     * @param filters       [ the {@link BaseFilter} instance ]
     * @param orders        [ the {@link Order} instance ]
     * @param page          the {@link Page} instance
     * @param shardingValue Sharding Value
     * @return [ [ Column Name : Column Value ] ]
     */
    public List<Map<String, Value>> selectList(@NotNull String tableName,
                                               List<BaseFilter> filters, List<Order> orders, Page page,
                                               @Nullable Value shardingValue) {
        Database database = getDatabase();

        Integer tableNum = getTableNum(tableName, shardingValue);
        List<Map<String, Value>> result = database.selectList(tableName, filters, orders, page, tableNum);

        writeLogSelectList(tableName, filters, orders, page, shardingValue, result);
        return result;
    }

    /**
     * SELECT COUNT
     *
     * @param tableName     FROM table
     * @param filters       [ the {@link BaseFilter} instance ]
     * @param shardingValue Sharding Value
     * @return AGGREGATE
     */
    public long selectCount(@NotNull String tableName, List<BaseFilter> filters, @Nullable Value shardingValue) {
        Database database = getDatabase();

        Integer tableNum = getTableNum(tableName, shardingValue);
        long result = database.selectCount(tableName, filters, tableNum);

        writeLogSelectCount(tableName, filters, shardingValue, result);
        return result;
    }

    /**
     * SELECT ONE
     *
     * @param tableName FROM table
     * @param keyValue  Key Value
     * @return [ Column Name : Column Value ]
     */
    public Map<String, Value> selectOne(@NotNull String tableName, @NotNull Value keyValue) {
        Cache cache = getCache();
        Map<String, Value> cachedResult = cache.get(tableName, keyValue);
        if (cachedResult != null) {
            writeLogSelectOne(tableName, keyValue, cachedResult, true);
            return cachedResult;
        }

        Database database = getDatabase();

        String keyName = getKeyName(tableName);
        Integer tableNum = getTableNum(tableName, keyValue);
        Map<String, Value> result = database.selectOne(tableName, keyValue, keyName, tableNum);
        if (result != null) {
            cache.set(tableName, keyValue, result);
        }

        writeLogSelectOne(tableName, keyValue, result, false);
        return result;
    }

    /**
     * SELECT ONE
     *
     * @param tableName     FROM table
     * @param filters       [ the {@link BaseFilter} instance ]
     * @param orders        [ the {@link Order} instance ]
     * @param shardingValue Sharding Value
     * @return [ Column Name : Column Value ]
     */
    public Map<String, Value> selectOne(@NotNull String tableName,
                                        List<BaseFilter> filters, List<Order> orders,
                                        @Nullable Value shardingValue) {
        Database database = getDatabase();

        Integer tableNum = getTableNum(tableName, shardingValue);
        Map<String, Value> result = database.selectOne(tableName, filters, orders, tableNum);

        writeLogSelectOne(tableName, filters, orders, shardingValue, result);
        return result;
    }

    /**
     * CHECK EXIST
     *
     * @param tableName     FROM table
     * @param filters       [ the {@link BaseFilter} instance ]
     * @param shardingValue Sharding Value
     * @return EXIST ? true : false
     */
    public boolean checkExist(@NotNull String tableName, List<BaseFilter> filters, @Nullable Value shardingValue) {
        Database database = getDatabase();

        Integer tableNum = getTableNum(tableName, shardingValue);
        boolean result = database.checkExist(tableName, filters, tableNum);

        writeLogCheckExist(tableName, filters, shardingValue, result);
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
        Database database = getDatabase();

        Value shardingValue = getShardingValue(tableName, values);
        Integer tableNum = getTableNum(tableName, shardingValue);
        int result = database.insert(tableName, values, tableNum);

        writeLogInsert(tableName, values, result);
        return result;
    }

    /**
     * BATCH INSERT
     *
     * @param tableName     INSERT INTO table
     * @param list          [ [ Column Name : Column Value ] ]
     * @param shardingValue Sharding Value
     * @return AFFECTED ROWS
     */
    public int batchInsert(@NotNull String tableName, @NotNull List<Map<String, Value>> list, @Nullable Value shardingValue) {
        Database database = getDatabase();

        Integer tableNum = getTableNum(tableName, shardingValue);
        int result = database.batchInsert(tableName, list, tableNum);

        writeLogBatchInsert(tableName, list, shardingValue, result);
        return result;
    }

    /**
     * UPDATE
     *
     * @param tableName UPDATE table
     * @param keyValue  Key Value
     * @param sets      [ column = column + 1 ]
     * @param setValues [ Set Name : Set Value ]
     * @return AFFECTED ROWS
     */
    public int update(@NotNull String tableName,
                      @NotNull Value keyValue,
                      List<String> sets, Map<String, Value> setValues) {
        Database database = getDatabase();

        String keyName = getKeyName(tableName);
        Integer tableNum = getTableNum(tableName, keyValue);
        int affectedRows = database.update(tableName, keyValue, sets, setValues, keyName, tableNum);
        if (affectedRows > 0) {
            Cache cache = getCache();
            cache.delete(tableName, keyValue);
        }

        writeLogUpdate(tableName, keyValue, sets, setValues, affectedRows);
        return affectedRows;
    }

    /**
     * UPDATE
     *
     * @param tableName     UPDATE table
     * @param sets          [ column = column + 1 ]
     * @param setValues     [ Set Name : Set Value ]
     * @param filters       [ the {@link BaseFilter} instance ]
     * @param shardingValue Sharding Value
     * @return AFFECTED ROWS
     */
    public int update(@NotNull String tableName,
                      List<String> sets, Map<String, Value> setValues,
                      List<BaseFilter> filters,
                      @Nullable Value shardingValue) {
        Database database = getDatabase();

        Integer tableNum = getTableNum(tableName, shardingValue);
        int result = database.update(tableName, sets, setValues, filters, tableNum);

        writeLogUpdate(tableName, sets, setValues, filters, shardingValue, result);
        return result;
    }

    /**
     * BATCH UPDATE, No Transactional
     *
     * @param tableName     UPDATE table
     * @param sets          [ column = column + 1 ]
     * @param setNames      [ Set Name ]
     * @param list          [ [ Parameter Name : Parameter Value ] ]
     * @param shardingValue Sharding Value
     * @return AFFECTED ROWS
     */
    public int[] batchUpdate(@NotNull String tableName,
                             List<String> sets, List<String> setNames,
                             List<Map<String, Value>> list,
                             @Nullable Value shardingValue) {
        Database database = getDatabase();

        String keyName = getKeyName(tableName);
        Integer tableNum = getTableNum(tableName, shardingValue);
        int[] result = database.batchUpdate(tableName, sets, setNames, list, keyName, tableNum);

        writeLogBatchUpdate(tableName, sets, setNames, list, shardingValue, result);
        return result;
    }

    /**
     * DELETE
     *
     * @param tableName DELETE FROM table
     * @param keyValue  Key Value
     * @return AFFECTED ROWS
     */
    public int delete(@NotNull String tableName, @NotNull Value keyValue) {
        Database database = getDatabase();

        String keyName = getKeyName(tableName);
        Integer tableNum = getTableNum(tableName, keyValue);
        int affectedRows = database.delete(tableName, keyValue, keyName, tableNum);
        if (affectedRows > 0) {
            Cache cache = getCache();
            cache.delete(tableName, keyValue);
        }

        writeLogDelete(tableName, keyValue, affectedRows);
        return affectedRows;
    }

    /**
     * DELETE
     *
     * @param tableName     DELETE FROM table
     * @param filters       [ the {@link BaseFilter} instance ]
     * @param shardingValue Sharding Value
     * @return AFFECTED ROWS
     */
    public int delete(@NotNull String tableName, List<BaseFilter> filters, @Nullable Value shardingValue) {
        Database database = getDatabase();

        Integer tableNum = getTableNum(tableName, shardingValue);
        int result = database.delete(tableName, filters, tableNum);

        writeLogDelete(tableName, filters, shardingValue, result);
        return result;
    }

    /**
     * SHARDING VALUE
     *
     * @param tableName Table Name
     * @param values    [ Column Name : Column Value ]
     * @return Sharding Value
     */
    @Nullable
    public Value getShardingValue(@NotNull String tableName, @NotNull Map<String, Value> values) {
        String keyName = getKeyNameOrIdName(tableName);
        return values.get(keyName);
    }

    /**
     * KEY NAME
     *
     * @param tableName Table Name
     * @return Key Name, Primary Key Name
     */
    @NotEmpty
    public String getKeyNameOrIdName(@NotNull String tableName) {
        String keyName = getKeyName(tableName);
        if (keyName == null) {
            return getIdName(tableName);
        } else {
            return keyName;
        }
    }

    /**
     * KEY NAME
     *
     * @param tableName Table Name
     * @return Key Name
     */
    @Nullable
    public String getKeyName(@NotNull String tableName) {
        return KeyRegistry.get(tableName);
    }

    /**
     * PRIMARY KEY
     *
     * @param tableName Table Name
     * @return Primary Key Name
     */
    @NotEmpty
    public String getIdName(@NotNull String tableName) {
        Database database = getDatabase();
        return database.getIdName(tableName, null);
    }

    /**
     * TABLE NUM
     *
     * @param tableName     Table Name
     * @param shardingValue Sharding Value
     * @return Table Num
     */
    @Nullable
    public Integer getTableNum(@NotNull String tableName, @Nullable Value shardingValue) {
        if (shardingValue == null) {
            return null;
        }

        Integer tableSize = ShardingRegistry.get(tableName);
        if (tableSize != null) {
            return ShardingValue.calculate(tableSize, shardingValue);
        } else {
            return null;
        }
    }

    @NotNull
    public Database getDatabase() {
        return database;
    }

    @NotNull
    public Cache getCache() {
        return cache;
    }

    protected void writeLogSelectList(String tableName, List<BaseFilter> filters, List<Order> orders, Page page, Value shardingValue, List<Map<String, Value>> result) {
        try {
            LogRepository logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectList(tableName, filters, orders, page, shardingValue, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogSelectCount(String tableName, List<BaseFilter> filters, Value shardingValue, long result) {
        try {
            LogRepository logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectCount(tableName, filters, shardingValue, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogSelectOne(String tableName, Value keyValue, Map<String, Value> result, boolean useCache) {
        try {
            LogRepository logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectOne(tableName, keyValue, result, useCache);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogSelectOne(String tableName, List<BaseFilter> filters, List<Order> orders, Value shardingValue, Map<String, Value> result) {
        try {
            LogRepository logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectOne(tableName, filters, orders, shardingValue, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogCheckExist(String tableName, List<BaseFilter> filters, Value shardingValue, boolean result) {
        try {
            LogRepository logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.checkExist(tableName, filters, shardingValue, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogInsert(String tableName, Map<String, Value> values, int result) {
        try {
            LogRepository logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.insert(tableName, values, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogBatchInsert(String tableName, List<Map<String, Value>> list, Value shardingValue, int result) {
        try {
            LogRepository logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.batchInsert(tableName, list, shardingValue, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogUpdate(String tableName, Value keyValue, List<String> sets, Map<String, Value> setValues, int result) {
        try {
            LogRepository logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.update(tableName, keyValue, sets, setValues, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogUpdate(String tableName, List<String> sets, Map<String, Value> setValues, List<BaseFilter> filters, Value shardingValue, int result) {
        try {
            LogRepository logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.update(tableName, sets, setValues, filters, shardingValue, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogBatchUpdate(String tableName, List<String> sets, List<String> setNames, List<Map<String, Value>> list, Value shardingValue, int[] result) {
        try {
            LogRepository logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.batchUpdate(tableName, sets, setNames, list, shardingValue, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogDelete(String tableName, Value keyValue, int result) {
        try {
            LogRepository logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.delete(tableName, keyValue, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogDelete(String tableName, List<BaseFilter> filters, Value shardingValue, int result) {
        try {
            LogRepository logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.delete(tableName, filters, shardingValue, result);
            }
        } catch (Throwable ignored) {
        }
    }

    @Nullable
    public LogRepository getLogWriter() {
        return logWriter;
    }

    public void setLogWriter(@Nullable LogRepository logWriter) {
        this.logWriter = logWriter;
    }

}
