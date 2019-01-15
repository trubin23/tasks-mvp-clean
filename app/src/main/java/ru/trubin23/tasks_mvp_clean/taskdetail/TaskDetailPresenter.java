package ru.trubin23.tasks_mvp_clean.taskdetail;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Strings;

import ru.trubin23.tasks_mvp_clean.UseCaseHandler;
import ru.trubin23.tasks_mvp_clean.addedittask.domain.usecase.DeleteTask;
import ru.trubin23.tasks_mvp_clean.addedittask.domain.usecase.GetTask;
import ru.trubin23.tasks_mvp_clean.tasks.TasksActivity;
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

    public TaskDetailPresenter(@NonNull UseCaseHandler useCaseHandler,
                               @Nullable String taskId,
                               @NonNull TaskDetailContract.View taskDetailView,
                               @NonNull GetTask getTask,
                               @NonNull ActivateTask activateTask,
                               @NonNull CompleteTask completeTask,
                               @NonNull DeleteTask deleteTask){
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
        if (Strings.isNullOrEmpty(mTaskId)){
            mTaskDetailView.showMissingTask();
            return;
        }

        mTaskDetailView.showLoadingIndicator();
    }

    @Override
    public void editTask() {
        if (Strings.isNullOrEmpty(mTaskId)){
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
}
