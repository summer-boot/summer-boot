package io.github.summer.boot.dto;

/**
 * 主键，排序
 *
 * @author changebooks@qq.com
 */
public class IdOrder extends Order {
    /**
     * 字段名
     */
    private static final String FIELD_NAME = "id";

    public IdOrder() {
    }

    /**
     * initialize value
     *
     * @param desc Desc ?
     */
    public IdOrder(Boolean desc) {
        setDesc(desc);
    }

    @Override
    public String getFieldName() {
        return FIELD_NAME;
    }

}
