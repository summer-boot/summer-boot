package io.github.summer.boot.filter;

import java.io.Serializable;

/**
 * 条件基类
 *
 * @author changebooks@qq.com
 */
public class BaseFilter implements Serializable {
    /**
     * 逻辑或？
     */
    private Boolean or;

    @Override
    public String toString() {
        Boolean or = getOr();
        return "{\"or\": %s}".formatted(or);
    }

    public Boolean getOr() {
        return or;
    }

    public void setOr(Boolean or) {
        this.or = or;
    }

}
