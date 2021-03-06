package ru.trubin23.tasks_mvp_clean.tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.trubin23.tasks_mvp_clean.R;
import ru.trubin23.tasks_mvp_clean.addedittask.AddEditTaskActivity;
import ru.trubin23.tasks_mvp_clean.taskdetail.TaskDetailActivity;
import ru.trubin23.tasks_mvp_clean.tasks.domain.model.Task;
import ru.trubin23.tasks_mvp_clean.tasks.tasklist.TaskItemListener;
import ru.trubin23.tasks_mvp_clean.tasks.tasklist.TasksAdapter;

public class TasksFragment extends Fragment implements TasksContract.View {

    private TasksContract.Presenter mPresenter;

    private TasksAdapter mTasksAdapter;

    private TextView mFilteringLabel;

    private Group mTasksView;
    private Group mNoTasksView;
    private ImageView mNoTasksIcon;
    private TextView mNoTasksText;

    private ScrollChildSwipeRefreshLayout mSwipeRefreshLayout;

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

        setHasOptionsMenu(true);

        mTasksView = root.findViewById(R.id.list_tasks);
        mNoTasksView = root.findViewById(R.id.no_tasks);
        mNoTasksIcon = root.findViewById(R.id.no_tasks_icon);
        mNoTasksText = root.findViewById(R.id.no_tasks_text);

        mFilteringLabel = root.findViewById(R.id.filtering_label);

        mTasksAdapter = new TasksAdapter(mTaskItemListener);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mTasksAdapter);

        Activity activity = getActivity();
        if (activity != null) {
            FloatingActionButton floatingActionButton = activity.findViewById(R.id.fab_add_task);
            if (floatingActionButton != null) {
                floatingActionButton.setOnClickListener(__ -> mPresenter.addNewTask());
            }
        }

        mSwipeRefreshLayout = root.findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setScrollUpChild(recyclerView);
        mSwipeRefreshLayout.setOnRefreshListener(() -> mPresenter.loadTasks(false));

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public boolean isNotActive() {
        return !isAdded();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tasks_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_filter:
                showFilteringPopUpMenu();
                return true;
            case R.id.menu_refresh:
                mPresenter.loadTasks(true);
                return true;
            case R.id.menu_clear:
                mPresenter.clearCompletedTasks();
                return true;
        }
        return false;
    }

    private void showFilteringPopUpMenu() {
        PopupMenu popupMenu = new PopupMenu(getContext(),
                getActivity().findViewById(R.id.menu_filter));
        popupMenu.getMenuInflater().inflate(R.menu.filter_tasks, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_active:
                    mPresenter.setFiltering(TasksFilterType.ACTIVE_TASKS);
                    break;
                case R.id.menu_completed:
                    mPresenter.setFiltering(TasksFilterType.COMPLETED_TASKS);
                    break;
                default:
                    mPresenter.setFiltering(TasksFilterType.ALL_TASKS);
                    break;
            }
            mPresenter.loadTasks(false);
            return true;
        });

        popupMenu.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public void showTaskMarkedComplete() {
        showMessage(getString(R.string.task_marked_complete));
    }

    @Override
    public void showTaskMarkedActive() {
        showMessage(getString(R.string.task_marked_active));
    }

    @Override
    public void showTaskDetail(@NonNull String taskId) {
        Intent intent = new Intent(getContext(), TaskDetailActivity.class);
        intent.putExtra(TaskDetailActivity.SHOW_TASK_ID, taskId);
        startActivity(intent);
    }

    @Override
    public void showAddTask() {
        Intent intent = new Intent(getContext(), AddEditTaskActivity.class);
        startActivityForResult(intent, TasksActivity.REQUEST_ADD_TASK);
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

    @Override
    public void showCompletedTasksCleared() {
        showMessage(getString(R.string.completed_tasks_cleared));
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(active));
    }

    @Override
    public void showTasks(@NonNull List<Task> tasks) {
        mTasksAdapter.setTasks(tasks);

        mTasksView.setVisibility(View.VISIBLE);
        mNoTasksView.setVisibility(View.GONE);
    }

    @Override
    public void showActiveFilterLabel() {
        mFilteringLabel.setText(R.string.label_active);
    }

    @Override
    public void showCompletedFilterLabel() {
        mFilteringLabel.setText(R.string.label_completed);
    }

    @Override
    public void showAllFilterLabel() {
        mFilteringLabel.setText(R.string.label_all);
    }

    @Override
    public void showNoActiveTasks() {
        showNoTasksViews(R.string.no_tasks_active, R.drawable.ic_check_circle);
    }

    @Override
    public void showNoCompletedTasks() {
        showNoTasksViews(R.string.no_tasks_completed, R.drawable.ic_check_box);
    }

    @Override
    public void showNoTasks() {
        showNoTasksViews(R.string.no_tasks_all, R.drawable.ic_verified);
    }

    private void showNoTasksViews(int textRes, int iconRes) {
        mTasksView.setVisibility(View.GONE);
        mNoTasksView.setVisibility(View.VISIBLE);

        mNoTasksIcon.setImageDrawable(getResources().getDrawable(iconRes));
        mNoTasksText.setText(textRes);
    }

    @Override
    public void showSuccessfullySavedMessage() {
        showMessage(getString(R.string.successfully_saved_task_message));
        mPresenter.loadTasks(true);
    }
}
