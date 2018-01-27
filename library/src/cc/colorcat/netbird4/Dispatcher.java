package cc.colorcat.netbird4;

import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;

/**
 * Created by cxx on 18-1-25.
 * xx.ch@outlook.com
 */
final class Dispatcher {
    private ExecutorService executor;
    private int maxRunning = 6;
    private final Queue<RealCall.AsyncCall> waitingAsyncCalls = new ConcurrentLinkedQueue<>();
    private final Set<RealCall.AsyncCall> runningAsyncCalls = new CopyOnWriteArraySet<>();
    private final Set<RealCall> runningSyncCalls = new CopyOnWriteArraySet<>();

    Dispatcher() {
    }

    synchronized void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    synchronized void setMaxRunning(int maxRunning) {
        this.maxRunning = maxRunning;
    }

    boolean executed(RealCall call) {
        return runningSyncCalls.add(call);
    }

    void enqueue(RealCall.AsyncCall call) {
        if (!waitingAsyncCalls.contains(call) && waitingAsyncCalls.offer(call)) {
            promoteCalls();
        } else {
            onDuplicateRequest(call);
        }
    }

    private synchronized void promoteCalls() {
        if (runningAsyncCalls.size() >= maxRunning) return;
        for (RealCall.AsyncCall call = waitingAsyncCalls.poll(); call != null; call = waitingAsyncCalls.poll()) {
            if (runningAsyncCalls.add(call)) {
                executor.execute(call);
                if (runningAsyncCalls.size() >= maxRunning) return;
            } else {
                onDuplicateRequest(call);
            }
        }
    }

    private static void onDuplicateRequest(RealCall.AsyncCall call) {
        Callback callback = call.callback();
        callback.onFailure(call.get(), HttpStatus.duplicateRequest());
        callback.onFinish();
    }

    void finished(RealCall call) {
        runningSyncCalls.remove(call);
    }

    void finished(RealCall.AsyncCall call) {
        runningAsyncCalls.remove(call);
        promoteCalls();
    }

    void cancelWaiting(Object tag) {
        Iterator<RealCall.AsyncCall> iterator = waitingAsyncCalls.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().request().tag().equals(tag)) {
                iterator.remove();
            }
        }
    }

    void cancelAll(Object tag) {
        cancelWaiting(tag);
        for (RealCall.AsyncCall call : runningAsyncCalls) {
            if (call.request().tag().equals(tag)) {
                call.get().cancel();
            }
        }
        for (RealCall call : runningSyncCalls) {
            if (call.request().tag().equals(tag)) {
                call.cancel();
            }
        }
    }


    void cancelAll() {
        waitingAsyncCalls.clear();
        for (RealCall.AsyncCall call : runningAsyncCalls) {
            call.get().cancel();
        }
        for (RealCall call : runningSyncCalls) {
            call.cancel();
        }
    }
}
