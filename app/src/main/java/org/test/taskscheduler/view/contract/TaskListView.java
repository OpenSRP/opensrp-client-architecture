package org.test.taskscheduler.view.contract;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by samuelgithengi on 4/11/18.
 */

public interface TaskListView {
    void refreshTasks();

    RecyclerView getRecyclerView();

    FragmentManager getSupportFragmentManager();

    boolean isTwoPanels();

}
