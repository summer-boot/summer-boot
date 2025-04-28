package io.github.summer.boot.sql;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.filter.SqlParameter;

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
     * @param list [ the {@link BaseFilter} instance ]
     * @return the {@link SqlParameter} instance
     */
    SqlParameter parse(List<BaseFilter> list);

    /**
     * 解析
     *
     * @param filter the {@link BaseFilter} instance
     * @return the {@link SqlParameter} instance
     */
    SqlParameter parse(BaseFilter filter);

    /**
     * 连接前缀
     *
     * @param sql column = ?
     * @return WHERE column = ?
     */
    String prefixed(String sql);

}
