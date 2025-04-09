package io.github.summer.boot.sql;

import java.io.Serializable;

/**
 * 聚合函数
 *
 * @author changebooks@qq.com
 */
public final class AggregateFunc implements Serializable {
    /**
     * Aggregate Code
     */
    private int code;

    /**
     * Column Name
     */
    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
