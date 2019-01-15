package ru.trubin23.tasks_mvp_clean.taskdetail;

import ru.trubin23.tasks_mvp_clean.BasePresenter;
import ru.trubin23.tasks_mvp_clean.BaseView;

public interface TaskDetailContract {

    interface View extends BaseView<Presenter> {

        void showMissingTask();

        void showEditTask(String taskId);

        void exit();

        void showLoadingIndicator();
    }

    interface Presenter extends BasePresenter {

        void editTask();

        void result(int requestCode, int resultCode);
    }
}
