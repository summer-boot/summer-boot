package io.github.summer.boot.sql;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author changebooks@qq.com
 */
public class JoinTableParserImpl implements JoinTableParser {

    @Override
    public String parseJoinTable(List<JoinTable> list) {
        if (list == null) {
            return null;
        }

        return list.stream()
                .peek(joinTable -> Preconditions.requireNonNull(joinTable, "joinTable must not be null"))
                .map(this::parseJoinTable)
                .collect(Collectors.joining(", "))
                .trim();
    }

    @Override
    public String parseJoinTable(JoinTable joinTable) {
        if (joinTable == null) {
            return null;
        }

        int joinCode = joinTable.getCode();
        String table = joinTable.getTable();
        List<String> on = joinTable.getOn();

        JoinType joinType = JoinType.forCode(joinCode);
        Preconditions.requireNonEmpty(joinType.name, "joinType.name must not be empty");

        String joinOn = Optional.ofNullable(on)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" AND "));
        return String.format("%s %s ON %s", joinType.name, table, joinOn);
    }

}
