package org.test.taskscheduler.interactor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.test.taskscheduler.BaseUnitTest;
import org.test.taskscheduler.dao.TaskDao;
import org.test.taskscheduler.model.Task;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
        List<Task> tasks = taskInteractor.getAllTasks();
        verify(taskDao).getAll();

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
