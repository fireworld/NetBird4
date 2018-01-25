package cc.colorcat.netbird4;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by cxx on 18-1-25.
 * xx.ch@outlook.com
 */
public interface Connection extends Closeable, Cloneable {

    void connect(NetBird netBird, Request request) throws IOException;

    void writeHeaders(Headers headers) throws IOException;

    void writeRequestBody(RequestBody requestBody) throws IOException;

    int responseCode() throws IOException;

    String responseMsg() throws IOException;

    Headers responseHeaders() throws IOException;

    ResponseBody responseBody() throws IOException;

    void cancel();

    Connection clone();
}
