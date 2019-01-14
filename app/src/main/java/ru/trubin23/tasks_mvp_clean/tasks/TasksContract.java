package ru.trubin23.tasks_mvp_clean.tasks;

import android.support.annotation.NonNull;

import java.util.List;

import ru.trubin23.tasks_mvp_clean.BasePresenter;
import ru.trubin23.tasks_mvp_clean.BaseView;
import ru.trubin23.tasks_mvp_clean.tasks.domain.model.Task;

public interface TasksContract {

    interface View extends BaseView<Presenter> {

        void showTaskDetail(@NonNull String taskId);

        void showAddTask();

        void showTaskMarkedComplete();

        void showTaskMarkedActive();

        void showLoadingTasksError();

        void showCompletedTasksCleared();

        void setLoadingIndicator(boolean active);

        boolean isNotActive();

        void showTasks(@NonNull List<Task> tasks);

        void showActiveFilterLabel();

        void showCompletedFilterLabel();

        void showAllFilterLabel();

        void showNoActiveTasks();

        void showNoCompletedTasks();

        void showNoTasks();

        void showSuccessfullySavedMessage();
    }

    interface Presenter extends BasePresenter {

        void setFiltering(TasksFilterType filterType);

        TasksFilterType getFiltering();

        void openTaskDetails(@NonNull String taskId);

        void completeTask(@NonNull String taskId);

        void activateTask(@NonNull String taskId);

        void loadTasks(boolean forceUpdate);

        void addNewTask();

        void clearCompletedTasks();

        void result(int requestCode, int resultCode);
    }
}
