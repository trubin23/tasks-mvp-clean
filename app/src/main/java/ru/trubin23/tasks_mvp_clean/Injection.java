package ru.trubin23.tasks_mvp_clean;

import android.content.Context;
import android.support.annotation.NonNull;

import ru.trubin23.tasks_mvp_clean.addedittask.domain.usecase.DeleteTask;
import ru.trubin23.tasks_mvp_clean.addedittask.domain.usecase.GetTask;
import ru.trubin23.tasks_mvp_clean.addedittask.domain.usecase.SaveTask;
import ru.trubin23.tasks_mvp_clean.data.TasksRepository;
import ru.trubin23.tasks_mvp_clean.data.cache.TasksCacheRepository;
import ru.trubin23.tasks_mvp_clean.data.local.TasksDatabase;
import ru.trubin23.tasks_mvp_clean.data.local.TasksLocalRepository;
import ru.trubin23.tasks_mvp_clean.data.remote.TasksRemoteRepository;
import ru.trubin23.tasks_mvp_clean.statistics.domain.usecase.GetStatistics;
import ru.trubin23.tasks_mvp_clean.tasks.domain.filter.FilterFactory;
import ru.trubin23.tasks_mvp_clean.tasks.domain.usecase.ActivateTask;
import ru.trubin23.tasks_mvp_clean.tasks.domain.usecase.ClearCompleteTasks;
import ru.trubin23.tasks_mvp_clean.tasks.domain.usecase.CompleteTask;
import ru.trubin23.tasks_mvp_clean.tasks.domain.usecase.GetTasks;

public class Injection {

    public static TasksRepository provideTasksRepository(@NonNull Context context) {
        TasksDatabase database = TasksDatabase.getInstance(context);
        return TasksRepository.getInstance(TasksRemoteRepository.getInstance(),
            TasksLocalRepository.getInstance(database.tasksDao()),
            TasksCacheRepository.getInstance());
    }

    public static UseCaseHandler provideUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }

    public static GetTasks provideGetTasks(@NonNull Context context) {
        return new GetTasks(provideTasksRepository(context), new FilterFactory());
    }

    public static ActivateTask provideActivateTask(@NonNull Context context) {
        return new ActivateTask(provideTasksRepository(context));
    }

    public static CompleteTask provideCompleteTask(@NonNull Context context) {
        return new CompleteTask(provideTasksRepository(context));
    }

    public static ClearCompleteTasks provideClearCompleteTasks(@NonNull Context context) {
        return new ClearCompleteTasks(provideTasksRepository(context));
    }

    public static GetTask provideGetTask(@NonNull Context context) {
        return new GetTask(provideTasksRepository(context));
    }

    public static SaveTask provideSaveTask(@NonNull Context context) {
        return new SaveTask(provideTasksRepository(context));
    }

    public static DeleteTask provideDeleteTask(@NonNull Context context) {
        return new DeleteTask(provideTasksRepository(context));
    }

    public static GetStatistics provideGetStatistics(@NonNull Context context) {
        return new GetStatistics(provideTasksRepository(context));
    }
}
