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
     * 编码
     */
    private int joinCode;

    /**
     * FROM table JOIN
     */
    private String from;

    /**
     * FROM JOIN table
     */
    private String to;

    /**
     * [ 连表字段 ]
     */
    private List<JoinOn> joinOn;

    public int getJoinCode() {
        return joinCode;
    }

    public void setJoinCode(int joinCode) {
        this.joinCode = joinCode;
    }

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

    public List<JoinOn> getJoinOn() {
        return joinOn;
    }

    public void setJoinOn(List<JoinOn> joinOn) {
        this.joinOn = joinOn;
    }

}
