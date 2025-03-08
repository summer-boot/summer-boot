package io.github.summer.boot.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * 日期时间
 *
 * @author changebooks@qq.com
 */
public final class DateTime {

    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String PATTERN_WITHOUT_SECOND = "yyyy-MM-dd HH:mm";
    private static final String PATTERN_WITHOUT_TIME = "yyyy-MM-dd";
    private static final String PATTERN_SLASH = "yyyy/MM/dd HH:mm:ss";
    private static final String PATTERN_SLASH_WITHOUT_SECOND = "yyyy/MM/dd HH:mm";
    private static final String PATTERN_SLASH_WITHOUT_TIME = "yyyy/MM/dd";

    private static final String[] PATTERNS = {
            PATTERN, PATTERN_WITHOUT_SECOND, PATTERN_WITHOUT_TIME,
            PATTERN_SLASH, PATTERN_SLASH_WITHOUT_SECOND, PATTERN_SLASH_WITHOUT_TIME
    };

    private DateTime() {
    }

    /**
     * 当前时间戳，单位：秒
     *
     * @return timestamp
     */
    public static int currentTimeSecond() {
        return (int) (System.currentTimeMillis() / 1000L);
    }

    /**
     * 当前时间，yyyy-MM-dd HH:mm:ss
     *
     * @return eg: "2022-02-22 22:22:22"
     */
    public static String now() {
        return format(new Date());
    }

    /**
     * Date format yyyy-MM-dd HH:mm:ss
     *
     * @param date the {@link Date} instance
     * @return eg: "2022-02-22 22:22:22"
     */
    public static String format(Date date) {
        return format(date, PATTERN);
    }

    /**
     * Date to String
     *
     * @param date    the {@link Date} instance
     * @param pattern the date format
     * @return eg: "2022-02-22 22:22:22", "2022/02/22 22:22", ...
     */
    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * String to Date
     * <pre>
     * 支持的格式：
     * yyyy-MM-dd HH:mm:ss
     * yyyy-MM-dd HH:mm
     * yyyy-MM-dd
     * yyyy/MM/dd HH:mm:ss
     * yyyy/MM/dd HH:mm
     * yyyy/MM/dd
     * </pre>
     *
     * @param source eg: "2022-02-22 22:22:22", "2022/02/22 22:22", ...
     * @return a {@link Date} instance
     */
    public static Date parse(String source) {
        try {
            return DateUtils.parseDate(source, PATTERNS);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

}
