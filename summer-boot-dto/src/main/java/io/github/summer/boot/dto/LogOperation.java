package io.github.summer.boot.dto;

import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 操作方式
 *
 * @author changebooks@qq.com
 */
public enum LogOperation {
    /**
     * 编码 : 描述
     */
    NULL(LogOperationCode.NULL, "未知"),
    SELECT(LogOperationCode.SELECT, "查询"),
    INSERT(LogOperationCode.INSERT, "新增"),
    UPDATE(LogOperationCode.UPDATE, "修改"),
    DELETE(LogOperationCode.DELETE, "删除"),

    ;

    /**
     * [ 编码 : 枚举类 ]
     */
    private static final Map<Integer, LogOperation> DATA = new HashMap<>();

    static {
        LogOperation[] values = LogOperation.values();
        for (LogOperation x : values) {
            DATA.put(x.code, x);
        }
    }

    /**
     * 编码
     */
    public final int code;

    /**
     * 描述
     */
    public final String message;

    LogOperation(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 编码 to 操作方式
     *
     * @param code 编码
     * @return 操作方式
     */
    @NotNull
    public static LogOperation forCode(Integer code) {
        LogOperation result = forCodeNullable(code);
        return Objects.requireNonNullElse(result, LogOperation.NULL);
    }

    /**
     * 编码 to 操作方式
     *
     * @param code 编码
     * @return 操作方式
     */
    public static LogOperation forCodeNullable(Integer code) {
        if (code != null) {
            return DATA.get(code);
        } else {
            return null;
        }
    }

    /**
     * 未知？
     *
     * @param code 编码
     * @return True-未知、False-其他
     */
    public static boolean isNull(Integer code) {
        return NULL.code == code;
    }

    /**
     * 查询？
     *
     * @param code 编码
     * @return True-查询、False-其他
     */
    public static boolean isSelect(Integer code) {
        return SELECT.code == code;
    }

    /**
     * 新增？
     *
     * @param code 编码
     * @return True-新增、False-其他
     */
    public static boolean isInsert(Integer code) {
        return INSERT.code == code;
    }

    /**
     * 修改？
     *
     * @param code 编码
     * @return True-修改、False-其他
     */
    public static boolean isUpdate(Integer code) {
        return UPDATE.code == code;
    }

    /**
     * 删除？
     *
     * @param code 编码
     * @return True-删除、False-其他
     */
    public static boolean isDelete(Integer code) {
        return DELETE.code == code;
    }

}
