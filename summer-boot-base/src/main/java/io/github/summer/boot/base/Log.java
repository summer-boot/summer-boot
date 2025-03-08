package io.github.summer.boot.base;

import java.io.Serializable;

/**
 * 日志
 *
 * @author changebooks@qq.com
 */
public final class Log implements Serializable {
    /**
     * 日志id
     */
    private String id;

    /**
     * 日志码
     */
    private String code;

    /**
     * 日志信息
     */
    private String message;

    @Override
    public String toString() {
        return JsonParser.toJson(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
