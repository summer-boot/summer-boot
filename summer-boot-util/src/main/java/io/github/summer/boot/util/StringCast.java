package io.github.summer.boot.util;

import com.google.common.base.CaseFormat;

/**
 * 字符串转换
 *
 * @author changebooks@qq.com
 */
public final class StringCast {

    private StringCast() {
    }

    /**
     * 下划线 to 驼峰式
     * Returns the converted value if it exists, or null
     *
     * @param s          eg: "customer_id"
     * @param upperFirst 首字母大写？
     * @return eg: "customerId", "CustomerId"
     */
    public static String underscoreToCamel(String s, boolean upperFirst) {
        if (s == null) {
            return null;
        } else {
            return CaseFormat.LOWER_UNDERSCORE.to(upperFirst ? CaseFormat.UPPER_CAMEL : CaseFormat.LOWER_CAMEL, s);
        }
    }

    /**
     * 下划线 to 连接线
     * Returns the converted value if it exists, or null
     *
     * @param s eg: "customer_id"
     * @return eg: "customer-id"
     */
    public static String underscoreToHyphen(String s) {
        if (s == null) {
            return null;
        } else {
            return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, s);
        }
    }

    /**
     * 驼峰式 to 下划线
     * Returns the converted value if it exists, or null
     *
     * @param s eg: "customerId"
     * @return eg: "customer_id"
     */
    public static String camelToUnderscore(String s) {
        if (s == null) {
            return null;
        } else {
            return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, s);
        }
    }

    /**
     * 驼峰式 to 连接线
     * Returns the converted value if it exists, or null
     *
     * @param s eg: "customerId"
     * @return eg: "customer-id"
     */
    public static String camelToHyphen(String s) {
        if (s == null) {
            return null;
        } else {
            return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, s);
        }
    }

    /**
     * 连接线 to 驼峰式
     * Returns the converted value if it exists, or null
     *
     * @param s          eg: "customer-id"
     * @param upperFirst 首字母大写？
     * @return eg: "customerId", "CustomerId"
     */
    public static String hyphenToCamel(String s, boolean upperFirst) {
        if (s == null) {
            return null;
        } else {
            return CaseFormat.LOWER_HYPHEN.to(upperFirst ? CaseFormat.UPPER_CAMEL : CaseFormat.LOWER_CAMEL, s);
        }
    }

    /**
     * 连接线 to 下划线
     * Returns the converted value if it exists, or null
     *
     * @param s eg: "customer-id"
     * @return eg: "customer_id"
     */
    public static String hyphenToUnderscore(String s) {
        if (s == null) {
            return null;
        } else {
            return CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_UNDERSCORE, s);
        }
    }

}
