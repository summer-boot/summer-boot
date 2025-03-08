package io.github.summer.boot.value;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Optional;

/**
 * 参数
 *
 * @author changebooks@qq.com
 */
public final class Parameter implements Serializable {
    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private Value value;

    @Override
    public String toString() {
        String name = getName();
        int type = getType();
        String value = Optional.ofNullable(getValue()).map(Value::toString).map(x -> '"' + x + '"').orElse(null);
        return "{\"name\": \"%s\", \"type\": %d, \"value\": %s}".formatted(name, type, value);
    }

    @NotNull
    public String getName() {
        return name != null ? name : "";
    }

    public void setName(String name) {
        this.name = name != null ? name.trim() : null;
    }

    public int getType() {
        return value != null ? value.getType() : Types.OBJECT;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

}
