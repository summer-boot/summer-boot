package io.github.summer.boot.database;

import io.github.summer.boot.database.result.ResultMapBuilder;
import io.github.summer.boot.database.tag.TagResultMap;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * add ResultMap to Configuration
 *
 * @author changebooks@qq.com
 */
public final class ResultMapUtils {
    /**
     * used in withoutId's Provider
     */
    public static final String[] DEFAULT_ID = {"BaseResultMap"};

    private ResultMapUtils() {
    }

    /**
     * parse ResultMap And add to Configuration
     *
     * @param configuration the {@link Configuration} instance
     */
    public static void addResultMaps(Configuration configuration) {
        addResultMaps(configuration, DEFAULT_ID);
    }

    /**
     * parse ResultMap And add to Configuration
     *
     * @param configuration the {@link Configuration} instance
     * @param defaultIds    used in withoutId's Provider
     */
    public static void addResultMaps(Configuration configuration, String[] defaultIds) {
        if (configuration == null) {
            return;
        }

        MapperRegistry registry = configuration.getMapperRegistry();
        if (registry == null) {
            return;
        }

        Collection<Class<?>> types = registry.getMappers();
        if (types != null) {
            addResultMaps(configuration, types, defaultIds);
        }
    }

    /**
     * parse ResultMap And add to Configuration
     *
     * @param configuration the {@link Configuration} instance
     * @param types         the interface or class
     * @param defaultIds    used in withoutId's Provider
     */
    public static void addResultMaps(Configuration configuration, Collection<Class<?>> types, String[] defaultIds) {
        if (configuration == null || types == null) {
            return;
        }

        for (Class<?> t : types) {
            addResultMap(configuration, t, defaultIds);
        }
    }

    /**
     * parse ResultMap And add to Configuration
     *
     * @param configuration the {@link Configuration} instance
     * @param type          the interface or class
     * @param defaultIds    used in withoutId's Provider
     */
    public static void addResultMap(Configuration configuration, Class<?> type, String[] defaultIds) {
        if (configuration == null || type == null) {
            return;
        }

        Map<String, TagResultMap> tags = AnnotationParser.parse(type, defaultIds);
        if (tags == null) {
            return;
        }

        List<ResultMap> resultMaps = buildResultMap(configuration, type.getName(), tags);
        if (resultMaps != null) {
            addResultMap(configuration, resultMaps);
        }
    }

    /**
     * add [ ResultMap, ResultMap ] to Configuration
     *
     * @param configuration the {@link Configuration} instance
     * @param resultMaps    [ ResultMap, ResultMap ]
     */
    public static void addResultMap(Configuration configuration, List<ResultMap> resultMaps) {
        if (configuration == null || resultMaps == null) {
            return;
        }

        for (ResultMap rm : resultMaps) {
            if (rm == null) {
                continue;
            }

            String id = rm.getId();
            if (id == null || id.isEmpty()) {
                continue;
            }

            if (configuration.hasResultMap(id)) {
                continue;
            }

            configuration.addResultMap(rm);
        }
    }

    /**
     * cast Map&lt;String, TagResultMap&gt; to List&lt;ResultMap&gt;
     *
     * @param configuration the {@link Configuration} instance
     * @param namespace     &lt;mapper namespace="namespace name"&gt;
     * @param tags          [ id : TagResultMap ]
     * @return [ ResultMap, ResultMap ]
     */
    public static List<ResultMap> buildResultMap(Configuration configuration, String namespace, Map<String, TagResultMap> tags) {
        if (configuration == null || tags == null) {
            return null;
        }

        List<ResultMap> result = new ArrayList<>();

        for (Map.Entry<String, TagResultMap> entry : tags.entrySet()) {
            if (entry == null) {
                continue;
            }

            String id = entry.getKey();
            if (id == null || id.isEmpty()) {
                continue;
            }

            TagResultMap tagMap = entry.getValue();
            if (tagMap == null) {
                continue;
            }

            id = ResultMapBuilder.joinNamespaceId(namespace, id);
            ResultMap resultMap = ResultMapBuilder.build(configuration, id, tagMap);
            if (resultMap != null) {
                result.add(resultMap);
            }
        }

        return result;
    }

}
