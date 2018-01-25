package cc.colorcat.netbird4;

/**
 * Created by cxx on 18-1-25.
 * xx.ch@outlook.com
 */
final class Log {
    static Level threshold = Level.NOTHING;

    static void e(Throwable throwable) {

    }

    private Log() {
        throw new AssertionError("no instance");
    }
}
