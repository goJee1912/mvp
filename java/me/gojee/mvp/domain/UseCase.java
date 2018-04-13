package me.gojee.mvp.domain;

import me.gojee.mvp.data.DataRepository;

/**
 * @author goJee
 * @since 2018/3/29
 */

public abstract class UseCase<Request extends RequestValue, Response extends ResponseValue> {

    private Request mRequest;
    private OnFinishListener<Response> mListener;

    void setRequestValue(Request request) {
        this.mRequest = request;
    }

    void setOnFinishListener(OnFinishListener<Response> listener) {
        this.mListener = listener;
    }

    void run() {
        executeUseCase(mRequest);
    }

    protected abstract void executeUseCase(Request request);

    protected abstract DataRepository getRepository();

    protected void success(Response response) {
        if (mListener != null) {
            mListener.onSuccess(response);
        }
    }

    protected void error(int errorCode) {
        if (mListener != null) {
            mListener.onError(errorCode);
        }
    }
}
