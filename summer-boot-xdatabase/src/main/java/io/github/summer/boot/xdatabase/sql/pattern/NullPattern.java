package io.github.summer.boot.xdatabase.sql.pattern;

import io.github.summer.boot.filter.NullFilter;
import jakarta.validation.constraints.NotNull;

/**
 * 空？
 *
 * @author changebooks@qq.com
 */
public final class NullPattern {
    /**
     * 空
     */
    private static final String PATTERN = "%s IS NULL";

    /**
     * 非空
     */
    private static final String PATTERN_NOT = "%s IS NOT NULL";

    private NullPattern() {
    }

    /**
     * 格式
     *
     * @param filter the {@link NullFilter} instance
     * @return %s IS NULL, %s IS NOT NULL
     */
    @NotNull
    public static String getPattern(@NotNull NullFilter filter) {
        boolean not = filter.isNot();
        return not ? PATTERN_NOT : PATTERN;
    }

}
