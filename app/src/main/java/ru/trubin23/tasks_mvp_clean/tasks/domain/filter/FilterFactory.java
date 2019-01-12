package ru.trubin23.tasks_mvp_clean.tasks.domain.filter;

import java.util.HashMap;
import java.util.Map;

import ru.trubin23.tasks_mvp_clean.tasks.TasksFilterType;

public class FilterFactory {

    private static final Map<TasksFilterType, TaskFilter> mFilters = new HashMap<>();

    public FilterFactory() {
        mFilters.put(TasksFilterType.ALL_TASKS, new FilterAllTaskFilter());
        mFilters.put(TasksFilterType.ACTIVE_TASKS, new ActiveTaskFilter());
        mFilters.put(TasksFilterType.COMPLETED_TASKS, new CompleteTaskFilter());
    }

    public TaskFilter create(TasksFilterType filterType) {
        return mFilters.get(filterType);
    }
}
