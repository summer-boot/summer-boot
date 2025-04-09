package io.github.summer.boot.sql;

import java.util.List;

/**
 * 聚合函数
 *
 * @author changebooks@qq.com
 */
public interface AggregateFuncParser {
    /**
     * 解析列表
     *
     * @param list [ the {@link AggregateFunc} instance ]
     * @return column, COUNT(*), SUM(column), MAX(column), MIN(column), AVG(column)
     */
    String parseAggregateFunc(List<AggregateFunc> list);

    /**
     * 解析
     *
     * @param aggregateFunc the {@link AggregateFunc} instance
     * @return column, COUNT(*), SUM(column), MAX(column), MIN(column), AVG(column)
     */
    String parseAggregateFunc(AggregateFunc aggregateFunc);

}
