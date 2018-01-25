package cc.colorcat.netbird4;

/**
 * Created by cxx on 18-1-25.
 * xx.ch@outlook.com
 */
public enum Method {
    GET, HEAD, TRACE, OPTIONS, POST, PUT, DELETE;

    public boolean needBody() {
        switch (this) {
            case POST:
            case PUT:
            case DELETE:
                return true;
            default:
                return false;
        }
    }
}
