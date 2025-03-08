package io.github.summer.boot.elastic;

import io.github.summer.boot.base.Check;
import io.github.summer.boot.base.JsonParser;
import io.github.summer.boot.dto.Order;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 排序
 *
 * @author changebooks@qq.com
 */
public final class OrderBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderBuilder.class);

    private OrderBuilder() {
    }

    /**
     * Build Filter
     *
     * @param builder SearchSourceBuilder
     * @param orders  [ Order ]
     */
    public static void build(SearchSourceBuilder builder, List<Order> orders) {
        if (builder == null) {
            LOGGER.error("build failed, builder must not be null, orders: {}", JsonParser.toJson(orders));
            return;
        }

        if (orders == null) {
            LOGGER.error("build failed, orders must not be null");
            return;
        }

        for (Order order : orders) {
            build(builder, order);
        }
    }

    /**
     * Build Filter
     *
     * @param builder SearchSourceBuilder
     * @param order   Order
     */
    public static void build(SearchSourceBuilder builder, Order order) {
        if (builder == null) {
            LOGGER.error("build failed, builder must not be null, order: {}", order);
            return;
        }

        if (order == null) {
            LOGGER.error("build failed, order must not be null");
            return;
        }

        String fieldName = order.getFieldName();
        if (Check.isEmpty(fieldName)) {
            LOGGER.error("build failed, fieldName must not be empty, order: {}", order);
            return;
        }

        boolean desc = order.isDesc();
        SortOrder sort = desc ? SortOrder.DESC : SortOrder.ASC;
        FieldSortBuilder fieldSortBuilder = new FieldSortBuilder(fieldName).order(sort);
        builder.sort(fieldSortBuilder);
    }

}
