package ru.trubin23.tasks_mvp_clean.statistics;

import android.support.v4.app.Fragment;

public class StatisticsFragment extends Fragment implements StatisticsContract.View {

    private StatisticsContract.Presenter mPresenter;

    @Override
    public void setPresenter(StatisticsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public static StatisticsFragment newInstance() {
        return new StatisticsFragment();
    }
}
