package io.github.summer.boot.dto;

import java.util.Date;

/**
 * 更新时间，范围
 *
 * @author changebooks@qq.com
 */
public class LastUpdateRange extends RangeFilter<Date> {
    /**
     * 包含开始和结束？
     */
    private static final boolean INCLUDE_LOWER = true;
    private static final boolean INCLUDE_UPPER = true;

    /**
     * 字段名
     */
    private static final String FIELD_NAME = "last_update";

    public LastUpdateRange() {
    }

    /**
     * initialize value
     *
     * @param from Begin Time
     * @param to   End Time
     */
    public LastUpdateRange(Date from, Date to) {
        setFrom(from);
        setTo(to);
    }

    @Override
    public String getFieldName() {
        return FIELD_NAME;
    }

    @Override
    public boolean isIncludeLower() {
        return INCLUDE_LOWER;
    }

    @Override
    public boolean isIncludeUpper() {
        return INCLUDE_UPPER;
    }

}
