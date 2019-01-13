package ru.trubin23.tasks_mvp_clean.tasks.tasklist;

import android.support.annotation.NonNull;

public interface TaskItemListener {

    void onTaskClick(@NonNull String taskId);

    void onCompleteTaskClick(@NonNull String taskId);

    void onActivateTaskClick(@NonNull String taskId);
}
