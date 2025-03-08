package io.github.summer.boot.dto;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 引用参数，填充占位符
 *
 * @author changebooks@qq.com
 */
public final class ValueQuoter {
    /**
     * 分隔列表
     */
    private static final String DELIMITER = ", ";

    /**
     * 默认的时间格式
     */
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    private ValueQuoter() {
    }

    /**
     * 拼接列表
     *
     * @param values 参数列表，如，[ 1, abc, 2000-01-01 00:00:00 ]
     * @param <T>    the type of the value
     * @return "1, 'abc', '2000-01-01 00:00:00'"
     */
    public static <T> String join(List<T> values) {
        if (values != null) {
            return values
                    .stream()
                    .map(ValueQuoter::quote)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(DELIMITER));
        } else {
            return null;
        }
    }

    /**
     * 引用
     *
     * @param value 参数，如，1, abc, 2000-01-01 00:00:00
     * @param <T>   the type of the value
     * @return 1, 'abc', '2000-01-01 00:00:00'
     */
    public static <T> String quote(T value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Date) {
            String time = DateFormatUtils.format((Date) value, PATTERN);
            return quote(time);
        }

        if (value instanceof String) {
            return "'" + value + "'";
        } else {
            return value.toString();
        }
    }

}
