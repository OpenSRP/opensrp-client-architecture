package org.test.taskscheduler.interactor;

import org.test.taskscheduler.R;
import org.test.taskscheduler.model.Task;

/**
 * Created by samuelgithengi on 5/4/18.
 */

public class TaskInteractor {

    public void saveTask(Task task) {
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
