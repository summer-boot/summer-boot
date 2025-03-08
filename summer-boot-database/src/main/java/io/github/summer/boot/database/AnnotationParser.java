package io.github.summer.boot.database;

import io.github.summer.boot.database.tag.TagResultMap;
import org.apache.ibatis.annotations.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Parse &#064;SelectProvider annotation
 * Parse &#064;InsertProvider annotation
 * Parse &#064;UpdateProvider annotation
 * Parse &#064;DeleteProvider annotation
 * Parse &#064;ResultMap annotation
 *
 * @author changebooks@qq.com
 */
public final class AnnotationParser {

    private AnnotationParser() {
    }

    /**
     * parse ResultMap value from &#064;ResultMap annotation
     * parse TagResultMap instance from &#064;Provider annotation
     *
     * @param type       the interface or class
     * @param defaultIds used in withoutId's Provider
     * @return [ id : TagResultMap ]
     */
    public static Map<String, TagResultMap> parse(Class<?> type, String[] defaultIds) {
        Map<String, BaseProvider<?>> providers = parseProvider(type, defaultIds);
        if (providers == null) {
            return null;
        }

        Map<String, TagResultMap> result = new HashMap<>(providers.size());

        for (Map.Entry<String, BaseProvider<?>> entry : providers.entrySet()) {
            if (entry == null) {
                continue;
            }

            BaseProvider<?> provider = entry.getValue();
            if (provider == null) {
                continue;
            }

            TagResultMap resultMap = provider.getResultMap();
            if (resultMap == null) {
                continue;
            }

            String id = entry.getKey();
            result.put(id, resultMap);
        }

        return result;
    }

    /**
     * parse ResultMap value from &#064;ResultMap annotation
     * parse Provider instance from &#064;Provider annotation
     *
     * @param type       the interface or class
     * @param defaultIds used in withoutId's Provider
     * @return [ id : Provider ]
     */
    public static Map<String, BaseProvider<?>> parseProvider(Class<?> type, String[] defaultIds) {
        if (type != null) {
            Method[] methods = type.getMethods();
            return parseProvider(methods, defaultIds);
        } else {
            return null;
        }
    }

    /**
     * parse ResultMap value from &#064;ResultMap annotation
     * parse Provider instance from &#064;Provider annotation
     *
     * @param methods    the type's methods
     * @param defaultIds used in withoutId's Provider
     * @return [ id : Provider ]
     */
    public static Map<String, BaseProvider<?>> parseProvider(Method[] methods, String[] defaultIds) {
        if (methods == null) {
            return null;
        }

        Map<String, BaseProvider<?>> withId = null;
        BaseProvider<?> withoutId = null;

        for (Method m : methods) {
            String[] ids = parseResultMap(m);
            BaseProvider<?> provider = parseSelectProvider(m);

            withId = choose(withId, ids, provider);
            withoutId = choose(withoutId, ids, provider);
        }

        for (Method m : methods) {
            String[] ids = parseResultMap(m);
            BaseProvider<?> provider = parseUpdateProvider(m);

            withId = choose(withId, ids, provider);
            withoutId = choose(withoutId, ids, provider);
        }

        for (Method m : methods) {
            String[] ids = parseResultMap(m);
            BaseProvider<?> provider = parseInsertProvider(m);

            withId = choose(withId, ids, provider);
            withoutId = choose(withoutId, ids, provider);
        }

        for (Method m : methods) {
            String[] ids = parseResultMap(m);
            BaseProvider<?> provider = parseDeleteProvider(m);

            withId = choose(withId, ids, provider);
            withoutId = choose(withoutId, ids, provider);
        }

        if (withId == null || withId.isEmpty()) {
            return combine(defaultIds, withoutId);
        } else {
            return withId;
        }
    }

    /**
     * choose withId's Provider
     *
     * @param result   withId's Provider
     * @param ids      the {@link ResultMap} value
     * @param provider the {@link BaseProvider} instance
     * @return withId's Provider
     */
    public static Map<String, BaseProvider<?>> choose(Map<String, BaseProvider<?>> result, String[] ids, BaseProvider<?> provider) {
        if (ids != null && ids.length > 0) {
            Map<String, BaseProvider<?>> other = combine(ids, provider);
            return choose(result, other);
        } else {
            return result;
        }
    }

    /**
     * choose withId's Provider
     *
     * @param result withId's Provider
     * @param other  withId's Provider
     * @return withId's Provider
     */
    public static Map<String, BaseProvider<?>> choose(Map<String, BaseProvider<?>> result, Map<String, BaseProvider<?>> other) {
        if (result == null) {
            return other;
        }

        if (other == null) {
            return result;
        }

        for (Map.Entry<String, BaseProvider<?>> entry : other.entrySet()) {
            if (entry == null) {
                continue;
            }

            BaseProvider<?> provider = entry.getValue();
            if (provider == null) {
                continue;
            }

            String id = entry.getKey();
            if (id == null || id.isEmpty()) {
                continue;
            }

            if (result.containsKey(id)) {
                continue;
            }

            result.put(id, provider);
        }

        return result;
    }

    /**
     * choose withoutId's Provider
     *
     * @param result   withoutId's Provider
     * @param ids      the {@link ResultMap} value
     * @param provider the {@link BaseProvider} instance
     * @return withoutId's Provider
     */
    public static BaseProvider<?> choose(BaseProvider<?> result, String[] ids, BaseProvider<?> provider) {
        if (ids == null || ids.length == 0) {
            return choose(result, provider);
        } else {
            return result;
        }
    }

    /**
     * choose withoutId's Provider
     *
     * @param result withoutId's Provider
     * @param other  withoutId's Provider
     * @return withoutId's Provider
     */
    public static BaseProvider<?> choose(BaseProvider<?> result, BaseProvider<?> other) {
        if (result != null) {
            return result;
        } else {
            return other;
        }
    }

    /**
     * combine ResultMap value And Provider instance
     *
     * @param ids      the {@link ResultMap} value
     * @param provider the {@link BaseProvider} instance
     * @return [ id : Provider ]
     */
    public static Map<String, BaseProvider<?>> combine(String[] ids, BaseProvider<?> provider) {
        if (ids == null || provider == null) {
            return null;
        }

        Map<String, BaseProvider<?>> result = Arrays.stream(ids)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(x -> !x.isEmpty())
                .distinct()
                .collect(Collectors.toMap(String::toString, x -> provider));

        return new HashMap<>(result);
    }

    /**
     * parse ResultMap value from &#064;ResultMap annotation
     *
     * @param method the type's method
     * @return ResultMap value
     */
    public static String[] parseResultMap(Method method) {
        if (method == null) {
            return null;
        }

        ResultMap annotation = method.getAnnotation(ResultMap.class);
        if (annotation != null) {
            return annotation.value();
        } else {
            return null;
        }
    }

    /**
     * parse Provider instance from &#064;SelectProvider annotation
     *
     * @param method the type's method
     * @return Provider instance
     */
    public static BaseProvider<?> parseSelectProvider(Method method) {
        if (method == null) {
            return null;
        }

        SelectProvider annotation = method.getAnnotation(SelectProvider.class);
        if (annotation == null) {
            return null;
        }

        Class<?> type = annotation.type();
        return newProviderInstance(type);
    }

    /**
     * parse Provider instance from &#064;InsertProvider annotation
     *
     * @param method the type's method
     * @return Provider instance
     */
    public static BaseProvider<?> parseInsertProvider(Method method) {
        if (method == null) {
            return null;
        }

        InsertProvider annotation = method.getAnnotation(InsertProvider.class);
        if (annotation == null) {
            return null;
        }

        Class<?> type = annotation.type();
        return newProviderInstance(type);
    }

    /**
     * parse Provider instance from &#064;UpdateProvider annotation
     *
     * @param method the type's method
     * @return Provider instance
     */
    public static BaseProvider<?> parseUpdateProvider(Method method) {
        if (method == null) {
            return null;
        }

        UpdateProvider annotation = method.getAnnotation(UpdateProvider.class);
        if (annotation == null) {
            return null;
        }

        Class<?> type = annotation.type();
        return newProviderInstance(type);
    }

    /**
     * parse Provider instance from &#064;DeleteProvider annotation
     *
     * @param method the type's method
     * @return Provider instance
     */
    public static BaseProvider<?> parseDeleteProvider(Method method) {
        if (method == null) {
            return null;
        }

        DeleteProvider annotation = method.getAnnotation(DeleteProvider.class);
        if (annotation == null) {
            return null;
        }

        Class<?> type = annotation.type();
        return newProviderInstance(type);
    }

    /**
     * cast BaseProvider class to Provider instance
     *
     * @param type the {@link BaseProvider} class
     * @return Provider instance
     */
    public static BaseProvider<?> newProviderInstance(Class<?> type) {
        if (type == null) {
            return null;
        }

        if (BaseProvider.class.isAssignableFrom(type)) {
            try {
                return (BaseProvider<?>) type.newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            return null;
        }
    }

}
