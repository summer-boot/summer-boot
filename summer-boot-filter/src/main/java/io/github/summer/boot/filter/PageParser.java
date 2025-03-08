package io.github.summer.boot.filter;

/**
 * 分页
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
    String parsePage(Page page);

    /**
     * 首页
     *
     * @return LIMIT 1
     */
    String parseFirstPage();

}
