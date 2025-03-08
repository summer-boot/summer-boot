package io.github.summer.boot.dto;

/**
 * 主键，不等于
 *
 * @author changebooks@qq.com
 */
public class IdNe extends ExpressionFilter<Integer> {
    /**
     * 字段名
     */
    private static final String FIELD_NAME = "id";

    public IdNe() {
    }

    /**
     * initialize value
     *
     * @param id Id
     */
    public IdNe(Integer id) {
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
