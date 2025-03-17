package io.github.summer.boot.sql;

import jakarta.validation.constraints.NotNull;

import java.util.Collection;

/**
 * 前提条件
 *
 * @author changebooks@qq.com
 */
public final class Preconditions {

    private Preconditions() {
    }

    /**
     * 非空对象？not null
     * %s must not be null
     *
     * @param obj     the check object
     * @param message the error message
     * @throws NullPointerException if the object is null
     */
    public static void requireNonNull(Object obj, String message) {
        if (obj == null) {
            throw new NullPointerException(message);
        }
    }

    /**
     * 非空字符串？not empty ("")
     * %s must not be empty
     *
     * @param str     the check string
     * @param message the error message
     * @throws IllegalArgumentException if the str is empty
     */
    public static void requireNonEmpty(@NotNull String str, String message) {
        if (str.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 非空白字符串？not empty ("") and not blank (" ")
     * %s must not be blank
     *
     * @param str     the check string
     * @param message the error message
     * @throws IllegalArgumentException if the str is blank
     */
    public static void requireNonBlank(@NotNull String str, String message) {
        if (str.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 非空集合？not empty
     * %s must not be empty
     *
     * @param collection the check collection
     * @param message    the error message
     * @throws IllegalArgumentException if the collection is empty
     */
    public static void requireNonEmpty(@NotNull Collection<?> collection, String message) {
        if (collection.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

}
