package io.github.summer.boot.filter;

import java.util.List;

/**
 * 条件组合
 *
 * @author changebooks@qq.com
 */
public class Filters extends BaseFilter {
    /**
     * [ the {@link BaseFilter} instance ]
     */
    private List<BaseFilter> filters;

    @Override
    public String toString() {
        Boolean or = getOr();
        List<BaseFilter> filters = getFilters();
        return "{\"or\": %s, \"filters\": %s}".formatted(or, filters);
    }

    public List<BaseFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<BaseFilter> filters) {
        this.filters = filters;
    }

}
