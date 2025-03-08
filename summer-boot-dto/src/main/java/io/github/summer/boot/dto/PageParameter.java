package io.github.summer.boot.dto;

/**
 * 分页参数
 *
 * @author changebooks@qq.com
 */
public class PageParameter extends PageCriteria {
    /**
     * 开始行数
     */
    private Long startRow;

    /**
     * 每页行数
     */
    private Integer pageSize;

    public Long getStartRow() {
        return startRow;
    }

    public void setStartRow(Long startRow) {
        this.startRow = startRow;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
