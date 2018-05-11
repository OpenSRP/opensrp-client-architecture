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
import java.util.List;

/**
 * Created by samuelgithengi on 4/11/18.
 */

public class TaskRecyclerViewAdapter
        extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

    private final List<Task> mValues;
    private TaskListPresenter taskListPresenter;

    public TaskRecyclerViewAdapter(TaskListPresenter taskListPresenter) {
        mValues = taskListPresenter.getAllTasks();
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
        holder.mItem = mValues.get(position);
        if (holder.titleView.getVisibility() == View.GONE) {
            holder.detailsView.setText(mValues.get(position).getTitle() + "\n" + mValues.get(position).getDetails());
        } else {
            holder.titleView.setText(mValues.get(position).getTitle());
            holder.detailsView.setText(mValues.get(position).getDetails());
        }
        holder.durationView.setText(mValues.get(position).getDuration() + " hours");
        holder.startView.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(mValues.get(position).getStart()));
        holder.completeView.setChecked(mValues.get(position).isCompleted());

        holder.mView.setOnClickListener(taskListPresenter.getTaskListView().getOnClickListener(holder.mItem.getId()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
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
}