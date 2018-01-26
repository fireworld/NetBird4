package cc.colorcat.netbird4;

import java.io.IOException;

/**
 * Created by cxx on 18-1-26.
 * xx.ch@outlook.com
 */
final class MCallback<T> implements Callback {
    private final Parser<? extends T> parser;
    private final MRequest.Listener<? super T> listener;
    private NetworkData<? extends T> networkData;

    MCallback(Parser<? extends T> parser, MRequest.Listener<? super T> listener) {
        this.parser = parser;
        this.listener = listener;
    }

    @Override
    public void onStart() {
        if (listener != null) {
            if (Utils.isTargetThread()) {
                listener.onStart();
            } else {
                Utils.onTargetThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onStart();
                    }
                });
            }
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.code == 200 && response.responseBody != null) {
            networkData = parser.parse(response);
        }
        if (networkData == null) {
            networkData = NetworkData.newFailure(response.code, response.msg);
        }
    }

    @Override
    public void onFailure(Call call, StateIOException cause) {
        networkData = NetworkData.newFailure(cause.state, Utils.nullElse(cause.getMessage(), ""));
    }

    @Override
    public void onFinish() {
        if (listener != null) {
            if (Utils.isTargetThread()) {
                deliverData();
            } else {
                Utils.onTargetThread(new Runnable() {
                    @Override
                    public void run() {
                        deliverData();
                    }
                });
            }
        }
    }

    private void deliverData() {
        if (networkData.isSuccess) {
            listener.onSuccess(networkData.data);
        } else {
            listener.onFailure(networkData.code, networkData.msg);
        }
        listener.onFinish();
    }
}
