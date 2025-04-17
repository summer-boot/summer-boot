package io.github.summer.boot.sql;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * 排序
 *
 * @author changebooks@qq.com
 */
public class Order implements Serializable {
    /**
     * 名称
     */
    private String name;

    /**
     * 降序？
     */
    private Boolean desc;

    @Override
    public String toString() {
        String name = getName();
        Boolean desc = getDesc();
        return "{\"name\": \"%s\", \"desc\": %s}".formatted(name, desc);
    }

    @NotNull
    public String getName() {
        return name != null ? name : "";
    }

    public void setName(String name) {
        this.name = name != null ? name.trim() : "";
    }

    public Boolean getDesc() {
        return desc;
    }

    public void setDesc(Boolean desc) {
        this.desc = desc;
    }

}
