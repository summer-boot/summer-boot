package io.github.summer.boot.filter;

import io.github.summer.boot.value.Parameter;

/**
 * 模糊匹配
 *
 * @author changebooks@qq.com
 */
public class WildcardFilter extends FilterNot {
    /**
     * the {@link WildcardCode} instance
     */
    private int code;

    /**
     * 参数
     */
    private Parameter parameter;

    @Override
    public String toString() {
        Boolean or = getOr();
        String name = getName();
        Boolean not = getNot();
        int code = getCode();
        Parameter parameter = getParameter();
        return "{\"or\": %s, \"name\": \"%s\", \"not\": %s, \"code\": %d, \"parameter\": %s}"
                .formatted(or, name, not, code, parameter);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

}
