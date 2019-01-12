package ru.trubin23.tasks_mvp_clean.addedittask.domain.usecase;

import android.support.annotation.NonNull;

import ru.trubin23.tasks_mvp_clean.UseCase;
import ru.trubin23.tasks_mvp_clean.data.TasksRepository;

public class SaveTask extends UseCase<SaveTask.RequestValues, SaveTask.ResponseValue> {

    private final TasksRepository mTasksRepository;

    public SaveTask(@NonNull TasksRepository tasksRepository){
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
