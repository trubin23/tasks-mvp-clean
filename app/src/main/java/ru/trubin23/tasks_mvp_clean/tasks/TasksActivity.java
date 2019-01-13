package ru.trubin23.tasks_mvp_clean.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import ru.trubin23.tasks_mvp_clean.R;
import ru.trubin23.tasks_mvp_clean.statistics.StatisticsActivity;
import ru.trubin23.tasks_mvp_clean.util.ActivityUtils;

public class TasksActivity extends AppCompatActivity {

    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    private DrawerLayout mDrawerLayout;

    private TasksPresenter mTasksPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_act);

        setupToolbar();

        setupNavigationDrawer();

        TasksFragment tasksFragment =
            (TasksFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (tasksFragment == null) {
            tasksFragment = TasksFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(), tasksFragment, R.id.contentFrame);
        }

        mTasksPresenter = new TasksPresenter();

        if (savedInstanceState != null) {
            TasksFilterType tasksFilterType =
                (TasksFilterType) savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
            mTasksPresenter.setFiltering(tasksFilterType);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CURRENT_FILTERING_KEY, mTasksPresenter.getFiltering());

        super.onSaveInstanceState(outState);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupNavigationDrawer() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.statistics_nav_menu_item) {
                Intent intent = new Intent(TasksActivity.this, StatisticsActivity.class);
                startActivity(intent);
            }

            menuItem.setChecked(true);
            mDrawerLayout.closeDrawers();
            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
