package io.github.summer.boot.elastic;

import io.github.summer.boot.base.Check;
import io.github.summer.boot.dto.ExistsFilter;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.ExistsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 有值？
 *
 * @author changebooks@qq.com
 */
public final class ExistsBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExistsBuilder.class);

    private ExistsBuilder() {
    }

    /**
     * Build Filter
     *
     * @param builder BoolQueryBuilder
     * @param filter  ExistsFilter
     * @param <T>     the type of the value
     */
    public static <T> void build(BoolQueryBuilder builder, ExistsFilter<T> filter) {
        if (builder == null) {
            LOGGER.error("build failed, builder must not be null, filter: {}", filter);
            return;
        }

        if (filter == null) {
            LOGGER.error("build failed, filter must not be null");
            return;
        }

        String fieldName = filter.getFieldName();
        if (Check.isEmpty(fieldName)) {
            LOGGER.error("build failed, fieldName must not be empty, filter: {}", filter);
            return;
        }

        ExistsQueryBuilder existsQueryBuilder = QueryBuilders.existsQuery(fieldName);
        boolean not = filter.isNot();
        if (not) {
            builder.mustNot(existsQueryBuilder);
        } else {
            builder.must(existsQueryBuilder);
        }
    }

}
