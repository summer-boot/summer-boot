package io.github.summer.boot.sql.parser;

import io.github.summer.boot.sql.Page;
import io.github.summer.boot.sql.PageParser;

/**
 * @author changebooks@qq.com
 */
public class PageParserImpl implements PageParser {

    @Override
    public String parse(Page page) {
        if (page == null) {
            return null;
        }

        Integer limit = page.getLimit();
        if (limit == null) {
            return null;
        }

        Long offset = page.getOffset();
        if (offset == null) {
            return "" + limit;
        } else {
            return offset + ", " + limit;
        }
    }

    @Override
    public String parseFirst() {
        return "LIMIT 1";
    }

}
