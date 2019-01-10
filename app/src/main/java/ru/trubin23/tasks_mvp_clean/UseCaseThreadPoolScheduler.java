package ru.trubin23.tasks_mvp_clean;

import android.os.Handler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UseCaseThreadPoolScheduler implements UseCaseScheduler {

    private final Handler mHandler = new Handler();

    private static final int POOL_SIZE = 2;

    private static final int MAX_POOL_SIZE = 4;

    private static final int TIMEOUT = 30;

    ThreadPoolExecutor mThreadPoolExecutor;

    UseCaseThreadPoolScheduler() {
        mThreadPoolExecutor = new ThreadPoolExecutor(POOL_SIZE, MAX_POOL_SIZE, TIMEOUT,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(POOL_SIZE));
    }

    @Override
    public void execute(Runnable runnable) {
        mThreadPoolExecutor.execute(runnable);
    }

    @Override
    public <V extends UseCase.ResponseValues> void notifyResponse(
            final V response, final UseCase.UseCaseCallback<V> useCaseCallback) {

        mHandler.post(() -> useCaseCallback.onSuccess(response));
    }

    @Override
    public <V extends UseCase.ResponseValues> void onError(
            final UseCase.UseCaseCallback<V> useCaseCallback) {

        mHandler.post(useCaseCallback::onError);
    }
}
