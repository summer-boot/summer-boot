package io.github.summer.boot.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 字符串
 *
 * @author changebooks@qq.com
 */
public final class StringUtils {
    /**
     * 默认的输出字符编码
     */
    private static final Charset DEFAULT_OUT_CHARSET = StandardCharsets.UTF_8;

    /**
     * 默认的输入字符编码
     */
    private static final Charset DEFAULT_IN_CHARSET = StandardCharsets.ISO_8859_1;

    private StringUtils() {
    }

    /**
     * 去掉左右空白
     * Returns the cleaned value if it exists, or null
     *
     * @param s 字符串，包含左右空白
     * @return 字符串，不包含左右空白
     */
    public static String trimSpace(String s) {
        return (s != null) ? s.trim() : null;
    }

    /**
     * 去掉左右字符
     * Returns the cleaned value if it exists, or null
     *
     * @param s 字符串，包含指定字符
     * @param c 指定字符
     * @return 字符串，不包含指定字符
     */
    public static String trim(String s, char c) {
        if (s == null) {
            return null;
        }

        int len = s.length();
        int st = 0;
        char[] val = s.toCharArray();

        while ((st < len) && (val[st] == c)) {
            st++;
        }

        while ((st < len) && (val[len - 1] == c)) {
            len--;
        }

        return ((st > 0) || (len < s.length())) ? s.substring(st, len) : s;
    }

    /**
     * 全部小写
     * Returns the lower case value if it exists, or null
     *
     * @param s 字符串，包含大写字符
     * @return 字符串，全部小写
     */
    public static String toLower(String s) {
        return (s != null) ? s.toLowerCase() : null;
    }

    /**
     * 全部大写
     * Returns the upper case value if it exists, or null
     *
     * @param s 字符串，包含小写字符
     * @return 字符串，全部大写
     */
    public static String toUpper(String s) {
        return (s != null) ? s.toUpperCase() : null;
    }

    /**
     * 首字母大写
     * Returns the first upper value if it exists, or null
     *
     * @param s          字符串
     * @param otherLower 其他字符小写？
     * @return 字符串
     */
    public static String upperFirst(String s, boolean otherLower) {
        if (s == null) {
            return null;
        }

        if (s.isEmpty()) {
            return "";
        }

        String first = s.substring(0, 1);
        String other = s.substring(1);
        if (otherLower) {
            other = other.toLowerCase();
        }

        return first.toUpperCase() + other;
    }

    /**
     * 转换编码格式
     * Returns the converted value if it exists, or null
     *
     * @param s       编码前的字符串
     * @param charset use Charset.forName(""), Default In: {@link #DEFAULT_IN_CHARSET}
     * @return 编码后的字符串
     */
    public static String encoding(String s, Charset charset) {
        if (s == null) {
            return null;
        }

        if (charset == null) {
            charset = DEFAULT_IN_CHARSET;
        }

        return new String(s.getBytes(charset), DEFAULT_OUT_CHARSET);
    }

    /**
     * 列表 to 字符串
     * Returns the joined value if it exists, or null
     * use StringJoiner contain StringBuilder
     *
     * @param list      列表
     * @param separator 连接符
     * @param <T>       列表类型
     * @return 字符串
     */
    public static <T> String join(Collection<T> list, String separator) {
        if (list == null) {
            return null;
        }

        StringJoiner result = new StringJoiner(separator);

        for (T t : list) {
            result.add(String.valueOf(t));
        }

        return result.toString();
    }

    /**
     * 字符串 to 列表
     * Returns the List&lt;String&gt; if it exists, or null
     *
     * @param s         字符串
     * @param separator 分隔符
     * @return 列表
     */
    public static List<String> split(String s, String separator) {
        if (s == null) {
            return null;
        }

        List<String> result = new ArrayList<>();

        StringTokenizer tokenizer = new StringTokenizer(s, separator);
        while (tokenizer.hasMoreTokens()) {
            result.add(tokenizer.nextToken());
        }

        return result;
    }

    /**
     * 指定长度的随机字符串
     *
     * @param count 指定长度
     * @return 随机字符串
     */
    public static String random(int count) {
        return RandomStringUtils.randomAlphanumeric(count);
    }

    /**
     * 唯一字符串
     *
     * @return UUID
     */
    public static String unique() {
        return UUID.randomUUID().toString();
    }

}
