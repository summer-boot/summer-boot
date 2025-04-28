package io.github.summer.boot.sql.parser;

import io.github.summer.boot.sql.Order;
import io.github.summer.boot.sql.OrderParser;
import io.github.summer.boot.sql.pattern.PatternParser;

import java.util.List;

/**
 * @author changebooks@qq.com
 */
public class OrderParserImpl implements OrderParser {

    @Override
    public String parse(List<Order> list) {
        if (list == null) {
            return null;
        } else {
            String pattern = PatternParser.parseOrder(list);
            return prefixed(pattern);
        }
    }

    @Override
    public String parse(Order order) {
        if (order == null) {
            return null;
        } else {
            String pattern = PatternParser.parseOrder(order);
            return prefixed(pattern);
        }
    }

    /**
     * 连接前缀
     *
     * @param sql name, name ASC, name DESC
     * @return ORDER BY name, name ASC, name DESC
     */
    public String prefixed(String sql) {
        if (sql == null) {
            return null;
        }

        if (sql.isBlank()) {
            return "";
        } else {
            return "ORDER BY " + sql;
        }
    }

}
