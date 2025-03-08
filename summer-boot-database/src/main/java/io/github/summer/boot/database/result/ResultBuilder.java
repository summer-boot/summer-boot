package io.github.summer.boot.database.result;

import io.github.summer.boot.database.tag.TagResult;
import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;

import java.util.ArrayList;
import java.util.List;

/**
 * Build ResultMapping class from TagResult class
 *
 * @author changebooks@qq.com
 */
public final class ResultBuilder {

    private ResultBuilder() {
    }

    /**
     * {@link TagResult} class to {@link ResultMapping} class
     *
     * @param configuration the {@link Configuration} instance
     * @param tag           the {@link TagResult} instance
     * @return ResultMapping
     */
    public static ResultMapping build(Configuration configuration, TagResult tag) {
        if (tag == null) {
            return null;
        }

        String property = tag.getProperty();
        String column = tag.getColumn();
        Class<?> javaType = tag.getJavaType();
        JdbcType jdbcType = tag.getJdbcType();
        boolean isId = tag.isId();

        List<ResultFlag> flags = new ArrayList<>();
        if (isId) {
            flags.add(ResultFlag.ID);
        }

        return new ResultMapping
                .Builder(configuration, property)
                .column(column)
                .javaType(javaType)
                .jdbcType(jdbcType)
                .flags(flags)
                .build();
    }

}
