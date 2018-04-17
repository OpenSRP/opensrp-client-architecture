package org.test.taskscheduler.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.test.taskscheduler.R;
import org.test.taskscheduler.adapter.TaskRecyclerViewAdapter;
import org.test.taskscheduler.presenter.TaskListPresenter;
import org.test.taskscheduler.view.contract.TaskListView;

/**
 * An activity representing a list of Tasks. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TaskDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class TaskListActivity extends AppCompatActivity implements TaskListView {

    private static final String TAG = TaskListActivity.class.getName();
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private TaskListPresenter taskListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        assert findViewById(R.id.task_list) != null;
        if (findViewById(R.id.task_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        taskListPresenter = new TaskListPresenter(this, this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(taskListPresenter.getListener(null));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        taskListPresenter.processActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void refreshTasks() {
        getRecyclerView().setAdapter(new TaskRecyclerViewAdapter(taskListPresenter));
    }

    @Override
    public RecyclerView getRecyclerView() {
        return findViewById(R.id.task_list);
    }

    @Override
    public boolean isTwoPanels() {
        return mTwoPane;
    }
}
