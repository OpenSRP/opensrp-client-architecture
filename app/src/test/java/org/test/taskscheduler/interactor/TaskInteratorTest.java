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

import static org.mockito.Mockito.verify;

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
        taskInteractor.getAllTasks();
        verify(taskDao).getAll();
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
}
