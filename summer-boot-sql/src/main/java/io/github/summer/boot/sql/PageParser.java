package io.github.summer.boot.sql;

/**
 * 解析分页
 *
 * @author changebooks@qq.com
 */
public interface PageParser {
    /**
     * 解析
     *
     * @param page the {@link Page} instance
     * @return LIMIT offset, limit
     */
    String parse(Page page);

    /**
     * 首页
     *
     * @return LIMIT 1
     */
    String parseFirst();

}
