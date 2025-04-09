package io.github.summer.boot.sql;

import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 连表方式
 *
 * @author changebooks@qq.com
 */
public enum JoinType {
    /**
     * 编码 : 名称 : 描述
     */
    NULL(JoinCode.NULL, "", "未知"),
    INNER(JoinCode.INNER, "INNER JOIN", "内连"),
    OUTER(JoinCode.OUTER, "OUTER JOIN", "外连"),
    LEFT(JoinCode.LEFT, "LEFT JOIN", "左外"),
    RIGHT(JoinCode.RIGHT, "RIGHT JOIN", "右外"),
    FULL(JoinCode.FULL, "FULL JOIN", "全量"),
    CROSS(JoinCode.CROSS, "CROSS JOIN", "交叉"),
    SELF(JoinCode.SELF, "SELF JOIN", "自连"),
    NATURAL(JoinCode.NATURAL, "NATURAL JOIN", "自然"),
    STRAIGHT(JoinCode.STRAIGHT, "STRAIGHT JOIN", "强制顺序"),

    ;

    /**
     * [ 编码 : 枚举 ]
     */
    private static final Map<Integer, JoinType> DATA = new HashMap<>();

    static {
        JoinType[] values = JoinType.values();
        for (JoinType x : values) {
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

    JoinType(int code, String name, String message) {
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
    public static JoinType forCode(Integer code) {
        JoinType result = forCodeNullable(code);
        return result != null ? result : NULL;
    }

    /**
     * 编码 to 枚举
     *
     * @param code 编码
     * @return 枚举
     */
    public static JoinType forCodeNullable(Integer code) {
        if (code != null) {
            return DATA.get(code);
        } else {
            return null;
        }
    }

    /**
     * 内连？
     *
     * @param code 编码
     * @return True-内连、False-其他
     */
    public static boolean isInner(Integer code) {
        return INNER.code == code;
    }

    /**
     * 外连？
     *
     * @param code 编码
     * @return True-外连、False-其他
     */
    public static boolean isOuter(Integer code) {
        return OUTER.code == code;
    }

    /**
     * 左外？
     *
     * @param code 编码
     * @return True-左外、False-其他
     */
    public static boolean isLeft(Integer code) {
        return LEFT.code == code;
    }

    /**
     * 右外？
     *
     * @param code 编码
     * @return True-右外、False-其他
     */
    public static boolean isRight(Integer code) {
        return RIGHT.code == code;
    }

    /**
     * 全量？
     *
     * @param code 编码
     * @return True-全量、False-其他
     */
    public static boolean isFull(Integer code) {
        return FULL.code == code;
    }

    /**
     * 交叉？
     *
     * @param code 编码
     * @return True-交叉、False-其他
     */
    public static boolean isCross(Integer code) {
        return CROSS.code == code;
    }

    /**
     * 自连？
     *
     * @param code 编码
     * @return True-自连、False-其他
     */
    public static boolean isSelf(Integer code) {
        return SELF.code == code;
    }

    /**
     * 自然？
     *
     * @param code 编码
     * @return True-自然、False-其他
     */
    public static boolean isNatural(Integer code) {
        return NATURAL.code == code;
    }

    /**
     * 强制顺序？
     *
     * @param code 编码
     * @return True-强制顺序、False-其他
     */
    public static boolean isStraight(Integer code) {
        return STRAIGHT.code == code;
    }

}
