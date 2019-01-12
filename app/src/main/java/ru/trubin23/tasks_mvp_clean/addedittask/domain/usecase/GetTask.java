package ru.trubin23.tasks_mvp_clean.addedittask.domain.usecase;

import android.support.annotation.NonNull;

import ru.trubin23.tasks_mvp_clean.UseCase;
import ru.trubin23.tasks_mvp_clean.data.TasksRepository;

public class GetTask extends UseCase<GetTask.RequestValues, GetTask.ResponseValue> {

    private final TasksRepository mTasksRepository;

    public GetTask(@NonNull TasksRepository tasksRepository){
        mTasksRepository = tasksRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {

    }

    public static final class RequestValues implements UseCase.RequestValues{

    }

    public static final class ResponseValue implements UseCase.ResponseValue {

    }

}
