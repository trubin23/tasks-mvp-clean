package ru.trubin23.tasks_mvp_clean.taskdetail;

import android.support.v4.app.Fragment;

public class TaskDetailFragment extends Fragment implements TaskDetailContract.View {

    private TaskDetailContract.Presenter mPresenter;

    @Override
    public void setPresenter(TaskDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
