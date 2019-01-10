package ru.trubin23.tasks_mvp_clean;

public interface UseCaseScheduler {

    void execute(Runnable runnable);

    <V extends UseCase.ResponseValues> void notifyResponse(
            final V response, final UseCase.UseCaseCallback<V> useCaseCallback);

    <V extends UseCase.ResponseValues> void onError(
            final UseCase.UseCaseCallback<V> useCaseCallback);
}
