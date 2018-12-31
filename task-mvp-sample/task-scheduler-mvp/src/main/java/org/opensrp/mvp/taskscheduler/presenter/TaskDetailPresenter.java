package org.opensrp.mvp.taskscheduler.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import org.opensrp.mvp.taskscheduler.R;
import org.opensrp.mvp.taskscheduler.interactor.TaskInteractor;
import org.opensrp.mvp.taskscheduler.interactor.TaskInteractor.type;
import org.opensrp.mvp.taskscheduler.model.Task;
import org.opensrp.mvp.taskscheduler.presenter.callback.TasksCallback.TaskDetailsCallBack;
import org.opensrp.mvp.taskscheduler.view.contract.TaskDetailsView;


/**
 * Created by samuelgithengi on 4/17/18.
 */

public class TaskDetailPresenter implements TaskDetailsCallBack {

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
        taskInteractor.saveOrUpdateTask(task, this);
    }

    public void displayTask(long taskId) {
        taskInteractor.getTask(taskId, this);
    }

    @Override
    public void onTaskFetched(Task task) {
        if (task != null)
            view.setTaskDetails(task);
    }

    @Override
    public void onTaskSaved(@NonNull type status) {
        int message;
        if (status.equals(type.SAVED)) {
            message = R.string.task_saved;
        } else {
            message = R.string.task_updated;
        }
        view.displayNotification(message);
        view.returnToListActivity(true);
    }
}
