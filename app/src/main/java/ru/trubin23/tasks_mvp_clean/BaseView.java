package ru.trubin23.tasks_mvp_clean;

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);
}
