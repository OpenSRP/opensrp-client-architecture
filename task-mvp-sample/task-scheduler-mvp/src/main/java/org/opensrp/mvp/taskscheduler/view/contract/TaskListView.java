package org.opensrp.mvp.taskscheduler.view.contract;

import android.content.Context;
import android.view.View;

import org.opensrp.mvp.taskscheduler.model.Task;

import java.util.List;

/**
 * Created by samuelgithengi on 4/11/18.
 */

public interface TaskListView {
    void refreshTasks();

    void displayTasks(List<Task> tasks);

    void showProgressBar();

    void hideProgressBar();

    boolean isTwoPanels();

    void startDetailsFragment(Long id);

    void startDetailsActivity(Long id);

    View.OnClickListener getOnClickListener(Long id);

    Context getContext();
}
