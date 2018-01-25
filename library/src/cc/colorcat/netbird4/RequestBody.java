package cc.colorcat.netbird4;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by cxx on 18-1-25.
 * xx.ch@outlook.com
 */
public abstract class RequestBody {

    public abstract String contentType();

    public long contentLength() throws IOException {
        return -1L;
    }

    public abstract void writeTo(OutputStream output) throws IOException;
}
