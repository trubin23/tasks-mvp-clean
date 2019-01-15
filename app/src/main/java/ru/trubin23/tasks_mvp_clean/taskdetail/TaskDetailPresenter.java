package ru.trubin23.tasks_mvp_clean.taskdetail;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Strings;

import ru.trubin23.tasks_mvp_clean.UseCase;
import ru.trubin23.tasks_mvp_clean.UseCaseHandler;
import ru.trubin23.tasks_mvp_clean.addedittask.domain.usecase.DeleteTask;
import ru.trubin23.tasks_mvp_clean.addedittask.domain.usecase.GetTask;
import ru.trubin23.tasks_mvp_clean.tasks.domain.model.Task;
import ru.trubin23.tasks_mvp_clean.tasks.domain.usecase.ActivateTask;
import ru.trubin23.tasks_mvp_clean.tasks.domain.usecase.CompleteTask;

public class TaskDetailPresenter implements TaskDetailContract.Presenter {

    private final UseCaseHandler mUseCaseHandler;

    @Nullable
    private String mTaskId;

    private final TaskDetailContract.View mTaskDetailView;

    private final GetTask mGetTask;
    private final ActivateTask mActivateTask;
    private final CompleteTask mCompleteTask;
    private final DeleteTask mDeleteTask;

    TaskDetailPresenter(@NonNull UseCaseHandler useCaseHandler,
                        @Nullable String taskId,
                        @NonNull TaskDetailContract.View taskDetailView,
                        @NonNull GetTask getTask,
                        @NonNull ActivateTask activateTask,
                        @NonNull CompleteTask completeTask,
                        @NonNull DeleteTask deleteTask) {
        mUseCaseHandler = useCaseHandler;
        mTaskId = taskId;
        mTaskDetailView = taskDetailView;
        mGetTask = getTask;
        mActivateTask = activateTask;
        mCompleteTask = completeTask;
        mDeleteTask = deleteTask;
    }

    @Override
    public void start() {
        if (Strings.isNullOrEmpty(mTaskId)) {
            mTaskDetailView.showMissingTask();
            return;
        }

        mTaskDetailView.showLoadingIndicator();

        mUseCaseHandler.execute(mGetTask, new GetTask.RequestValues(mTaskId),
                new UseCase.UseCaseCallback<GetTask.ResponseValue>() {
                    @Override
                    public void onSuccess(GetTask.ResponseValue response) {
                        Task task = response.getTask();

                        if (mTaskDetailView.isActive()) {
                            showTask(task);
                        }
                    }

                    @Override
                    public void onError() {
                        if (mTaskDetailView.isActive()) {
                            mTaskDetailView.showMissingTask();
                        }
                    }
                });
    }

    @Override
    public void editTask() {
        if (Strings.isNullOrEmpty(mTaskId)) {
            mTaskDetailView.showMissingTask();
        } else {
            mTaskDetailView.showEditTask(mTaskId);
        }
    }

    @Override
    public void result(int requestCode, int resultCode) {
        if (TaskDetailActivity.REQUEST_EDIT_TASK == requestCode &&
                Activity.RESULT_OK == resultCode) {
            mTaskDetailView.exit();
        }
    }

    private void showTask(Task task) {
        String title = task.getTitle();
        String description = task.getDescription();

        if (Strings.isNullOrEmpty(title)) {
            mTaskDetailView.hideTitle();
        } else {
            mTaskDetailView.showTitle(title);
        }

        if (Strings.isNullOrEmpty(description)) {
            mTaskDetailView.hideDescription();
        } else {
            mTaskDetailView.showDescription(description);
        }

        mTaskDetailView.showCompletionStatus(task.isCompleted());
    }

    @Override
    public void completeTask() {
        if (Strings.isNullOrEmpty(mTaskId)) {
            mTaskDetailView.showMissingTask();
            return;
        }

        mUseCaseHandler.execute(mCompleteTask, new CompleteTask.RequestValues(mTaskId),
                new UseCase.UseCaseCallback<CompleteTask.ResponseValue>() {
                    @Override
                    public void onSuccess(CompleteTask.ResponseValue response) {
                        mTaskDetailView.showTaskMarkedComplete();
                    }

                    @Override
                    public void onError() {
                        //ignore
                    }
                });
    }

    @Override
    public void activateTask() {
        if (Strings.isNullOrEmpty(mTaskId)) {
            mTaskDetailView.showMissingTask();
            return;
        }

        mUseCaseHandler.execute(mActivateTask, new ActivateTask.RequestValues(mTaskId),
                new UseCase.UseCaseCallback<ActivateTask.ResponseValue>() {
                    @Override
                    public void onSuccess(ActivateTask.ResponseValue response) {
                        mTaskDetailView.showTaskMarkedActivate();
                    }

                    @Override
                    public void onError() {
                        //ignore
                    }
                });
    }

    @Override
    public void deleteTask() {
        mUseCaseHandler.execute(mDeleteTask, new DeleteTask.RequestValues(mTaskId),
                new UseCase.UseCaseCallback<DeleteTask.ResponseValue>() {
                    @Override
                    public void onSuccess(DeleteTask.ResponseValue response) {
                        mTaskDetailView.showTaskDeleted();
                    }

                    @Override
                    public void onError() {
                        //ignore
                    }
                });
    }
}
