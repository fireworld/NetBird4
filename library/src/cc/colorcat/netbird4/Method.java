package cc.colorcat.netbird4;

/**
 * Created by cxx on 18-1-25.
 * xx.ch@outlook.com
 */
public enum Method {
    GET(false), HEAD(false), TRACE(false), OPTIONS(false), POST(true), PUT(true), DELETE(true);

    Method(boolean needBody) {
        this.needBody = needBody;
    }

    private boolean needBody;

    public boolean needBody() {
        return needBody;
    }
}
