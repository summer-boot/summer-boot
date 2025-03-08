package io.github.summer.boot.dto;

/**
 * 表达式编码
 *
 * @author changebooks@qq.com
 */
public interface ExpressionCode {
    /**
     * 未知
     */
    int NULL = 0;

    /**
     * 等于
     */
    int EQ = 1;

    /**
     * 不等于
     */
    int NE = 2;

    /**
     * 大于
     */
    int GT = 3;

    /**
     * 小于
     */
    int LT = 4;

    /**
     * 大于等于
     */
    int GE = 5;

    /**
     * 小于等于
     */
    int LE = 6;

}
