package io.github.summer.boot.dto;

import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 模糊匹配
 *
 * @author changebooks@qq.com
 */
public enum Wildcard {
    /**
     * 编码 : 格式 : 描述
     */
    NULL(WildcardCode.NULL, "", "未知"),
    CONTAINS(WildcardCode.CONTAINS, "%s LIKE '%%%s%%'", "包含"),
    STARTS(WildcardCode.STARTS, "%s LIKE '%s%%'", "开始于"),
    ENDS(WildcardCode.ENDS, "%s LIKE '%%%s'", "结束于"),

    ;

    /**
     * [ 编码 : 枚举类 ]
     */
    private static final Map<Integer, Wildcard> DATA = new HashMap<>();

    static {
        Wildcard[] values = Wildcard.values();
        for (Wildcard x : values) {
            DATA.put(x.code, x);
        }
    }

    /**
     * 编码
     */
    public final int code;

    /**
     * 格式
     */
    public final String pattern;

    /**
     * 描述
     */
    public final String message;

    Wildcard(int code, String pattern, String message) {
        this.code = code;
        this.pattern = pattern;
        this.message = message;
    }

    /**
     * 编码 to 模糊匹配
     *
     * @param code 编码
     * @return 模糊匹配
     */
    @NotNull
    public static Wildcard forCode(Integer code) {
        Wildcard result = forCodeNullable(code);
        return Objects.requireNonNullElse(result, Wildcard.NULL);
    }

    /**
     * 编码 to 模糊匹配
     *
     * @param code 编码
     * @return 模糊匹配
     */
    public static Wildcard forCodeNullable(Integer code) {
        if (code != null) {
            return DATA.get(code);
        } else {
            return null;
        }
    }

    /**
     * 包含？
     *
     * @param code 编码
     * @return True-包含、False-其他
     */
    public static boolean isContains(Integer code) {
        return CONTAINS.code == code;
    }

    /**
     * 开始于？
     *
     * @param code 编码
     * @return True-开始于、False-其他
     */
    public static boolean isStarts(Integer code) {
        return STARTS.code == code;
    }

    /**
     * 结束于？
     *
     * @param code 编码
     * @return True-结束于、False-其他
     */
    public static boolean isEnds(Integer code) {
        return ENDS.code == code;
    }

}
