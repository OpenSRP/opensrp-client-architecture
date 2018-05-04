package org.test.taskscheduler.presenter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RuntimeEnvironment;
import org.test.taskscheduler.BaseUnitTest;
import org.test.taskscheduler.R;
import org.test.taskscheduler.interactor.TaskInteractor;
import org.test.taskscheduler.view.contract.TaskListView;

import static android.app.Activity.RESULT_OK;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.test.taskscheduler.utils.Constants.TASKS_MODIFIED;
import static org.test.taskscheduler.utils.Constants.TASKS_MODIFIED_RESULT_CODE;


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
        verify(taskInteractor).getAllTasks();
    }

    @Test
    public void testListenerStartsActivity() {
        View view = LayoutInflater.from(RuntimeEnvironment.application).inflate(R.layout.task_list_content, null);
        view.setOnClickListener(taskListPresenter.getListener(null));
        view.performClick();
        verify(listView).startDetailsActivity(null, view);
    }

    @Test
    public void testListenerStartsFragment() {
        View view = LayoutInflater.from(RuntimeEnvironment.application).inflate(R.layout.task_list_content, null);
        when(listView.isTwoPanels()).thenReturn(true);
        Long taskId = 120l;
        view.setOnClickListener(taskListPresenter.getListener(taskId));
        view.performClick();
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
