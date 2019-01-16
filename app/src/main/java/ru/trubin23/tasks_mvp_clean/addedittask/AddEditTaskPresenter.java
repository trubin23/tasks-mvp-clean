package ru.trubin23.tasks_mvp_clean.addedittask;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.trubin23.tasks_mvp_clean.UseCase;
import ru.trubin23.tasks_mvp_clean.UseCaseHandler;
import ru.trubin23.tasks_mvp_clean.addedittask.domain.usecase.GetTask;
import ru.trubin23.tasks_mvp_clean.addedittask.domain.usecase.SaveTask;
import ru.trubin23.tasks_mvp_clean.tasks.domain.model.Task;

public class AddEditTaskPresenter implements AddEditTaskContract.Presenter {

    private final UseCaseHandler mUseCaseHandler;

    @Nullable
    private String mTaskId;

    private final AddEditTaskContract.View mTaskDetailView;

    private final GetTask mGetTask;

    private final SaveTask mSaveTask;

    private boolean mIsDataMissing;

    AddEditTaskPresenter(@NonNull UseCaseHandler useCaseHandler, @Nullable String taskId,
                         @NonNull AddEditTaskContract.View addTaskView, @NonNull GetTask getTask,
                         @NonNull SaveTask saveTask, boolean shouldLoadDataFromRepo) {
        mUseCaseHandler = useCaseHandler;

        mTaskId = taskId;

        mTaskDetailView = addTaskView;
        mGetTask = getTask;
        mSaveTask = saveTask;
        mIsDataMissing = shouldLoadDataFromRepo;

        mTaskDetailView.setPresenter(this);
    }

    @Override
    public void start() {
        if (!isNewTask() && mIsDataMissing) {
            populateTask();
        }
    }

    private void populateTask(){
        mUseCaseHandler.execute(mGetTask, new GetTask.RequestValues(mTaskId),
            new UseCase.UseCaseCallback<GetTask.ResponseValue>() {
                @Override
                public void onSuccess(GetTask.ResponseValue response) {
                    showTask(response.getTask());
                }

                @Override
                public void onError() {
                    showEmptyTaskError();
                }
            });
    }

    private void showTask(Task task) {

    }

    private void showEmptyTaskError() {

    }

    @Override
    public boolean isDataMissing() {
        return mIsDataMissing;
    }

    private boolean isNewTask() {
        return mTaskId == null;
    }
}
