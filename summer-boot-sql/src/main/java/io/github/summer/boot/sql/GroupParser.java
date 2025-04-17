package io.github.summer.boot.sql;

import io.github.summer.boot.filter.Statement;

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
     * @return the {@link Statement} instance
     */
    Statement parse(Group group);

}
