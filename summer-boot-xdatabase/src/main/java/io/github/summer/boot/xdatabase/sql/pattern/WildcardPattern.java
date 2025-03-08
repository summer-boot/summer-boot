package io.github.summer.boot.xdatabase.sql.pattern;

import io.github.summer.boot.filter.WildcardFilter;
import jakarta.validation.constraints.NotNull;

/**
 * 模糊匹配
 *
 * @author changebooks@qq.com
 */
public final class WildcardPattern {

    private WildcardPattern() {
    }

    /**
     * 格式
     *
     * @param filter the {@link WildcardFilter} instance
     * @return %s LIKE CONCAT('%%%%', %s, '%%%%'), %s NOT LIKE CONCAT('%%%%', %s, '%%%%')
     */
    @NotNull
    public static String getPattern(@NotNull WildcardFilter filter) {
        Wildcard wildcard = getWildcard(filter);
        boolean not = filter.isNot();
        return not ? wildcard.patternNot : wildcard.pattern;
    }

    /**
     * 表达式
     *
     * @param filter the {@link WildcardFilter} instance
     * @return Wildcard.NULL, Wildcard.CONTAINS, Wildcard.STARTS, Wildcard.ENDS
     */
    @NotNull
    public static Wildcard getWildcard(@NotNull WildcardFilter filter) {
        int code = filter.getCode();
        return Wildcard.forCode(code);
    }

}
