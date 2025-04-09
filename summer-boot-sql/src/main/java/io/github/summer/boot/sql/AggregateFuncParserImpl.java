package io.github.summer.boot.sql;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author changebooks@qq.com
 */
public class AggregateFuncParserImpl implements AggregateFuncParser {

    @Override
    public String parseAggregateFunc(List<AggregateFunc> list) {
        if (list == null) {
            return null;
        }

        return list.stream()
                .peek(aggregateFunc -> Preconditions.requireNonNull(aggregateFunc, "aggregateFunc must not be null"))
                .map(this::parseAggregateFunc)
                .collect(Collectors.joining(", "))
                .trim();
    }

    @Override
    public String parseAggregateFunc(AggregateFunc aggregateFunc) {
        if (aggregateFunc == null) {
            return null;
        }

        int aggregateCode = aggregateFunc.getCode();
        String columnName = aggregateFunc.getName();

        AggregateType aggregateType = AggregateType.forCode(aggregateCode);
        if (aggregateType == null) {
            return columnName;
        }

        if (aggregateType.name.isEmpty()) {
            return columnName;
        } else {
            return String.format("%s(%s)", aggregateType.name, columnName);
        }
    }

}
