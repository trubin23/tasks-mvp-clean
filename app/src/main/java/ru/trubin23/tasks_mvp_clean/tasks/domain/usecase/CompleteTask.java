package ru.trubin23.tasks_mvp_clean.tasks.domain.usecase;

import android.support.annotation.NonNull;

import ru.trubin23.tasks_mvp_clean.UseCase;
import ru.trubin23.tasks_mvp_clean.data.TasksRepository;

public class CompleteTask extends UseCase<CompleteTask.RequestValues, CompleteTask.RequestValue> {

    private final TasksRepository mTasksRepository;

    public CompleteTask(@NonNull TasksRepository tasksRepository){
        mTasksRepository = tasksRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {

    }

    public static final class RequestValues implements UseCase.RequestValues{

    }

    public static final class RequestValue implements UseCase.ResponseValue {

    }

}
