package cc.colorcat.netbird4;

/**
 * Created by cxx on 18-1-25.
 * xx.ch@outlook.com
 */
final class Log {
    static Level threshold = Level.NOTHING;

    static void v(String tag, String msg) {
        log(tag, msg, Level.VERBOSE);
    }

    static void d(String tag, String msg) {
        log(tag, msg, Level.DEBUG);
    }

    static void i(String tag, String msg) {
        log(tag, msg, Level.INFO);
    }

    static void w(String tag, String msg) {
        log(tag, msg, Level.WARN);
    }

    static void e(String tag, String msg) {
        log(tag, msg, Level.ERROR);
    }

    static void e(Throwable throwable) {
        if (Level.ERROR.priority >= threshold.priority) {
            throwable.printStackTrace();
        }
    }

    private static void log(String tag, String msg, Level level) {
        if (level.priority >= threshold.priority) {
            Platform.get().logger().log(tag, msg, level);
        }
    }

    private Log() {
        throw new AssertionError("no instance");
    }
}
