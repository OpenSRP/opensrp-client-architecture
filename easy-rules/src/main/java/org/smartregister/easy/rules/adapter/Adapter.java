package org.smartregister.easy.rules.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import org.smartregister.easy.rules.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<Integer> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        private ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    public Adapter(List<Integer> myDataset) {
        mDataset = myDataset;
    }

    public void setmDataset(List<Integer> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                 int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text = "Contact (" + (position + 1) + ") : " + mDataset.get(position) + " Weeks";
        if (position == (mDataset.size() - 1)) {
            holder.mTextView.setBackgroundResource(R.color.green);
        } else {
            holder.mTextView.setBackgroundResource(R.color.transparent);
        }
        holder.mTextView.setText(text);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
