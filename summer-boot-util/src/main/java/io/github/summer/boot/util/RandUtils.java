package io.github.summer.boot.util;

import java.util.Random;
import java.util.UUID;

/**
 * 随机数
 *
 * @author changebooks@qq.com
 */
public final class RandUtils {
    /**
     * java.util.Random
     */
    private static final Random EXECUTOR = new Random();

    private RandUtils() {
    }

    /**
     * 唯一的随机串
     *
     * @return UUID
     */
    public static String unique() {
        return UUID.randomUUID().toString();
    }

    /**
     * 两非负数之间的随机数
     *
     * @param min 最小非负数，随机数包含此数
     * @param max 最大非负数，随机数不包含此数
     * @return 随机数，[最小, 最大)
     */
    public static int betweenInt(int min, int max) {
        if (min < 0) {
            return 0;
        }

        if (max <= 0) {
            return min;
        }

        int bound = max - min;
        return nextInt(bound) + min;
    }

    /**
     * 随机非负数
     *
     * @param bound 正整数，随机数不包含此数
     * @return 随机数，[0, 正整数)
     */
    public static int nextInt(int bound) {
        if (bound > 0) {
            return EXECUTOR.nextInt(bound);
        } else {
            return 0;
        }
    }

}
