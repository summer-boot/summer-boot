package io.github.summer.boot.database.result;

import io.github.summer.boot.database.tag.TagResult;
import io.github.summer.boot.database.tag.TagResultMap;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Build ResultMap class from TagResultMap class
 *
 * @author changebooks@qq.com
 */
public final class ResultMapBuilder {

    private ResultMapBuilder() {
    }

    /**
     * {@link TagResultMap} class to {@link ResultMap} class
     *
     * @param configuration the {@link Configuration} instance
     * @param id            the namespace.id
     * @param tagMap        the {@link TagResultMap} instance
     * @return ResultMap
     */
    public static ResultMap build(Configuration configuration, String id, TagResultMap tagMap) {
        if (tagMap == null) {
            return null;
        }

        Class<?> type = tagMap.getType();
        List<TagResult> columns = tagMap.getElements();
        List<ResultMapping> mappings = Optional.ofNullable(columns)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .map(x -> ResultBuilder.build(configuration, x))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new ResultMap.Builder(configuration, id, type, mappings)
                .build();
    }

    /**
     * join namespace . id
     *
     * @param namespace &lt;mapper namespace="" &#47;&gt;
     * @param id        &lt;resultMap id="" &#47;&gt;
     * @return namespace.id
     */
    public static String joinNamespaceId(String namespace, String id) {
        if (namespace == null || namespace.isEmpty() || id == null || id.isEmpty()) {
            return id;
        } else {
            return String.format("%s.%s", namespace, id);
        }
    }

}
