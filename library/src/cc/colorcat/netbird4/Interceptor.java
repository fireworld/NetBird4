package cc.colorcat.netbird4;

import java.io.IOException;

/**
 * Created by cxx on 18-1-25.
 * xx.ch@outlook.com
 */
public interface Interceptor {

    Response intercept(Chain chain) throws IOException;

    interface Chain {

        Connection connection();

        Request request();

        Response proceed(Request request) throws IOException;
    }
}
