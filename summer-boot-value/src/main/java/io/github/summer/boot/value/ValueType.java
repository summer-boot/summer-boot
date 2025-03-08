package io.github.summer.boot.value;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 类型枚举
 *
 * @author changebooks@qq.com
 */
public enum ValueType {
    /**
     * 编码 : 类型 : 描述
     */
    OBJECT(Types.OBJECT, Object.class, "未知"),
    STRING(Types.STRING, String.class, "字符串"),
    INTEGER(Types.INTEGER, Integer.class, "整数"),
    LONG(Types.LONG, Long.class, "长整数"),
    BIG_DECIMAL(Types.BIG_DECIMAL, BigDecimal.class, "小数"),
    DATE(Types.DATE, Date.class, "日期时间"),

    ;

    /**
     * [ 编码 : 枚举 ]
     */
    private static final Map<Integer, ValueType> DATA = new HashMap<>();

    static {
        ValueType[] values = ValueType.values();
        for (ValueType x : values) {
            DATA.put(x.code, x);
        }
    }

    /**
     * 编码
     */
    public final int code;

    /**
     * 类型
     */
    public final Class<?> type;

    /**
     * 描述
     */
    public final String message;

    ValueType(int code, Class<?> type, String message) {
        this.code = code;
        this.type = type;
        this.message = message;
    }

    /**
     * 编码 to 枚举
     *
     * @param code 编码
     * @return 枚举
     */
    @NotNull
    public static ValueType forCode(Integer code) {
        ValueType result = forCodeNullable(code);
        return result != null ? result : OBJECT;
    }

    /**
     * 编码 to 枚举
     *
     * @param code 编码
     * @return 枚举
     */
    public static ValueType forCodeNullable(Integer code) {
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
    public static boolean isObject(Integer code) {
        return OBJECT.code == code;
    }

    /**
     * 字符串？
     *
     * @param code 编码
     * @return True-字符串、False-其他
     */
    public static boolean isString(Integer code) {
        return STRING.code == code;
    }

    /**
     * 整数？
     *
     * @param code 编码
     * @return True-整数、False-其他
     */
    public static boolean isInteger(Integer code) {
        return INTEGER.code == code;
    }

    /**
     * 长整数？
     *
     * @param code 编码
     * @return True-长整数、False-其他
     */
    public static boolean isLong(Integer code) {
        return LONG.code == code;
    }

    /**
     * 小数？
     *
     * @param code 编码
     * @return True-小数、False-其他
     */
    public static boolean isBigDecimal(Integer code) {
        return BIG_DECIMAL.code == code;
    }

    /**
     * 日期时间？
     *
     * @param code 编码
     * @return True-日期时间、False-其他
     */
    public static boolean isDate(Integer code) {
        return DATE.code == code;
    }

}
