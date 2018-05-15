package org.opensrp.mvp.taskscheduler.presenter;

import android.content.Intent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.opensrp.mvp.taskscheduler.BaseUnitTest;
import org.opensrp.mvp.taskscheduler.interactor.TaskInteractor;
import org.opensrp.mvp.taskscheduler.view.contract.TaskListView;

import static android.app.Activity.RESULT_OK;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.opensrp.mvp.taskscheduler.utils.Constants.TASKS_MODIFIED;
import static org.opensrp.mvp.taskscheduler.utils.Constants.TASKS_MODIFIED_RESULT_CODE;

/**
 * Created by samuelgithengi on 5/1/18.
 */

public class TaskListPresenterTest extends BaseUnitTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private TaskListView listView;

    @Mock
    private TaskInteractor taskInteractor;

    private TaskListPresenter taskListPresenter;

    @Before
    public void setUp() {
        taskListPresenter = new TaskListPresenter(taskInteractor, listView);
    }

    @Test
    public void testGetAllTasks() {
        taskListPresenter.getAllTasks();
        verify(taskInteractor).getAllTasks(taskListPresenter);
    }

    @Test
    public void testListenerStartsActivity() {
        taskListPresenter.openDetailsView(null);
        verify(listView).startDetailsActivity(null);
    }

    @Test
    public void testListenerStartsFragment() {
        when(listView.isTwoPanels()).thenReturn(true);
        Long taskId = 120l;
        taskListPresenter.openDetailsView(taskId);
        verify(listView).startDetailsFragment(taskId);
    }


    @Test
    public void testProcessActivityResult() {
        Intent intent = new Intent();
        intent.putExtra(TASKS_MODIFIED, true);
        taskListPresenter.processActivityResult(TASKS_MODIFIED_RESULT_CODE, RESULT_OK, intent);
        verify(listView).refreshTasks();
    }

    @Test
    public void testProcessActivityResultDoesNotRefresh() {
        taskListPresenter.processActivityResult(TASKS_MODIFIED_RESULT_CODE, RESULT_OK, new Intent());
        verify(listView, never()).refreshTasks();
    }


}
