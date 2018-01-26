import cc.colorcat.netbird4.*;

import java.io.File;

public class Main {
    public static final String TAG = "NetBird";

    private static final NetBird NET_BIRD;

    static {
        NET_BIRD = new NetBird.Builder("http://www.pconline.com.cn")
                .enableGzip(true)
                .maxRunning(10)
                .logLevel(Level.VERBOSE)
                .build();
    }

    public static void main(String[] args) {
        final String qq = "https://dldir1.qq.com/invc/tt/QQBrowser_Setup_9.7.12672.400.exe";
        final File savePath = new File("/home/cxx/qq.exe");
        MRequest<File> request = new MRequest.Builder<File>(FileParser.create(savePath))
                .url(qq)
                .downloadListener(new DownloadListener() {
                    @Override
                    public void onChanged(long finished, long total, int percent) {
                        log(String.format("finished = %d, total = %d, percent = %d", finished, total, percent), Level.INFO);
                    }
                })
                .listener(new MRequest.Listener<File>() {
                    @Override
                    public void onStart() {
                        log("onStart", Level.INFO);
                    }

                    @Override
                    public void onSuccess(File result) {
                        log("onSuccess, result = " + result.getAbsolutePath(), Level.VERBOSE);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        log("onFailure, code = " + code + ", msg = " + msg, Level.ERROR);
                    }

                    @Override
                    public void onFinish() {
                        log("onFinish", Level.INFO);
                    }
                })
                .build();
        NET_BIRD.send(request);
    }

    private static void log(String msg, Level level) {
        Platform.get().logger().log(TAG, msg, level);
    }
}
