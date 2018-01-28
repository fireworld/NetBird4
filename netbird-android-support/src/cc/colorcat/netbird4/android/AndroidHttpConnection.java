package cc.colorcat.netbird4.android;


import android.net.http.HttpResponseCache;
import cc.colorcat.netbird4.Connection;
import cc.colorcat.netbird4.HttpConnection;

import java.io.File;
import java.io.IOException;

/**
 * Created by cxx on 2018/1/28.
 * xx.ch@outlook.com
 */
final class AndroidHttpConnection extends HttpConnection {

    @Override
    public Connection clone() {
        return new AndroidHttpConnection();
    }

    @Override
    protected void enableCache(File cachePath, long cacheSize) {
        if (cachePath != null && cacheSize > 0L) {
            if (!HttpConnection.cacheEnabled) {
                try {
                    HttpResponseCache cache = HttpResponseCache.getInstalled();
                    if (cache == null) {
                        if (cachePath.exists() || cachePath.mkdirs()) {
                            cache = HttpResponseCache.install(cachePath, cacheSize);
                        }
                    }
                    HttpConnection.cacheEnabled = (cache != null);
                } catch (IOException e) {
                    HttpConnection.cacheEnabled = false;
                }
            }
        } else if (HttpConnection.cacheEnabled) {
            HttpConnection.cacheEnabled = false;
            try {
                HttpResponseCache cache = HttpResponseCache.getInstalled();
                if (cache != null) {
                    cache.close();
                }
            } catch (IOException ignore) {
            }
        }
    }
}
