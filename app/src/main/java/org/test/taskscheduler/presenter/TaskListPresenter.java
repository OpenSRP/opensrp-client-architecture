package org.test.taskscheduler.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.test.taskscheduler.R;
import org.test.taskscheduler.adapter.TaskRecyclerViewAdapter;
import org.test.taskscheduler.dao.TaskDao;
import org.test.taskscheduler.model.Task;
import org.test.taskscheduler.repository.TaskRepository;
import org.test.taskscheduler.view.TaskDetailActivity;
import org.test.taskscheduler.view.TaskDetailFragment;
import org.test.taskscheduler.view.contract.TaskListView;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static org.test.taskscheduler.utils.Constants.TASKS_MODIFIED;
import static org.test.taskscheduler.utils.Constants.TASKS_MODIFIED_RESULT_CODE;

/**
 * Created by samuelgithengi on 4/11/18.
 */

public class TaskListPresenter {

    private TaskDao taskDao;
    private TaskListView taskListView;


    public TaskListPresenter(Context context, TaskListView taskListView) {
        taskDao = TaskRepository.getInstance(context).getTaskDao();
        this.taskListView = taskListView;
        taskListView.getRecyclerView().setAdapter(new TaskRecyclerViewAdapter(this));
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
        taskListView.getSupportFragmentManager().beginTransaction()
                .replace(R.id.task_detail_container, fragment)
                .commit();
    }

    public void startDetailsActivity(Long id, View view) {
        Activity context = (Activity) view.getContext();
        Intent intent = new Intent(context, TaskDetailActivity.class);
        if (id != null)
            intent.putExtra(TaskDetailFragment.ARG_ITEM_ID, id);

        context.startActivityForResult(intent, TASKS_MODIFIED_RESULT_CODE);
    }

    public View.OnClickListener getListener(final Long id) {


        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskListView.isTwoPanels())
                    startDetailsFragment(id);
                else
                    startDetailsActivity(id, v);
            }
        };
    }

    public void processActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == TASKS_MODIFIED_RESULT_CODE) {
            if (data.getBooleanExtra(TASKS_MODIFIED, false))
                taskListView.refreshTasks();
        }
    }


}
