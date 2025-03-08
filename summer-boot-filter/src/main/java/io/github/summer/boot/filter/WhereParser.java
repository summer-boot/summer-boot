package io.github.summer.boot.filter;

import java.util.List;

/**
 * 条件
 *
 * @author changebooks@qq.com
 */
public interface WhereParser {
    /**
     * 解析列表
     *
     * @param filters [ the {@link BaseFilter} instance ]
     * @return the {@link Where} instance
     */
    Where parseWhere(List<BaseFilter> filters);

    /**
     * 解析
     *
     * @param filter the {@link BaseFilter} instance
     * @return the {@link Where} instance
     */
    Where parseWhere(BaseFilter filter);

    /**
     * Prefixed Sql
     *
     * @param sql column = ?
     * @return WHERE column = ?
     */
    String prefixedWhere(String sql);

}
