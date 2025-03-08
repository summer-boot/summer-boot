package io.github.summer.boot.dto;

/**
 * 有值？
 *
 * @author changebooks@qq.com
 */
public abstract class ExistsFilter<T> extends BaseFilterNot {
    /**
     * 默认值
     *
     * @return "", 0, 0.00
     */
    public abstract T getDefaultValue();

}
