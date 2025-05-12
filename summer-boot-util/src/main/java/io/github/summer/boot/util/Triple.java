package io.github.summer.boot.util;

import java.io.Serializable;
import java.util.Objects;

/**
 * A simple tripled value class
 *
 * @author changebooks@qq.com
 */
public final class Triple<F, S, T> implements Serializable {

    public final F first;
    public final S second;
    public final T third;

    public Triple(F first, S second, T third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    // Because 'of()' is shorter than 'new Triple<>()'.
    // Sometimes this difference might be very significant (especially in a
    // 80-ish characters boundary). Sorry diamond operator.
    public static <F, S, T> Triple<F, S, T> of(F first, S second, T third) {
        return new Triple<>(first, second, third);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Triple<?, ?, ?> other = (Triple<?, ?, ?>) o;
        return Objects.equals(first, other.first)
                && Objects.equals(second, other.second)
                && Objects.equals(third, other.third);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, third);
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ", " + third + ")";
    }

}
