package me.gojee.mvp.domain;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author goJee
 * @since 2018/3/29
 */

public class UseCaseSchedulerImpl implements UseCaseScheduler {

    private static final int POOL_SIZE = 2;
    private static final int MAX_POOL_SIZE = 4;
    private static final int TIMEOUT = 30;

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private ThreadPoolExecutor mThreadPoolExecutor;

    public UseCaseSchedulerImpl() {
        mThreadPoolExecutor = new ThreadPoolExecutor(POOL_SIZE, MAX_POOL_SIZE, TIMEOUT,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(POOL_SIZE));
    }

    @Override
    public void execute(Runnable runnable) {
        mThreadPoolExecutor.execute(runnable);
    }

    @Override
    public <Response extends ResponseValue> void notifyResponse(final Response response, final OnFinishListener<Response> listener) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                 listener.onSuccess(response);
            }
        });
    }

    @Override
    public <Response extends ResponseValue> void notifyError(final int errorCode, final OnFinishListener<Response> listener) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onError(errorCode);
            }
        });
    }
}
