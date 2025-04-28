package io.github.summer.boot.sql.parser;

import io.github.summer.boot.filter.LogicalOperator;
import io.github.summer.boot.sql.Join;
import io.github.summer.boot.sql.Preconditions;
import io.github.summer.boot.sql.Table;
import io.github.summer.boot.sql.TableParser;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author changebooks@qq.com
 */
public class TableParserImpl implements TableParser {

    @Override
    public String parse(List<Table> list) {
        if (list == null) {
            return null;
        } else {
            return list.stream()
                    .peek(table -> Preconditions.requireNonNull(table, "table must not be null"))
                    .map(this::parse)
                    .filter(Objects::nonNull)
                    .filter(x -> !x.isEmpty())
                    .collect(Collectors.joining(" "))
                    .trim();
        }
    }

    @Override
    public String parse(Table table) {
        if (table == null) {
            return null;
        }

        String name = table.getName();
        if (name.isEmpty()) {
            return null;
        }

        Join join = table.getJoin();
        if (join == null) {
            return null;
        }

        String ons = String.join(" " + LogicalOperator.AND + " ", table.getOns());
        if (ons.isEmpty()) {
            return String.format("%s %s", join.name(), name);
        } else {
            return String.format("%s %s ON %s", join.name(), name, ons);
        }
    }

}
