package ru.trubin23.tasks_mvp_clean.taskdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import ru.trubin23.tasks_mvp_clean.R;
import ru.trubin23.tasks_mvp_clean.addedittask.AddEditTaskActivity;

public class TaskDetailFragment extends Fragment implements TaskDetailContract.View {

    private TaskDetailContract.Presenter mPresenter;

    TextView mTitle;
    TextView mDescription;
    CheckBox mCompleteStatus;

    @Override
    public void setPresenter(TaskDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public static TaskDetailFragment newInstance() {
        return new TaskDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.taskdetail_frag, container, false);

        setHasOptionsMenu(true);

        mTitle = root.findViewById(R.id.task_detail_title);
        mDescription = root.findViewById(R.id.task_detail_description);
        mCompleteStatus = root.findViewById(R.id.task_detail_complete);

        getActivity().findViewById(R.id.fab_edit_task)
                .setOnClickListener(view -> mPresenter.editTask());

        return root;
    }

    @Override
    public void showMissingTask() {
        mTitle.setText("");
        mDescription.setText(getString(R.string.no_data));
    }

    @Override
    public void showEditTask(String taskId) {
        Intent intent = new Intent(getContext(), AddEditTaskActivity.class);
        intent.putExtra(AddEditTaskActivity.EXTRA_TASK_ID, taskId);
        startActivityForResult(intent, TaskDetailActivity.REQUEST_EDIT_TASK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public void exit() {
        getActivity().finish();
    }

    @Override
    public void showLoadingIndicator() {
        mTitle.setText("");
        mDescription.setText(getString(R.string.loading));
    }
}
