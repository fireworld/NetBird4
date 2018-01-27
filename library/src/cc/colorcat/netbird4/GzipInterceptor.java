package cc.colorcat.netbird4;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * Created by cxx on 18-1-26.
 * xx.ch@outlook.com
 */
final class GzipInterceptor implements Interceptor {
    private final boolean gzipEnabled;

    GzipInterceptor(boolean gzipEnabled) {
        this.gzipEnabled = gzipEnabled;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!gzipEnabled) return chain.proceed(chain.request());
        boolean transparentGzip = false;
        final Request.Builder builder = chain.request().unfreeze().newBuilder();
        if (builder.headerValue("Accept-Encoding") == null && builder.headerValue("Range") == null) {
            transparentGzip = true;
            builder.addHeader("Accept-Encoding", "gzip");
        }

        Response response = chain.proceed(builder.build().freeze());
        if (transparentGzip && "gzip".equalsIgnoreCase(response.header("Content-Encoding"))) {
            final ResponseBody body = response.responseBody;
            if (body != null) {
                final InputStream newStream = new GZIPInputStream(body.stream());
                final Response.Builder newBuilder = response.newBuilder()
                        .removeHeader("Content-Encoding")
                        .removeHeader("Content-Length");
                final ResponseBody newBody = ResponseBody.create(newStream, newBuilder.headers());
                response = newBuilder.responseBody(newBody).build();
            }
        }
        return response;
    }
}
