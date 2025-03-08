package io.github.summer.boot.dto;

/**
 * 主键，不等于
 *
 * @author changebooks@qq.com
 */
public class IdNeL extends ExpressionFilter<Long> {
    /**
     * 字段名
     */
    private static final String FIELD_NAME = "id";

    public IdNeL() {
    }

    /**
     * initialize value
     *
     * @param id Id
     */
    public IdNeL(Long id) {
        setValue(id);
    }

    @Override
    public String getFieldName() {
        return FIELD_NAME;
    }

    @Override
    public Integer getCode() {
        return Expression.NE.code;
    }

}
