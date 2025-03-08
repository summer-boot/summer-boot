package io.github.summer.boot.filter;

/**
 * 条件，支持取反
 *
 * @author changebooks@qq.com
 */
public class FilterNot extends Filter {
    /**
     * 默认取反？
     */
    private static final boolean DEFAULT_NOT = false;

    /**
     * 取反？
     */
    private Boolean not;

    @Override
    public String toString() {
        Boolean or = getOr();
        String name = getName();
        Boolean not = getNot();
        return "{\"or\": %s, \"name\": \"%s\", \"not\": %s}".formatted(or, name, not);
    }

    public boolean isNot() {
        Boolean not = getNot();
        return not != null ? not : DEFAULT_NOT;
    }

    public Boolean getNot() {
        return not;
    }

    public void setNot(Boolean not) {
        this.not = not;
    }

}
