package cc.colorcat.netbird4;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by cxx on 18-1-25.
 * xx.ch@outlook.com
 */
public class Request {
    final String url;
    final String path;
    final Method method;
    final Parameters parameters;
    final List<FileBody> fileBodies;
    final Headers headers;
    final DownloadListener downloadListener;
    final String boundary;
    final Object tag;
    private RequestBody requestBody;

    boolean freeze = false;

    protected Request(Builder builder) {
        this.url = builder.url;
        this.path = builder.path;
        this.method = builder.method;
        this.parameters = builder.parameters.toParameters();
        this.fileBodies = Utils.immutableList(builder.fileBodies);
        this.headers = builder.headers.toHeaders();
        this.downloadListener = builder.downloadListener;
        this.boundary = builder.boundary;
        this.tag = builder.tag;
    }

    public final String url() {
        return url;
    }

    public final String path() {
        return path;
    }

    public final Method method() {
        return method;
    }

    public final Parameters parameters() {
        return parameters;
    }

    public final List<FileBody> fileBodies() {
        return fileBodies;
    }

    public final Headers headers() {
        return headers;
    }

    public final DownloadListener downloadListener() {
        return downloadListener;
    }

    public final Object tag() {
        return tag;
    }

    final RequestBody requestBody() {
        if (requestBody == null) {
            requestBody = Utils.buildRequestBody(parameters, fileBodies, boundary);
        }
        return requestBody;
    }

    public final boolean isFreeze() {
        return freeze;
    }

    final Request freeze() {
        this.freeze = true;
        return this;
    }

    final Request unfreeze() {
        this.freeze = false;
        return this;
    }

    public Builder newBuilder() {
        if (freeze) throw new IllegalStateException("The request has been frozen, call isFreeze() to check.");
        return new Builder(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(boundary, request.boundary) &&
                Objects.equals(url, request.url) &&
                Objects.equals(path, request.path) &&
                method == request.method &&
                Objects.equals(parameters, request.parameters) &&
                Objects.equals(fileBodies, request.fileBodies) &&
                Objects.equals(headers, request.headers) &&
                Objects.equals(downloadListener, request.downloadListener) &&
                Objects.equals(tag, request.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, path, method, parameters, fileBodies, headers, downloadListener, boundary, tag);
    }

    @Override
    public String toString() {
        return "Request{" +
                "url='" + url + '\'' +
                ", path='" + path + '\'' +
                ", method=" + method +
                ", parameters=" + parameters +
                ", fileBodies=" + fileBodies +
                ", headers=" + headers +
                ", downloadListener=" + downloadListener +
                ", boundary='" + boundary + '\'' +
                ", tag=" + tag +
                ", freeze=" + freeze +
                '}';
    }


    public static class Builder {
        private String url;
        private String path;
        private Method method;
        private final MutableParameters parameters;
        private final List<FileBody> fileBodies;
        private final MutableHeaders headers;
        private DownloadListener downloadListener;
        private final String boundary;
        private Object tag;
        private RequestBody requestBody;

        public Builder() {
            this.url = "";
            this.path = "";
            this.method = Method.GET;
            this.parameters = MutableParameters.create(6);
            this.fileBodies = new ArrayList<>(1);
            this.headers = MutableHeaders.create(6);
            this.downloadListener = null;
            this.boundary = "==" + System.currentTimeMillis() + "==";
            this.tag = this.boundary;
            this.requestBody = null;
        }

        protected Builder(Request request) {
            this.url = request.url;
            this.path = request.path;
            this.method = request.method;
            this.parameters = request.parameters.toMutableParameters();
            this.fileBodies = new ArrayList<>(request.fileBodies);
            this.headers = request.headers.toMutableHeaders();
            this.downloadListener = request.downloadListener;
            this.boundary = request.boundary;
            this.tag = request.tag;
            this.requestBody = null;
        }

        final RequestBody requestBody() {
            if (requestBody == null) {
                requestBody = Utils.buildRequestBody(parameters, fileBodies, boundary);
            }
            return requestBody;
        }

        public final String url() {
            return url;
        }

        public final String path() {
            return path;
        }

        public final Method method() {
            return method;
        }

        public final DownloadListener downloadListener() {
            return downloadListener;
        }

        public final Object tag() {
            return tag;
        }

        public final Parameters parameters() {
            return parameters.toParameters();
        }

        public final List<String> names() {
            return parameters.names();
        }

        public final List<String> values() {
            return parameters.values();
        }

        public final String value(String name) {
            return parameters.value(name);
        }

        public final String value(String name, String defaultValue) {
            return parameters.value(name, defaultValue);
        }

        public final List<String> values(String name) {
            return parameters.values(name);
        }

        public final Headers headers() {
            return headers.toHeaders();
        }

        public final List<String> headerNames() {
            return headers.names();
        }

        public final List<String> headerValues() {
            return headers.values();
        }

        public final String headerValue(String name) {
            return headers.value(name);
        }

        public final String headerValue(String name, String defaultValue) {
            return headers.value(name, defaultValue);
        }

        public final List<String> headerValues(String name) {
            return headers.values();
        }

        public Builder url(String url) {
            this.url = Utils.checkedUrl(url);
            return this;
        }

        public Builder clearUrl() {
            this.url = "";
            return this;
        }

        public Builder path(String path) {
            if (path == null) throw new NullPointerException("path == null");
            this.path = path;
            return this;
        }

        public Builder clearPath() {
            this.path = "";
            return this;
        }

        public Builder method(Method method) {
            if (method == null) throw new NullPointerException("method == null");
            this.method = method;
            return this;
        }

        public Builder get() {
            this.method = Method.GET;
            return this;
        }

        public Builder head() {
            this.method = Method.HEAD;
            return this;
        }

        public Builder trace() {
            this.method = Method.TRACE;
            return this;
        }

        public Builder options() {
            this.method = Method.OPTIONS;
            return this;
        }

        public Builder post() {
            this.method = Method.POST;
            return this;
        }

        public Builder put() {
            this.method = Method.PUT;
            return this;
        }

        public Builder delete() {
            this.method = Method.DELETE;
            return this;
        }

        public Builder replace(Parameters parameters) {
            this.parameters.clear();
            this.parameters.addAll(parameters.names(), parameters.values());
            return this;
        }

        public Builder add(Parameters parameters) {
            this.parameters.addAll(parameters.names(), parameters.values());
            return this;
        }

        public Builder add(String name, String value) {
            this.parameters.add(name, value);
            return this;
        }

        public Builder addIfNot(String name, String value) {
            this.parameters.addIfNot(name, value);
            return this;
        }

        public Builder addAll(List<String> names, List<String> values) {
            this.parameters.addAll(names, values);
            return this;
        }

        public Builder set(String name, String value) {
            this.parameters.set(name, value);
            return this;
        }

        public Builder replaceIfExists(String name, String value) {
            this.parameters.replaceIfExists(name, value);
            return this;
        }

        public Builder remove(String name) {
            this.parameters.removeAll(name);
            return this;
        }

        public Builder clear() {
            this.parameters.clear();
            return this;
        }

        public Builder addFile(String name, String contentType, File file) {
            addFile(name, contentType, file, null);
            return this;
        }

        public Builder addFile(String name, String contentType, File file, UploadListener listener) {
            this.fileBodies.add(FileBody.create(name, contentType, file, listener));
            return this;
        }

        public Builder clearFile() {
            this.fileBodies.clear();
            return this;
        }

        public Builder replaceHeaders(Headers headers) {
            this.headers.clear();
            this.headers.addAll(headers.names(), headers.values());
            return this;
        }

        public Builder addHeaders(Headers headers) {
            this.headers.addAll(headers.names(), headers.values());
            return this;
        }

        public Builder addHeader(String name, String value) {
            this.headers.add(name, value);
            return this;
        }

        public Builder addHeaderIfNot(String name, String value) {
            this.headers.addIfNot(name, value);
            return this;
        }

        public Builder addHeaders(List<String> names, List<String> values) {
            this.headers.addAll(names, values);
            return this;
        }

        public Builder setHeader(String name, String value) {
            this.headers.set(name, value);
            return this;
        }

        public Builder replaceHeaderIfExists(String name, String value) {
            this.headers.replaceIfExists(name, value);
            return this;
        }

        public Builder removeHeader(String name) {
            this.headers.removeAll(name);
            return this;
        }

        public Builder clearHeaders() {
            this.headers.clear();
            return this;
        }

        public Builder downloadListener(DownloadListener listener) {
            this.downloadListener = listener;
            return this;
        }

        public Builder clearDownloadListener() {
            this.downloadListener = null;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }
}
