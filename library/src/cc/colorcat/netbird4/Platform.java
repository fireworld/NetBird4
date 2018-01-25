package cc.colorcat.netbird4;

/**
 * Created by cxx on 18-1-25.
 * xx.ch@outlook.com
 */
public abstract class Platform {
    static volatile Platform instance;

    public static Platform get() {
        if (instance == null) {
            synchronized (Platform.class) {
                if (instance == null) {
                    Platform.instance = findPlatform();
                }
            }
        }
        return instance;
    }

    private static Platform findPlatform() {
        return new GenericPlatform();
    }

    abstract Connection connection();

    abstract Scheduler scheduler();

    abstract Logger logger();
}
