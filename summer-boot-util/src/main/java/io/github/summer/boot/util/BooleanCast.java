package io.github.summer.boot.util;

/**
 * Boolean类型转换
 *
 * <pre>
 * 1 to True
 * !1 to False
 * True to 1
 * False to 0
 * </pre>
 *
 * @author changebooks@qq.com
 */
public final class BooleanCast {
    /**
     * 1-True
     * 0-False
     */
    public static final int TRUE = 1;
    public static final int FALSE = 0;

    public static Boolean fromInt(Integer value) {
        if (value != null) {
            return value == TRUE;
        } else {
            return null;
        }
    }

    public static Integer toInt(Boolean value) {
        if (value != null) {
            return value ? TRUE : FALSE;
        } else {
            return null;
        }
    }

}
