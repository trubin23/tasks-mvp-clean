package ru.trubin23.tasks_mvp_clean.tasks.domain.usecase;

import android.support.annotation.NonNull;

import ru.trubin23.tasks_mvp_clean.UseCase;
import ru.trubin23.tasks_mvp_clean.data.TasksRepository;

public class ClearCompleteTasks
    extends UseCase<ClearCompleteTasks.RequestValues, ClearCompleteTasks.ResponseValue> {

    private final TasksRepository mTasksRepository;

    public ClearCompleteTasks(@NonNull TasksRepository tasksRepository){
        mTasksRepository = tasksRepository;
    }

    @Override
    protected void executeUseCase(RequestValues values) {
        mTasksRepository.clearCompletedTask();
        useCaseCallback.onSuccess(new ResponseValue());
    }

    public static final class RequestValues implements UseCase.RequestValues{

    }

    public static final class ResponseValue implements UseCase.ResponseValue {

    }
}
