package io.github.summer.boot.sql;

import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 聚合函数类型
 *
 * @author changebooks@qq.com
 */
public enum AggregateType {
    /**
     * 编码 : 名称 : 描述
     */
    NULL(AggregateCode.NULL, "", "未知"),
    COUNT(AggregateCode.COUNT, "COUNT", "总行数"),
    SUM(AggregateCode.SUM, "SUM", "总和"),
    MAX(AggregateCode.MAX, "MAX", "最大值"),
    MIN(AggregateCode.MIN, "MIN", "最小值"),
    AVG(AggregateCode.AVG, "AVG", "平均值"),

    ;

    /**
     * [ 编码 : 枚举 ]
     */
    private static final Map<Integer, AggregateType> DATA = new HashMap<>();

    static {
        AggregateType[] values = AggregateType.values();
        for (AggregateType x : values) {
            DATA.put(x.code, x);
        }
    }

    /**
     * 编码
     */
    public final int code;

    /**
     * 名称
     */
    public final String name;

    /**
     * 描述
     */
    public final String message;

    AggregateType(int code, String name, String message) {
        this.code = code;
        this.name = name;
        this.message = message;
    }

    /**
     * 编码 to 枚举
     *
     * @param code 编码
     * @return 枚举
     */
    @NotNull
    public static AggregateType forCode(Integer code) {
        AggregateType result = forCodeNullable(code);
        return result != null ? result : NULL;
    }

    /**
     * 编码 to 枚举
     *
     * @param code 编码
     * @return 枚举
     */
    public static AggregateType forCodeNullable(Integer code) {
        if (code != null) {
            return DATA.get(code);
        } else {
            return null;
        }
    }

    /**
     * 总行数？
     *
     * @param code 编码
     * @return True-总行数、False-其他
     */
    public static boolean isCount(Integer code) {
        return COUNT.code == code;
    }

    /**
     * 总和？
     *
     * @param code 编码
     * @return True-总和、False-其他
     */
    public static boolean isSum(Integer code) {
        return SUM.code == code;
    }

    /**
     * 最大值？
     *
     * @param code 编码
     * @return True-最大值、False-其他
     */
    public static boolean isMax(Integer code) {
        return MAX.code == code;
    }

    /**
     * 最小值？
     *
     * @param code 编码
     * @return True-最小值、False-其他
     */
    public static boolean isMin(Integer code) {
        return MIN.code == code;
    }

    /**
     * 平均值？
     *
     * @param code 编码
     * @return True-平均值、False-其他
     */
    public static boolean isAvg(Integer code) {
        return AVG.code == code;
    }

}
