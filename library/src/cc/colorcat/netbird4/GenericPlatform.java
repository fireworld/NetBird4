package cc.colorcat.netbird4;

/**
 * Created by cxx on 18-1-25.
 * xx.ch@outlook.com
 */
public final class GenericPlatform extends Platform {
    private final Connection conn = new HttpConnection();
    private final Scheduler scheduler = new GenericScheduler();
    private final Logger logger = new GenericLogger();

    @Override
    public Connection connection() {
        return conn;
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
