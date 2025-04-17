package io.github.summer.boot.filter;

import io.github.summer.boot.value.Parameter;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 范围
 *
 * @author changebooks@qq.com
 */
public class RangeFilter extends Filter {
    /**
     * 默认包含开始和结束？
     */
    private static final boolean DEFAULT_INCLUDE_LOWER = true;
    private static final boolean DEFAULT_INCLUDE_UPPER = true;

    /**
     * 开始参数
     */
    private Parameter from;

    /**
     * 结束参数
     */
    private Parameter to;

    /**
     * 包含开始？
     */
    private Boolean includeLower;

    /**
     * 包含结束？
     */
    private Boolean includeUpper;

    @Override
    public String toString() {
        Boolean or = getOr();
        String name = getName();
        Parameter from = getFrom();
        Parameter to = getTo();
        Boolean includeLower = getIncludeLower();
        Boolean includeUpper = getIncludeUpper();
        return "{\"or\": %s, \"name\": \"%s\", \"from\": %s, \"to\": %s, \"includeLower\": %s, \"includeUpper\": %s}"
                .formatted(or, name, from, to, includeLower, includeUpper);
    }

    @NotNull
    public List<Parameter> getParameters() {
        List<Parameter> result = new ArrayList<>();

        Parameter from = getFrom();
        if (from != null) {
            result.add(from);
        }

        Parameter to = getTo();
        if (to != null) {
            result.add(to);
        }

        return result;
    }

    public Parameter getFrom() {
        return from;
    }

    public void setFrom(Parameter from) {
        this.from = from;
    }

    public Parameter getTo() {
        return to;
    }

    public void setTo(Parameter to) {
        this.to = to;
    }

    public boolean isIncludeLower() {
        Boolean includeLower = getIncludeLower();
        return includeLower != null ? includeLower : DEFAULT_INCLUDE_LOWER;
    }

    public Boolean getIncludeLower() {
        return includeLower;
    }

    public void setIncludeLower(Boolean includeLower) {
        this.includeLower = includeLower;
    }

    public boolean isIncludeUpper() {
        Boolean includeUpper = getIncludeUpper();
        return includeUpper != null ? includeUpper : DEFAULT_INCLUDE_UPPER;
    }

    public Boolean getIncludeUpper() {
        return includeUpper;
    }

    public void setIncludeUpper(Boolean includeUpper) {
        this.includeUpper = includeUpper;
    }

}
