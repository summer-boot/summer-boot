package io.github.summer.boot.binlog;

import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 操作方式
 *
 * @author changebooks@qq.com
 */
public enum BinlogOperation {
    /**
     * 编码 : 格式 : 描述
     */
    NULL(BinlogOperationCode.NULL, "未知"),
    SELECT(BinlogOperationCode.SELECT, "查询"),
    INSERT(BinlogOperationCode.INSERT, "新增"),
    UPDATE(BinlogOperationCode.UPDATE, "修改"),
    DELETE(BinlogOperationCode.DELETE, "删除"),

    ;

    /**
     * [ 编码 : 枚举类 ]
     */
    private static final Map<Integer, BinlogOperation> DATA = new HashMap<>();

    static {
        BinlogOperation[] values = BinlogOperation.values();
        for (BinlogOperation x : values) {
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

    BinlogOperation(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 编码 to 表达式
     *
     * @param code 编码
     * @return 表达式
     */
    @NotNull
    public static BinlogOperation forCode(Integer code) {
        BinlogOperation result = forCodeNullable(code);
        return Objects.requireNonNullElse(result, BinlogOperation.NULL);
    }

    /**
     * 编码 to 表达式
     *
     * @param code 编码
     * @return 表达式
     */
    public static BinlogOperation forCodeNullable(Integer code) {
        if (code != null) {
            return DATA.get(code);
        } else {
            return null;
        }
    }

    /**
     * 查询？
     *
     * @param code 编码
     * @return True-等于、False-其他
     */
    public static boolean isSelect(Integer code) {
        return SELECT.code == code;
    }

    /**
     * 新增？
     *
     * @param code 编码
     * @return True-等于、False-其他
     */
    public static boolean isInsert(Integer code) {
        return INSERT.code == code;
    }

    /**
     * 修改？
     *
     * @param code 编码
     * @return True-等于、False-其他
     */
    public static boolean isUpdate(Integer code) {
        return UPDATE.code == code;
    }

    /**
     * 删除？
     *
     * @param code 编码
     * @return True-等于、False-其他
     */
    public static boolean isDelete(Integer code) {
        return DELETE.code == code;
    }

}
