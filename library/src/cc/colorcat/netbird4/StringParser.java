package cc.colorcat.netbird4;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by cxx on 18-1-26.
 * xx.ch@outlook.com
 */
public final class StringParser implements Parser<String> {
    private static volatile StringParser utf8;
    private static volatile StringParser defaultStringParser;

    public static StringParser getUtf8() {
        if (utf8 == null) {
            synchronized (StringParser.class) {
                if (utf8 == null) {
                    utf8 = new StringParser(Utils.UTF8);
                }
            }
        }
        return utf8;
    }

    public static StringParser getDefault() {
        if (defaultStringParser == null) {
            synchronized (StringParser.class) {
                if (defaultStringParser == null) {
                    defaultStringParser = new StringParser(null);
                }
            }
        }
        return defaultStringParser;
    }

    public static StringParser create(String charsetIfAbsent) {
        if (Utils.isEmpty(charsetIfAbsent)) return getDefault();
        final Charset charset = Charset.forName(charsetIfAbsent);
        if (Utils.UTF8.equals(charset)) return getUtf8();
        return new StringParser(charset);
    }

    public static StringParser create(Charset ifAbsent) {
        if (ifAbsent == null) return getDefault();
        if (Utils.UTF8.equals(ifAbsent)) return getUtf8();
        return new StringParser(ifAbsent);
    }

    private final Charset ifAbsent;

    private StringParser(Charset ifAbsent) {
        this.ifAbsent = ifAbsent;
    }

    @Override
    public NetworkData<? extends String> parse(Response response) throws IOException {
        final ResponseBody body = response.responseBody;
        return ifAbsent != null ? NetworkData.newSuccess(body.string(ifAbsent)) : NetworkData.newSuccess(body.string());
    }
}
