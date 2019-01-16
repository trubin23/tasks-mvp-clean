package ru.trubin23.tasks_mvp_clean.addedittask;

import android.support.v4.app.Fragment;
import android.widget.EditText;

public class AddEditTaskFragment extends Fragment implements AddEditTaskContract.View {

    private AddEditTaskContract.Presenter mPresenter;

    EditText mTitle;
    EditText mDescription;

    public static AddEditTaskFragment newInstance() {
        return new AddEditTaskFragment();
    }

    @Override
    public void setPresenter(AddEditTaskContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
