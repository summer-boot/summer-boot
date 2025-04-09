package io.github.summer.boot.sql;

import java.io.Serializable;

/**
 * 连表字段
 *
 * @author changebooks@qq.com
 */
public final class JoinOn implements Serializable {
    /**
     * ON .column =
     */
    private String from;

    /**
     * ON = .column
     */
    private String to;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

}
