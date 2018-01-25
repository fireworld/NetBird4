package cc.colorcat.netbird4;

import java.util.Objects;

/**
 * Created by cxx on 18-1-25.
 * xx.ch@outlook.com
 */
final class Entry<F, S> {
    public final F first;
    public final S second;

    public Entry(F first, S second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry<?, ?> entry = (Entry<?, ?>) o;
        return Objects.equals(first, entry.first) &&
                Objects.equals(second, entry.second);
    }

    @Override
    public int hashCode() {

        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "Entry{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
