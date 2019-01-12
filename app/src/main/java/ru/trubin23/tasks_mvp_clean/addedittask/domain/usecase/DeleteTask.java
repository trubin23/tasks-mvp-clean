package ru.trubin23.tasks_mvp_clean.addedittask.domain.usecase;

import android.support.annotation.NonNull;

import ru.trubin23.tasks_mvp_clean.UseCase;
import ru.trubin23.tasks_mvp_clean.data.TasksRepository;

public class DeleteTask extends UseCase<DeleteTask.RequestValues, DeleteTask.ResponseValue> {

    private final TasksRepository mTasksRepository;

    public DeleteTask(@NonNull TasksRepository tasksRepository){
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
