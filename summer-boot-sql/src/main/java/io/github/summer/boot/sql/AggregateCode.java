package io.github.summer.boot.sql;

/**
 * 聚合函数编码
 *
 * @author changebooks@qq.com
 */
public interface AggregateCode {
    /**
     * 未知
     */
    int NULL = 0;

    /**
     * 总行数
     */
    int COUNT = 1;

    /**
     * 总和
     */
    int SUM = 2;

    /**
     * 最大值
     */
    int MAX = 3;

    /**
     * 最小值
     */
    int MIN = 4;

    /**
     * 平均值
     */
    int AVG = 5;

}
