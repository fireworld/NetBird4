package cc.colorcat.netbird4;

/**
 * Created by cxx on 18-1-26.
 * xx.ch@outlook.com
 */
final class GenericScheduler implements Scheduler {

    @Override
    public boolean isTargetThread() {
        return true;
    }

    @Override
    public void onTargetThread(Runnable runnable) {
        runnable.run();
    }
}
