package io.github.summer.boot.sql.parser;

import io.github.summer.boot.sql.Order;
import io.github.summer.boot.sql.OrderParser;
import io.github.summer.boot.sql.Preconditions;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author changebooks@qq.com
 */
public class OrderParserImpl implements OrderParser {
    /**
     * 未知
     */
    private static final String PATTERN_NULL = "%s";

    /**
     * 升序
     */
    private static final String PATTERN_ASC = "%s ASC";

    /**
     * 降序
     */
    private static final String PATTERN_DESC = "%s DESC";

    @Override
    public String parse(List<Order> list) {
        if (list == null) {
            return null;
        } else {
            String pattern = parseOrder(list);
            return prefixed(pattern);
        }
    }

    @Override
    public String parse(Order order) {
        if (order == null) {
            return null;
        } else {
            String pattern = parseOrder(order);
            return prefixed(pattern);
        }
    }

    /**
     * 排序
     *
     * @param list [ the {@link Order} instance ]
     * @return name, name ASC, name DESC
     */
    @NotNull
    public String parseOrder(@NotNull List<Order> list) {
        return list.stream()
                .peek(order -> Preconditions.requireNonNull(order, "order must not be null"))
                .map(this::parseOrder)
                .collect(Collectors.joining(", "));
    }

    /**
     * 排序
     *
     * @param order the {@link Order} instance
     * @return name, name ASC, name DESC
     */
    @NotNull
    public String parseOrder(@NotNull Order order) {
        String name = order.getName();
        Preconditions.requireNonEmpty(name, "name must not be empty");

        String pattern = getPattern(order);
        return String.format(pattern, name);
    }

    /**
     * 格式
     *
     * @param order the {@link Order} instance
     * @return %s, %s ASC, %s DESC
     */
    @NotNull
    public String getPattern(@NotNull Order order) {
        Boolean desc = order.getDesc();
        if (desc == null) {
            return PATTERN_NULL;
        } else {
            return desc ? PATTERN_DESC : PATTERN_ASC;
        }
    }

    /**
     * 连接前缀
     *
     * @param sql name DESC
     * @return ORDER BY name DESC
     */
    @NotNull
    public String prefixed(String sql) {
        if (sql == null || sql.isBlank()) {
            return "";
        } else {
            return "ORDER BY " + sql;
        }
    }

}
