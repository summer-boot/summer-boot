package io.github.summer.boot.xdatabase.sql.pattern;

import jakarta.validation.constraints.NotNull;

/**
 * 分页
 *
 * @author changebooks@qq.com
 */
public final class PagePattern {

    private PagePattern() {
    }

    /**
     * 格式
     *
     * @param offset 开始行数
     * @param limit  每页行数
     * @return OFFSET, LIMIT
     */
    @NotNull
    public static String getPattern(Long offset, Integer limit) {
        if (limit == null) {
            return "";
        }

        if (offset == null) {
            return "" + limit;
        } else {
            return offset + ", " + limit;
        }
    }

}
