package io.github.summer.boot.util;

import org.mapstruct.Named;

/**
 * 对象映射
 *
 * @author changebooks@qq.com
 */
public interface BaseMapper {
    /**
     * 去掉左右空白
     * Returns the cleaned value if it exists, or null
     *
     * @param s 字符串，包含左右空白
     * @return 字符串，不包含左右空白
     */
    @Named("trimSpace")
    default String trimSpace(String s) {
        return (s != null) ? s.trim() : null;
    }

}
