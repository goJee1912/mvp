package me.gojee.mvp.domain;

/**
 * @author goJee
 * @since 2017/12/28
 */

public interface UseCaseScheduler {

    void execute(Runnable runnable);

    <Response extends ResponseValue>
    void notifyResponse(final Response response, final OnFinishListener<Response> listener);

    <Response extends ResponseValue>
    void notifyError(final int errorCode, final OnFinishListener<Response> listener);

}
