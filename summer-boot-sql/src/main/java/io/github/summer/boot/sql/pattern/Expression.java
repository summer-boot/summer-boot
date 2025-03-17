package io.github.summer.boot.sql.pattern;

import io.github.summer.boot.filter.ExpressionCode;
import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 表达式
 *
 * @author changebooks@qq.com
 */
public enum Expression {
    /**
     * 编码 : 格式 : 描述
     */
    NULL(ExpressionCode.NULL, "", "未知"),
    EQ(ExpressionCode.EQ, "%s = %s", "等于"),
    NE(ExpressionCode.NE, "%s != %s", "不等于"),
    GT(ExpressionCode.GT, "%s > %s", "大于"),
    LT(ExpressionCode.LT, "%s < %s", "小于"),
    GE(ExpressionCode.GE, "%s >= %s", "大于等于"),
    LE(ExpressionCode.LE, "%s <= %s", "小于等于"),

    ;

    /**
     * [ 编码 : 枚举类 ]
     */
    private static final Map<Integer, Expression> DATA = new HashMap<>();

    static {
        Expression[] values = Expression.values();
        for (Expression x : values) {
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

    Expression(int code, String pattern, String message) {
        this.code = code;
        this.pattern = pattern;
        this.message = message;
    }

    /**
     * 编码 to 表达式
     *
     * @param code 编码
     * @return 表达式
     */
    @NotNull
    public static Expression forCode(Integer code) {
        Expression result = forCodeNullable(code);
        return Objects.requireNonNullElse(result, Expression.NULL);
    }

    /**
     * 编码 to 表达式
     *
     * @param code 编码
     * @return 表达式
     */
    public static Expression forCodeNullable(Integer code) {
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
     * 等于？
     *
     * @param code 编码
     * @return True-等于、False-其他
     */
    public static boolean isEq(Integer code) {
        return EQ.code == code;
    }

    /**
     * 不等于？
     *
     * @param code 编码
     * @return True-不等于、False-其他
     */
    public static boolean isNe(Integer code) {
        return NE.code == code;
    }

    /**
     * 大于？
     *
     * @param code 编码
     * @return True-大于、False-其他
     */
    public static boolean isGt(Integer code) {
        return GT.code == code;
    }

    /**
     * 小于？
     *
     * @param code 编码
     * @return True-小于、False-其他
     */
    public static boolean isLt(Integer code) {
        return LT.code == code;
    }

    /**
     * 大于等于？
     *
     * @param code 编码
     * @return True-大于等于、False-其他
     */
    public static boolean isGe(Integer code) {
        return GE.code == code;
    }

    /**
     * 小于等于？
     *
     * @param code 编码
     * @return True-小于等于、False-其他
     */
    public static boolean isLe(Integer code) {
        return LE.code == code;
    }

}
