package io.github.summer.boot.dto;

/**
 * 未回收
 *
 * @author changebooks@qq.com
 */
public class UnRecycled extends ExpressionFilter<Integer> {
    /**
     * 字段名
     */
    private static final String FIELD_NAME = "internal_recycle";

    /**
     * recycled ? id : 0
     */
    private static final int VALUE = 0;

    @Override
    public String getFieldName() {
        return FIELD_NAME;
    }

    @Override
    public Integer getCode() {
        return Expression.EQ.code;
    }

    @Override
    public Integer getValue() {
        return VALUE;
    }

}
