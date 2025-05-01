package io.github.summer.boot.sql;

/**
 * 解析聚合函数
 *
 * @author changebooks@qq.com
 */
public interface AggregateParser {
    /**
     * 解析
     *
     * @param aggregate the {@link Aggregate} instance
     * @return COUNT(1), SUM(column), MAX(column), MIN(column), AVG(column)
     */
    String parse(Aggregate aggregate);

}
