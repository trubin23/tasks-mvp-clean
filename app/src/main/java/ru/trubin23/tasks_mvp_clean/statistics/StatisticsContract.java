package ru.trubin23.tasks_mvp_clean.statistics;

import ru.trubin23.tasks_mvp_clean.BasePresenter;
import ru.trubin23.tasks_mvp_clean.BaseView;

public interface StatisticsContract {

    interface View extends BaseView<Presenter> {

        void showProgressIndicator();

        void showStatistics(int numberOfActiveTasks, int numberOfCompletedTask);

        void showLoadingStatisticsError();

        boolean isActive();

    }

    interface Presenter extends BasePresenter {

    }
}
