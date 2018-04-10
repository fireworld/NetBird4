package cc.colorcat.netbird4;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Created by cxx on 18-1-26.
 * xx.ch@outlook.com
 */
final class GenericLogger implements Logger {
    private final java.util.logging.Logger logger;

    {
        logger = java.util.logging.Logger.getLogger(NetBird.class.getSimpleName());
        final Formatter formatter = new Formatter() {
            @Override
            public synchronized String format(LogRecord record) {
                return String.format("%-7s %s\n", toLevel(record.getLevel()).name(), record.getMessage());
            }
        };
        logger.setUseParentHandlers(false);
        final ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(formatter);
        final java.util.logging.Level level = java.util.logging.Level.ALL;
        handler.setLevel(level);
        logger.addHandler(handler);
        logger.setLevel(level);
    }

    @Override
    public void log(String tag, String msg, Level level) {
        String log = tag + " --> " + msg;
        switch (level) {
            case VERBOSE:
                logger.log(java.util.logging.Level.FINE, log);
                break;
            case DEBUG:
                logger.log(java.util.logging.Level.CONFIG, log);
                break;
            case INFO:
                logger.log(java.util.logging.Level.INFO, log);
                break;
            case WARN:
                logger.log(java.util.logging.Level.WARNING, log);
                break;
            case ERROR:
                logger.log(java.util.logging.Level.SEVERE, log);
                break;
            default:
                break;
        }
    }

    private static Level toLevel(java.util.logging.Level level) {
        final int value = level.intValue();
        if (value == java.util.logging.Level.FINE.intValue()) {
            return Level.VERBOSE;
        }
        if (value == java.util.logging.Level.CONFIG.intValue()) {
            return Level.DEBUG;
        }
        if (value == java.util.logging.Level.INFO.intValue()) {
            return Level.INFO;
        }
        if (value == java.util.logging.Level.WARNING.intValue()) {
            return Level.WARN;
        }
        if (value == java.util.logging.Level.SEVERE.intValue()) {
            return Level.ERROR;
        }
        throw new IllegalArgumentException("un supported level = " + level);
    }
}
