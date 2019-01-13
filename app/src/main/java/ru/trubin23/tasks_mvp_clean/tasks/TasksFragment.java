package ru.trubin23.tasks_mvp_clean.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.trubin23.tasks_mvp_clean.R;
import ru.trubin23.tasks_mvp_clean.taskdetail.TaskDetailActivity;
import ru.trubin23.tasks_mvp_clean.tasks.tasklist.TaskItemListener;

public class TasksFragment extends Fragment implements TasksContract.View {

    private TasksContract.Presenter mPresenter;

    private TaskItemListener mTaskItemListener = new TaskItemListener() {
        @Override
        public void onTaskClick(String taskId) {
            mPresenter.openTaskDetails(taskId);
        }

        @Override
        public void onCompleteTaskClick(String taskId) {
            mPresenter.completeTask(taskId);
        }

        @Override
        public void onActivateTaskClick(String taskId) {
            mPresenter.activateTask(taskId);
        }
    };

    @Override
    public void setPresenter(TasksContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public static TasksFragment newInstance() {
        return new TasksFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tasks_frag, container, false);

        return root;
    }

    @Override
    public void showTaskMarkedComplete() {
        showMessage(getString(R.string.task_marked_complete));
    }

    @Override
    public void showTaskDetail(@NonNull String taskId) {
        Intent intent = new Intent(getContext(), TaskDetailActivity.class);
        intent.putExtra(TaskDetailActivity.SHOW_TASK_ID, taskId);
        startActivity(intent);
    }

    @Override
    public void showTaskMarkedActive() {
        showMessage(getString(R.string.task_marked_active));
    }

    private void showMessage(String message) {
        View view = getView();
        if (view != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void showLoadingTasksError() {
        showMessage(getString(R.string.loading_tasks_error));
    }
}
