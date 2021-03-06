package ru.trubin23.tasks_mvp_clean.tasks.domain.usecase;

import android.support.annotation.NonNull;

import java.util.List;

import ru.trubin23.tasks_mvp_clean.UseCase;
import ru.trubin23.tasks_mvp_clean.data.TasksDataSource;
import ru.trubin23.tasks_mvp_clean.data.TasksRepository;
import ru.trubin23.tasks_mvp_clean.tasks.TasksFilterType;
import ru.trubin23.tasks_mvp_clean.tasks.domain.filter.FilterFactory;
import ru.trubin23.tasks_mvp_clean.tasks.domain.filter.TaskFilter;
import ru.trubin23.tasks_mvp_clean.tasks.domain.model.Task;

public class GetTasks extends UseCase<GetTasks.RequestValues, GetTasks.ResponseValue> {

    private final TasksRepository mTasksRepository;

    private final FilterFactory mFilterFactory;

    public GetTasks(@NonNull TasksRepository tasksRepository,
                    @NonNull FilterFactory filterFactory) {
        mTasksRepository = tasksRepository;
        mFilterFactory = filterFactory;
    }

    @Override
    protected void executeUseCase(RequestValues values) {
        if (values.isForceUpdate()) {
            mTasksRepository.refreshTasks();
        }

        mTasksRepository.getTasks(new TasksDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                TasksFilterType currentFiltering = values.mCurrentFiltering;
                TaskFilter taskFilter = mFilterFactory.create(currentFiltering);

                List<Task> tasksFiltered = taskFilter.filter(tasks);
                ResponseValue responseValue = new ResponseValue(tasksFiltered);
                useCaseCallback.onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                useCaseCallback.onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final boolean mForceUpdate;
        private final TasksFilterType mCurrentFiltering;

        public RequestValues(boolean forceUpdate, @NonNull TasksFilterType currentFiltering) {
            mForceUpdate = forceUpdate;
            mCurrentFiltering = currentFiltering;
        }

        boolean isForceUpdate() {
            return mForceUpdate;
        }

        TasksFilterType getCurrentFiltering() {
            return mCurrentFiltering;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<Task> mTasks;

        public ResponseValue(@NonNull List<Task> tasks) {
            mTasks = tasks;
        }

        public List<Task> getTasks() {
            return mTasks;
        }
    }
}
