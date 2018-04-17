package org.test.taskscheduler.presenter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;

import org.test.taskscheduler.R;
import org.test.taskscheduler.dao.TaskDao;
import org.test.taskscheduler.model.Task;
import org.test.taskscheduler.view.contract.TaskDetailsView;
import org.test.taskscheduler.repository.TaskRepository;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by samuelgithengi on 4/17/18.
 */

public class TaskDetailPresenter {

    private TaskDao taskDao;

    private DatePicker taskStart;

    private NumberPicker taskDuration;

    private EditText taskTitle;

    private EditText taskDetails;

    private CheckBox taskCompleted;

    private TaskDetailsView view;

    public TaskDetailPresenter(Context context, TaskDetailsView view) {
        taskDao = TaskRepository.getInstance(context).getTaskDao();
        this.view = view;
    }

    public View displayTask(View view, final Task task) {
        taskTitle = view.findViewById(R.id.task_title);
        taskDetails = view.findViewById(R.id.task_detail);
        taskStart = view.findViewById(R.id.task_start);
        taskCompleted = view.findViewById(R.id.task_complete);
        taskDuration = view.findViewById(R.id.task_duration);
        taskStart.setMinDate(System.currentTimeMillis());

        taskDuration.setMinValue(1);
        taskDuration.setMaxValue(24);
        if (task != null) {
            taskTitle.setText(task.getTitle());
            taskDetails.setText(task.getDetails());
            taskDuration.setValue(task.getDuration());
            taskCompleted.setChecked(task.isCompleted());
            Calendar cal = Calendar.getInstance();
            cal.setTime(task.getStart());
            taskStart.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        }
        view.findViewById(R.id.task_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask(task);
            }
        });
        return view;
    }

    private void saveTask(Task task) {
        if (task == null)
            task = new Task();
        task.setTitle(taskTitle.getText().toString());
        task.setDetails(taskDetails.getText().toString());
        Calendar cal = new GregorianCalendar(taskStart.getYear(), taskStart.getMonth(), taskStart.getDayOfMonth());
        task.setStart(cal.getTime());
        task.setDuration(taskDuration.getValue());
        task.setCompleted(taskCompleted.isChecked());
        String message;
        if (task.getId() == 0) {
            taskDao.insert(task);
            message = "Task has been successfully saved";
        } else {
            taskDao.update(task);
            message = "Task has been successfully updated";
        }
        view.displayNotification(message);
        view.returnToListActivity(true);

    }

}
