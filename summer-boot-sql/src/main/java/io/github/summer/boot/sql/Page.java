package io.github.summer.boot.sql;

import java.io.Serializable;

/**
 * 分页
 *
 * @author changebooks@qq.com
 */
public class Page implements Serializable {
    /**
     * 开始行数
     */
    private Long offset;

    /**
     * 每页行数
     */
    private Integer limit;

    @Override
    public String toString() {
        Long offset = getOffset();
        Integer limit = getLimit();
        return "{\"offset\": %d, \"limit\": %d}".formatted(offset, limit);
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

}
