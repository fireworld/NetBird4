package cc.colorcat.netbird4;

/**
 * Created by cxx on 18-1-25.
 * xx.ch@outlook.com
 */
public enum Level {
    VERBOSE(2), DEBUG(3), INFO(4), WARN(5), ERROR(6), NOTHING(10);

    public final int priority;

    Level(int priority) {
        this.priority = priority;
    }
}
