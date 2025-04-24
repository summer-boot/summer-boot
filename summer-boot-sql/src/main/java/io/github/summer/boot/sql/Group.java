package io.github.summer.boot.sql;

import io.github.summer.boot.filter.BaseFilter;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.*;

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

    @NotNull
    public List<String> getColumns() {
        return columns != null ? columns : new ArrayList<>();
    }

    public void setColumns(List<String> columns) {
        this.columns = Optional.ofNullable(columns)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(x -> !x.isEmpty())
                .distinct()
                .toList();
    }

    public List<BaseFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<BaseFilter> filters) {
        this.filters = filters;
    }

}
