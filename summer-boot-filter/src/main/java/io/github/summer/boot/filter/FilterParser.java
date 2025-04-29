package io.github.summer.boot.filter;

import jakarta.validation.constraints.NotNull;

/**
 * 条件
 *
 * @author changebooks@qq.com
 */
public interface FilterParser {
    /**
     * 表达式
     *
     * @param filter the {@link ExpressionFilter} instance
     * @return name = :parameterName, name != :parameterName, name > :parameterName, name < :parameterName
     */
    @NotNull
    String parseExpression(@NotNull ExpressionFilter filter);

    /**
     * 在列表中？
     *
     * @param filter the {@link InFilter} instance
     * @return name IN (:parameterName1, :parameterName2, ...), name NOT IN (:parameterName1, :parameterName2, ...)
     */
    @NotNull
    String parseIn(@NotNull InFilter filter);

    /**
     * 空？
     *
     * @param filter the {@link NullFilter} instance
     * @return name IS NULL, name IS NOT NULL
     */
    @NotNull
    String parseNull(@NotNull NullFilter filter);

    /**
     * 范围、范围开始、范围结束
     *
     * @param filter the {@link RangeFilter} instance
     * @return name >= :parameterName AND name <= :parameterName, name > :parameterName, name < :parameterName
     */
    @NotNull
    String parseRange(@NotNull RangeFilter filter);

    /**
     * 模糊匹配
     *
     * @param filter the {@link WildcardFilter} instance
     * @return name LIKE CONCAT('%', :parameterName, '%'), name LIKE CONCAT(:parameterName, '%'), name LIKE CONCAT('%', :parameterName)
     */
    @NotNull
    String parseWildcard(@NotNull WildcardFilter filter);

}
