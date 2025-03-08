package io.github.summer.boot.dto;

/**
 * 更新时间，排序
 *
 * @author changebooks@qq.com
 */
public class LastUpdateOrder extends Order {
    /**
     * 字段名
     */
    private static final String FIELD_NAME = "last_update";

    public LastUpdateOrder() {
    }

    /**
     * initialize value
     *
     * @param desc Desc ?
     */
    public LastUpdateOrder(Boolean desc) {
        setDesc(desc);
    }

    @Override
    public String getFieldName() {
        return FIELD_NAME;
    }

}
