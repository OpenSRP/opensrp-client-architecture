package org.opensrp.mvp.taskscheduler.presenter;

import android.content.Context;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.opensrp.mvp.taskscheduler.BaseUnitTest;
import org.opensrp.mvp.taskscheduler.interactor.TaskInteractor;
import org.opensrp.mvp.taskscheduler.model.Task;
import org.opensrp.mvp.taskscheduler.view.contract.TaskDetailsView;
import org.robolectric.RuntimeEnvironment;
import org.opensrp.mvp.taskscheduler.R;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.opensrp.mvp.taskscheduler.interactor.TaskInteractor.type;

/**
 * Created by samuelgithengi on 5/4/18.
 */

public class TaskDetailsPresenterTest extends BaseUnitTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private TaskDetailsView detailsView;

    @Mock
    private TaskInteractor taskInteractor;

    private TaskDetailPresenter taskDetailPresenter;

    private Context context = RuntimeEnvironment.application;

    @Before
    public void setUp() {
        taskDetailPresenter = new TaskDetailPresenter(context, detailsView, taskInteractor);

    }

    @Test
    public void testOnSaveTaskClicked() {
        Task task = new Task();
        when(detailsView.retrieveTaskDetails(task)).thenReturn(task);
        when(taskInteractor.saveOrUpdateTask(task)).thenReturn(type.SAVED);
        taskDetailPresenter.onSaveTaskClicked(task);
        verify(detailsView).retrieveTaskDetails(task);
        verify(taskInteractor).saveOrUpdateTask(task);
        verify(detailsView).displayNotification(context.getResources().getString(R.string.task_saved));
        verify(detailsView).returnToListActivity(true);
    }

    @Test
    public void testOnSaveExistingTaskClicked() {
        Task task = new Task();
        task.setId(12l);
        task.setTitle("MVP Testing");
        when(detailsView.retrieveTaskDetails(task)).thenReturn(task);
        when(taskInteractor.saveOrUpdateTask(task)).thenReturn(type.UPDATED);

        taskDetailPresenter.onSaveTaskClicked(task);
        verify(detailsView).retrieveTaskDetails(task);
        verify(taskInteractor).saveOrUpdateTask(task);
        verify(detailsView).displayNotification(context.getResources().getString(R.string.task_updated));
        verify(detailsView).returnToListActivity(true);
    }

    @Test
    public void testDisplayTask() {
        long taskId = 20l;
        Task task = new Task();
        task.setId(taskId);
        task.setTitle("Display task");
        when(taskInteractor.getTask(taskId)).thenReturn(task);
        taskDetailPresenter.displayTask(taskId);
        verify(taskInteractor).getTask(taskId);
        verify(detailsView).setTaskDetails(task);
    }

    @Test
    public void testDisplayTaskMissingTask() {
        long taskId = 20l;
        Task task = null;
        when(taskInteractor.getTask(taskId)).thenReturn(task);
        taskDetailPresenter.displayTask(taskId);
        verify(taskInteractor).getTask(taskId);
        verify(detailsView, never()).setTaskDetails(task);
    }

}
