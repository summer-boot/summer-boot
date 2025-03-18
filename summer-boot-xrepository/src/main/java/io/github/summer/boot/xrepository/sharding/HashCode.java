package io.github.summer.boot.xrepository.sharding;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * 哈希码
 *
 * @author changebooks@qq.com
 */
public final class HashCode {

    private HashCode() {
    }

    /**
     * String Value
     *
     * @param value the {@link String} instance
     * @return hashed code
     */
    public static int hashCode(String value) {
        int hashedCode = Objects.hashCode(value);
        return Math.abs(hashedCode);
    }

    /**
     * Integer Value
     *
     * @param value the {@link Integer} instance
     * @return hashed code
     */
    public static int hashCode(Integer value) {
        int hashedCode = value != null ? value : 0;
        return Math.abs(hashedCode);
    }

    /**
     * Long Value
     *
     * @param value the {@link Long} instance
     * @return hashed code
     */
    public static long hashCode(Long value) {
        long hashedCode = value != null ? value : 0;
        return Math.abs(hashedCode);
    }

    /**
     * BigDecimal Value
     *
     * @param value the {@link BigDecimal} instance
     * @return hashed code
     */
    public static int hashCode(BigDecimal value) {
        int hashedCode = Objects.hashCode(value);
        return Math.abs(hashedCode);
    }

    /**
     * Date Value
     *
     * @param value the {@link Date} instance
     * @return hashed code
     */
    public static long hashCode(Date value) {
        long hashedCode = value != null ? value.getTime() : 0;
        return Math.abs(hashedCode);
    }

}
