package io.github.summer.boot.dto;

/**
 * 主键，等于
 *
 * @author changebooks@qq.com
 */
public class IdEqL extends ExpressionFilter<Long> {
    /**
     * 字段名
     */
    private static final String FIELD_NAME = "id";

    public IdEqL() {
    }

    /**
     * initialize value
     *
     * @param id Id
     */
    public IdEqL(Long id) {
        setValue(id);
    }

    @Override
    public String getFieldName() {
        return FIELD_NAME;
    }

    @Override
    public Integer getCode() {
        return Expression.EQ.code;
    }

}
