package io.github.summer.boot.sql;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

/**
 * 连表
 *
 * @author changebooks@qq.com
 */
public class Table implements Serializable {
    /**
     * INNER, OUTER, LEFT, RIGHT, FULL, CROSS, SELF, NATURAL, STRAIGHT
     */
    private Join join;

    /**
     * 名称
     */
    private String name;

    /**
     * [ table1.column1 = table2.column2 ]
     */
    private List<String> ons;

    public Join getJoin() {
        return join;
    }

    public void setJoin(Join join) {
        this.join = join;
    }

    @NotNull
    public String getName() {
        return name != null ? name : "";
    }

    public void setName(String name) {
        this.name = name != null ? name.trim() : "";
    }

    public List<String> getOns() {
        return ons;
    }

    public void setOns(List<String> ons) {
        this.ons = ons;
    }

}
