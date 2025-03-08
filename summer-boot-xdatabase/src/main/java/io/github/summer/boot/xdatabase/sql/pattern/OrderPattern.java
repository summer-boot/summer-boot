package io.github.summer.boot.xdatabase.sql.pattern;

import io.github.summer.boot.filter.Order;
import jakarta.validation.constraints.NotNull;

/**
 * 排序
 *
 * @author changebooks@qq.com
 */
public final class OrderPattern {
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

    private OrderPattern() {
    }

    /**
     * 格式
     *
     * @param order the {@link Order} instance
     * @return %s, %s ASC, %s DESC
     */
    @NotNull
    public static String getPattern(@NotNull Order order) {
        Boolean desc = order.getDesc();
        if (desc == null) {
            return PATTERN_NULL;
        } else {
            return desc ? PATTERN_DESC : PATTERN_ASC;
        }
    }

}
