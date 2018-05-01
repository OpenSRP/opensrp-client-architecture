package org.test.taskscheduler.view.contract;

import org.test.taskscheduler.model.Task;

/**
 * Created by samuelgithengi on 4/17/18.
 */

public interface TaskDetailsView {
    void displayNotification(String message);

    void returnToListActivity(boolean taskModified);

    Task populateTaskDetails(Task task);
}
