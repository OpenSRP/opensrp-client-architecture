package org.test.taskscheduler.presenter;

import android.content.Context;
import android.support.annotation.VisibleForTesting;

import org.test.taskscheduler.R;
import org.test.taskscheduler.interactor.TaskInteractor;
import org.test.taskscheduler.interactor.TaskInteractor.type;
import org.test.taskscheduler.model.Task;
import org.test.taskscheduler.view.contract.TaskDetailsView;

/**
 * Created by samuelgithengi on 4/17/18.
 */

public class TaskDetailPresenter {

    private TaskInteractor taskInteractor;

    private TaskDetailsView view;

    private Context context;

    public TaskDetailPresenter(Context context, TaskDetailsView view) {
        this(context, view, new TaskInteractor(context));
    }

    @VisibleForTesting
    TaskDetailPresenter(Context context, TaskDetailsView view, TaskInteractor taskInteractor) {
        this.view = view;
        this.context = context;
        this.taskInteractor = taskInteractor;
    }

    public void onSaveTaskClicked(Task task) {
        task = view.retrieveTaskDetails(task);
        saveTask(task);
    }

    private void saveTask(Task task) {
        String message;
        type operation = taskInteractor.saveOrUpdateTask(task);
        if (operation.equals(type.SAVED)) {
            message = context.getResources().getString(R.string.task_saved);
        } else {
            message = context.getResources().getString(R.string.task_updated);
        }
        view.displayNotification(message);
        view.returnToListActivity(true);

    }

    public void displayTask(long taskId) {
        Task task = taskInteractor.getTask(taskId);
        if (task != null)
            view.setTaskDetails(task);
    }

}
