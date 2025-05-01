package io.github.summer.boot.sql.filter;

import io.github.summer.boot.filter.*;
import io.github.summer.boot.sql.Preconditions;
import io.github.summer.boot.sql.filter.pattern.*;
import io.github.summer.boot.value.Parameter;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * @author changebooks@qq.com
 */
public class FilterParserImpl implements FilterParser {
    /**
     * Prepared Statement Setter Placeholder
     */
    private static final String PLACEHOLDER = "?";

    @NotNull
    @Override
    public String parseExpression(@NotNull ExpressionFilter filter) {
        String pattern = getExpressionPattern(filter);
        String name = getFilterName(filter);
        String parameterName = getExpressionParameterName(filter);

        return String.format(pattern, name, parameterName);
    }

    /**
     * 表达式，格式
     *
     * @param filter the {@link ExpressionFilter} instance
     * @return %s = %s, %s != %s, %s > %s, %s < %s, %s >= %s, %s <= %s
     */
    @NotEmpty
    protected String getExpressionPattern(@NotNull ExpressionFilter filter) {
        String pattern = ExpressionPattern.getPattern(filter);
        Preconditions.requireNonBlank(pattern, "pattern must not be blank, filterName: " + filter.getName());

        return pattern;
    }

    /**
     * 表达式，参数名称
     *
     * @param filter the {@link ExpressionFilter} instance
     * @return :parameter.name, placeholder
     */
    @NotEmpty
    protected String getExpressionParameterName(@NotNull ExpressionFilter filter) {
        Parameter parameter = filter.getParameter();

        String parameterName = getParameterName(parameter);
        Preconditions.requireNonEmpty(parameterName, "parameterName must not be empty, filterName: " + filter.getName());

        return parameterName;
    }

    @NotNull
    @Override
    public String parseIn(@NotNull InFilter filter) {
        String pattern = getInPattern(filter);
        String name = getFilterName(filter);
        String parameterName = getInParameterName(filter);

        return String.format(pattern, name, parameterName);
    }

    /**
     * 在列表中，格式
     *
     * @param filter the {@link InFilter} instance
     * @return %s IN (%s), %s NOT IN (%s)
     */
    @NotEmpty
    protected String getInPattern(@NotNull InFilter filter) {
        String pattern = InPattern.getPattern(filter);
        Preconditions.requireNonBlank(pattern, "pattern must not be blank, filterName: " + filter.getName());

        return pattern;
    }

    /**
     * 在列表中，参数名称
     *
     * @param filter the {@link InFilter} instance
     * @return joined :parameter.name, joined placeholder
     */
    @NotEmpty
    protected String getInParameterName(@NotNull InFilter filter) {
        List<Parameter> parameters = filter.getParameters();

        String parameterName = getParameterName(parameters);
        Preconditions.requireNonEmpty(parameterName, "parameterName must not be empty, filterName: " + filter.getName());

        return parameterName;
    }

    @NotNull
    @Override
    public String parseNull(@NotNull NullFilter filter) {
        String pattern = getNullPattern(filter);
        String name = getFilterName(filter);

        return String.format(pattern, name);
    }

    /**
     * 空，格式
     *
     * @param filter the {@link NullFilter} instance
     * @return %s IS NULL, %s IS NOT NULL
     */
    @NotEmpty
    protected String getNullPattern(@NotNull NullFilter filter) {
        String pattern = NullPattern.getPattern(filter);
        Preconditions.requireNonBlank(pattern, "pattern must not be blank, filterName: " + filter.getName());

        return pattern;
    }

    @NotNull
    @Override
    public String parseRange(@NotNull RangeFilter filter) {
        Parameter from = filter.getFrom();
        Parameter to = filter.getTo();

        if (from != null) {
            if (to != null) {
                String rangeFrom = parseRangeFrom(filter);
                String rangeTo = parseRangeTo(filter);
                return rangeFrom + LogicalOperator.AND_WITH_WHITESPACE + rangeTo;
            } else {
                return parseRangeFrom(filter);
            }
        } else {
            if (to != null) {
                return parseRangeTo(filter);
            } else {
                return "";
            }
        }
    }

    /**
     * 范围开始
     *
     * @param filter the {@link RangeFilter} instance
     * @return name >= :parameterName, name > :parameterName
     */
    @NotNull
    protected String parseRangeFrom(@NotNull RangeFilter filter) {
        String pattern = getRangeFromPattern(filter);
        String name = getFilterName(filter);
        String parameterName = getRangeFromParameterName(filter);

        return String.format(pattern, name, parameterName);
    }

    /**
     * 范围开始，格式
     *
     * @param filter the {@link RangeFilter} instance
     * @return %s >= %s, %s > %s
     */
    @NotEmpty
    protected String getRangeFromPattern(@NotNull RangeFilter filter) {
        String pattern = RangeFromPattern.getPattern(filter);
        Preconditions.requireNonBlank(pattern, "pattern must not be blank, filterName: " + filter.getName());

        return pattern;
    }

    /**
     * 范围开始，参数名称
     *
     * @param filter the {@link RangeFilter} instance
     * @return :parameter.name, placeholder
     */
    @NotEmpty
    protected String getRangeFromParameterName(@NotNull RangeFilter filter) {
        Parameter parameter = filter.getFrom();

        String parameterName = getParameterName(parameter);
        Preconditions.requireNonEmpty(parameterName, "parameterName must not be empty, filterName: " + filter.getName());

        return parameterName;
    }

    /**
     * 范围结束
     *
     * @param filter the {@link RangeFilter} instance
     * @return name <= :parameterName, name < :parameterName
     */
    @NotNull
    protected String parseRangeTo(@NotNull RangeFilter filter) {
        String pattern = getRangeToPattern(filter);
        String name = getFilterName(filter);
        String parameterName = getRangeToParameterName(filter);

        return String.format(pattern, name, parameterName);
    }

    /**
     * 范围结束，格式
     *
     * @param filter the {@link RangeFilter} instance
     * @return %s <= %s, %s < %s
     */
    @NotEmpty
    protected String getRangeToPattern(@NotNull RangeFilter filter) {
        String pattern = RangeToPattern.getPattern(filter);
        Preconditions.requireNonBlank(pattern, "pattern must not be blank, filterName: " + filter.getName());

        return pattern;
    }

    /**
     * 范围结束，参数名称
     *
     * @param filter the {@link RangeFilter} instance
     * @return :parameter.name, placeholder
     */
    @NotEmpty
    protected String getRangeToParameterName(@NotNull RangeFilter filter) {
        Parameter parameter = filter.getTo();

        String parameterName = getParameterName(parameter);
        Preconditions.requireNonEmpty(parameterName, "parameterName must not be empty, filterName: " + filter.getName());

        return parameterName;
    }

    @NotNull
    @Override
    public String parseWildcard(@NotNull WildcardFilter filter) {
        String pattern = getWildcardPattern(filter);
        String name = getFilterName(filter);
        String parameterName = getWildcardParameterName(filter);

        return String.format(pattern, name, parameterName);
    }

    /**
     * 模糊匹配，格式
     *
     * @param filter the {@link WildcardFilter} instance
     * @return %s LIKE CONCAT('%%', %s, '%%'), %s NOT LIKE CONCAT('%%', %s, '%%')
     */
    @NotEmpty
    protected String getWildcardPattern(@NotNull WildcardFilter filter) {
        String pattern = WildcardPattern.getPattern(filter);
        Preconditions.requireNonBlank(pattern, "pattern must not be blank, filterName: " + filter.getName());

        return pattern;
    }

    /**
     * 模糊匹配，参数名称
     *
     * @param filter the {@link WildcardFilter} instance
     * @return :parameter.name, placeholder
     */
    @NotEmpty
    protected String getWildcardParameterName(@NotNull WildcardFilter filter) {
        Parameter parameter = filter.getParameter();

        String parameterName = getParameterName(parameter);
        Preconditions.requireNonEmpty(parameterName, "parameterName must not be empty, filterName: " + filter.getName());

        return parameterName;
    }

    /**
     * 获取条件名称
     *
     * @param filter the {@link Filter} instance
     * @return filter.name
     */
    @NotEmpty
    protected String getFilterName(@NotNull Filter filter) {
        String filterName = filter.getName();
        Preconditions.requireNonEmpty(filterName, "filterName must not be empty");

        return filterName;
    }

    /**
     * 获取参数名称
     *
     * @param parameters [ the {@link Parameter} instance ]
     * @return joined :parameter.name, joined placeholder
     */
    @NotNull
    protected String getParameterName(List<Parameter> parameters) {
        if (parameters == null) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        String delimiter = "";

        for (Parameter parameter : parameters) {
            String parameterName = getParameterName(parameter);
            if (parameterName.isEmpty()) {
                return "";
            }

            result.append(delimiter).append(parameterName);
            delimiter = ", ";
        }

        return result.toString();
    }

    /**
     * 获取参数名称
     *
     * @param parameter the {@link Parameter} instance
     * @return :parameter.name, placeholder
     */
    @NotNull
    protected String getParameterName(Parameter parameter) {
        if (parameter == null) {
            return "";
        }

        String parameterName = parameter.getName();
        if (parameterName.isEmpty()) {
            return PLACEHOLDER;
        } else {
            return ":" + parameterName;
        }
    }

}
