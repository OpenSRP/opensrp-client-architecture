package org.test.taskscheduler.view.contract;

/**
 * Created by samuelgithengi on 4/17/18.
 */

public interface TaskDetailsView {
    void displayNotification(String message);

    void returnToListActivity(boolean taskModified);
}
