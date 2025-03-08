package io.github.summer.boot.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 模糊匹配
 *
 * @author changebooks@qq.com
 */
public final class WildcardBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(WildcardBuilder.class);

    private WildcardBuilder() {
    }

    /**
     * Build WHERE SQL
     *
     * @param filter WildcardFilter
     * @return SQL Condition
     */
    public static String build(WildcardFilter filter) {
        if (filter == null) {
            LOGGER.error("build failed, filter must not be null");
            return null;
        }

        Integer code = filter.getCode();
        if (code == null) {
            LOGGER.error("build failed, code must not be null, filter: {}", filter);
            return null;
        }

        Wildcard wildcard = Wildcard.forCode(code);
        if (wildcard == null) {
            LOGGER.error("build failed, wildcard must not be null, code: {}, filter: {}", code, filter);
            return null;
        }

        String pattern = wildcard.pattern;
        String fieldName = filter.getFieldName();
        String value = filter.getValue();

        return BuilderUtils.buildWhere(pattern, fieldName, value);
    }

}
