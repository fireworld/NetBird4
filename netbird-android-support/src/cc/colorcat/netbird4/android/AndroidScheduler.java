package cc.colorcat.netbird4.android;

import android.os.Handler;
import android.os.Looper;
import cc.colorcat.netbird4.Scheduler;

/**
 * Created by cxx on 2018/1/28.
 * xx.ch@outlook.com
 */
final class AndroidScheduler implements Scheduler {
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public boolean isTargetThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    @Override
    public void onTargetThread(Runnable runnable) {
        handler.post(runnable);
    }
}
