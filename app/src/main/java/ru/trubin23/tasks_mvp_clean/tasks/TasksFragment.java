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

import ru.trubin23.tasks_mvp_clean.R;
import ru.trubin23.tasks_mvp_clean.addedittask.AddEditTaskActivity;
import ru.trubin23.tasks_mvp_clean.taskdetail.TaskDetailActivity;
import ru.trubin23.tasks_mvp_clean.tasks.tasklist.TaskItemListener;
import ru.trubin23.tasks_mvp_clean.tasks.tasklist.TasksAdapter;

public class TasksFragment extends Fragment implements TasksContract.View {

    public static final int REQUEST_ADD_TASK = 1;

    private TasksContract.Presenter mPresenter;

    private TasksAdapter mTasksAdapter;

    private TextView mFilteringLabel;

    private Group mTasksView;
    private Group mNoTasksView;
    private ImageView mNoTasksIcon;
    private TextView mNoTasksText;

    private SwipeRefreshLayout swipeRefreshLayout;

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

        swipeRefreshLayout = root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> mPresenter.loadTasks(false));

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
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
                mPresenter.clearCompletedTask();
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
    public void showAddTask() {
        Intent intent = new Intent(getContext(), AddEditTaskActivity.class);
        startActivityForResult(intent, REQUEST_ADD_TASK);
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

    @Override
    public void showCompletedTasksCleared(){
        showMessage(getString(R.string.completed_tasks_cleared));
    }
}
