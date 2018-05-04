package org.test.taskscheduler.presenter;

import android.content.Context;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RuntimeEnvironment;
import org.test.taskscheduler.BaseUnitTest;
import org.test.taskscheduler.R;
import org.test.taskscheduler.dao.TaskDao;
import org.test.taskscheduler.model.Task;
import org.test.taskscheduler.repository.TaskRepository;
import org.test.taskscheduler.view.contract.TaskDetailsView;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by samuelgithengi on 5/4/18.
 */

public class TaskDetailsPresenterTest extends BaseUnitTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private TaskDao taskDao;

    @Mock
    private TaskDetailsView detailsView;

    @Mock
    private TaskRepository taskRepository;

    private TaskDetailPresenter taskDetailPresenter;

    private Context context = RuntimeEnvironment.application;

    @Before
    public void setUp() {
        taskDetailPresenter = new TaskDetailPresenter(context, detailsView, taskDao);
    }

    @Test
    public void testOnSaveTaskClicked() {
        Task task = new Task();
        when(detailsView.populateTaskDetails(task)).thenReturn(task);
        taskDetailPresenter.onSaveTaskClicked(task);
        verify(detailsView).populateTaskDetails(task);
        verify(taskDao).insert(task);
        verify(detailsView).displayNotification(context.getResources().getString(R.string.task_saved));
        verify(detailsView).returnToListActivity(true);
    }

    @Test
    public void testOnSaveExistingTaskClicked() {
        Task task = new Task();
        task.setId(12l);
        task.setTitle("MVP Testing");
        when(detailsView.populateTaskDetails(task)).thenReturn(task);

        taskDetailPresenter.onSaveTaskClicked(task);
        verify(detailsView).populateTaskDetails(task);
        verify(taskDao).update(task);
        verify(detailsView).displayNotification(context.getResources().getString(R.string.task_updated));
        verify(detailsView).returnToListActivity(true);
    }


}
