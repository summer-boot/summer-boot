package io.github.summer.boot.dto;

import java.util.Date;

/**
 * 创建时间，范围
 *
 * @author changebooks@qq.com
 */
public class CreateDateRange extends RangeFilter<Date> {
    /**
     * 包含开始和结束？
     */
    private static final boolean INCLUDE_LOWER = true;
    private static final boolean INCLUDE_UPPER = true;

    /**
     * 字段名
     */
    private static final String FIELD_NAME = "create_date";

    public CreateDateRange() {
    }

    /**
     * initialize value
     *
     * @param from Begin Time
     * @param to   End Time
     */
    public CreateDateRange(Date from, Date to) {
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
