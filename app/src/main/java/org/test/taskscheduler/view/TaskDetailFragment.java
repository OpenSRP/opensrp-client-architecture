package org.test.taskscheduler.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import org.test.taskscheduler.R;
import org.test.taskscheduler.model.Task;
import org.test.taskscheduler.presenter.TaskDetailPresenter;
import org.test.taskscheduler.repository.TaskRepository;
import org.test.taskscheduler.view.contract.TaskDetailsView;

import java.util.Calendar;
import java.util.GregorianCalendar;

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

    private DatePicker taskStart;

    private NumberPicker taskDuration;

    private EditText taskTitle;

    private EditText taskDetails;

    private CheckBox taskCompleted;

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
        View view = inflater.inflate(R.layout.task_detail, container, false);
        taskTitle = view.findViewById(R.id.task_title);
        taskDetails = view.findViewById(R.id.task_detail);
        taskStart = view.findViewById(R.id.task_start);
        taskCompleted = view.findViewById(R.id.task_complete);
        taskDuration = view.findViewById(R.id.task_duration);
        taskStart.setMinDate(System.currentTimeMillis());

        taskDuration.setMinValue(1);
        taskDuration.setMaxValue(24);
        if (mItem != null) {
            taskTitle.setText(mItem.getTitle());
            taskDetails.setText(mItem.getDetails());
            taskDuration.setValue(mItem.getDuration());
            taskCompleted.setChecked(mItem.isCompleted());
            Calendar cal = Calendar.getInstance();
            cal.setTime(mItem.getStart());
            taskStart.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        }
        view.findViewById(R.id.task_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskDetailPresenter.onSaveTaskClicked(mItem);
            }
        });
        return view;
    }

    @Override
    public Task populateTaskDetails(Task task) {
        if (task == null)
            task = new Task();
        task.setTitle(taskTitle.getText().toString());
        task.setDetails(taskDetails.getText().toString());
        Calendar cal = new GregorianCalendar(taskStart.getYear(), taskStart.getMonth(), taskStart.getDayOfMonth());
        task.setStart(cal.getTime());
        task.setDuration(taskDuration.getValue());
        task.setCompleted(taskCompleted.isChecked());
        return task;
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
