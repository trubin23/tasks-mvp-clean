package ru.trubin23.tasks_mvp_clean.addedittask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.trubin23.tasks_mvp_clean.R;

public class AddEditTaskActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_ID = "EXTRA_TASK_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtask_act);
    }
}
