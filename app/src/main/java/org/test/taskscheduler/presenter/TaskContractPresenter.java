package org.test.taskscheduler.presenter;

import android.content.Context;

import org.test.taskscheduler.dao.TaskDao;
import org.test.taskscheduler.model.Task;
import org.test.taskscheduler.presenter.contract.TaskListPresenterContract;
import org.test.taskscheduler.repository.TaskRepository;

import java.util.List;

/**
 * Created by samuelgithengi on 4/11/18.
 */

public class TaskContractPresenter implements TaskListPresenterContract {

    private TaskDao taskDao;


    public TaskContractPresenter(Context context) {
        taskDao = TaskRepository.getInstance(context).getTaskDao();
    }

    public List<Task> getAllTasks() {
        return taskDao.getAll();
    }

}
