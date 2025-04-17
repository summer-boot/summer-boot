package io.github.summer.boot.sql;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.filter.Statement;

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
     * @return the {@link Statement} instance
     */
    Statement parse(List<BaseFilter> list);

    /**
     * 解析
     *
     * @param filter the {@link BaseFilter} instance
     * @return the {@link Statement} instance
     */
    Statement parse(BaseFilter filter);

}
