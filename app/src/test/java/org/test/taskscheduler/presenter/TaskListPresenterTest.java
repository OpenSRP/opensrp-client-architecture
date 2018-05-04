package org.test.taskscheduler.presenter;

import android.content.Context;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.robolectric.RuntimeEnvironment;
import org.test.taskscheduler.BaseUnitTest;
import org.test.taskscheduler.dao.TaskDao;
import org.test.taskscheduler.repository.TaskRepository;
import org.test.taskscheduler.view.contract.TaskListView;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Created by samuelgithengi on 5/1/18.
 */

/*@RunWith(PowerMockRunner.class)*/
@PrepareForTest(TaskRepository.class)
public class TaskListPresenterTest extends BaseUnitTest {

    @Rule
    //public PowerMockRule rule = new PowerMockRule();
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
        //initMocks(this);
        PowerMockito.mockStatic(TaskRepository.class);
        //initMocks(this);
        when(TaskRepository.getInstance(context)).thenReturn(taskRepository);
        when(taskRepository.getTaskDao()).thenReturn(taskDao);
        taskListPresenter = new TaskListPresenter(context, listView);
    }

    @Test
    public void testGetAllTasks() {
        taskListPresenter.getAllTasks();
        verify(taskDao).getAll();
    }


}
