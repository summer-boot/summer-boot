package io.github.summer.boot.sql;

import io.github.summer.boot.filter.BaseFilter;

import java.io.Serializable;
import java.util.List;

/**
 * 分组
 *
 * @author changebooks@qq.com
 */
public class Group implements Serializable {
    /**
     * GROUP BY name, name
     */
    private List<String> columns;

    /**
     * HAVING column = ? AND column = ?
     */
    private List<BaseFilter> filters;

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public List<BaseFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<BaseFilter> filters) {
        this.filters = filters;
    }

}
