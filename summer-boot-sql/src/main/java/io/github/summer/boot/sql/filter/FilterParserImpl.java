package io.github.summer.boot.sql.filter;

import io.github.summer.boot.filter.*;
import io.github.summer.boot.sql.Preconditions;
import io.github.summer.boot.sql.pattern.PatternParser;
import io.github.summer.boot.value.Parameter;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public String parseFilter(@NotNull ExpressionFilter filter) {
        String pattern = PatternParser.parseExpression(filter);

        Parameter parameter = filter.getParameter();
        String parameterName = getParameterName(parameter);

        return String.format(pattern, parameterName);
    }

    @NotNull
    @Override
    public String parseFilter(@NotNull InFilter filter) {
        String pattern = PatternParser.parseIn(filter);

        List<Parameter> parameters = filter.getParameters();
        String parameterName = getParameterName(parameters);

        return String.format(pattern, parameterName);
    }

    @NotNull
    @Override
    public String parseFilter(@NotNull NullFilter filter) {
        return PatternParser.parseNull(filter);
    }

    @NotNull
    @Override
    public String parseFilter(@NotNull RangeFilter filter) {
        String fromSql = PatternParser.parseRangeFrom(filter);
        String toSql = PatternParser.parseRangeTo(filter);

        Parameter from = filter.getFrom();
        Parameter to = filter.getTo();

        if (from != null) {
            if (to != null) {
                return String.format("%s %s %s", fromSql, LogicalOperator.AND, toSql);
            } else {
                return fromSql;
            }
        } else {
            if (to != null) {
                return toSql;
            } else {
                return "";
            }
        }
    }

    @NotNull
    @Override
    public String parseFilter(@NotNull WildcardFilter filter) {
        String pattern = PatternParser.parseWildcard(filter);

        Parameter parameter = filter.getParameter();
        String parameterName = getParameterName(parameter);

        return String.format(pattern, parameterName);
    }

    /**
     * 获取参数名称
     *
     * @param parameters [ the {@link Parameter} instance ]
     * @return joined parameter.name, joined placeholder
     */
    @NotNull
    String getParameterName(List<Parameter> parameters) {
        Preconditions.requireNonNull(parameters, "parameters must not be null");
        Preconditions.requireNonEmpty(parameters, "parameters must not be empty");

        return parameters.stream()
                .filter(Objects::nonNull)
                .map(this::getParameterName)
                .filter(x -> !x.isEmpty())
                .collect(Collectors.joining(", "));
    }

    /**
     * 获取参数名称
     *
     * @param parameter the {@link Parameter} instance
     * @return parameter.name, placeholder
     */
    @NotNull
    String getParameterName(Parameter parameter) {
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
