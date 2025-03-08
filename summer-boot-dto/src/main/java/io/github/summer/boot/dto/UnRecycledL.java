package io.github.summer.boot.dto;

/**
 * 未回收
 *
 * @author changebooks@qq.com
 */
public class UnRecycledL extends ExpressionFilter<Long> {
    /**
     * 字段名
     */
    private static final String FIELD_NAME = "internal_recycle";

    /**
     * recycled ? id : 0
     */
    private static final long VALUE = 0L;

    @Override
    public String getFieldName() {
        return FIELD_NAME;
    }

    @Override
    public Integer getCode() {
        return Expression.EQ.code;
    }

    @Override
    public Long getValue() {
        return VALUE;
    }

}
