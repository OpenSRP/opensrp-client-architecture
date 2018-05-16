package org.opensrp.mvp.taskscheduler.presenter.callback;

import org.opensrp.mvp.taskscheduler.model.Task;

import java.util.List;

import static org.opensrp.mvp.taskscheduler.interactor.TaskInteractor.type;

/**
 * Created by samuelgithengi on 5/15/18.
 */
public interface TasksCallback {

    interface TaskListCallBack {
        void onTasksFetched(List<Task> tasks);
    }


    interface TaskDetailsCallBack {
        void onTaskFetched(Task task);

        void onTaskSaved(type status);

    }

}