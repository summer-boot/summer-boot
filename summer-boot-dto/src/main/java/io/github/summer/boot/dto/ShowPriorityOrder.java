package io.github.summer.boot.dto;

/**
 * 排序
 *
 * @author changebooks@qq.com
 */
public class ShowPriorityOrder extends Order {
    /**
     * 字段名
     */
    private static final String FIELD_NAME = "show_priority";

    public ShowPriorityOrder() {
    }

    /**
     * initialize value
     *
     * @param desc Desc ?
     */
    public ShowPriorityOrder(Boolean desc) {
        setDesc(desc);
    }

    @Override
    public String getFieldName() {
        return FIELD_NAME;
    }

}
