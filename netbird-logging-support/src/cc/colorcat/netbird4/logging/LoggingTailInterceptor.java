package cc.colorcat.netbird4.logging;

import cc.colorcat.netbird4.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cxx on 2018/1/28.
 * xx.ch@outlook.com
 */
public class LoggingTailInterceptor implements Interceptor {
    private static final String TAG = NetBird.class.getSimpleName();
    private static final String LINE = buildString(80, '-');
    private static final String HALF_LINE = buildString(38, '-');
    private final Charset charsetIfAbsent;
    private final boolean deUnicode;

    public LoggingTailInterceptor() {
        this(Charset.forName("UTF-8"), false);
    }

    public LoggingTailInterceptor(boolean deUnicode) {
        this(Charset.forName("UTF-8"), deUnicode);
    }

    public LoggingTailInterceptor(Charset charsetIfAbsent, boolean deUnicode) {
        this.charsetIfAbsent = charsetIfAbsent;
        this.deUnicode = deUnicode;
    }

    @Override
    public final Response intercept(Chain chain) throws IOException {
        final Request request = chain.request();
        Response response = chain.proceed(request);

        final StringBuilder builder = new StringBuilder();
        builder.append(" \n").append(HALF_LINE).append(request.method().name()).append(HALF_LINE)
                .append("\nrequest url --> ").append(request.url());
        appendPair(builder, "request header --> ", request.headers());

        if (request.method().needBody()) {
            appendPair(builder, "request parameter --> ", request.parameters());
            appendFile(builder, "request file --> ", request.fileBodies());
        }

        builder.append("\n\nresponse --> ").append(response.responseCode()).append("--").append(response.responseMsg());
        appendPair(builder, "response header --> ", response.headers());
        ResponseBody body = response.responseBody();
        if (body != null) {
            final String contentType = body.contentType();
            if (contentType != null && contentFilter(contentType)) {
                final byte[] bytes = body.bytes();
                Charset charset = body.charset();
                if (charset == null) charset = charsetIfAbsent;
                final String content = new String(bytes, charset);
                builder.append("\nresponse content --> ").append(deUnicode ? decode(content) : content);
                final ResponseBody newBody = ResponseBody.create(bytes, contentType, charset);
                response = response.newBuilder()
                        .setHeader(Headers.CONTENT_LENGTH, Long.toString(newBody.contentLength()))
                        .responseBody(newBody)
                        .build();
            }
        }
        builder.append('\n').append(LINE);
        Platform.get().logger().log(TAG, builder.toString(), Level.INFO);
        return response;
    }

    protected boolean contentFilter(String contentType) {
        return contentType.matches(".*(charset|text|html|htm|json|urlencoded)+.*");
    }

    private static void appendPair(StringBuilder builder, String prefix, PairReader reader) {
        for (NameAndValue nv : reader) {
            builder.append('\n').append(prefix).append(nv.name).append('=').append(nv.value);
        }
    }

    private static void appendFile(StringBuilder builder, String prefix, List<FileBody> bodies) {
        for (FileBody body : bodies) {
            builder.append('\n').append(prefix).append(body.toString());
        }
    }

    private static String buildString(int count, char c) {
        StringBuilder builder = new StringBuilder(count);
        for (int i = 0; i < count; ++i) {
            builder.append(c);
        }
        return builder.toString();
    }

    private static String decode(String unicode) {
        StringBuilder builder = new StringBuilder(unicode.length());
        Matcher matcher = Pattern.compile("\\\\u[0-9a-fA-F]{4}").matcher(unicode);
        int last = 0;
        for (int start, end = 0; matcher.find(end); last = end) {
            start = matcher.start();
            end = matcher.end();
            builder.append(unicode.substring(last, start))
                    .append((char) Integer.parseInt(unicode.substring(start + 2, end), 16));
        }
        return builder.append(unicode.substring(last)).toString();
    }
}
