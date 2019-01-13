package ru.trubin23.tasks_mvp_clean.tasks;

import android.support.annotation.NonNull;

import ru.trubin23.tasks_mvp_clean.UseCase;
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

    private void loadTasks(boolean forceUpdate, final boolean showLoadingUI) {

    }

    @Override
    public void setFiltering(TasksFilterType filterType) {
        mFilterType = filterType;
    }

    @Override
    public TasksFilterType getFiltering() {
        return mFilterType;
    }

    @Override
    public void openTaskDetails(@NonNull String taskId) {
        mTasksView.showTaskDetail(taskId);
    }

    @Override
    public void completeTask(@NonNull String taskId) {
        mUseCaseHandler.execute(mCompleteTask, new CompleteTask.RequestValues(taskId),
                new UseCase.UseCaseCallback<CompleteTask.ResponseValue>() {
                    @Override
                    public void onSuccess(CompleteTask.ResponseValue response) {
                        mTasksView.showTaskMarkedComplete();
                        loadTasks(false, false);
                    }

                    @Override
                    public void onError() {
                        mTasksView.showLoadingTasksError();
                    }
                });
    }

    @Override
    public void activateTask(@NonNull String taskId) {
        mUseCaseHandler.execute(mActivateTask, new ActivateTask.RequestValues(taskId),
                new UseCase.UseCaseCallback<ActivateTask.ResponseValue>() {
                    @Override
                    public void onSuccess(ActivateTask.ResponseValue response) {
                        mTasksView.showTaskMarkedActive();
                        loadTasks(false, false);
                    }

                    @Override
                    public void onError() {
                        mTasksView.showLoadingTasksError();
                    }
                });
    }
}
