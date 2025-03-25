package io.github.summer.boot.filter;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * 分组
 *
 * @author changebooks@qq.com
 */
public class Group implements Serializable {
    /**
     * 名称
     */
    private String name;

    @Override
    public String toString() {
        String name = getName();
        return "{\"name\": \"%s\"}".formatted(name);
    }

    @NotNull
    public String getName() {
        return name != null ? name : "";
    }

    public void setName(String name) {
        this.name = name != null ? name.trim() : null;
    }

}
