package io.github.summer.boot.dto;

/**
 * 滚屏分页
 *
 * @author changebooks@qq.com
 */
public class ScrollParameter extends PageCriteria {
    /**
     * 开始索引
     */
    private Long startId;

    /**
     * 每页行数
     */
    private Integer pageSize;

    public Long getStartId() {
        return startId;
    }

    public void setStartId(Long startId) {
        this.startId = startId;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
