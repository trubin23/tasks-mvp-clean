package ru.trubin23.tasks_mvp_clean.data.remote;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.trubin23.tasks_mvp_clean.tasks.domain.model.Task;

class TaskMapper {

    @NonNull
    static Task networkTaskToTask(@NonNull NetworkTask networkTask) {
        return new Task(networkTask.getTitle(), networkTask.getDescription(),
                networkTask.getId(), StatusOfTask.integerToBoolean(networkTask.getCompleted()));
    }

    @NonNull
    static NetworkTask taskToNetworkTask(@NonNull Task task) {
        return new NetworkTask(task.getId(), task.getTitle(),
                task.getDescription(), task.isCompleted());
    }

    @NonNull
    static List<Task> networkTaskListToTaskList(@NonNull List<NetworkTask> networkTaskList) {
        List<Task> taskList = new ArrayList<>();

        for (NetworkTask networkTask : networkTaskList) {
            Task task = networkTaskToTask(networkTask);
            taskList.add(task);
        }

        return taskList;
    }
}
