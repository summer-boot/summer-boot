package io.github.summer.boot.dto;

/**
 * 排序
 *
 * @author changebooks@qq.com
 */
public abstract class Order extends BaseFilter {
    /**
     * 降序？
     */
    private Boolean desc;

    public boolean isDesc() {
        return desc != null && desc;
    }

    public void setDesc(Boolean desc) {
        this.desc = desc;
    }

}
