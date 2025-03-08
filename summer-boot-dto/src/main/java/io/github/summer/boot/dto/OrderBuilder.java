package io.github.summer.boot.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

/**
 * 排序
 *
 * @author changebooks@qq.com
 */
public final class OrderBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderBuilder.class);

    /**
     * 升序
     */
    private static final String PATTERN_ASC = "%s ASC";

    /**
     * 降序
     */
    private static final String PATTERN_DESC = "%s DESC";

    private OrderBuilder() {
    }

    /**
     * Build ORDER SQL
     *
     * @param orders list
     * @return [ fieldName DESC, fieldName ASC ]
     */
    public static String[] build(List<Order> orders) {
        if (orders != null) {
            return orders
                    .stream()
                    .map(OrderBuilder::build)
                    .filter(Objects::nonNull)
                    .toArray(String[]::new);
        } else {
            return null;
        }
    }

    /**
     * Build ORDER SQL
     *
     * @param order Order
     * @return fieldName DESC
     */
    public static String build(Order order) {
        if (order == null) {
            LOGGER.error("build failed, order must not be null");
            return null;
        }

        String pattern = order.isDesc() ? PATTERN_DESC : PATTERN_ASC;
        String fieldName = order.getFieldName();

        return BuilderUtils.build(pattern, fieldName);
    }

}
