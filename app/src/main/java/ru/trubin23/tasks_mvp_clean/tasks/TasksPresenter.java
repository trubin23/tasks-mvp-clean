package ru.trubin23.tasks_mvp_clean.tasks;

import android.support.annotation.NonNull;

import ru.trubin23.tasks_mvp_clean.UseCaseHandler;
import ru.trubin23.tasks_mvp_clean.tasks.domain.usecase.ActivateTask;
import ru.trubin23.tasks_mvp_clean.tasks.domain.usecase.ClearCompleteTasks;
import ru.trubin23.tasks_mvp_clean.tasks.domain.usecase.CompleteTask;
import ru.trubin23.tasks_mvp_clean.tasks.domain.usecase.GetTasks;

public class TasksPresenter implements TasksContract.Presenter {

    private final UseCaseHandler mUseCaseHandler;

    private final TasksContract.View mTasksView;

    private final GetTasks mGetTasks;
    private final ActivateTask mActivateTask;
    private final CompleteTask mCompleteTask;
    private final ClearCompleteTasks mClearCompleteTasks;

    @NonNull
    private TasksFilterType mFilterType = TasksFilterType.ALL_TASKS;

    private boolean mFirstLoad = true;

    TasksPresenter(@NonNull UseCaseHandler useCaseHandler,
                   @NonNull TasksContract.View tasksView, @NonNull GetTasks getTasks,
                   @NonNull ActivateTask activateTask, @NonNull CompleteTask completeTask,
                   @NonNull ClearCompleteTasks clearCompleteTasks) {
        mUseCaseHandler = useCaseHandler;

        mTasksView = tasksView;

        mGetTasks = getTasks;
        mActivateTask = activateTask;
        mCompleteTask = completeTask;
        mClearCompleteTasks = clearCompleteTasks;

        mTasksView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void setFiltering(TasksFilterType filterType) {
        mFilterType = filterType;
    }

    @Override
    public TasksFilterType getFiltering() {
        return mFilterType;
    }
}
