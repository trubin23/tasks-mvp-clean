package ru.trubin23.tasks_mvp_clean.addedittask;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.trubin23.tasks_mvp_clean.UseCaseHandler;
import ru.trubin23.tasks_mvp_clean.addedittask.domain.usecase.GetTask;
import ru.trubin23.tasks_mvp_clean.addedittask.domain.usecase.SaveTask;

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

    }

    @Override
    public boolean isDataMissing() {
        return mIsDataMissing;
    }
}
