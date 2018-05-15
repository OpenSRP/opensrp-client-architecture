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
import org.opensrp.mvp.taskscheduler.presenter.callback.TasksCallback.TaskDetailsCallBack;
import org.opensrp.mvp.taskscheduler.presenter.callback.TasksCallback.TaskListCallBack;
import org.opensrp.mvp.taskscheduler.utils.AppExecutors;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.opensrp.mvp.taskscheduler.interactor.TaskInteractor.type;

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

    @Mock
    private TaskDetailsCallBack detailsCallBack;

    @Captor
    private ArgumentCaptor<Task> detailsArgumentCaptor;

    @Captor
    private ArgumentCaptor<TaskInteractor.type> statusArgumentCaptor;

    @Before
    public void setUp() {
        taskInteractor = new TaskInteractor(taskDao, new AppExecutors(Executors.newSingleThreadExecutor(), Executors.newSingleThreadExecutor(), Executors.newSingleThreadExecutor()));
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
        taskInteractor.saveOrUpdateTask(task, detailsCallBack);
        verify(taskDao, timeout(ASYNC_TIMEOUT)).insert(task);

        verify(detailsCallBack).onTaskSaved(statusArgumentCaptor.capture());
        assertEquals(type.SAVED, statusArgumentCaptor.getValue());
    }

    @Test
    public void testUpdateExistingTask() {
        Task task = new Task();
        task.setId(12l);
        task.setTitle("MVP Testing");

        taskInteractor.saveOrUpdateTask(task, detailsCallBack);
        verify(taskDao, timeout(ASYNC_TIMEOUT)).update(task);

        verify(detailsCallBack).onTaskSaved(statusArgumentCaptor.capture());
        assertEquals(type.UPDATED, statusArgumentCaptor.getValue());

    }


    @Test
    public void testGetTask() {
        long taskId = 23;
        Task task = new Task();
        task.setId(taskId);
        task.setTitle("testGetTask");

        when(taskDao.findById(taskId)).thenReturn(task);
        taskInteractor.getTask(taskId, detailsCallBack);
        verify(taskDao, timeout(ASYNC_TIMEOUT)).findById(taskId);

        verify(detailsCallBack).onTaskFetched(detailsArgumentCaptor.capture());
        Task returnedTask = detailsArgumentCaptor.getValue();
        assertEquals(taskId, returnedTask.getId());
        assertEquals("testGetTask", returnedTask.getTitle());
    }
}
