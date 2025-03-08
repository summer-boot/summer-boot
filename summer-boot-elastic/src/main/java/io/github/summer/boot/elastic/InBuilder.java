package io.github.summer.boot.elastic;

import io.github.summer.boot.base.Check;
import io.github.summer.boot.dto.InFilter;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 在列表中？
 *
 * @author changebooks@qq.com
 */
public final class InBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(InBuilder.class);

    private InBuilder() {
    }

    /**
     * Build Filter
     *
     * @param builder BoolQueryBuilder
     * @param filter  InFilter
     * @param <T>     the type of the value
     */
    public static <T> void build(BoolQueryBuilder builder, InFilter<T> filter) {
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

        List<T> values = filter.getValues();

        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery(fieldName, values);
        boolean not = filter.isNot();
        if (not) {
            builder.mustNot(termsQueryBuilder);
        } else {
            builder.must(termsQueryBuilder);
        }
    }

}
