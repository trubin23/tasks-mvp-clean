package ru.trubin23.tasks_mvp_clean.taskdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.task_detail_frag_menu, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        boolean deletePressed = item.getItemId() == R.id.menu_delete;
        if (deletePressed) {
            mPresenter.deleteTask();
        }
        return deletePressed;
    }

    @Override
    public void showMissingTask() {
        mTitle.setText("");
        mDescription.setText(getString(R.string.no_data));
    }

    @Override
    public void showEditTask(@NonNull String taskId) {
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

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void hideTitle() {
        mDescription.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showTitle(@NonNull String title) {
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText(title);
    }

    @Override
    public void hideDescription() {
        mDescription.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showDescription(@NonNull String description) {
        mDescription.setVisibility(View.VISIBLE);
        mDescription.setText(description);
    }

    @Override
    public void showCompletionStatus(boolean complete) {
        mCompleteStatus.setChecked(complete);
        mCompleteStatus.setOnCheckedChangeListener(
                (view, isChecked) -> {
                    if (isChecked) {
                        mPresenter.completeTask();
                    } else {
                        mPresenter.activateTask();
                    }
                }
        );
    }

    @Override
    public void showTaskMarkedComplete() {
        Snackbar.make(getView(), getString(R.string.task_marked_complete), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showTaskMarkedActivate() {
        Snackbar.make(getView(), getString(R.string.task_marked_active), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showTaskDeleted(){
        getActivity().finish();
    }
}
