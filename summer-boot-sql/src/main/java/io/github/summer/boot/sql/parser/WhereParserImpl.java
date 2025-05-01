package io.github.summer.boot.sql.parser;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.filter.FilterParser;
import io.github.summer.boot.filter.Statement;
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
    public Statement parse(List<BaseFilter> list) {
        return null;
    }

    @Override
    public Statement parse(BaseFilter filter) {
        return null;
    }

    @Override
    public String prefixed(String sql) {
        return null;
    }

    public FilterParser getFilterParser() {
        return filterParser;
    }

}
