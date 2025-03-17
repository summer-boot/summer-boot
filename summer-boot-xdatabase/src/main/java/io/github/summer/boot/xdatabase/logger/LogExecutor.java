package io.github.summer.boot.xdatabase.logger;

import io.github.summer.boot.sql.SqlParameter;
import io.github.summer.boot.value.Value;

import java.util.List;
import java.util.Map;

/**
 * Log Executor
 *
 * @author changebooks@qq.com
 */
public interface LogExecutor {
    /**
     * SELECT LIST
     *
     * @param sqlParameter the {@link SqlParameter} instance
     * @param columnNames  [ Column Name ]
     * @param valueTypes   [ Column Name : Value Type ]
     * @param result       [ [ Column Name : Column Value ] ]
     */
    void selectList(SqlParameter sqlParameter, List<String> columnNames, Map<String, Integer> valueTypes, List<Map<String, Value>> result);

    /**
     * SELECT ONE
     *
     * @param sqlParameter the {@link SqlParameter} instance
     * @param columnNames  [ Column Name ]
     * @param valueTypes   [ Column Name : Value Type ]
     * @param result       [ Column Name : Column Value ]
     */
    void selectOne(SqlParameter sqlParameter, List<String> columnNames, Map<String, Integer> valueTypes, Map<String, Value> result);

    /**
     * GET ONE
     *
     * @param sqlParameter the {@link SqlParameter} instance
     * @param valueType    Value Type
     * @param result       Column Value
     */
    void getOne(SqlParameter sqlParameter, int valueType, Value result);

    /**
     * UPDATE
     *
     * @param sqlParameter the {@link SqlParameter} instance
     * @param result       AFFECTED ROWS
     */
    void update(SqlParameter sqlParameter, int result);

    /**
     * UPDATE LIST
     *
     * @param sqlParameter the {@link SqlParameter} instance
     * @param result       AFFECTED ROWS
     */
    void updateList(SqlParameter sqlParameter, int result);

    /**
     * BATCH UPDATE
     *
     * @param sqlParameter the {@link SqlParameter} instance
     * @param result       AFFECTED ROWS
     */
    void batchUpdate(SqlParameter sqlParameter, int[] result);

}
