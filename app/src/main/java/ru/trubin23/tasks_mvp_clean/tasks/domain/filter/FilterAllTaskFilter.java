package ru.trubin23.tasks_mvp_clean.tasks.domain.filter;

import java.util.ArrayList;
import java.util.List;

import ru.trubin23.tasks_mvp_clean.tasks.domain.model.Task;

public class FilterAllTaskFilter implements TaskFilter {

    @Override
    public List<Task> filter(List<Task> tasks) {
        return new ArrayList<>(tasks);
    }
}
