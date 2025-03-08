package io.github.summer.boot.util;

import java.io.Serializable;

/**
 * A simple paired value class
 *
 * @author changebooks@qq.com
 */
public final class Pair<F, S> implements Serializable {

    public final F first;
    public final S second;

    public Pair(F first, S second) {
        this.second = second;
        this.first = first;
    }

    // Because 'pair()' is shorter than 'new Pair<>()'.
    // Sometimes this difference might be very significant (especially in a
    // 80-ish characters boundary). Sorry diamond operator.
    public static <F, S> Pair<F, S> pair(F first, S second) {
        return new Pair<>(first, second);
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

}
