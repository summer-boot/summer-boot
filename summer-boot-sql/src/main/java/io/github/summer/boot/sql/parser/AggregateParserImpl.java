package io.github.summer.boot.sql.parser;

import io.github.summer.boot.sql.Aggregate;
import io.github.summer.boot.sql.AggregateFunc;
import io.github.summer.boot.sql.AggregateParser;

/**
 * @author changebooks@qq.com
 */
public class AggregateParserImpl implements AggregateParser {

    @Override
    public String parse(Aggregate aggregate) {
        if (aggregate == null) {
            return null;
        }

        String columnName = aggregate.getName();
        if (columnName.isEmpty()) {
            return null;
        }

        AggregateFunc func = aggregate.getFunc();
        if (func == null) {
            return columnName;
        } else {
            return String.format("%s(%s)", func.name(), columnName);
        }
    }

}
