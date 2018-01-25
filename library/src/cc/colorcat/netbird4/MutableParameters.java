package cc.colorcat.netbird4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Created by cxx on 18-1-25.
 * xx.ch@outlook.com
 */
public final class MutableParameters extends Parameters implements PairWriter {
    public static MutableParameters create(int initCapacity) {
        List<String> names = new ArrayList<>(initCapacity);
        List<String> values = new ArrayList<>(initCapacity);
        return new MutableParameters(new MutablePair(names, values, PARAMETERS_COMPARATOR));
    }

    private static final Comparator<String> PARAMETERS_COMPARATOR = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    };


    MutableParameters(Pair delegate) {
        super(delegate);
    }

    @Override
    public void add(String name, String value) {
        checkNameAndValue(name, value);
        cast().add(name, value);
    }

    @Override
    public void addIfNot(String name, String value) {
        checkNameAndValue(name, value);
        cast().add(name, value);
    }

    @Override
    public void addAll(List<String> names, List<String> values) {
        for (int i = 0, size = names.size(); i < size; ++i) {
            checkNameAndValue(names.get(i), values.get(i));
        }
        cast().addAll(names, values);
    }

    @Override
    public void set(String name, String value) {
        checkNameAndValue(name, value);
        cast().set(name, value);
    }

    @Override
    public void removeAll(String name) {
        cast().removeAll(name);
    }

    @Override
    public void clear() {
        cast().clear();
    }

    public Parameters toParameters() {
        return new Parameters(cast().toPair());
    }

    private MutablePair cast() {
        return (MutablePair) delegate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MutableParameters that = (MutableParameters) o;
        return Objects.equals(delegate, that.delegate);
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(delegate);
    }

    private static void checkNameAndValue(String name, String value) {
        if (name == null) {
            throw new NullPointerException("name == null");
        }
        if (value == null) {
            throw new NullPointerException("value == null");
        }
    }
}
