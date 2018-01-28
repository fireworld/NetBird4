package cc.colorcat.netbird4.android;

import android.util.Log;
import cc.colorcat.netbird4.Level;
import cc.colorcat.netbird4.Logger;

/**
 * Created by cxx on 2018/1/28.
 * xx.ch@outlook.com
 */
final class AndroidLogger implements Logger {
    private static final int MAX_LENGTH = 1024 * 2;

    @Override
    public void log(String tag, String msg, Level level) {
        switch (level) {
            case VERBOSE:
                println(tag, msg, Log.VERBOSE);
                break;
            case DEBUG:
                println(tag, msg, Log.DEBUG);
                break;
            case INFO:
                println(tag, msg, Log.INFO);
                break;
            case WARN:
                println(tag, msg, Log.WARN);
                break;
            case ERROR:
                println(tag, msg, Log.ERROR);
                break;
            default:
                break;
        }
    }

    private static void println(String tag, String msg, int priority) {
        for (int start = 0, end = start + MAX_LENGTH, size = msg.length(); start < size; start = end, end = start + MAX_LENGTH) {
            if (end >= size) {
                Log.println(priority, tag, msg.substring(start));
            } else {
                Log.println(priority, tag, msg.substring(start, end));
            }
        }
    }
}
