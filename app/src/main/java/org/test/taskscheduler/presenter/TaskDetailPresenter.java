package org.test.taskscheduler.presenter;

import android.content.Context;

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

    public TaskDetailPresenter(Context context, TaskDetailsView view) {
        taskDao = TaskRepository.getInstance(context).getTaskDao();
        this.view = view;
    }

    public void onSaveTaskClicked(Task task) {
        task = view.populateTaskDetails(task);
        saveTask(task);
    }


    public void saveTask(Task task) {
        String message;
        if (task.getId() == 0) {
            taskDao.insert(task);
            message = "Task has been successfully saved";
        } else {
            taskDao.update(task);
            message = "Task has been successfully updated";
        }
        view.displayNotification(message);
        view.returnToListActivity(true);

    }

}
