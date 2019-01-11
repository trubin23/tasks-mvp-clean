package ru.trubin23.tasks_mvp_clean.data.local;

import android.support.annotation.NonNull;

import java.util.List;

import ru.trubin23.tasks_mvp_clean.data.TasksDataSource;
import ru.trubin23.tasks_mvp_clean.tasks.domain.model.Task;

public interface TasksLocalDataSource extends TasksDataSource {

    void setTasks(@NonNull List<Task> tasks);
}
