package ru.trubin23.tasks_mvp_clean.tasks;

import ru.trubin23.tasks_mvp_clean.BasePresenter;
import ru.trubin23.tasks_mvp_clean.BaseView;

public interface TasksContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
