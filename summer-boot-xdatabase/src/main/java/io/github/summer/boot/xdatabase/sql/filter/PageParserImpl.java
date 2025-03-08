package io.github.summer.boot.xdatabase.sql.filter;

import io.github.summer.boot.filter.Page;
import io.github.summer.boot.filter.PageParser;
import io.github.summer.boot.xdatabase.sql.pattern.PatternParser;

/**
 * @author changebooks@qq.com
 */
public class PageParserImpl implements PageParser {

    @Override
    public String parsePage(Page page) {
        if (page == null) {
            return null;
        }

        String pattern = PatternParser.parsePage(page);
        if (pattern.isEmpty()) {
            return null;
        } else {
            return "LIMIT " + pattern;
        }
    }

    @Override
    public String parseFirstPage() {
        return "LIMIT 1";
    }

}
