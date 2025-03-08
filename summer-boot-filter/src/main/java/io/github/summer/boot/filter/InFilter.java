package io.github.summer.boot.filter;

import io.github.summer.boot.value.Parameter;

import java.util.List;

/**
 * 在列表中？
 *
 * @author changebooks@qq.com
 */
public class InFilter extends FilterNot {
    /**
     * 参数列表
     */
    private List<Parameter> parameters;

    @Override
    public String toString() {
        Boolean or = getOr();
        String name = getName();
        Boolean not = getNot();
        List<Parameter> parameters = getParameters();
        return "{\"or\": %s, \"name\": \"%s\", \"not\": %s, \"parameters\": %s}".formatted(or, name, not, parameters);
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

}
