package io.github.summer.boot.filter;

import java.util.List;

/**
 * 分组条件
 *
 * @author changebooks@qq.com
 */
public interface HavingParser {
    /**
     * 解析列表
     *
     * @param filters [ the {@link BaseFilter} instance ]
     * @return the {@link Having} instance
     */
    Having parseHaving(List<BaseFilter> filters);

    /**
     * 解析
     *
     * @param filter the {@link BaseFilter} instance
     * @return the {@link Having} instance
     */
    Having parseHaving(BaseFilter filter);

    /**
     * Prefixed Sql
     *
     * @param sql column = ?
     * @return HAVING column = ?
     */
    String prefixedHaving(String sql);

}
