package org.opensrp.mvp.taskscheduler.interactor;

import android.content.Context;
import android.support.annotation.VisibleForTesting;

import org.opensrp.mvp.taskscheduler.dao.TaskDao;
import org.opensrp.mvp.taskscheduler.model.Task;
import org.opensrp.mvp.taskscheduler.presenter.callback.TasksCallback.TaskDetailsCallBack;
import org.opensrp.mvp.taskscheduler.presenter.callback.TasksCallback.TaskListCallBack;
import org.opensrp.mvp.taskscheduler.repository.TaskRepository;
import org.opensrp.mvp.taskscheduler.utils.AppExecutors;

import java.util.List;

/**
 * Created by samuelgithengi on 5/4/18.
 */

public class TaskInteractor {

    public static final String TAG = TaskInteractor.class.getName();

    public enum type {SAVED, UPDATED}

    private TaskDao taskDao;

    private AppExecutors appExecutors;

    @VisibleForTesting
    TaskInteractor(TaskDao taskDao, AppExecutors appExecutors) {
        this.taskDao = taskDao;
        this.appExecutors = appExecutors;
    }

    public TaskInteractor(Context context) {
        this(TaskRepository.getInstance(context).getTaskDao(), new AppExecutors());
    }

    public void getAllTasks(final TaskListCallBack callBack) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Task> tasks = taskDao.getAll();
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onTasksFetched(tasks);
                    }
                });
            }
        };

        appExecutors.diskIO().execute(runnable);
    }

    public void saveOrUpdateTask(final Task task, final TaskDetailsCallBack callBack) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final type status;
                if (task.getId() == 0) {
                    taskDao.insert(task);
                    status = type.SAVED;
                } else {
                    taskDao.update(task);
                    status = type.UPDATED;
                }
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onTaskSaved(status);
                    }
                });
            }
        };
        appExecutors.diskIO().execute(runnable);
    }

    public void getTask(final long taskId, final TaskDetailsCallBack callBack) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Task task = taskDao.findById(taskId);
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onTaskFetched(task);
                    }
                });
            }
        };
        appExecutors.diskIO().execute(runnable);
    }


}
