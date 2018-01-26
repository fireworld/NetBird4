package cc.colorcat.netbird4;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

/**
 * Created by cxx on 18-1-26.
 * xx.ch@outlook.com
 */
public class HttpConnection implements Connection {
    protected static boolean cacheEnabled = false;
    private HttpURLConnection conn;
    private InputStream input;

    protected HttpConnection() {
    }

    @Override
    public void connect(NetBird netBird, Request request) throws IOException {
        enableCache(netBird.cachePath, netBird.cacheSize);
        final URL url = new URL(request.url);
        final Proxy proxy = netBird.proxy;
        conn = (HttpURLConnection) (proxy == null ? url.openConnection() : url.openConnection(proxy));
        conn.setConnectTimeout(netBird.connectTimeOut);
        conn.setReadTimeout(netBird.readTimeOut);
        conn.setDoInput(true);
        conn.setRequestMethod(request.method.name());
        conn.setDoOutput(request.method.needBody());
        conn.setUseCaches(cacheEnabled);
        if (conn instanceof HttpsURLConnection) {
            final HttpsURLConnection cast = (HttpsURLConnection) conn;
            final SSLSocketFactory sslSocketFactory = netBird.sslSocketFactory;
            if (sslSocketFactory != null) cast.setSSLSocketFactory(sslSocketFactory);
            final HostnameVerifier hostnameVerifier = netBird.hostnameVerifier;
            if (hostnameVerifier != null) cast.setHostnameVerifier(hostnameVerifier);
        }
    }

    @Override
    public void writeHeaders(Headers headers) throws IOException {
        for (int i = 0, size = headers.size(); i < size; ++i) {
            conn.addRequestProperty(headers.name(i), headers.value(i));
        }
    }

    @Override
    public void writeRequestBody(RequestBody requestBody) throws IOException {
        final long contentLength = requestBody.contentLength();
        if (contentLength > 0L) {
            OutputStream output = null;
            try {
                output = conn.getOutputStream();
                requestBody.writeTo(output);
                output.flush();
            } finally {
                Utils.close(output);
            }
        }
    }

    @Override
    public int responseCode() throws IOException {
        return conn.getResponseCode();
    }

    @Override
    public String responseMsg() throws IOException {
        return conn.getResponseMessage();
    }

    @Override
    public Headers responseHeaders() throws IOException {
        return Headers.ofWithIgnoreNull(conn.getHeaderFields());
    }

    @Override
    public ResponseBody responseBody(Headers headers) throws IOException {
        if (input == null) {
            input = conn.getInputStream();
        }
        return input != null ? ResponseBody.create(input, headers) : null;
    }

    @Override
    public void cancel() {
        if (conn != null) {
            conn.disconnect();
        }
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public Connection clone() {
        return new HttpConnection();
    }

    @Override
    public void close() throws IOException {
        Utils.close(input);
        if (conn != null) {
            conn.disconnect();
        }
    }

    protected void enableCache(File cachePath, long cacheSize) {
        HttpConnection.cacheEnabled = (cachePath != null && cacheSize > 0L);
    }
}
