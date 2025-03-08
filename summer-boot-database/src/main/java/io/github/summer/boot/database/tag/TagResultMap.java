package io.github.summer.boot.database.tag;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Parse &#064;MybatisResultMap annotation's result
 *
 * @author changebooks@qq.com
 */
public final class TagResultMap implements Serializable {
    /**
     * table name to java bean name
     */
    private String table;

    /**
     * &lt;resultMap type="class"&gt;
     */
    private Class<?> type;

    /**
     * &lt;id column="jdbc column" jdbcType="jdbc type" property="java field" &#47;&gt;
     * &lt;result column="jdbc column" jdbcType="jdbc type" property="java field" &#47;&gt;
     */
    private List<TagResult> elements;

    @Override
    public String toString() {
        String table = Optional.ofNullable(getTable()).orElse("");
        String type = Optional.ofNullable(getType()).map(Class::getName).orElse("");
        String elements = Optional.ofNullable(getElements())
                .orElse(Collections.emptyList())
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "[", "]"));

        return "{" +
                "\"table\": \"" + table + "\", " +
                "\"type\": \"" + type + "\", " +
                "\"elements\": " + elements +
                "}";
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public List<TagResult> getElements() {
        return elements;
    }

    public void setElements(List<TagResult> elements) {
        this.elements = elements;
    }

}
