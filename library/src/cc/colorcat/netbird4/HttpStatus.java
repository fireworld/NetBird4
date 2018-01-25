package cc.colorcat.netbird4;

/**
 * Created by cxx on 2018/1/25.
 * xx.ch@outlook.com
 */
public final class HttpStatus {
    public static final int CODE_CONNECT_ERROR = -100;
    public static final String MSG_CONNECT_ERROR = "connect error";

    public static final int CODE_DUPLICATE_REQUEST = -101;
    public static final String MSG_DUPLICATE_REQUEST = "duplicate request";

    public static final int CODE_REQUEST_CANCELED = -102;
    public static final String MSG_REQUEST_CANCELED = "request canceled";

    private static volatile StateIOException duplicateRequest;
    private static volatile StateIOException requestCanceled;

    static StateIOException duplicateRequest() {
        if (duplicateRequest == null) {
            synchronized (HttpStatus.class) {
                if (duplicateRequest == null) {
                    duplicateRequest = new StateIOException(CODE_DUPLICATE_REQUEST, MSG_DUPLICATE_REQUEST);
                }
            }
        }
        return duplicateRequest;
    }

    static StateIOException requestCanceled() {
        if (requestCanceled == null) {
            synchronized (HttpStatus.class) {
                if (requestCanceled == null) {
                    requestCanceled = new StateIOException(CODE_REQUEST_CANCELED, MSG_REQUEST_CANCELED);
                }
            }
        }
        return requestCanceled;
    }

    private HttpStatus() {
        throw new AssertionError("no instance");
    }
}
