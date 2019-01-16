package ru.trubin23.tasks_mvp_clean.addedittask;

import ru.trubin23.tasks_mvp_clean.BasePresenter;
import ru.trubin23.tasks_mvp_clean.BaseView;

public interface AddEditTaskContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        boolean isDataMissing();
    }
}
