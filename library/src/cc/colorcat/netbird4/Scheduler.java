package cc.colorcat.netbird4;

/**
 * Created by cxx on 18-1-25.
 * xx.ch@outlook.com
 */
public interface Scheduler {

    boolean isTargetThread();

    void onTargetThread(Runnable runnable);
}
