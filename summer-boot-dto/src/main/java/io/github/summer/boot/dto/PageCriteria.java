package io.github.summer.boot.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页参数
 *
 * @author changebooks@qq.com
 */
public class PageCriteria {
    /**
     * 条件
     */
    private List<BaseFilter> filters;

    /**
     * 排序
     */
    private List<Order> orders;

    public List<BaseFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<BaseFilter> filters) {
        this.filters = filters;
    }

    /**
     * 新增条件
     *
     * @param f BaseFilter
     * @return 成功？
     */
    public boolean addFilter(BaseFilter f) {
        if (f == null) {
            return false;
        }

        if (this.filters == null) {
            this.filters = new ArrayList<>();
        }

        return this.filters.add(f);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    /**
     * 新增排序
     *
     * @param o Order
     * @return 成功？
     */
    public boolean addOrder(Order o) {
        if (o == null) {
            return false;
        }

        if (this.orders == null) {
            this.orders = new ArrayList<>();
        }

        return this.orders.add(o);
    }

}
