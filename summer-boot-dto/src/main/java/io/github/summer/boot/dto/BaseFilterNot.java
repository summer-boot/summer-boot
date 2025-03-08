package io.github.summer.boot.dto;

/**
 * 条件基类，支持取反
 *
 * @author changebooks@qq.com
 */
public abstract class BaseFilterNot extends BaseFilter {
    /**
     * 取反？
     */
    private Boolean not;

    public boolean isNot() {
        return not != null && not;
    }

    public void setNot(Boolean not) {
        this.not = not;
    }

}
