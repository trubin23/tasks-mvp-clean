package ru.trubin23.tasks_mvp_clean.addedittask;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ru.trubin23.tasks_mvp_clean.R;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.addtask_frag, container, false);

        mTitle = root.findViewById(R.id.add_task_title);
        mDescription = root.findViewById(R.id.add_task_description);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab_edit_task_done);
        fab.setOnClickListener(v -> mPresenter.saveTask(
                mTitle.getText().toString(), mDescription.getText().toString()));

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void setDescription(String description) {
        mDescription.setText(description);
    }

    @Override
    public void showEmptyTaskError() {
        Snackbar.make(mTitle, R.string.empty_task_message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showTasksList(){
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }
}
