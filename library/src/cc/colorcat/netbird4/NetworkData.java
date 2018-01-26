package cc.colorcat.netbird4;

import java.util.Objects;

/**
 * Created by cxx on 18-1-26.
 * xx.ch@outlook.com
 */
public final class NetworkData<T> {

    public static <T> NetworkData<? extends T> newSuccess(T data) {
        if (data == null) throw new IllegalArgumentException("data == null");
        return new NetworkData<>(200, "ok", data);
    }

    public static <T> NetworkData<? extends T> newFailure(int code, String msg) {
        if (msg == null) throw new IllegalArgumentException("msg == null");
        return new NetworkData<>(code, msg, null);
    }

    public final int code;
    public final String msg;
    public final T data;
    public final boolean isSuccess;

    private NetworkData(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.isSuccess = (this.data != null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NetworkData<?> that = (NetworkData<?>) o;
        return code == that.code &&
                Objects.equals(msg, that.msg) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, msg, data);
    }

    @Override
    public String toString() {
        return "NetworkData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", isSuccess=" + isSuccess +
                '}';
    }
}
