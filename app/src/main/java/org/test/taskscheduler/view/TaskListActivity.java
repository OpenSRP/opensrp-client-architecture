package org.test.taskscheduler.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.test.taskscheduler.R;
import org.test.taskscheduler.adapter.TaskRecyclerViewAdapter;
import org.test.taskscheduler.presenter.TaskListPresenter;
import org.test.taskscheduler.view.contract.TaskListView;

import static org.test.taskscheduler.utils.Constants.TASKS_MODIFIED_RESULT_CODE;

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
        getRecyclerView().setAdapter(new TaskRecyclerViewAdapter(taskListPresenter));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(taskListPresenter.getListener(null));

    }

    @Override
    public void startDetailsFragment(Long id) {
        Bundle arguments = new Bundle();
        if (id != null)
            arguments.putLong(TaskDetailFragment.ARG_ITEM_ID, id);
        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.task_detail_container, fragment)
                .commit();
    }

    @Override
    public void startDetailsActivity(Long id, View view) {
        Activity context = (Activity) view.getContext();
        Intent intent = new Intent(context, TaskDetailActivity.class);
        if (id != null)
            intent.putExtra(TaskDetailFragment.ARG_ITEM_ID, id);

        startActivityForResult(intent, TASKS_MODIFIED_RESULT_CODE);
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
