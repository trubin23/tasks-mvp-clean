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

    private void populateTask() {
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
        if (mTaskDetailView.isActive()) {
            mTaskDetailView.setTitle(task.getTitle());
            mTaskDetailView.setDescription(task.getDescription());
        }
        mIsDataMissing = false;
    }

    private void showEmptyTaskError() {
        if (mTaskDetailView.isActive()) {
            mTaskDetailView.showEmptyTaskError();
        }
    }

    @Override
    public void saveTask(String title, String description) {
        if (isNewTask()) {
            createTask(title, description);
        } else {
            updateTask(title, description);
        }
    }

    @Override
    public boolean isDataMissing() {
        return mIsDataMissing;
    }

    private boolean isNewTask() {
        return mTaskId == null;
    }

    private void createTask(String title, String description) {
        Task task = new Task(title, description);
        if (task.isEmpty()) {
            mTaskDetailView.showEmptyTaskError();
        } else {
            mUseCaseHandler.execute(mSaveTask, new SaveTask.RequestValues(task),
                    new UseCase.UseCaseCallback<SaveTask.ResponseValue>() {
                        @Override
                        public void onSuccess(SaveTask.ResponseValue response) {
                            mTaskDetailView.showTasksList();
                        }

                        @Override
                        public void onError() {
                            //ignore
                        }
                    });
        }
    }

    private void updateTask(String title, String description) {
        if (isNewTask()) {
            return;
        }
        Task task = new Task(title, description, mTaskId);
        mUseCaseHandler.execute(mSaveTask, new SaveTask.RequestValues(task),
                new UseCase.UseCaseCallback<SaveTask.ResponseValue>() {
                    @Override
                    public void onSuccess(SaveTask.ResponseValue response) {
                        mTaskDetailView.showTasksList();
                    }

                    @Override
                    public void onError() {
                        //ignore
                    }
                });
    }
}
