package cc.colorcat.netbird4.logging;

import cc.colorcat.netbird4.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by cxx on 2018/1/28.
 * xx.ch@outlook.com
 */
public class LoggingTailInterceptor implements Interceptor {
    private static final String TAG = NetBird.class.getSimpleName();
    private static final String LINE = buildString(80, '-');
    private static final String HALF_LINE = buildString(38, '-');
    private final Charset charsetIfAbsent;

    public LoggingTailInterceptor(Charset charsetIfAbsent) {
        this.charsetIfAbsent = charsetIfAbsent;
    }

    public LoggingTailInterceptor() {
        charsetIfAbsent = Charset.forName("UTF-8");
    }

    @Override
    public final Response intercept(Chain chain) throws IOException {
        final Request request = chain.request();
        Response response = chain.proceed(request);

        synchronized (TAG) {
            log(HALF_LINE + request.method().name() + HALF_LINE, Level.DEBUG);
            log("request url = " + request.url(), Level.DEBUG);
            logPair("request header", request.headers(), Level.DEBUG);
            if (request.method().needBody()) {
                logPair("request parameter", request.parameters(), Level.DEBUG);
                logFile(request.fileBodies(), Level.DEBUG);
            }

            log("response --> " + response.responseCode() + "--" + response.responseMsg(), Level.INFO);
            logPair("response header", response.headers(), Level.INFO);
            final ResponseBody body = response.responseBody();
            if (body != null) {
                final String contentType = body.contentType();
                if (contentType != null && contentFilter(contentType)) {
                    final byte[] bytes = body.bytes();
                    Charset charset = body.charset();
                    if (charset == null) charset = charsetIfAbsent;
                    final String content = new String(bytes, charset);
                    log("response content --> " + content, Level.INFO);
                    final ResponseBody newBody = ResponseBody.create(bytes, contentType, charset);
                    response = response.newBuilder()
                            .setHeader(Headers.CONTENT_LENGTH, Long.toString(newBody.contentLength()))
                            .responseBody(newBody)
                            .build();
                }
            }
            log(LINE, Level.INFO);
            return response;
        }
    }

    protected boolean contentFilter(String contentType) {
        return contentType.matches(".*(charset|text|html|htm|json|urlencoded)+.*");
    }

    private static void logPair(String type, PairReader reader, Level level) {
        for (NameAndValue nv : reader) {
            String msg = type + " --> " + nv.name + " = " + nv.value;
            log(msg, level);
        }
    }

    private static void logFile(List<FileBody> fileBodies, Level level) {
        for (FileBody body : fileBodies) {
            log("request file --> " + body, level);
        }
    }

    private static void log(String msg, Level level) {
        Platform.get().logger().log(TAG, msg, level);
    }

    private static String buildString(int count, char c) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; ++i) {
            builder.append(c);
        }
        return builder.toString();
    }
}
