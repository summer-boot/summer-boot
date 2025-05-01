package io.github.summer.boot.sql.parser;

import io.github.summer.boot.filter.FilterParser;
import io.github.summer.boot.filter.Statement;
import io.github.summer.boot.sql.Group;
import io.github.summer.boot.sql.GroupParser;
import io.github.summer.boot.sql.filter.FilterParserImpl;

/**
 * @author changebooks@qq.com
 */
public class GroupParserImpl implements GroupParser {
    /**
     * the {@link FilterParser} instance
     */
    private final FilterParser filterParser;

    public GroupParserImpl() {
        this.filterParser = new FilterParserImpl();
    }

    @Override
    public Statement parse(Group group) {
        return null;
    }

    public FilterParser getFilterParser() {
        return filterParser;
    }

}
