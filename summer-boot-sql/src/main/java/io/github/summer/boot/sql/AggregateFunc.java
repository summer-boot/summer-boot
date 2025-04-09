package io.github.summer.boot.sql;

import java.io.Serializable;

/**
 * 聚合函数
 *
 * @author changebooks@qq.com
 */
public final class AggregateFunc implements Serializable {
    /**
     * 编码
     */
    private int aggregateCode;

    /**
     * 名称
     */
    private String name;

    public int getAggregateCode() {
        return aggregateCode;
    }

    public void setAggregateCode(int aggregateCode) {
        this.aggregateCode = aggregateCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
