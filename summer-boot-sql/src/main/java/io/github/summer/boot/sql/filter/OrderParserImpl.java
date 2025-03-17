package io.github.summer.boot.sql.filter;

import io.github.summer.boot.filter.Order;
import io.github.summer.boot.filter.OrderParser;

import java.util.List;

/**
 * @author changebooks@qq.com
 */
public class OrderParserImpl implements OrderParser {

    @Override
    public String parseOrder(List<Order> orders) {
        if (orders == null) {
            return null;
        }

        String pattern = PatternParser.parseOrder(orders);
        if (pattern.isEmpty()) {
            return null;
        } else {
            return "ORDER BY " + pattern;
        }
    }

    @Override
    public String parseOrder(Order order) {
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
