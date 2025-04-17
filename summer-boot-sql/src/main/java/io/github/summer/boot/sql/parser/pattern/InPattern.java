package io.github.summer.boot.sql.parser.pattern;

import io.github.summer.boot.filter.InFilter;
import jakarta.validation.constraints.NotNull;

/**
 * 在列表中？
 *
 * @author changebooks@qq.com
 */
public final class InPattern {
    /**
     * 在列表中
     */
    private static final String PATTERN = "%s IN (%s)";

    /**
     * 不在列表
     */
    private static final String PATTERN_NOT = "%s NOT IN (%s)";

    private InPattern() {
    }

    /**
     * 格式
     *
     * @param filter the {@link InFilter} instance
     * @return %s IN (%s), %s NOT IN (%s)
     */
    @NotNull
    public static String getPattern(@NotNull InFilter filter) {
        boolean not = filter.isNot();
        return not ? PATTERN_NOT : PATTERN;
    }

}
