package org.test.taskscheduler.presenter;

import android.content.Context;

import org.test.taskscheduler.R;
import org.test.taskscheduler.dao.TaskDao;
import org.test.taskscheduler.model.Task;
import org.test.taskscheduler.repository.TaskRepository;
import org.test.taskscheduler.view.contract.TaskDetailsView;

/**
 * Created by samuelgithengi on 4/17/18.
 */

public class TaskDetailPresenter {

    private TaskDao taskDao;

    private TaskDetailsView view;

    private Context context;

    public TaskDetailPresenter(Context context, TaskDetailsView view) {
        this(context, view, TaskRepository.getInstance(context).getTaskDao());
    }

    public TaskDetailPresenter(Context context, TaskDetailsView view, TaskDao taskDao) {
        this.view = view;
        this.context = context;
        this.taskDao = taskDao;

    }

    public void onSaveTaskClicked(Task task) {
        task = view.populateTaskDetails(task);
        saveTask(task);
    }


    private void saveTask(Task task) {
        String message;
        if (task.getId() == 0) {
            taskDao.insert(task);
            message = context.getResources().getString(R.string.task_saved);
        } else {
            taskDao.update(task);
            message = context.getResources().getString(R.string.task_updated);
        }
        view.displayNotification(message);
        view.returnToListActivity(true);

    }

}
