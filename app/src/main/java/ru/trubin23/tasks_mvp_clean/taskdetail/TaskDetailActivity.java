package ru.trubin23.tasks_mvp_clean.taskdetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.trubin23.tasks_mvp_clean.R;

public class TaskDetailActivity extends AppCompatActivity {

    public static final String SHOW_TASK_ID = "TASK_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskdetail_act);
    }
}
