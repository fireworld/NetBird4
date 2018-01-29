import cc.colorcat.netbird4.*;
import cc.colorcat.netbird4.logging.LoggingTailInterceptor;

public class Main {
    public static final String TAG = "NetBird";

    private static final NetBird NET_BIRD;

    static {
        NET_BIRD = new NetBird.Builder("http://www.imooc.com/")
                .enableGzip(true)
                .addTailInterceptor(new LoggingTailInterceptor())
                .maxRunning(10)
                .logLevel(Level.VERBOSE)
                .build();
    }

    public static void main(String[] args) {
//        final String qq = "https://dldir1.qq.com/invc/tt/QQBrowser_Setup_9.7.12672.400.exe";
//        final File savePath = new File("/home/cxx/qq.exe");
//        MRequest<File> request = new MRequest.Builder<File>(FileParser.create(savePath))
//                .url(qq)
//                .downloadListener(new DownloadListener() {
//                    @Override
//                    public void onChanged(long finished, long total, int percent) {
//                        log(String.format("finished = %d, total = %d, percent = %d", finished, total, percent), Level.INFO);
//                    }
//                })
//                .listener(new MRequest.Listener<File>() {
//                    @Override
//                    public void onStart() {
//                        log("onStart", Level.INFO);
//                    }
//
//                    @Override
//                    public void onSuccess(File result) {
//                        log("onSuccess, result = " + result.getAbsolutePath(), Level.VERBOSE);
//                    }
//
//                    @Override
//                    public void onFailure(int code, String msg) {
//                        log("onFailure, code = " + code + ", msg = " + msg, Level.ERROR);
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        log("onFinish", Level.INFO);
//                    }
//                })
//                .build();
        final MRequest<String> request = new MRequest.Builder<>(StringParser.getUtf8())
                .path("/api/teacher")
                .add("type", String.valueOf(4))
                .add("num", String.valueOf(30))
//                .listener(new MRequest.Listener<String>() {
//                    @Override
//                    public void onStart() {
//                        log("onStart", Level.VERBOSE);
//                    }
//
//                    @Override
//                    public void onSuccess(String result) {
//                        log(result, Level.INFO);
//                    }
//
//                    @Override
//                    public void onFailure(int code, String msg) {
//                        log("code = " + code + ", msg = " + msg, Level.ERROR);
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        log("onFinish", Level.VERBOSE);
//                    }
//                })
                .build();
        final MRequest<String> req = new MRequest.Builder<>(StringParser.getUtf8())
                .path("/api/teacher")
                .add("type", String.valueOf(4))
                .add("num", String.valueOf(30))
//                .listener(new MRequest.Listener<String>() {
//                    @Override
//                    public void onStart() {
//                        log("onStart", Level.VERBOSE);
//                    }
//
//                    @Override
//                    public void onSuccess(String result) {
//                        log(result, Level.INFO);
//                    }
//
//                    @Override
//                    public void onFailure(int code, String msg) {
//                        log("code = " + code + ", msg = " + msg, Level.ERROR);
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        log("onFinish", Level.VERBOSE);
//                    }
//                })
                .build();
        System.out.println(request.equals(req));
        System.out.println("request = " + request);
        System.out.println("req = " + req);
//        NET_BIRD.send(request);
    }

    private static void log(String msg, Level level) {
        Platform.get().logger().log(TAG, msg, level);
    }
}
