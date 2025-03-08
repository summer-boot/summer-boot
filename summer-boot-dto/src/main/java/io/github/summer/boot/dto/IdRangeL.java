package io.github.summer.boot.dto;

/**
 * 主键，范围
 *
 * @author changebooks@qq.com
 */
public class IdRangeL extends RangeFilter<Long> {
    /**
     * 包含开始和结束？
     */
    private static final boolean INCLUDE_LOWER = true;
    private static final boolean INCLUDE_UPPER = true;

    /**
     * 字段名
     */
    private static final String FIELD_NAME = "id";

    /**
     * initialize value
     *
     * @param from Begin Id
     * @param to   End Id
     */
    public IdRangeL(Long from, Long to) {
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
