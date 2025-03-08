package io.github.summer.boot.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果
 *
 * @author changebooks@qq.com
 */
public class PageResponse<T> implements Serializable {
    /**
     * 总行数
     */
    private long totalNum;

    /**
     * 内容
     */
    private List<T> data;

    /**
     * 分页条件
     */
    private PageRequest request;

    public long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(long totalNum) {
        this.totalNum = totalNum;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public PageRequest getRequest() {
        return request;
    }

    public void setRequest(PageRequest request) {
        this.request = request;
    }

}
