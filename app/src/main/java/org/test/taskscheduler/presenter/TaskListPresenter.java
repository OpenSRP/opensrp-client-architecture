package org.test.taskscheduler.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.test.taskscheduler.R;
import org.test.taskscheduler.adapter.TaskRecyclerViewAdapter;
import org.test.taskscheduler.dao.TaskDao;
import org.test.taskscheduler.model.Task;
import org.test.taskscheduler.presenter.contract.TaskListPresenterContract;
import org.test.taskscheduler.repository.TaskRepository;
import org.test.taskscheduler.view.TaskDetailActivity;
import org.test.taskscheduler.view.TaskDetailFragment;

import java.util.List;

/**
 * Created by samuelgithengi on 4/11/18.
 */

public class TaskListPresenter implements TaskListPresenterContract {

    private TaskDao taskDao;
    private boolean mTwoPane;

    private FragmentManager fragmentManager;


    public TaskListPresenter(Context context, FragmentManager fragmentManager, RecyclerView recyclerView, boolean mTwoPane) {
        taskDao = TaskRepository.getInstance(context).getTaskDao();
        this.fragmentManager = fragmentManager;
        this.mTwoPane = mTwoPane;
        recyclerView.setAdapter(new TaskRecyclerViewAdapter(getAllTasks(), this));
    }

    public List<Task> getAllTasks() {
        return taskDao.getAll();
    }

    public void startDetailsFragment(Long id) {
        Bundle arguments = new Bundle();
        if (id != null)
            arguments.putLong(TaskDetailFragment.ARG_ITEM_ID, id);
        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(arguments);
        fragmentManager.beginTransaction()
                .replace(R.id.task_detail_container, fragment)
                .commit();
    }

    public void startDetailsActivity(Long id, View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, TaskDetailActivity.class);
        if (id != null)
            intent.putExtra(TaskDetailFragment.ARG_ITEM_ID, id);

        context.startActivity(intent);
    }

    public View.OnClickListener getListener(final Long id) {


        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPane)
                    startDetailsFragment(id);
                else
                    startDetailsActivity(id, v);
            }
        };
    }


}
