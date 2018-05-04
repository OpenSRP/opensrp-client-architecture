package org.test.taskscheduler.presenter;

import android.content.Context;
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
import org.test.taskscheduler.dao.TaskDao;
import org.test.taskscheduler.repository.TaskRepository;
import org.test.taskscheduler.view.contract.TaskListView;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by samuelgithengi on 5/1/18.
 */

public class TaskListPresenterTest2 extends BaseUnitTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private TaskDao taskDao;

    @Mock
    private TaskListView listView;

    @Mock
    private TaskRepository taskRepository;

    private TaskListPresenter taskListPresenter;

    private Context context = RuntimeEnvironment.application;

    @Before
    public void setUp() {
        taskListPresenter = new TaskListPresenter(context, listView);
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
}
