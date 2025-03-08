package io.github.summer.boot.filter;

import io.github.summer.boot.value.Parameter;

/**
 * 表达式
 *
 * @author changebooks@qq.com
 */
public class ExpressionFilter extends Filter {
    /**
     * the {@link ExpressionCode} instance
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
        int code = getCode();
        Parameter parameter = getParameter();
        return "{\"or\": %s, \"name\": \"%s\", \"code\": %d, \"parameter\": %s}".formatted(or, name, code, parameter);
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
