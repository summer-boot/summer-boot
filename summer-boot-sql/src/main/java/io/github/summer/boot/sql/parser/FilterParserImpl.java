package io.github.summer.boot.sql.parser;

import io.github.summer.boot.filter.*;
import io.github.summer.boot.sql.Preconditions;
import io.github.summer.boot.value.Parameter;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author changebooks@qq.com
 */
public class FilterParserImpl implements FilterParser {
    /**
     * Prepared Statement Setter Placeholder
     */
    private static final String PLACEHOLDER = "?";

    @Override
    public String parseFilter(ExpressionFilter filter) {
        String pattern = PatternParser.parseExpression(filter);

        Parameter parameter = filter.getParameter();
        String parameterName = getParameterName(parameter);

        return String.format(pattern, parameterName);
    }

    @Override
    public String parseFilter(InFilter filter) {
        String pattern = PatternParser.parseIn(filter);

        List<Parameter> parameters = filter.getParameters();
        String parameterName = getParameterName(parameters);

        return String.format(pattern, parameterName);
    }

    @Override
    public String parseFilter(NullFilter filter) {
        return PatternParser.parseNull(filter);
    }

    @Override
    public String parseFilter(RangeFilter filter) {
        String fromSql = PatternParser.parseRangeFrom(filter);
        String toSql = PatternParser.parseRangeTo(filter);

        return fromSql + " AND " + toSql;
    }

    @Override
    public String parseFilter(WildcardFilter filter) {
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
                .map(this::getParameterName)
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
        String parameterName = parameter != null ? parameter.getName() : "";
        if (parameterName.isEmpty()) {
            return PLACEHOLDER;
        } else {
            return ":" + parameterName;
        }
    }

}
