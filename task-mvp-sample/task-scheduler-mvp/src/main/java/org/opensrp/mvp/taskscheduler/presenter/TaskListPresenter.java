package org.opensrp.mvp.taskscheduler.presenter;

import android.content.Intent;
import android.support.annotation.VisibleForTesting;

import org.opensrp.mvp.taskscheduler.interactor.TaskInteractor;
import org.opensrp.mvp.taskscheduler.model.Task;
import org.opensrp.mvp.taskscheduler.presenter.callback.TaskListCallBack;
import org.opensrp.mvp.taskscheduler.view.contract.TaskListView;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static org.opensrp.mvp.taskscheduler.utils.Constants.TASKS_MODIFIED;
import static org.opensrp.mvp.taskscheduler.utils.Constants.TASKS_MODIFIED_RESULT_CODE;

/**
 * Created by samuelgithengi on 4/11/18.
 */

public class TaskListPresenter implements TaskListCallBack {

    private TaskInteractor taskInteractor;
    private TaskListView taskListView;


    public TaskListPresenter(TaskListView taskListView) {
        this(new TaskInteractor(taskListView.getContext()), taskListView);
    }

    @VisibleForTesting
    TaskListPresenter(TaskInteractor taskInteractor, TaskListView taskListView) {
        this.taskInteractor = taskInteractor;
        this.taskListView = taskListView;
    }


    public void getAllTasks() {
        taskInteractor.getAllTasks(this);
    }

    public void openDetailsView(Long id) {
        if (taskListView.isTwoPanels())
            taskListView.startDetailsFragment(id);
        else
            taskListView.startDetailsActivity(id);
    }

    public void processActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == TASKS_MODIFIED_RESULT_CODE) {
            if (data.getBooleanExtra(TASKS_MODIFIED, false))
                taskListView.refreshTasks();
        }
    }

    @Override
    public void onTasksFetched(List<Task> tasks) {
        taskListView.displayTasks(tasks);
        taskListView.hideProgressBar();
    }

    public TaskListView getTaskListView() {
        return taskListView;
    }

}
