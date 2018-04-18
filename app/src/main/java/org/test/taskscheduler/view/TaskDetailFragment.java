package org.test.taskscheduler.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.test.taskscheduler.R;
import org.test.taskscheduler.model.Task;
import org.test.taskscheduler.presenter.TaskDetailPresenter;
import org.test.taskscheduler.repository.TaskRepository;
import org.test.taskscheduler.view.contract.TaskDetailsView;

import static org.test.taskscheduler.utils.Constants.TASKS_MODIFIED;

/**
 * A fragment representing a single Task detail screen.
 * This fragment is either contained in a {@link TaskListActivity}
 * in two-pane mode (on tablets) or a {@link TaskDetailActivity}
 * on handsets.
 */
public class TaskDetailFragment extends Fragment implements TaskDetailsView {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "id";


    private Task mItem;

    private TaskDetailPresenter taskDetailPresenter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskDetailPresenter = new TaskDetailPresenter(getActivity(), this);
        if (getArguments().containsKey(ARG_ITEM_ID)) {

            long id = getArguments().getLong(ARG_ITEM_ID);

            mItem = TaskRepository.getInstance(getActivity()).getTaskDao().findById(id);

        }
        Activity activity = this.getActivity();
        Toolbar toolbar = activity.findViewById(R.id.detail_toolbar);
        if (toolbar != null) {
            toolbar.setTitle(mItem == null ? "Add New Task" : mItem.getTitle());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.task_detail, container, false);
        return taskDetailPresenter.displayTask(rootView, mItem);
    }

    public void displayNotification(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnToListActivity(boolean modified) {
        if (getActivity() instanceof TaskDetailActivity) {
            Intent intent = new Intent();
            intent.putExtra(TASKS_MODIFIED, modified);
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        } else {
            getFragmentManager().beginTransaction().remove(this).commit();
        }
    }
}
