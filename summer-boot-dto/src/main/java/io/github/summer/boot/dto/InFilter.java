package io.github.summer.boot.dto;

import java.util.List;

/**
 * 在列表中？
 *
 * @author changebooks@qq.com
 */
public abstract class InFilter<T> extends BaseFilterNot {
    /**
     * 列表
     */
    private List<T> values;

    public List<T> getValues() {
        return values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }

}
