package io.github.summer.boot.sql;

import java.util.List;

/**
 * 解析排序
 *
 * @author changebooks@qq.com
 */
public interface OrderParser {
    /**
     * 解析列表
     *
     * @param list [ the {@link Order} instance ]
     * @return ORDER BY name, name ASC, name DESC
     */
    String parse(List<Order> list);

    /**
     * 解析
     *
     * @param order the {@link Order} instance
     * @return ORDER BY name, ORDER BY name ASC, ORDER BY name DESC
     */
    String parse(Order order);

}
