package org.opensrp.mvp.taskscheduler.presenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.opensrp.mvp.taskscheduler.BaseUnitTest;
import org.opensrp.mvp.taskscheduler.R;
import org.opensrp.mvp.taskscheduler.interactor.TaskInteractor;
import org.opensrp.mvp.taskscheduler.interactor.TaskInteractor.type;
import org.opensrp.mvp.taskscheduler.model.Task;
import org.opensrp.mvp.taskscheduler.view.contract.TaskDetailsView;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Before
    public void setUp() {
        taskDetailPresenter = new TaskDetailPresenter(detailsView, taskInteractor);

    }

    @Test
    public void testOnSaveTaskClicked() {
        Task task = new Task();
        when(detailsView.retrieveTaskDetails(task)).thenReturn(task);
        taskDetailPresenter.onSaveTaskClicked(task);
        verify(detailsView).retrieveTaskDetails(task);
        verify(taskInteractor, timeout(ASYNC_TIMEOUT)).saveOrUpdateTask(task, taskDetailPresenter);
    }

    @Test
    public void testDisplayTask() {
        long taskId = 20l;
        Task task = new Task();
        task.setId(taskId);
        task.setTitle("Display task");
        taskDetailPresenter.displayTask(taskId);
        verify(taskInteractor).getTask(taskId, taskDetailPresenter);
    }

    @Test
    public void testOnTaskFetched() {
        Task task = new Task();
        task.setId(12);
        task.setTitle("testOnTaskFetched");
        taskDetailPresenter.onTaskFetched(task);
        verify(detailsView).setTaskDetails(task);
    }

    @Test
    public void testOnTaskFetchedWithNullDoesNothing() {
        Task task = null;
        taskDetailPresenter.onTaskFetched(task);
        verify(detailsView, never()).setTaskDetails(task);
    }

    @Test
    public void onTaskSaved() {
        taskDetailPresenter.onTaskSaved(type.SAVED);
        verify(detailsView).displayNotification(R.string.task_saved);
        verify(detailsView).returnToListActivity(true);
    }

    @Test
    public void onTaskUpdated() {
        taskDetailPresenter.onTaskSaved(type.UPDATED);
        verify(detailsView).displayNotification(R.string.task_updated);
        verify(detailsView).returnToListActivity(true);
    }

}
