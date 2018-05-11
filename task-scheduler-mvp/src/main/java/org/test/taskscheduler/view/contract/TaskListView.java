package org.test.taskscheduler.view.contract;

import android.view.View;

/**
 * Created by samuelgithengi on 4/11/18.
 */

public interface TaskListView {
    void refreshTasks();

    boolean isTwoPanels();

    void startDetailsFragment(Long id);

    void startDetailsActivity(Long id, View v);
}
