package io.github.summer.boot.sql.parser;

import io.github.summer.boot.filter.*;
import io.github.summer.boot.sql.Order;
import io.github.summer.boot.sql.Preconditions;
import io.github.summer.boot.sql.parser.pattern.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 格式
 *
 * @author changebooks@qq.com
 */
public final class PatternParser {

    private PatternParser() {
    }

    /**
     * 表达式
     *
     * @param filter the {@link ExpressionFilter} instance
     * @return name = %s, name != %s, name > %s, name < %s, name >= %s, name <= %s
     */
    @NotNull
    public static String parseExpression(@NotNull ExpressionFilter filter) {
        String pattern = ExpressionPattern.getPattern(filter);
        String name = filter.getName();
        return replaceName(pattern, name);
    }

    /**
     * 在列表中？
     *
     * @param filter the {@link InFilter} instance
     * @return name IN (%s), name NOT IN (%s)
     */
    @NotNull
    public static String parseIn(@NotNull InFilter filter) {
        String pattern = InPattern.getPattern(filter);
        String name = filter.getName();
        return replaceName(pattern, name);
    }

    /**
     * 空？
     *
     * @param filter the {@link NullFilter} instance
     * @return name IS NULL, name IS NOT NULL
     */
    @NotNull
    public static String parseNull(@NotNull NullFilter filter) {
        String pattern = NullPattern.getPattern(filter);
        String name = filter.getName();
        return replaceName(pattern, name);
    }

    /**
     * 范围开始
     *
     * @param filter the {@link RangeFilter} instance
     * @return name >= %s, name > %s
     */
    @NotNull
    public static String parseRangeFrom(@NotNull RangeFilter filter) {
        String pattern = RangeFromPattern.getPattern(filter);
        String name = filter.getName();
        return replaceName(pattern, name);
    }

    /**
     * 范围结束
     *
     * @param filter the {@link RangeFilter} instance
     * @return name <= %s, name < %s
     */
    @NotNull
    public static String parseRangeTo(@NotNull RangeFilter filter) {
        String pattern = RangeToPattern.getPattern(filter);
        String name = filter.getName();
        return replaceName(pattern, name);
    }

    /**
     * 模糊匹配
     *
     * @param filter the {@link WildcardFilter} instance
     * @return name LIKE CONCAT('%%', %s, '%%'), name NOT LIKE CONCAT('%%', %s, '%%')
     */
    @NotNull
    public static String parseWildcard(@NotNull WildcardFilter filter) {
        String pattern = WildcardPattern.getPattern(filter);
        String name = filter.getName();
        return replaceName(pattern, name);
    }

    /**
     * 排序
     *
     * @param orders [ the {@link Order} instance ]
     * @return name, name ASC, name DESC
     */
    @NotNull
    public static String parseOrder(@NotNull List<Order> orders) {
        return orders.stream()
                .peek(order -> Preconditions.requireNonNull(order, "order must not be null"))
                .map(PatternParser::parseOrder)
                .collect(Collectors.joining(", "))
                .trim();
    }

    /**
     * 排序
     *
     * @param order the {@link Order} instance
     * @return name, name ASC, name DESC
     */
    @NotNull
    public static String parseOrder(@NotNull Order order) {
        String pattern = OrderPattern.getPattern(order);
        String name = order.getName();
        return replaceName(pattern, name);
    }

    /**
     * 替换名称
     *
     * @param pattern 格式
     * @param name    名称
     * @return First %s to name
     */
    @NotNull
    static String replaceName(@NotNull String pattern, @NotNull String name) {
        Preconditions.requireNonEmpty(name, "name must not be empty");
        Preconditions.requireNonBlank(pattern, "pattern must not be blank, name: " + name);
        return String.format(pattern, name, "%s");
    }

}
