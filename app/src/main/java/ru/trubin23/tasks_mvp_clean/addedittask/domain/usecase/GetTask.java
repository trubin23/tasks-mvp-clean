package ru.trubin23.tasks_mvp_clean.addedittask.domain.usecase;

import android.support.annotation.NonNull;

import ru.trubin23.tasks_mvp_clean.UseCase;
import ru.trubin23.tasks_mvp_clean.data.TasksDataSource;
import ru.trubin23.tasks_mvp_clean.data.TasksRepository;
import ru.trubin23.tasks_mvp_clean.tasks.domain.model.Task;

public class GetTask extends UseCase<GetTask.RequestValues, GetTask.ResponseValue> {

    private final TasksRepository mTasksRepository;

    public GetTask(@NonNull TasksRepository tasksRepository) {
        mTasksRepository = tasksRepository;
    }

    @Override
    protected void executeUseCase(RequestValues values) {
        mTasksRepository.getTask(values.getTaskId(), new TasksDataSource.GetTaskCallback() {
            @Override
            public void onTaskLoaded(@NonNull Task task) {
                if (task != null) {
                    ResponseValue responseValue = new ResponseValue(task);
                    useCaseCallback.onSuccess(responseValue);
                } else {
                    useCaseCallback.onError();
                }
            }

            @Override
            public void onDataNotAvailable() {
                useCaseCallback.onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final String mTaskId;

        public RequestValues(@NonNull String taskId) {
            mTaskId = taskId;
        }

        String getTaskId() {
            return mTaskId;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final Task mTask;

        public ResponseValue(@NonNull Task task) {
            mTask = task;
        }

        public Task getTask() {
            return mTask;
        }
    }

}
