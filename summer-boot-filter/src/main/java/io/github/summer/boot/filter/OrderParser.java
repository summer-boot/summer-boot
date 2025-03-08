package io.github.summer.boot.filter;

import java.util.List;

/**
 * 排序
 *
 * @author changebooks@qq.com
 */
public interface OrderParser {
    /**
     * 解析列表
     *
     * @param orders [ the {@link Order} instance ]
     * @return ORDER BY name, name ASC, name DESC
     */
    String parseOrder(List<Order> orders);

    /**
     * 解析
     *
     * @param order the {@link Order} instance
     * @return ORDER BY name, ORDER BY name ASC, ORDER BY name DESC
     */
    String parseOrder(Order order);

}
