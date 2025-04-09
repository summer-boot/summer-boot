package io.github.summer.boot.sql;

/**
 * 连表编码
 *
 * @author changebooks@qq.com
 */
public interface JoinCode {
    /**
     * 未知
     */
    int NULL = 0;

    /**
     * 内连
     */
    int INNER = 1;

    /**
     * 外连
     */
    int OUTER = 2;

    /**
     * 左外
     */
    int LEFT = 3;

    /**
     * 右外
     */
    int RIGHT = 4;

    /**
     * 全量
     */
    int FULL = 5;

    /**
     * 交叉
     */
    int CROSS = 6;

    /**
     * 自连
     */
    int SELF = 7;

    /**
     * 自然
     */
    int NATURAL = 8;

    /**
     * 强制顺序
     */
    int STRAIGHT = 9;

}
