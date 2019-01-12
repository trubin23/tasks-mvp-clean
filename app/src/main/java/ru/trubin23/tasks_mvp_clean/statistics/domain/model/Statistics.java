package ru.trubin23.tasks_mvp_clean.statistics.domain.model;

public class Statistics {

    private final int mCompletedTasks;
    private final int mActiveTasks;

    public Statistics(int completedTasks, int activeTasks) {
        mCompletedTasks = completedTasks;
        mActiveTasks = activeTasks;
    }

    public int getCompletedTasks() {
        return mCompletedTasks;
    }

    public int getActiveTasks() {
        return mActiveTasks;
    }
}
