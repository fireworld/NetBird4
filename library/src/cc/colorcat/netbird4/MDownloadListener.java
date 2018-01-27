package cc.colorcat.netbird4;

/**
 * Created by cxx on 18-1-26.
 * xx.ch@outlook.com
 */
final class MDownloadListener implements DownloadListener {
    static DownloadListener wrap(DownloadListener listener) {
        return listener != null ? new MDownloadListener(listener) : null;
    }

    private final DownloadListener listener;

    private MDownloadListener(DownloadListener listener) {
        this.listener = listener;
    }

    @Override
    public void onChanged(final long finished, final long total, final int percent) {
        if (Utils.isTargetThread()) {
            listener.onChanged(finished, total, percent);
        } else {
            Utils.onTargetThread(new Runnable() {
                @Override
                public void run() {
                    listener.onChanged(finished, total, percent);
                }
            });
        }
    }
}
