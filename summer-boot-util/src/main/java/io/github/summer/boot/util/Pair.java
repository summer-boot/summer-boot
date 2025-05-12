package io.github.summer.boot.util;

import java.io.Serializable;
import java.util.Objects;

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

    // Because 'of()' is shorter than 'new Pair<>()'.
    // Sometimes this difference might be very significant (especially in a
    // 80-ish characters boundary). Sorry diamond operator.
    public static <F, S> Pair<F, S> of(F first, S second) {
        return new Pair<>(first, second);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pair<?, ?> other = (Pair<?, ?>) o;
        return Objects.equals(first, other.first) && Objects.equals(second, other.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

}
