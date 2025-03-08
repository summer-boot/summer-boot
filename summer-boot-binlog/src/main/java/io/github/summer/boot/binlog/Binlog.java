package io.github.summer.boot.binlog;

import io.github.summer.boot.base.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;

/**
 * Binary log Schema
 *
 * @author changebooks@qq.com
 */
public final class Binlog implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Binlog.class);

    /**
     * 库名
     */
    private String db;

    /**
     * 表名
     */
    private String table;

    /**
     * 操作方式，1-查询、2-新增、3-修改、4-删除
     */
    private Integer op;

    /**
     * 操作时间|毫秒
     */
    private Long timestamp;

    /**
     * 日志位置
     */
    private String position;

    /**
     * 操作前记录
     */
    private Map<String, Object> before;

    /**
     * 操作后记录
     */
    private Map<String, Object> after;

    /**
     * 解析Json
     *
     * @param json the json string
     * @return the {@link Binlog} instance
     */
    public static Binlog fromJson(String json) {
        if (json == null) {
            LOGGER.error("fromJson failed, json must not be null");
            return null;
        }

        if (json.isEmpty()) {
            LOGGER.error("fromJson failed, json must not be empty");
            return null;
        }

        return JsonParser.fromJson(json, Binlog.class);
    }

    @Override
    public String toString() {
        return JsonParser.toJson(this);
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Integer getOp() {
        return op;
    }

    public void setOp(Integer op) {
        this.op = op;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Map<String, Object> getBefore() {
        return before;
    }

    public void setBefore(Map<String, Object> before) {
        this.before = before;
    }

    public Map<String, Object> getAfter() {
        return after;
    }

    public void setAfter(Map<String, Object> after) {
        this.after = after;
    }

}
