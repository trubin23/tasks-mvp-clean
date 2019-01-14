package ru.trubin23.tasks_mvp_clean.taskdetail;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ru.trubin23.tasks_mvp_clean.R;
import ru.trubin23.tasks_mvp_clean.util.ActivityUtils;

public class TaskDetailActivity extends AppCompatActivity {

    public static final String SHOW_TASK_ID = "TASK_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskdetail_act);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        String taskId = getIntent().getStringExtra(SHOW_TASK_ID);

        //TaskDetailFragment taskDetailFragment = (TaskDetailFragment)
        //        getSupportFragmentManager().findFragmentById(R.id.content_frame);
        //if (taskDetailFragment == null) {
        //    taskDetailFragment = TaskDetailFragment.newInstance();
        //    ActivityUtils.addFragmentToActivity(
        //            getSupportFragmentManager(), taskDetailFragment, R.id.content_frame);
        //}
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
