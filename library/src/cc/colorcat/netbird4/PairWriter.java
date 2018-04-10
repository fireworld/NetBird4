package cc.colorcat.netbird4;

import java.util.List;

/**
 * Created by cxx on 18-1-24.
 * xx.ch@outlook.com
 */
public interface PairWriter extends PairReader {

    void add(String name, String value);

    void addIfNot(String name, String value);

    void addAll(List<String> names, List<String> values);

    void set(String name, String value);

    void replaceIfExists(String name, String value);

    void removeAll(String name);

    void clear();
}
