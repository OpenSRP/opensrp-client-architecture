package org.opensrp.mvp.taskscheduler.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import org.opensrp.mvp.taskscheduler.R;
import org.opensrp.mvp.taskscheduler.model.Task;
import org.opensrp.mvp.taskscheduler.presenter.TaskListPresenter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by samuelgithengi on 4/11/18.
 */

public class TaskRecyclerViewAdapter
        extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

    private List<Task> tasks = new ArrayList<>();
    private TaskListPresenter taskListPresenter;

    public TaskRecyclerViewAdapter(TaskListPresenter taskListPresenter) {
        taskListPresenter.fetchTasks();
        this.taskListPresenter = taskListPresenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = tasks.get(position);
        if (holder.titleView.getVisibility() == View.GONE) {
            holder.detailsView.setText(tasks.get(position).getTitle() + "\n" + tasks.get(position).getDetails());
        } else {
            holder.titleView.setText(tasks.get(position).getTitle());
            holder.detailsView.setText(tasks.get(position).getDetails());
        }
        holder.durationView.setText(tasks.get(position).getDuration() + " hours");
        holder.startView.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(tasks.get(position).getStart()));
        holder.completeView.setChecked(tasks.get(position).isCompleted());

        holder.mView.setOnClickListener(taskListPresenter.getTaskListView().getOnClickListener(holder.mItem.getId()));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView titleView;
        public final TextView detailsView;
        public final TextView durationView;
        public final TextView startView;
        public final CheckBox completeView;
        public Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            titleView = view.findViewById(R.id.task_title);
            detailsView = view.findViewById(R.id.task_detail);
            durationView = view.findViewById(R.id.task_duration);
            startView = view.findViewById(R.id.task_start);
            completeView = view.findViewById(R.id.task_complete);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + detailsView.getText() + "'";
        }
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}