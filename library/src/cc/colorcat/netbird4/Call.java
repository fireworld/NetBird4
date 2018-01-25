package cc.colorcat.netbird4;

import java.io.IOException;

/**
 * Created by cxx on 2018/1/25.
 * xx.ch@outlook.com
 */
public interface Call extends Cloneable {

    Request request();

    Response execute() throws IOException;

    void enqueue(Callback callback);

    void cancel();

    boolean canceled();

    Call clone();

    interface Factory {

        Call newCall(Request request);
    }
}
