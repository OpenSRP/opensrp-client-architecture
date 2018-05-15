package org.opensrp.mvp.taskscheduler.presenter.callback;

import org.opensrp.mvp.taskscheduler.model.Task;

import java.util.List;

/**
 * Created by samuelgithengi on 5/15/18.
 */

public interface TaskListCallBack {
    void onTasksFetched(List<Task> tasks);
}