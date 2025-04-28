package io.github.summer.boot.sql;

import io.github.summer.boot.filter.SqlParameter;

/**
 * 分组
 *
 * @author changebooks@qq.com
 */
public interface GroupParser {
    /**
     * 解析
     *
     * @param group the {@link Group} instance
     * @return the {@link SqlParameter} instance
     */
    SqlParameter parse(Group group);

}
