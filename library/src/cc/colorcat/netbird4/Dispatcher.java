package cc.colorcat.netbird4;

import java.util.concurrent.ExecutorService;

/**
 * Created by cxx on 18-1-25.
 * xx.ch@outlook.com
 */
class Dispatcher {
    private ExecutorService executor;
    private int maxRunning;

    Dispatcher() {

    }

    synchronized void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    synchronized void setMaxRunning(int maxRunning) {
        this.maxRunning = maxRunning;
    }
}
