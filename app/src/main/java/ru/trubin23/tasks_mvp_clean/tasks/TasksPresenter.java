package ru.trubin23.tasks_mvp_clean.tasks;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.util.List;

import ru.trubin23.tasks_mvp_clean.UseCase;
import ru.trubin23.tasks_mvp_clean.UseCaseHandler;
import ru.trubin23.tasks_mvp_clean.tasks.domain.model.Task;
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
        loadTasks(false);
    }

    @Override
    public void loadTasks(boolean forceUpdate) {
        loadTasks(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadTasks(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mTasksView.setLoadingIndicator(true);
        }

        GetTasks.RequestValues requestValues = new GetTasks.RequestValues(forceUpdate, mFilterType);

        mUseCaseHandler.execute(mGetTasks, requestValues,
                new UseCase.UseCaseCallback<GetTasks.ResponseValue>() {
                    @Override
                    public void onSuccess(GetTasks.ResponseValue response) {
                        List<Task> tasks = response.getTasks();

                        if (mTasksView.isNotActive()) {
                            return;
                        }
                        if (showLoadingUI) {
                            mTasksView.setLoadingIndicator(false);
                        }

                        processTasks(tasks);
                    }

                    @Override
                    public void onError() {
                        if (mTasksView.isNotActive()) {
                            return;
                        }
                        mTasksView.showLoadingTasksError();
                    }
                });
    }

    private void processTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            processEmptyTasks();
        } else {
            mTasksView.showTasks(tasks);
            showFilterLabel();
        }
    }

    private void processEmptyTasks() {
        switch (mFilterType) {
            case ACTIVE_TASKS:
                mTasksView.showNoActiveTasks();
                break;
            case COMPLETED_TASKS:
                mTasksView.showNoCompletedTasks();
                break;
            case ALL_TASKS:
            default:
                mTasksView.showNoTasks();
                break;
        }
    }

    private void showFilterLabel() {
        switch (mFilterType) {
            case ACTIVE_TASKS:
                mTasksView.showActiveFilterLabel();
                break;
            case COMPLETED_TASKS:
                mTasksView.showCompletedFilterLabel();
                break;
            case ALL_TASKS:
            default:
                mTasksView.showAllFilterLabel();
                break;
        }
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
    public void addNewTask() {
        mTasksView.showAddTask();
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

    @Override
    public void clearCompletedTasks() {
        mUseCaseHandler.execute(mClearCompleteTasks, new ClearCompleteTasks.RequestValues(),
                new UseCase.UseCaseCallback<ClearCompleteTasks.ResponseValue>() {
                    @Override
                    public void onSuccess(ClearCompleteTasks.ResponseValue response) {
                        mTasksView.showCompletedTasksCleared();
                        loadTasks(false, false);
                    }

                    @Override
                    public void onError() {
                        mTasksView.showLoadingTasksError();
                    }
                });
    }

    @Override
    public void result(int requestCode, int resultCode) {
        if (TasksActivity.REQUEST_ADD_TASK == requestCode && Activity.RESULT_OK == resultCode) {
            mTasksView.showSuccessfullySavedMessage();
        }
    }
}
