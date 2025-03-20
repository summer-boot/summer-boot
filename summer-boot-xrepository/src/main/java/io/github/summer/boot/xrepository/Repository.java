package io.github.summer.boot.xrepository;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.filter.Order;
import io.github.summer.boot.filter.Page;
import io.github.summer.boot.sql.Preconditions;
import io.github.summer.boot.value.Value;
import io.github.summer.boot.xdatabase.Database;
import io.github.summer.boot.xrepository.key.KeyRegistry;
import io.github.summer.boot.xrepository.sharding.ShardingRegistry;
import io.github.summer.boot.xrepository.sharding.ShardingValue;
import jakarta.annotation.Nullable;
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

    public Repository(Database database) {
        Preconditions.requireNonNull(database, "database must not be null");

        this.database = database;
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
        return database.selectList(tableName, filters, orders, page, tableNum);
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
        return database.selectCount(tableName, filters, tableNum);
    }

    /**
     * SELECT ONE
     *
     * @param tableName FROM table
     * @param keyValue  Key Value
     * @return [ Column Name : Column Value ]
     */
    public Map<String, Value> selectOne(@NotNull String tableName, @NotNull Value keyValue) {
        Database database = getDatabase();

        String keyName = getKeyName(tableName);
        Integer tableNum = getTableNum(tableName, keyValue);
        return database.selectOne(tableName, keyValue, keyName, tableNum);
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
        return database.selectOne(tableName, filters, orders, tableNum);
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
        return database.checkExist(tableName, filters, tableNum);
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

        String keyName = getKeyName(tableName);
        if (keyName == null) {
            keyName = database.getIdName(tableName, null);
        }

        Value shardingValue = values.get(keyName);
        Integer tableNum = getTableNum(tableName, shardingValue);
        return database.insert(tableName, values, tableNum);
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
        return database.batchInsert(tableName, list, tableNum);
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
        return database.update(tableName, keyValue, sets, setValues, keyName, tableNum);
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
        return database.update(tableName, sets, setValues, filters, tableNum);
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
        return database.batchUpdate(tableName, sets, setNames, list, keyName, tableNum);
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
        return database.delete(tableName, keyValue, keyName, tableNum);
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
        return database.delete(tableName, filters, tableNum);
    }

    /**
     * Get Key Name
     *
     * @param tableName Table Name
     * @return Key Name
     */
    @Nullable
    public String getKeyName(@NotNull String tableName) {
        return KeyRegistry.get(tableName);
    }

    /**
     * Get Table Num
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

}
