package cc.colorcat.netbird4;

/**
 * Created by cxx on 18-1-26.
 * xx.ch@outlook.com
 */
final class Version {

    static String userAgent() {
        return "NetBird/4.0.0";
    }

    private Version() {
        throw new AssertionError("no instance");
    }
}
