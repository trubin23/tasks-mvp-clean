package ru.trubin23.tasks_mvp_clean.taskdetail;

import android.support.annotation.NonNull;

import ru.trubin23.tasks_mvp_clean.BasePresenter;
import ru.trubin23.tasks_mvp_clean.BaseView;

public interface TaskDetailContract {

    interface View extends BaseView<Presenter> {

        void showMissingTask();

        void showEditTask(@NonNull String taskId);

        void exit();

        void showLoadingIndicator();

        boolean isActive();

        void hideTitle();

        void showTitle(@NonNull String title);

        void hideDescription();

        void showDescription(@NonNull String description);

        void showCompletionStatus(boolean complete);

        void showTaskMarkedComplete();

        void showTaskMarkedActivate();
    }

    interface Presenter extends BasePresenter {

        void editTask();

        void result(int requestCode, int resultCode);

        void completeTask();

        void activateTask();
    }
}
