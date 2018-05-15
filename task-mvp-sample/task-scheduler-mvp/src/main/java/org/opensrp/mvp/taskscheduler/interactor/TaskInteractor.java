package org.opensrp.mvp.taskscheduler.interactor;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import org.opensrp.mvp.taskscheduler.dao.TaskDao;
import org.opensrp.mvp.taskscheduler.model.Task;
import org.opensrp.mvp.taskscheduler.presenter.callback.TaskListCallBack;
import org.opensrp.mvp.taskscheduler.repository.TaskRepository;
import org.opensrp.mvp.taskscheduler.utils.AppExecutors;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by samuelgithengi on 5/4/18.
 */

public class TaskInteractor {

    public static final String TAG = TaskInteractor.class.getName();

    public enum type {SAVED, UPDATED}

    private TaskDao taskDao;

    private AppExecutors appExecutors;

    @VisibleForTesting
    TaskInteractor(TaskDao taskDao) {
        this.taskDao = taskDao;
        appExecutors = new AppExecutors(Executors.newSingleThreadExecutor(), Executors.newSingleThreadExecutor(), Executors.newSingleThreadExecutor());
    }

    public TaskInteractor(Context context) {
        this(TaskRepository.getInstance(context).getTaskDao());
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

    public type saveOrUpdateTask(final Task task) {
        FutureTask<type> future =
                new FutureTask<>(new Callable<type>() {
                    public type call() {
                        if (task.getId() == 0) {
                            taskDao.insert(task);
                            return type.SAVED;
                        } else {
                            taskDao.update(task);
                            return type.UPDATED;
                        }
                    }
                });
        appExecutors.diskIO().execute(future);
        try {
            return future.get();
        } catch (InterruptedException e) {
            Log.e(TAG, "saveOrUpdateTask:InterruptedException ", e);
        } catch (ExecutionException e) {
            Log.e(TAG, "saveOrUpdateTask:ExecutionException ", e);
        }
        return null;
    }

    public Task getTask(final long taskId) {
        FutureTask<Task> future =
                new FutureTask<>(new Callable<Task>() {
                    public Task call() {
                        return taskDao.findById(taskId);
                    }
                });
        appExecutors.diskIO().execute(future);
        try {
            return future.get();
        } catch (InterruptedException e) {
            Log.e(TAG, "saveOrUpdateTask:InterruptedException ", e);
        } catch (ExecutionException e) {
            Log.e(TAG, "saveOrUpdateTask:ExecutionException ", e);
        }
        return null;
    }


}
