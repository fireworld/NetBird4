package cc.colorcat.netbird4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by cxx on 18-1-25.
 * xx.ch@outlook.com
 */
public class Main {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>(Arrays.asList("a001", "b4", "c2", "d3465", "e21", "content-type"));
        List<String> values = new ArrayList<>(Arrays.asList("jack", "tom", "john", "jim", "lucy", "image/jpg"));
        Headers headers = Headers.ofWithIgnoreNull(names, values).toMutableHeaders();
        System.out.println(headers);
        Iterator<NameAndValue> itr = headers.iterator();
        while (itr.hasNext()) {
            if (itr.next().name.length() <= 3) {
                itr.remove();
            }
        }
        System.out.println(headers);
        System.out.println(headers.contentType());
    }
}
