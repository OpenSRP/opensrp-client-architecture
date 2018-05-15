package org.opensrp.mvp.taskscheduler.interactor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.opensrp.mvp.taskscheduler.BaseUnitTest;
import org.opensrp.mvp.taskscheduler.dao.TaskDao;
import org.opensrp.mvp.taskscheduler.model.Task;
import org.opensrp.mvp.taskscheduler.presenter.callback.TaskListCallBack;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by samuelgithengi on 5/4/18.
 */

public class TaskInteratorTest extends BaseUnitTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private TaskInteractor taskInteractor;

    @Mock
    private TaskDao taskDao;

    @Mock
    private TaskListCallBack callBack;

    @Captor
    private ArgumentCaptor<List<Task>> listArgumentCaptor;

    @Before
    public void setUp() {
        taskInteractor = new TaskInteractor(taskDao);
    }

    @Test
    public void testGetAllTasks() {
        Task task = new Task();
        task.setId(12l);
        task.setTitle("Get all Testing");
        Date now = new Date();
        task.setStart(now);
        when(taskDao.getAll()).thenReturn(Arrays.asList(task));
        taskInteractor.getAllTasks(callBack);
        verify(taskDao, timeout(ASYNC_TIMEOUT)).getAll();

        verify(callBack).onTasksFetched(listArgumentCaptor.capture());
        List<Task> tasks = listArgumentCaptor.getValue();

        assertEquals(1, tasks.size());
        assertEquals(12, tasks.get(0).getId());
        assertEquals("Get all Testing", tasks.get(0).getTitle());
        assertEquals(now, tasks.get(0).getStart());
    }


    @Test
    public void testSaveNewTask() {
        Task task = new Task();
        taskInteractor.saveOrUpdateTask(task);
        verify(taskDao).insert(task);
    }

    @Test
    public void testUpdateExistingTask() {
        Task task = new Task();
        task.setId(12l);
        task.setTitle("MVP Testing");

        taskInteractor.saveOrUpdateTask(task);
        verify(taskDao).update(task);
    }


    @Test
    public void testGetTask() {
        long taskId = 23;
        Task task = new Task();
        task.setId(taskId);
        task.setTitle("MVP Testing");

        when(taskDao.findById(taskId)).thenReturn(task);
        Task returnedTask = taskInteractor.getTask(taskId);
        verify(taskDao).findById(taskId);
        assertEquals(taskId, returnedTask.getId());
        assertEquals("MVP Testing", returnedTask.getTitle());
    }
}
