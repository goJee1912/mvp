package me.gojee.mvp.domain;

/**
 * @author goJee
 * @since 2018/3/29
 */

public interface OnFinishListener<Response extends ResponseValue> {

    void onSuccess(Response response);
    void onError(int errorCode);
}
