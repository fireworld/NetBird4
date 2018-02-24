package cc.colorcat.netbird4;

import java.util.*;

/**
 * Created by cxx on 18-1-25.
 * xx.ch@outlook.com
 */
public class Parameters implements PairReader {
    final Pair delegate;

    Parameters(Pair delegate) {
        this.delegate = delegate;
    }

    @Override
    public final int size() {
        return delegate.size();
    }

    @Override
    public final boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public final boolean contains(String name) {
        return delegate.contains(name);
    }

    @Override
    public final List<String> names() {
        return delegate.names();
    }

    @Override
    public final List<String> values() {
        return delegate.values();
    }

    @Override
    public final String name(int index) {
        return delegate.name(index);
    }

    @Override
    public final String value(int index) {
        return delegate.value(index);
    }

    @Override
    public final String value(String name) {
        return delegate.value(name);
    }

    @Override
    public final String value(String name, String defaultValue) {
        return delegate.value(name, defaultValue);
    }

    @Override
    public final List<String> values(String name) {
        return delegate.values(name);
    }

    @Override
    public final Set<String> nameSet() {
        return delegate.nameSet();
    }

    @Override
    public final Map<String, List<String>> toMultimap() {
        return delegate.toMultimap();
    }

    @Override
    public final Iterator<NameAndValue> iterator() {
        return delegate.iterator();
    }

    public final String contentToString(String nameValueSeparator, String lineSeparator) {
        return delegate.contentToString(nameValueSeparator, lineSeparator);
    }

    public final MutableParameters toMutableParameters() {
        return new MutableParameters(delegate.toMutablePair());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameters that = (Parameters) o;
        return Objects.equals(delegate, that.delegate);
    }

    @Override
    public int hashCode() {
        return 23 * Objects.hash(delegate);
    }

    @Override
    public final String toString() {
        return getClass().getSimpleName() + '{' + delegate.contentToString("=", ", ") + '}';
    }
}
