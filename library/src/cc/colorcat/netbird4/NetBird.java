package cc.colorcat.netbird4;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import java.io.File;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by cxx on 18-1-25.
 * xx.ch@outlook.com
 */
public final class NetBird implements Call.Factory {
    private final Platform platform;
    final String baseUrl;
    final List<Interceptor> headInterceptors;
    final List<Interceptor> tailInterceptors;
    final ExecutorService executor;
    final Dispatcher dispatcher;
    final Connection connection;
    final Proxy proxy;
    final SSLSocketFactory sslSocketFactory;
    final HostnameVerifier hostnameVerifier;
    final long cacheSize;
    final File cachePath;
    final int maxRunning;
    final int readTimeOut;
    final int connectTimeOut;
    final boolean gzipEnabled;

    private NetBird(Builder builder) {
        this.platform = builder.platform;
        this.baseUrl = builder.baseUrl;
        this.headInterceptors = Utils.immutableList(builder.headInterceptors);
        this.tailInterceptors = Utils.immutableList(builder.tailInterceptors);
        this.executor = builder.executor;
        this.dispatcher = builder.dispatcher;
        this.connection = builder.connection;
        this.proxy = builder.proxy;
        this.sslSocketFactory = builder.sslSocketFactory;
        this.hostnameVerifier = builder.hostnameVerifier;
        this.cacheSize = builder.cacheSize;
        this.cachePath = builder.cachePath;
        this.maxRunning = builder.maxRunning;
        this.readTimeOut = builder.readTimeOut;
        this.connectTimeOut = builder.connectTimeOut;
        this.gzipEnabled = builder.gzipEnabled;
        Platform.instance = this.platform;
        dispatcher.setExecutor(this.executor);
        dispatcher.setMaxRunning(this.maxRunning);
        Log.threshold = builder.logLevel;
    }

    @Override
    public Call newCall(Request request) {
        return null;
    }

    public String baseUrl() {
        return baseUrl;
    }

    public List<Interceptor> headInterceptors() {
        return headInterceptors;
    }

    public List<Interceptor> tailInterceptors() {
        return tailInterceptors;
    }

    public Connection connection() {
        return connection.clone();
    }

    public Proxy proxy() {
        return proxy;
    }

    public SSLSocketFactory sslSocketFactory() {
        return sslSocketFactory;
    }

    public HostnameVerifier hostnameVerifier() {
        return hostnameVerifier;
    }

    public long cacheSize() {
        return cacheSize;
    }

    public File cachePath() {
        return cachePath;
    }

    public int maxRunning() {
        return maxRunning;
    }

    public int readTimeOut() {
        return readTimeOut;
    }

    public int connectTimeOut() {
        return connectTimeOut;
    }

    public boolean gzipEnabled() {
        return gzipEnabled;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }


    public static final class Builder {
        private Platform platform;
        private String baseUrl;
        private List<Interceptor> headInterceptors;
        private List<Interceptor> tailInterceptors;
        private ExecutorService executor;
        private Dispatcher dispatcher;
        private Connection connection;
        private Proxy proxy;
        private SSLSocketFactory sslSocketFactory;
        private HostnameVerifier hostnameVerifier;
        private long cacheSize;
        private File cachePath;
        private int maxRunning;
        private int readTimeOut;
        private int connectTimeOut;
        private boolean gzipEnabled;
        private Level logLevel;

        public Builder(String baseUrl) {
            this.baseUrl = Utils.checkedUrl(baseUrl);
            this.platform = Platform.get();
            this.headInterceptors = new ArrayList<>(2);
            this.tailInterceptors = new ArrayList<>(2);
            this.executor = null;
            this.dispatcher = new Dispatcher();
            this.connection = this.platform.connection();
            this.proxy = null;
            this.sslSocketFactory = null;
            this.hostnameVerifier = null;
            this.cacheSize = -1L;
            this.cachePath = null;
            this.maxRunning = 6;
            this.readTimeOut = 10000;
            this.connectTimeOut = 10000;
            this.gzipEnabled = false;
            this.logLevel = Level.NOTHING;
        }

        private Builder(NetBird netBird) {
            this.platform = netBird.platform;
            this.baseUrl = netBird.baseUrl;
            this.headInterceptors = new ArrayList<>(netBird.headInterceptors);
            this.tailInterceptors = new ArrayList<>(netBird.tailInterceptors);
            this.executor = netBird.executor;
            this.dispatcher = netBird.dispatcher;
            this.connection = netBird.connection;
            this.proxy = netBird.proxy;
            this.sslSocketFactory = netBird.sslSocketFactory;
            this.hostnameVerifier = netBird.hostnameVerifier;
            this.cacheSize = netBird.cacheSize;
            this.cachePath = netBird.cachePath;
            this.maxRunning = netBird.maxRunning;
            this.readTimeOut = netBird.readTimeOut;
            this.connectTimeOut = netBird.connectTimeOut;
            this.gzipEnabled = netBird.gzipEnabled;
            this.logLevel = Log.threshold;
        }

        public Builder platform(Platform platform) {
            this.platform = platform;
            this.connection = platform.connection();
            return this;
        }

        public Builder addHeadInterceptor(Interceptor interceptor) {
            if (interceptor == null) throw new IllegalArgumentException("interceptor == null");
            this.headInterceptors.add(interceptor);
            return this;
        }

        public Builder removeHeadInterceptor(Interceptor interceptor) {
            this.headInterceptors.remove(interceptor);
            return this;
        }

        public Builder addTailInterceptor(Interceptor interceptor) {
            if (interceptor == null) throw new IllegalArgumentException("interceptor == null");
            this.tailInterceptors.add(interceptor);
            return this;
        }

        public Builder removeTailInterceptor(Interceptor interceptor) {
            this.tailInterceptors.remove(interceptor);
            return this;
        }

        public Builder executor(ExecutorService executor) {
            if (executor == null) throw new IllegalArgumentException("executor == null");
            this.executor = executor;
            return this;
        }

        public Builder connection(Connection connection) {
            if (connection == null) throw new IllegalArgumentException("connection == null");
            this.connection = connection;
            return this;
        }
    }
}
