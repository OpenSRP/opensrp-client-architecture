package org.test.taskscheduler.interactor;

import android.content.Context;

import org.test.taskscheduler.dao.TaskDao;
import org.test.taskscheduler.model.Task;
import org.test.taskscheduler.repository.TaskRepository;

import java.util.List;

/**
 * Created by samuelgithengi on 5/4/18.
 */

public class TaskInteractor {

    public enum type {SAVED, UPDATED}

    private TaskDao taskDao;

    public TaskInteractor(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public TaskInteractor(Context context) {
        taskDao = TaskRepository.getInstance(context).getTaskDao();
    }

    public List<Task> getAllTasks() {
        return taskDao.getAll();
    }

    public type saveOrUpdateTask(Task task) {
        if (task.getId() == 0) {
            taskDao.insert(task);
            return type.SAVED;
        } else {
            taskDao.update(task);
            return type.UPDATED;
        }

    }


}
