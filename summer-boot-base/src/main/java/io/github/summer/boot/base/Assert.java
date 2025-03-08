package io.github.summer.boot.base;

/**
 * 前提条件
 *
 * @author changebooks@qq.com
 */
public final class Assert {

    private Assert() {
    }

    /**
     * 非空对象？
     *
     * @param obj     the object
     * @param message the error message
     * @throws NullPointerException if the object is null
     */
    public static void checkNonNull(Object obj, String message) {
        if (obj == null) {
            throw new NullPointerException(message);
        }
    }

    /**
     * 参数正确？
     *
     * @param expression the expression
     * @param message    the error message
     * @throws IllegalArgumentException if the expression is false
     */
    public static void checkArgument(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 正确？
     *
     * @param expression the expression
     * @param message    the error message
     * @throws IllegalStateException if the expression is false
     */
    public static void checkState(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

}
