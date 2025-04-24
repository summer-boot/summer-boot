package io.github.summer.boot.sql.parser;

import io.github.summer.boot.sql.Order;
import io.github.summer.boot.sql.OrderParser;

import java.util.List;

/**
 * @author changebooks@qq.com
 */
public class OrderParserImpl implements OrderParser {

    @Override
    public String parse(List<Order> list) {
        if (list == null) {
            return null;
        }

        String pattern = PatternParser.parseOrder(list);
        if (pattern.isEmpty()) {
            return null;
        } else {
            return "ORDER BY " + pattern;
        }
    }

    @Override
    public String parse(Order order) {
        if (order == null) {
            return null;
        }

        String pattern = PatternParser.parseOrder(order);
        if (pattern.isEmpty()) {
            return null;
        } else {
            return "ORDER BY " + pattern;
        }
    }

}
