package ru.trubin23.tasks_mvp_clean.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.trubin23.tasks_mvp_clean.R;

public class StatisticsFragment extends Fragment implements StatisticsContract.View {

    private StatisticsContract.Presenter mPresenter;

    TextView mStatistics;

    @Override
    public void setPresenter(StatisticsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public static StatisticsFragment newInstance() {
        return new StatisticsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.statistics_frag, container, false);

        mStatistics = root.findViewById(R.id.statistics);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showProgressIndicator() {
        mStatistics.setText(getString(R.string.loading));
    }

    @Override
    public void showStatistics(int numberOfActiveTasks, int numberOfCompletedTask) {
        if (numberOfActiveTasks == 0 && numberOfCompletedTask == 0) {
            mStatistics.setText(getResources().getString(R.string.statistics_no_tasks));
        } else {
            String displayString = getString(R.string.statistics_active_tasks)
                    + numberOfActiveTasks + "\n"
                    + getString(R.string.statistics_completed_tasks)
                    + numberOfCompletedTask;
            mStatistics.setText(displayString);
        }
    }

    @Override
    public void showLoadingStatisticsError() {
        mStatistics.setText(getString(R.string.statistics_error));
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
