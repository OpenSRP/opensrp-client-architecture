package org.test.taskscheduler.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.view.View;

import org.test.taskscheduler.interactor.TaskInteractor;
import org.test.taskscheduler.model.Task;
import org.test.taskscheduler.view.contract.TaskListView;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static org.test.taskscheduler.utils.Constants.TASKS_MODIFIED;
import static org.test.taskscheduler.utils.Constants.TASKS_MODIFIED_RESULT_CODE;

/**
 * Created by samuelgithengi on 4/11/18.
 */

public class TaskListPresenter {

    private TaskInteractor taskInteractor;
    private TaskListView taskListView;


    public TaskListPresenter(Context context, TaskListView taskListView) {
        this(new TaskInteractor(context), taskListView);
    }

    @VisibleForTesting
    TaskListPresenter(TaskInteractor taskInteractor, TaskListView taskListView) {
        this.taskInteractor = taskInteractor;
        this.taskListView = taskListView;
    }


    public List<Task> getAllTasks() {
        return taskInteractor.getAllTasks();
    }

    public View.OnClickListener getListener(final Long id) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskListView.isTwoPanels())
                    taskListView.startDetailsFragment(id);
                else
                    taskListView.startDetailsActivity(id, v);
            }
        };
    }

    public void processActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == TASKS_MODIFIED_RESULT_CODE) {
            if (data.getBooleanExtra(TASKS_MODIFIED, false))
                taskListView.refreshTasks();
        }
    }

}
