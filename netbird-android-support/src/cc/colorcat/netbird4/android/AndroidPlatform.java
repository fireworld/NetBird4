package cc.colorcat.netbird4.android;

import cc.colorcat.netbird4.Connection;
import cc.colorcat.netbird4.Logger;
import cc.colorcat.netbird4.Platform;
import cc.colorcat.netbird4.Scheduler;

/**
 * Created by cxx on 2018/1/28.
 * xx.ch@outlook.com
 */
public final class AndroidPlatform extends Platform {
    private final Connection connection = new AndroidHttpConnection();
    private final Scheduler scheduler = new AndroidScheduler();
    private final Logger logger = new AndroidLogger();

    @Override
    public Connection connection() {
        return connection;
    }

    @Override
    public Scheduler scheduler() {
        return scheduler;
    }

    @Override
    public Logger logger() {
        return logger;
    }
}
