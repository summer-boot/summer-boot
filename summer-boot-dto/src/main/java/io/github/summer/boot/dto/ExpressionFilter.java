package io.github.summer.boot.dto;

/**
 * 表达式
 *
 * @author changebooks@qq.com
 */
public abstract class ExpressionFilter<T> extends BaseFilter {
    /**
     * 编码
     */
    private Integer code;

    /**
     * 值
     */
    private T value;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
