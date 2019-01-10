package ru.trubin23.tasks_mvp_clean;

public abstract class UseCase<Q extends UseCase.RequestValues, P extends UseCase.ResponseValues> {

    public Q requestValues;

    public UseCaseCallback<P> useCaseCallback;

    void run() {
        executeUseCase(requestValues);
    }

    protected abstract void executeUseCase(Q requestValues);

    public interface RequestValues {

    }

    public interface ResponseValues {

    }

    public interface UseCaseCallback<R> {

        void onSuccess(R response);

        void onError();
    }
}
