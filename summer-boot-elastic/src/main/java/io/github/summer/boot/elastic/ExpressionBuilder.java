package io.github.summer.boot.elastic;

import io.github.summer.boot.base.Check;
import io.github.summer.boot.dto.ExpressionCode;
import io.github.summer.boot.dto.ExpressionFilter;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 表达式
 *
 * @author changebooks@qq.com
 */
public class ExpressionBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressionBuilder.class);

    private ExpressionBuilder() {
    }

    /**
     * Build Filter
     *
     * @param builder BoolQueryBuilder
     * @param filter  ExpressionFilter
     * @param <T>     the type of the value
     */
    public static <T> void build(BoolQueryBuilder builder, ExpressionFilter<T> filter) {
        if (builder == null) {
            LOGGER.error("build failed, builder must not be null, filter: {}", filter);
            return;
        }

        if (filter == null) {
            LOGGER.error("build failed, filter must not be null");
            return;
        }

        Integer code = filter.getCode();
        if (code == null) {
            LOGGER.error("build failed, code must not be null, filter: {}", filter);
            return;
        }

        String fieldName = filter.getFieldName();
        if (Check.isEmpty(fieldName)) {
            LOGGER.error("build failed, fieldName must not be empty, code: {}, filter: {}", code, filter);
            return;
        }

        T value = filter.getValue();

        switch (code) {
            case ExpressionCode.EQ -> builder.filter(QueryBuilders.termQuery(fieldName, value));
            case ExpressionCode.NE -> builder.mustNot(QueryBuilders.termQuery(fieldName, value));
            case ExpressionCode.GT -> builder.filter(QueryBuilders.rangeQuery(fieldName).gt(value));
            case ExpressionCode.LT -> builder.filter(QueryBuilders.rangeQuery(fieldName).lt(value));
            case ExpressionCode.GE -> builder.filter(QueryBuilders.rangeQuery(fieldName).gte(value));
            case ExpressionCode.LE -> builder.filter(QueryBuilders.rangeQuery(fieldName).lte(value));
            default -> LOGGER.error("build failed, unsupported code, code: {}, filter: {}", code, filter);
        }
    }

}
