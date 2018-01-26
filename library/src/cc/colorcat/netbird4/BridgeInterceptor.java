package cc.colorcat.netbird4;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

/**
 * Created by cxx on 18-1-26.
 * xx.ch@outlook.com
 */
final class BridgeInterceptor implements Interceptor {
    private final String baseUrl;

    BridgeInterceptor(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        URI uri = URI.create(Utils.emptyElse(builder.url(), baseUrl));
        final String path = builder.path();
        if (!Utils.isEmpty(path)) uri = uri.resolve(path);
        String url = uri.toString();

        RequestBody body;
        if (!builder.method().needBody()) {
            String query = concatParameters(builder.names(), builder.values());
            if (query != null) {
                url = url + '?' + query;
                builder.clear();
            }
        } else if ((body = builder.requestBody()) != null) {
            builder.setHeader(Headers.CONTENT_TYPE, body.contentType());
            final long contentLength = body.contentLength();
            if (contentLength > 0L) {
                builder.setHeader(Headers.CONTENT_LENGTH, Long.toString(contentLength))
                        .removeHeader("Transfer-Encoding");
            } else {
                builder.setHeader("Transfer-Encoding", "chunked")
                        .removeHeader(Headers.CONTENT_LENGTH);
            }
        }
        builder.url(url).clearPath()
                .addHeaderIfNot("Connection", "Keep-Alive")
                .addHeaderIfNot("User-Agent", Version.userAgent());

        Response response = chain.proceed(builder.build().freeze());
        final DownloadListener listener = builder.downloadListener();
        ResponseBody responseBody = response.responseBody;
        if (listener != null && responseBody != null) {
            final long contentLength = responseBody.contentLength();
            if (contentLength > 0L) {
                final InputStream newStream = ProgressInputStream.of(responseBody.stream(), contentLength, listener);
                final ResponseBody newBody = ResponseBody.create(newStream, responseBody.contentType(), contentLength, responseBody.charset());
                response = response.newBuilder().responseBody(newBody).build();
            }
        }
        return response;
    }

    private static String concatParameters(List<String> names, List<String> values) {
        if (names.isEmpty()) return null;
        StringBuilder builder = new StringBuilder();
        for (int i = 0, size = names.size(); i < size; ++i) {
            if (i > 0) builder.append('&');
            String encodedName = Utils.smartEncode(names.get(i));
            String encodedValue = Utils.smartEncode(values.get(i));
            builder.append(encodedName).append('=').append(encodedValue);
        }
        return builder.toString();
    }
}
