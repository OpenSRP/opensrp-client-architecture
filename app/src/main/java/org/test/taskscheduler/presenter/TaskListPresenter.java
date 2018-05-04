package org.test.taskscheduler.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import org.test.taskscheduler.dao.TaskDao;
import org.test.taskscheduler.model.Task;
import org.test.taskscheduler.repository.TaskRepository;
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
    }

    public TaskListPresenter(TaskDao taskDao, TaskListView taskListView) {
        this.taskDao = taskDao;
        this.taskListView = taskListView;
    }

    public List<Task> getAllTasks() {
        return taskDao.getAll();
    }

    public View.OnClickListener getListener(final Long id) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskListView.isTwoPanels())
                    taskListView.startDetailsFragment(id);
                else
                    taskListView.startDetailsActivity(id, v);
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
