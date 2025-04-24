package io.github.summer.boot.sql;

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

    /**
     * 连接前缀
     *
     * @param sql column
     * @return GROUP BY column
     */
    String prefixedGroup(String sql);

    /**
     * 连接前缀
     *
     * @param sql column = ?
     * @return HAVING column = ?
     */
    String prefixedHaving(String sql);

}
