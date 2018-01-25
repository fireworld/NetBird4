package cc.colorcat.netbird4;

import java.io.IOException;

/**
 * Created by cxx on 2018/1/25.
 * xx.ch@outlook.com
 */
public interface Callback {

    void onStart();

    void onResponse(Call call, Response response) throws IOException;

    void onFailure(Call call, IOException cause);

    void onFinish();
}
