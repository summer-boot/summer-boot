package io.github.summer.boot.sql;

import java.io.Serializable;

/**
 * 聚合函数
 *
 * @author changebooks@qq.com
 */
public class Aggregate implements Serializable {
    /**
     * COUNT, SUM, MAX, MIN, AVG
     */
    private AggregateFunc func;

    /**
     * 名称
     */
    private String name;

    public AggregateFunc getFunc() {
        return func;
    }

    public void setFunc(AggregateFunc func) {
        this.func = func;
    }

    public String getName() {
        return name != null ? name : "";
    }

    public void setName(String name) {
        this.name = name != null ? name.trim() : "";
    }

}
