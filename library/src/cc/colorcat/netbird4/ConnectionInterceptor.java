package cc.colorcat.netbird4;

import java.io.IOException;

/**
 * Created by cxx on 18-1-26.
 * xx.ch@outlook.com
 */
final class ConnectionInterceptor implements Interceptor {
    private final NetBird netBird;

    ConnectionInterceptor(NetBird netBird) {
        this.netBird = netBird;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Connection conn = chain.connection();
        final Request request = chain.request();
        conn.connect(netBird, request);
        conn.writeHeaders(request.headers);
        final Method method = request.method;
        if (method.needBody()) {
            final RequestBody body = request.requestBody();
            if (body == null) {
                throw new IllegalArgumentException("method " + request.method.name() + " must have a request body");
            }
            conn.writeRequestBody(body);
        }
        final Headers headers = Utils.nullElse(conn.responseHeaders(), Headers.EMPTY);
        final int code = conn.responseCode();
        final String msg = Utils.nullElse(conn.responseMsg(), "");
        ResponseBody body = null;
        if (code == 200) {
            body = conn.responseBody(headers);
        }
        return new Response.Builder().responseCode(code).responseMsg(msg).headers(headers).responseBody(body).build();
    }
}
