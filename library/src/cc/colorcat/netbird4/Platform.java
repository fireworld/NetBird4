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
        try {
            Class<?> clazz = Class.forName("cc.colorcat.netbird4.android.AndroidPlatform");
            return (Platform) clazz.newInstance();
        } catch (Exception e) {
            Log.e(e);
        }
        return new GenericPlatform();
    }

    public abstract Connection connection();

    public abstract Scheduler scheduler();

    public abstract Logger logger();
}
