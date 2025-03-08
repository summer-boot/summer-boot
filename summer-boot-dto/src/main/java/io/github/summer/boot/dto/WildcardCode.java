package io.github.summer.boot.dto;

/**
 * 模糊匹配编码
 *
 * @author changebooks@qq.com
 */
public interface WildcardCode {
    /**
     * 未知
     */
    int NULL = 0;

    /**
     * 包含
     */
    int CONTAINS = 1;

    /**
     * 开始于
     */
    int STARTS = 2;

    /**
     * 结束于
     */
    int ENDS = 3;

}
