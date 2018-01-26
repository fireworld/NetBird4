package cc.colorcat.netbird4;

import java.io.IOException;

/**
 * Created by cxx on 2018/1/25.
 * xx.ch@outlook.com
 */
public final class StateIOException extends IOException {
    int state;

    public StateIOException(int state, String message) {
        super(message);
        this.state = state;
    }

    public StateIOException(int state, String message, Throwable cause) {
        super(message, cause);
        this.state = state;
    }

    public StateIOException(int state, Throwable cause) {
        super(cause);
        this.state = state;
    }

    public int state() {
        return this.state;
    }

    @Override
    public String toString() {
        return "StateIOException{" +
                "state=" + state +
                ", message=" + getMessage() +
                ", cause=" + getCause() +
                '}';
    }
}
