package cc.colorcat.netbird4;

import java.util.*;

/**
 * Created by cxx on 18-1-24.
 * xx.ch@outlook.com
 */
class Pair implements PairReader {
    protected List<String> names;
    protected List<String> values;
    protected Comparator<String> comparator;

    Pair(List<String> names, List<String> values, Comparator<String> comparator) {
        this.names = names;
        this.values = values;
        this.comparator = comparator;
    }

    @Override
    public final int size() {
        return names.size();
    }

    @Override
    public final boolean isEmpty() {
        return names.isEmpty();
    }

    @Override
    public final boolean contains(String name) {
        for (String e : names) {
            if (equal(name, e)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public final List<String> names() {
        return Utils.immutableList(names);
    }

    @Override
    public final List<String> values() {
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
        return result == null ? Collections.<String>emptyList() : Collections.unmodifiableList(result);
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
        return null;
    }

    protected final boolean equal(String str1, String str2) {
        return comparator.compare(str1, str2) == 0;
    }
}
