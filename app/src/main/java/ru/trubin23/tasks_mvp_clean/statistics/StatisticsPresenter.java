package ru.trubin23.tasks_mvp_clean.statistics;

import android.support.annotation.NonNull;

import ru.trubin23.tasks_mvp_clean.UseCase;
import ru.trubin23.tasks_mvp_clean.UseCaseHandler;
import ru.trubin23.tasks_mvp_clean.statistics.domain.model.Statistics;
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
        mStatisticsView.showProgressIndicator();

        mUseCaseHandler.execute(mGetStatistics, new GetStatistics.RequestValues(),
                new UseCase.UseCaseCallback<GetStatistics.ResponseValue>() {
                    @Override
                    public void onSuccess(GetStatistics.ResponseValue response) {
                        Statistics statistics = response.getStatistics();

                        if (mStatisticsView.isActive()){
                            mStatisticsView.showStatistics(
                                    statistics.getActiveTasks(), statistics.getCompletedTasks());
                        }
                    }

                    @Override
                    public void onError() {
                        if (mStatisticsView.isActive()){
                            mStatisticsView.showLoadingStatisticsError();
                        }
                    }
                });
    }
}
