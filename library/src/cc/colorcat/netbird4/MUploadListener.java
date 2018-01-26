package cc.colorcat.netbird4;

/**
 * Created by cxx on 18-1-26.
 * xx.ch@outlook.com
 */
class MUploadListener implements UploadListener {
    static UploadListener wrap(UploadListener listener) {
        return listener != null ? new MUploadListener(listener) : null;
    }

    private final UploadListener listener;

    private MUploadListener(UploadListener listener) {
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
