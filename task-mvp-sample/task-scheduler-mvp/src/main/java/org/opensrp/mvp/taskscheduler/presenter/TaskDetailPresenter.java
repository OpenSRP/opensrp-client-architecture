package org.opensrp.mvp.taskscheduler.presenter;

import android.support.annotation.VisibleForTesting;

import org.opensrp.mvp.taskscheduler.R;
import org.opensrp.mvp.taskscheduler.interactor.TaskInteractor;
import org.opensrp.mvp.taskscheduler.interactor.TaskInteractor.type;
import org.opensrp.mvp.taskscheduler.model.Task;
import org.opensrp.mvp.taskscheduler.view.contract.TaskDetailsView;


/**
 * Created by samuelgithengi on 4/17/18.
 */

public class TaskDetailPresenter {

    private TaskInteractor taskInteractor;

    private TaskDetailsView view;

    public TaskDetailPresenter(TaskDetailsView view) {
        this(view, new TaskInteractor(view.getContext()));
    }

    @VisibleForTesting
    TaskDetailPresenter(TaskDetailsView view, TaskInteractor taskInteractor) {
        this.view = view;
        this.taskInteractor = taskInteractor;
    }

    public void onSaveTaskClicked(Task task) {
        task = view.retrieveTaskDetails(task);
        saveTask(task);
    }

    private void saveTask(Task task) {
        int message;
        type operation = taskInteractor.saveOrUpdateTask(task);
        if (operation.equals(type.SAVED)) {
            message = R.string.task_saved;
        } else {
            message = R.string.task_updated;
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
