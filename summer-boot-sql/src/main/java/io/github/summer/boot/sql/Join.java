package io.github.summer.boot.sql;

/**
 * 连表方式
 *
 * @author changebooks@qq.com
 */
public enum Join {
    /**
     * 内连
     */
    INNER,

    /**
     * 外连
     */
    OUTER,

    /**
     * 左外
     */
    LEFT,

    /**
     * 右外
     */
    RIGHT,

    /**
     * 全量
     */
    FULL,

    /**
     * 交叉
     */
    CROSS,

    /**
     * 自连
     */
    SELF,

    /**
     * 自然
     */
    NATURAL,

    /**
     * 强制顺序
     */
    STRAIGHT,

}
