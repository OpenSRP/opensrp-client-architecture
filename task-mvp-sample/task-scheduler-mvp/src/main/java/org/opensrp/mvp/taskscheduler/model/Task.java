package org.opensrp.mvp.taskscheduler.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.opensrp.mvp.taskscheduler.utils.Constants;

import java.util.Date;

/**
 * Created by samuelgithengi on 4/11/18.
 */

@Entity(tableName = Constants.TASK_TABLE_NAME)
public class Task {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String details;
    private Date start;
    public int duration;
    public boolean completed;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Task))
            return false;
        Task task = (Task) o;
        if (id != task.getId()) return false;
        return title != null ? title.equals(task.getTitle()) : task.getTitle() == null;
    }

    @Override
    public String toString() {
        return String.format("%s starting on %s for %f hours", title, start.toString(), duration);
    }
}
