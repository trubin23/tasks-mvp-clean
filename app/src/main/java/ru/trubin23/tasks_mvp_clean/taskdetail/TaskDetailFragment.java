package ru.trubin23.tasks_mvp_clean.taskdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import ru.trubin23.tasks_mvp_clean.R;

public class TaskDetailFragment extends Fragment implements TaskDetailContract.View {

    private TaskDetailContract.Presenter mPresenter;

    TextView mTitle;
    TextView mDescription;
    CheckBox mCompleteStatus;

    @Override
    public void setPresenter(TaskDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.taskdetail_frag, container, false);

        setHasOptionsMenu(true);

        mTitle = root.findViewById(R.id.task_detail_title);
        mDescription = root.findViewById(R.id.task_detail_description);
        mCompleteStatus = root.findViewById(R.id.task_detail_complete);

        getActivity().findViewById(R.id.fab_edit_task)
                .setOnClickListener(view -> mPresenter.editTask());

        return root;
    }
}
