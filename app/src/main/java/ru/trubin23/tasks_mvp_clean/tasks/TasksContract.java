package ru.trubin23.tasks_mvp_clean.tasks;

import android.support.annotation.NonNull;

import ru.trubin23.tasks_mvp_clean.BasePresenter;
import ru.trubin23.tasks_mvp_clean.BaseView;

public interface TasksContract {

    interface View extends BaseView<Presenter> {

        void showTaskDetail(@NonNull String taskId);

        void showTaskMarkedComplete();

        void showTaskMarkedActive();

        void showLoadingTasksError();
    }

    interface Presenter extends BasePresenter {

        void setFiltering(TasksFilterType filterType);

        TasksFilterType getFiltering();

        void openTaskDetails(@NonNull String taskId);

        void completeTask(@NonNull String taskId);

        void activateTask(@NonNull String taskId);
    }
}
