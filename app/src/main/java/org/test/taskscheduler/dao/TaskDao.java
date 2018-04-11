package org.test.taskscheduler.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import org.test.taskscheduler.model.Task;
import org.test.taskscheduler.utils.Constants;

import java.util.List;

/**
 * Created by samuelgithengi on 4/11/18.
 */

@Dao
public interface TaskDao {

    @Query("SELECT * FROM  " + Constants.TASK_TABLE_NAME)
    List<Task> getAll();


    @Query("SELECT * FROM  " + Constants.TASK_TABLE_NAME + " WHERE id=:id")
    Task findById(long id);

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);


}
