package cc.colorcat.netbird4;

import java.io.IOException;

/**
 * Created by cxx on 18-1-26.
 * xx.ch@outlook.com
 */
public interface Parser<T> {

    NetworkData<? extends T> parse(Response response) throws IOException;
}
