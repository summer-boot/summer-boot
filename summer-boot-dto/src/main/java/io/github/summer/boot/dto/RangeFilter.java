package io.github.summer.boot.dto;

/**
 * 范围
 *
 * @author changebooks@qq.com
 */
public abstract class RangeFilter<T> extends BaseFilter {
    /**
     * 默认包含开始和结束？
     */
    private static final boolean DEFAULT_INCLUDE_LOWER = true;
    private static final boolean DEFAULT_INCLUDE_UPPER = true;

    /**
     * 开始值
     */
    private T from;

    /**
     * 结束值
     */
    private T to;

    /**
     * 包含开始？
     */
    private Boolean includeLower;

    /**
     * 包含结束？
     */
    private Boolean includeUpper;

    public T getFrom() {
        return from;
    }

    public void setFrom(T from) {
        this.from = from;
    }

    public T getTo() {
        return to;
    }

    public void setTo(T to) {
        this.to = to;
    }

    public boolean isIncludeLower() {
        return includeLower != null ? includeLower : DEFAULT_INCLUDE_LOWER;
    }

    public void setIncludeLower(Boolean includeLower) {
        this.includeLower = includeLower;
    }

    public boolean isIncludeUpper() {
        return includeUpper != null ? includeUpper : DEFAULT_INCLUDE_UPPER;
    }

    public void setIncludeUpper(Boolean includeUpper) {
        this.includeUpper = includeUpper;
    }

}
