package io.github.summer.boot.base;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

/**
 * 校验
 *
 * @author changebooks@qq.com
 */
public final class Check {

    private Check() {
    }

    /**
     * 布尔值True？
     *
     * @param obj the boolean
     * @return not null and true ? true : false
     */
    public static boolean isTrue(Boolean obj) {
        return obj != null && obj;
    }

    /**
     * 空对象？
     *
     * @param obj the object
     * @return null ? true : false
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * 非空对象？
     *
     * @param obj the object
     * @return not null ? true : false
     */
    public static boolean nonNull(Object obj) {
        return obj != null;
    }

    /**
     * 空字符串？
     * <pre>
     * Check.isEmpty(null)      = true
     * Check.isEmpty("")        = true
     * Check.isEmpty(" ")       = false
     * Check.isEmpty("bob")     = false
     * Check.isEmpty("  bob  ") = false
     * </pre>
     *
     * @param str the string
     * @return null or "" ? true : false
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * 非空字符串？
     * <pre>
     * Check.nonEmpty(null)      = false
     * Check.nonEmpty("")        = false
     * Check.nonEmpty(" ")       = true
     * Check.nonEmpty("bob")     = true
     * Check.nonEmpty("  bob  ") = true
     * </pre>
     *
     * @param str the string
     * @return not null and not empty ("") ? true : false
     */
    public static boolean nonEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    /**
     * 空白字符串？
     * <pre>
     * Check.isBlank(null)      = true
     * Check.isBlank("")        = true
     * Check.isBlank(" ")       = true
     * Check.isBlank("bob")     = false
     * Check.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str the string
     * @return null or "" or " " ? true : false
     */
    public static boolean isBlank(String str) {
        return str == null || str.isBlank();
    }

    /**
     * 非空白字符串？
     * <pre>
     * Check.nonBlank(null)      = false
     * Check.nonBlank("")        = false
     * Check.nonBlank(" ")       = false
     * Check.nonBlank("bob")     = true
     * Check.nonBlank("  bob  ") = true
     * </pre>
     *
     * @param str the string
     * @return not null and not empty ("") and not empty (" ") ? true : false
     */
    public static boolean nonBlank(String str) {
        return str != null && !str.isBlank();
    }

    /**
     * 空集合？
     *
     * @param collection the collection
     * @return null or empty ? true : false
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 非空集合？
     *
     * @param collection the collection
     * @return not null and not empty ? true : false
     */
    public static boolean nonEmpty(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    /**
     * 空Map？
     *
     * @param map the map
     * @return null or empty ? true : false
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 非空Map？
     *
     * @param map the map
     * @return not null and not empty ? true : false
     */
    public static boolean nonEmpty(Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }

    /**
     * 类型可转化？
     *
     * @param type the class
     * @param obj  the object
     * @return not null and instanceof ? true : false
     */
    public static boolean isInstance(Class<?> type, Object obj) {
        return type != null && type.isInstance(obj);
    }

    /**
     * 非零？
     *
     * @param num the int
     * @return not null and neq 0 ? true : false
     */
    public static boolean nonZero(Integer num) {
        return num != null && num != 0;
    }

    /**
     * 非零？
     *
     * @param num the long
     * @return not null and neq 0 ? true : false
     */
    public static boolean nonZero(Long num) {
        return num != null && num != 0L;
    }

    /**
     * 非零？
     *
     * @param num the decimal
     * @return not null and neq 0 ? true : false
     */
    public static boolean nonZero(BigDecimal num) {
        return num != null && num.compareTo(BigDecimal.ZERO) != 0;
    }

    /**
     * 正数？
     *
     * @param num the int
     * @return not null and gt 0 ? true : false
     */
    public static boolean isPositive(Integer num) {
        return num != null && num > 0;
    }

    /**
     * 正数？
     *
     * @param num the long
     * @return not null and gt 0 ? true : false
     */
    public static boolean isPositive(Long num) {
        return num != null && num > 0L;
    }

    /**
     * 正数？
     *
     * @param num the decimal
     * @return not null and gt 0 ? true : false
     */
    public static boolean isPositive(BigDecimal num) {
        return num != null && num.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 非负数？
     *
     * @param num the int
     * @return not null and ge 0 ? true : false
     */
    public static boolean nonNegative(Integer num) {
        return num != null && num >= 0;
    }

    /**
     * 非负数？
     *
     * @param num the long
     * @return not null and ge 0 ? true : false
     */
    public static boolean nonNegative(Long num) {
        return num != null && num >= 0L;
    }

    /**
     * 非负数？
     *
     * @param num the decimal
     * @return not null and ge 0 ? true : false
     */
    public static boolean nonNegative(BigDecimal num) {
        return num != null && num.compareTo(BigDecimal.ZERO) > -1;
    }

}
