package io.github.summer.boot.dto;

/**
 * 模糊匹配
 *
 * @author changebooks@qq.com
 */
public abstract class WildcardFilter extends BaseFilterNot {
    /**
     * 编码
     */
    private Integer code;

    /**
     * 值
     */
    private String value;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
