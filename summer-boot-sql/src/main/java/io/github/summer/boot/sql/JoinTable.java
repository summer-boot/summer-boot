package io.github.summer.boot.sql;

import java.io.Serializable;
import java.util.List;

/**
 * 连表
 *
 * @author changebooks@qq.com
 */
public final class JoinTable implements Serializable {
    /**
     * Join Code
     */
    private int code;

    /**
     * LEFT JOIN table2
     */
    private String table;

    /**
     * [ table1.column1 = table2.column1 ]
     */
    private List<String> on;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<String> getOn() {
        return on;
    }

    public void setOn(List<String> on) {
        this.on = on;
    }

}
