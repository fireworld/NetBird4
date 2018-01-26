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

    public final Reader reader(String charsetIfAbsent) {
        final Charset charset = Charset.forName(charsetIfAbsent);
        return reader(charset);
    }

    public final Reader reader(Charset ifAbsent) {
        Charset charset = charset();
        if (charset == null) charset = ifAbsent;
        return new InputStreamReader(stream(), charset);
    }

    public final String string() throws IOException {
        return Utils.justRead(reader());
    }

    public final String string(String charsetIfAbsent) throws IOException {
        final Charset charset = Charset.forName(charsetIfAbsent);
        return Utils.justRead(reader(charset));
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


    public static ResponseBody create(String content, String contentType) {
        return create(content, contentType, null);
    }

    public static ResponseBody create(String content, String contentType, Charset charset) {
        final Charset c = charset != null ? charset : Utils.parseCharset(contentType, null);
        final byte[] bytes = c != null ? content.getBytes(c) : content.getBytes();
        return create(bytes, contentType, c);
    }

    public static ResponseBody create(byte[] bytes, String contentType) {
        return create(new ByteArrayInputStream(bytes), contentType, bytes.length, null);
    }

    public static ResponseBody create(byte[] bytes, String contentType, Charset charset) {
        return create(new ByteArrayInputStream(bytes), contentType, bytes.length, charset);
    }

    public static ResponseBody create(final InputStream input, final String contentType, final long contentLength) {
        return create(input, contentType, contentLength, null);
    }

    public static ResponseBody create(final InputStream input, final String contentType, final long contentLength, final Charset charset) {
        if (input == null) { throw new IllegalArgumentException("input == null");}
        if (contentLength < -1L) throw new IllegalArgumentException("contentLength < -1L");
        return new ResponseBody() {
            @Override
            String contentType() {
                return contentType;
            }

            @Override
            long contentLength() {
                return contentLength;
            }

            @Override
            Charset charset() {
                return charset != null ? charset : Utils.parseCharset(contentType, null);
            }

            @Override
            InputStream stream() {
                return input;
            }
        };
    }

    public static ResponseBody create(final InputStream input, final Headers headers) {
        if (input == null) throw new IllegalArgumentException("input == null");
        if (headers == null) throw new IllegalArgumentException("headers = null");
        return new ResponseBody() {
            @Override
            String contentType() {
                return headers.contentType();
            }

            @Override
            long contentLength() {
                return headers.contentLength();
            }

            @Override
            Charset charset() {
                return headers.charset();
            }

            @Override
            InputStream stream() {
                return input;
            }
        };
    }
}
