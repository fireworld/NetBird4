package cc.colorcat.netbird4;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by cxx on 18-1-24.
 * xx.ch@outlook.com
 */
public interface PairReader extends Iterable<NameAndValue> {

    int size();

    boolean isEmpty();

    boolean contains(String name);

    List<String> names();

    List<String> values();

    String name(int index);

    String value(int index);

    String value(String name);

    String value(String name, String defaultValue);

    List<String> values(String name);

    Set<String> nameSet();

    Map<String, List<String>> toMultimap();
}
