package ru.trubin23.tasks_mvp_clean.statistics.domain.usecase;

import android.support.annotation.NonNull;

import java.util.List;

import ru.trubin23.tasks_mvp_clean.UseCase;
import ru.trubin23.tasks_mvp_clean.data.TasksDataSource;
import ru.trubin23.tasks_mvp_clean.data.TasksRepository;
import ru.trubin23.tasks_mvp_clean.statistics.domain.model.Statistics;
import ru.trubin23.tasks_mvp_clean.tasks.domain.model.Task;

public class GetStatistics
        extends UseCase<GetStatistics.RequestValues, GetStatistics.ResponseValue> {

    private final TasksRepository mTasksRepository;

    public GetStatistics(@NonNull TasksRepository tasksRepository) {
        mTasksRepository = tasksRepository;
    }

    @Override
    protected void executeUseCase(RequestValues values) {
        mTasksRepository.getTasks(new TasksDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                int activeTasks = 0;
                int completedTasks = 0;

                for (Task task : tasks) {
                    if (task.isCompleted()) {
                        completedTasks++;
                    } else {
                        activeTasks++;
                    }
                }

                ResponseValue responseValue = new ResponseValue(
                        new Statistics(completedTasks, activeTasks));
                useCaseCallback.onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                useCaseCallback.onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {

    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final Statistics mStatistics;

        public ResponseValue(@NonNull Statistics statistics) {
            mStatistics = statistics;
        }

        public Statistics getStatistics() {
            return mStatistics;
        }
    }

}
