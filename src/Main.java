import cc.colorcat.netbird4.*;
import cc.colorcat.netbird4.logging.LoggingTailInterceptor;

public class Main {
    private static final String TAG = "NetBird";

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
        MRequest req = createRequest();
        NET_BIRD.send(req);
    }

    private static MRequest createRequest() {
        return new MRequest.Builder<>(StringParser.getUtf8())
                .path("api/teacher")
                .add("type", String.valueOf(4))
                .add("num", String.valueOf(30))
                .listener(new MRequest.Listener<String>() {
                    @Override
                    public void onStart() {
                        log("onStart", Level.VERBOSE);
                    }

                    @Override
                    public void onSuccess(String result) {
                        log(result, Level.INFO);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        log("code = " + code + ", msg = " + msg, Level.ERROR);
                    }

                    @Override
                    public void onFinish() {
                        log("onFinish", Level.VERBOSE);
                    }
                })
                .build();
    }

    private static void log(String msg, Level level) {
        Platform.get().logger().log(TAG, msg, level);
    }
}
