package org.opensrp.mvp.taskscheduler.view.contract;


import android.content.Context;

import org.opensrp.mvp.taskscheduler.model.Task;

/**
 * Created by samuelgithengi on 4/17/18.
 */

public interface TaskDetailsView {
    void displayNotification(int messageId);

    void returnToListActivity(boolean taskModified);

    Task retrieveTaskDetails(Task task);

    void setTaskDetails(Task task);

    Context getContext();
}
