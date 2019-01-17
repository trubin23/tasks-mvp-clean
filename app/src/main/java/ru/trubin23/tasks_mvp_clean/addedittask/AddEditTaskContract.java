package ru.trubin23.tasks_mvp_clean.addedittask;

import ru.trubin23.tasks_mvp_clean.BasePresenter;
import ru.trubin23.tasks_mvp_clean.BaseView;

public interface AddEditTaskContract {

    interface View extends BaseView<Presenter> {

        boolean isActive();

        void setTitle(String title);

        void setDescription(String description);

        void showEmptyTaskError();

        void showTasksList();
    }

    interface Presenter extends BasePresenter {

        boolean isDataMissing();

        void saveTask(String title, String description);
    }
}
