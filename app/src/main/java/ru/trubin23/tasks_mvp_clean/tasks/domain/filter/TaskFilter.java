package ru.trubin23.tasks_mvp_clean.tasks.domain.filter;

import java.util.List;

import ru.trubin23.tasks_mvp_clean.tasks.domain.model.Task;

public interface TaskFilter {

    List<Task> filter(List<Task> tasks);
}
