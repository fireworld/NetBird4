package cc.colorcat.netbird4;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by cxx on 18-1-24.
 * xx.ch@outlook.com
 */
final class Utils {
    static final Charset UTF8 = Charset.forName("UTF-8");

    static <T> List<T> immutableList(List<T> list) {
        return Collections.unmodifiableList(new ArrayList<>(list));
    }

    static <T> T nullElse(T value, T other) {
        return value != null ? value : other;
    }

    static <K, V> Entry<List<K>, List<V>> unzipWithIgnoreNull(Map<K, List<V>> multimap) {
        List<K> ks = new ArrayList<>();
        List<V> vs = new ArrayList<>();
        for (Map.Entry<K, List<V>> entry : multimap.entrySet()) {
            K k = entry.getKey();
            List<V> vList = entry.getValue();
            if (k == null || vList == null) continue;
            for (V v : vList) {
                if (v == null) continue;
                ks.add(k);
                vs.add(v);
            }
        }
        return new Entry<>(ks, vs);
    }

    static long quiteToLong(String number, long defaultValue) {
        if (isEmpty(number)) return defaultValue;
        try {
            return Long.parseLong(number);
        } catch (Exception e) {
            // TODO: 18-1-25
        }
        return defaultValue;
    }

    static boolean isEmpty(CharSequence text) {
        return text == null || text.length() == 0;
    }

    static Charset parseCharset(String contentType, Charset defaultValue) {
        if (isEmpty(contentType)) return defaultValue;
        String[] params = contentType.split(";");
        final int length = params.length;
        for (int i = 1; i < length; i++) {
            String[] pair = params[i].trim().split("=");
            if (pair.length == 2) {
                if (pair[0].equalsIgnoreCase("charset")) {
                    try {
                        return Charset.forName(pair[1]);
                    } catch (Exception ignore) {
                    }
                }
            }
        }
        return defaultValue;
    }
}
