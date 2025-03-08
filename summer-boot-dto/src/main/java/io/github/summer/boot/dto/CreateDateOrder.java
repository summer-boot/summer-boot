package io.github.summer.boot.dto;

/**
 * 创建时间，排序
 *
 * @author changebooks@qq.com
 */
public class CreateDateOrder extends Order {
    /**
     * 字段名
     */
    private static final String FIELD_NAME = "create_date";

    public CreateDateOrder() {
    }

    /**
     * initialize value
     *
     * @param desc Desc ?
     */
    public CreateDateOrder(Boolean desc) {
        setDesc(desc);
    }

    @Override
    public String getFieldName() {
        return FIELD_NAME;
    }

}
