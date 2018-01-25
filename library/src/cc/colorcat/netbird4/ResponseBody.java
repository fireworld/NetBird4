package cc.colorcat.netbird4;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by cxx on 18-1-25.
 * xx.ch@outlook.com
 */
public abstract class ResponseBody implements Closeable {

    abstract String contentType();

    abstract long contentLength();

    abstract Charset charset();

    abstract InputStream stream();

    public final Reader reader() {
        final Charset charset = charset();
        return charset != null ? new InputStreamReader(stream(), charset) : new InputStreamReader(stream());
    }

    public final Reader reader(Charset ifAbsent) {
        Charset charset = charset();
        if (charset == null) charset = ifAbsent;
        return new InputStreamReader(stream(), charset);
    }

    public final String string() throws IOException {
        return Utils.justRead(reader());
    }

    public final String string(Charset ifAbsent) throws IOException {
        return Utils.justRead(reader(ifAbsent));
    }

    public final byte[] bytes() throws IOException {
        return Utils.justRead(stream());
    }

    @Override
    public void close() {
        Utils.close(stream());
    }
}
