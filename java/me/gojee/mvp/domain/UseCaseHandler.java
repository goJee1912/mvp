package me.gojee.mvp.domain;

/**
 * @author goJee
 * @since 2018/4/3
 */

public class UseCaseHandler {

    private final UseCaseScheduler mUseCaseScheduler;

    private UseCaseHandler(UseCaseScheduler useCaseScheduler) {
        mUseCaseScheduler = useCaseScheduler;
    }

    private static class Holder {
        static UseCaseHandler instance = new UseCaseHandler(new UseCaseSchedulerImpl());
    }

    public static UseCaseHandler getInstance() {
        return Holder.instance;
    }

    public <Request extends RequestValue, Response extends ResponseValue>
    void execute(final UseCase<Request, Response> useCase,
                 Request request, OnFinishListener<Response> listener) {
        useCase.setRequestValue(request);
        useCase.setOnFinishListener(new OnFinishListenerWrapper<>(listener, this));

        mUseCaseScheduler.execute(new Runnable() {
            @Override
            public void run() {
                useCase.run();
            }
        });
    }

    private <Response extends ResponseValue> void notifyResponse(Response response,
                                                                 OnFinishListener<Response> listener) {
        mUseCaseScheduler.notifyResponse(response, listener);
    }

    private <Response extends ResponseValue> void notifyError(int errorCode,
                                                              OnFinishListener<Response> listener) {
        mUseCaseScheduler.notifyError(errorCode, listener);
    }

    private static final class OnFinishListenerWrapper<Response extends ResponseValue> implements
            OnFinishListener<Response> {

        private final OnFinishListener<Response> mListener;
        private final UseCaseHandler mUseCaseHandler;

        OnFinishListenerWrapper(OnFinishListener<Response> listener,
                                UseCaseHandler useCaseHandler) {
            mListener = listener;
            mUseCaseHandler = useCaseHandler;
        }

        @Override
        public void onSuccess(Response response) {
            mUseCaseHandler.notifyResponse(response, mListener);
        }

        @Override
        public void onError(int errorCode) {
            mUseCaseHandler.notifyError(errorCode, mListener);
        }
    }
}
