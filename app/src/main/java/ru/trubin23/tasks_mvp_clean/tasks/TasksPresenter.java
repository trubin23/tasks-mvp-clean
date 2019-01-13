package ru.trubin23.tasks_mvp_clean.tasks;

import android.support.annotation.NonNull;

public class TasksPresenter implements TasksContract.Presenter {

    @NonNull
    private TasksFilterType mFilterType = TasksFilterType.ALL_TASKS;

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
