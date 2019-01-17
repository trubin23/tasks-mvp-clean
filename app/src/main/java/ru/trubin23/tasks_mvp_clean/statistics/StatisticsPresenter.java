package ru.trubin23.tasks_mvp_clean.statistics;

import android.support.annotation.NonNull;

import ru.trubin23.tasks_mvp_clean.UseCaseHandler;
import ru.trubin23.tasks_mvp_clean.statistics.domain.usecase.GetStatistics;

public class StatisticsPresenter implements StatisticsContract.Presenter {

    private final UseCaseHandler mUseCaseHandler;

    private final StatisticsContract.View mStatisticsView;

    private final GetStatistics mGetStatistics;

    StatisticsPresenter(@NonNull UseCaseHandler useCaseHandler,
                        @NonNull StatisticsContract.View statisticsView,
                        @NonNull GetStatistics getStatistics) {
        mUseCaseHandler = useCaseHandler;

        mStatisticsView = statisticsView;

        mGetStatistics = getStatistics;

        mStatisticsView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
