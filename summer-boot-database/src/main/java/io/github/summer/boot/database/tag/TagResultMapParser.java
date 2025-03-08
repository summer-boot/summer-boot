package io.github.summer.boot.database.tag;

import io.github.summer.boot.database.annotation.MybatisResultMap;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Parse &#064;MybatisResultMap annotation to TagResultMap class
 *
 * @author changebooks@qq.com
 */
public final class TagResultMapParser {

    private TagResultMapParser() {
    }

    /**
     * Class&lt;Generic&gt; to {@link TagResultMap} class
     *
     * @param type Class&lt;Generic&gt;
     * @return TagResultMap
     */
    public static TagResultMap parseGeneric(Class<?> type) {
        if (type == null) {
            return null;
        }

        Type parameterizedType = type.getGenericSuperclass();
        if (parameterizedType instanceof ParameterizedType) {
            Type[] typeArguments = ((ParameterizedType) parameterizedType).getActualTypeArguments();
            if (typeArguments != null && typeArguments.length > 0) {
                Class<?> clazzArgument = (Class<?>) typeArguments[0];
                return parse(clazzArgument);
            }
        }

        return null;
    }

    /**
     * java bean class to {@link TagResultMap} class
     *
     * @param type java bean class
     * @return TagResultMap
     */
    public static TagResultMap parse(Class<?> type) {
        if (type == null) {
            return null;
        }

        MybatisResultMap annotation = type.getAnnotation(MybatisResultMap.class);
        if (annotation == null) {
            return null;
        }

        String table = annotation.table();
        Field[] fields = type.getDeclaredFields();
        List<TagResult> elements = Arrays.stream(fields)
                .filter(Objects::nonNull)
                .peek(TagResultParser::setAccessibleTrue)
                .map(TagResultParser::parse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        TagResultMap result = new TagResultMap();

        result.setTable(table);
        result.setType(type);
        result.setElements(elements);

        return result;
    }

}
