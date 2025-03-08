package io.github.summer.boot.dto;

import java.io.Serializable;

/**
 * 条件基类
 *
 * @author changebooks@qq.com
 */
public abstract class BaseFilter implements Serializable {
    /**
     * 字段名
     *
     * @return WHERE fieldName = ? ORDER BY fieldName
     */
    public abstract String getFieldName();

}
