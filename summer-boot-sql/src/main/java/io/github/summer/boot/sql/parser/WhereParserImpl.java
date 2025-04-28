package io.github.summer.boot.sql.parser;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.filter.FilterParser;
import io.github.summer.boot.filter.SqlParameter;
import io.github.summer.boot.sql.WhereParser;
import io.github.summer.boot.sql.filter.FilterParserImpl;

import java.util.List;

/**
 * @author changebooks@qq.com
 */
public class WhereParserImpl implements WhereParser {
    /**
     * the {@link FilterParser} instance
     */
    private final FilterParser filterParser;

    public WhereParserImpl() {
        this.filterParser = new FilterParserImpl();
    }

    @Override
    public SqlParameter parse(List<BaseFilter> list) {
        FilterParser filterParser = getFilterParser();
        return filterParser.parse(list, "WHERE");
    }

    @Override
    public SqlParameter parse(BaseFilter filter) {
        FilterParser filterParser = getFilterParser();
        return filterParser.parse(filter, "WHERE");
    }

    @Override
    public String prefixed(String sql) {
        if (sql == null) {
            return null;
        }

        if (sql.isBlank()) {
            return "";
        } else {
            return "WHERE " + sql;
        }
    }

    public FilterParser getFilterParser() {
        return filterParser;
    }

}
