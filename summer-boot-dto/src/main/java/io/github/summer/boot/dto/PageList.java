package io.github.summer.boot.dto;

import java.util.List;

/**
 * 分页结果
 *
 * @author changebooks@qq.com
 */
public class PageList<T> {
    /**
     * 总行数
     */
    private long totalNum;

    /**
     * 内容
     */
    private List<T> data;

    public PageList() {
        this.totalNum = 0;
        this.data = null;
    }

    public PageList(long totalNum, List<T> data) {
        this.totalNum = totalNum;
        this.data = data;
    }

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

}
