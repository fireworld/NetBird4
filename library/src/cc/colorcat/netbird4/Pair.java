package cc.colorcat.netbird4;

import java.util.*;

/**
 * Created by cxx on 18-1-24.
 * xx.ch@outlook.com
 */
final class Pair implements PairWriter {
    static final Pair EMPTY_PAIR = new Pair(Collections.<String>emptyList(), Collections.<String>emptyList(), String.CASE_INSENSITIVE_ORDER);

    static Pair of(List<String> names, List<String> values, Comparator<String> comparator) {
        if (comparator == null) {
            throw new NullPointerException("comparator is null");
        }
        checkNamesAndValues(names, values);
        return new Pair(new ArrayList<>(names), new ArrayList<>(values), comparator);
    }

    static void checkNameAndValue(String name, String value) {
        if (name == null) {
            throw new NullPointerException("name is null");
        }
        if (value == null) {
            throw new NullPointerException("values is null");
        }
    }

    static void checkNamesAndValues(List<String> names, List<String> values) {
        int ns = names.size(), vs = values.size();
        if (ns != vs) {
            throw new IllegalArgumentException("names.size(" + ns + ") != values.size(" + vs + ')');
        }
        for (int i = 0, size = names.size(); i < size; ++i) {
            if (names.get(i) == null) {
                throw new NullPointerException("name is null at " + i);
            }
            if (values.get(i) == null) {
                throw new NullPointerException("value is null at " + i);
            }
        }
    }

    final List<String> names;
    final List<String> values;
    final Comparator<String> comparator;

    Pair(List<String> names, List<String> values, Comparator<String> comparator) {
        this.names = names;
        this.values = values;
        this.comparator = comparator;
    }

    @Override
    public int size() {
        return names.size();
    }

    @Override
    public boolean isEmpty() {
        return names.isEmpty();
    }

    @Override
    public boolean contains(String name) {
        for (String e : names) {
            if (equal(name, e)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> names() {
        return Utils.immutableList(names);
    }

    @Override
    public List<String> values() {
        return Utils.immutableList(values);
    }

    @Override
    public String name(int index) {
        return names.get(index);
    }

    @Override
    public String value(int index) {
        return values.get(index);
    }

    @Override
    public String value(String name) {
        for (int i = 0, size = names.size(); i < size; ++i) {
            if (equal(name, names.get(i))) {
                return values.get(i);
            }
        }
        return null;
    }

    @Override
    public String value(String name, String defaultValue) {
        return Utils.nullElse(value(name), defaultValue);
    }

    @Override
    public List<String> values(String name) {
        List<String> result = null;
        for (int i = 0, size = names.size(); i < size; ++i) {
            if (equal(name, names.get(i))) {
                if (result == null) result = new ArrayList<>(2);
                result.add(values.get(i));
            }
        }
        return result != null ? Collections.unmodifiableList(result) : Collections.<String>emptyList();
    }

    @Override
    public Set<String> nameSet() {
        if (names.isEmpty()) return Collections.emptySet();
        Set<String> result = new TreeSet<>(comparator);
        result.addAll(names);
        return Collections.unmodifiableSet(result);
    }

    @Override
    public Map<String, List<String>> toMultimap() {
        if (names.isEmpty()) return Collections.emptyMap();
        Map<String, List<String>> result = new HashMap<>();
        for (String name : nameSet()) {
            result.put(name, values(name));
        }
        return Collections.unmodifiableMap(result);
    }

    @Override
    public Iterator<NameAndValue> iterator() {
        return new PairIterator();
    }

    @Override
    public void add(String name, String value) {
        checkNameAndValue(name, value);
        names.add(name);
        values.add(value);
    }

    @Override
    public void addIfNot(String name, String value) {
        if (!contains(name)) {
            add(name, value);
        }
    }

    @Override
    public void addAll(List<String> names, List<String> values) {
        int ns = names.size(), vs = values.size();
        if (ns != vs) {
            throw new IllegalArgumentException("names.size(" + ns + ") != values.size(" + vs + ")");
        }
        this.names.addAll(names);
        this.values.addAll(values);
    }

    @Override
    public void set(String name, String value) {
        removeAll(name);
        add(name, value);
    }

    @Override
    public void removeAll(String name) {
        for (int i = names.size() - 1; i >= 0; --i) {
            if (equal(name, names.get(i))) {
                names.remove(i);
                values.remove(i);
            }
        }
    }

    @Override
    public void clear() {
        names.clear();
        values.clear();
    }

    public Iterator<NameAndValue> mutableIterator() {
        return new MutablePairIterator();
    }

    private boolean equal(String str1, String str2) {
        return comparator.compare(str1, str2) == 0;
    }

    public String contentToString(String separator) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, size = names.size(); i < size; ++i) {
            if (i > 0) builder.append(separator);
            builder.append(names.get(i)).append('=').append(values.get(i));
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return Objects.equals(names, pair.names) &&
                Objects.equals(values, pair.values) &&
                Objects.equals(comparator, pair.comparator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(names, values, comparator);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '{' + contentToString(", ") + '}';
    }


    private class PairIterator implements Iterator<NameAndValue> {
        Iterator<String> namesItr = names.iterator();
        Iterator<String> valuesItr = values.iterator();

        @Override
        public final boolean hasNext() {
            boolean result = namesItr.hasNext() && valuesItr.hasNext();
            check();
            return result;
        }

        @Override
        public final NameAndValue next() {
            NameAndValue next = new NameAndValue(namesItr.next(), valuesItr.next());
            check();
            return next;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }

        final void check() {
            int ns = names.size(), vs = values.size();
            if (ns != vs) {
                throw new IllegalArgumentException("names.size(" + ns + ") != values.size(" + vs + ")");
            }
        }
    }


    private final class MutablePairIterator extends PairIterator {
        @Override
        public void remove() {
            check();
            namesItr.remove();
            valuesItr.remove();
            check();
        }
    }
}
