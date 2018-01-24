package cc.colorcat.netbird4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cxx on 18-1-24.
 * xx.ch@outlook.com
 */
final class Utils {

    static <T> List<T> immutableList(List<T> list) {
        return Collections.unmodifiableList(new ArrayList<>(list));
    }

    static <T> T nullElse(T value, T other) {
        return value != null ? value : other;
    }
}
