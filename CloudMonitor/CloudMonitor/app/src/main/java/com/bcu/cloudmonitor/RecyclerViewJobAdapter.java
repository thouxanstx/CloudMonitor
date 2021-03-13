package com.bcu.cloudmonitor;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecyclerViewJobAdapter extends RecyclerView.Adapter<RecyclerViewJobAdapter.JobViewHolder> {
    private List<Job> mJobs;
    private Context mContext;

    public RecyclerViewJobAdapter(Context context, List<Job> jobs) {
        this.mJobs = jobs;
        this.mContext = context;
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {
        private TextView mJobId;
        private TextView mJobState;
        private TextView mJobStartTime;
        private TextView mJobEndTime;

        public JobViewHolder(View itemView) {
            super(itemView);
            mJobId = itemView.findViewById(R.id.jobId);
            mJobState = itemView.findViewById(R.id.jobState);
            mJobStartTime = itemView.findViewById(R.id.jobStartTime);
            mJobEndTime = itemView.findViewById(R.id.jobEndTime);
        }
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.job_list_item, parent, false);
        JobViewHolder jvh = new JobViewHolder(v);
        return jvh;
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        Job job = mJobs.get(position);
        holder.mJobId.setText(job.getId());
        holder.mJobState.setText(job.getState());
        switch (job.getState()){
            case "FAILED":
                holder.mJobState.setTextColor(Color.RED);
                break;
            case "SUCCEEDED":
                holder.mJobState.setTextColor(Color.GREEN);
                break;
            case "CANCELLED":
                holder.mJobState.setTextColor(Color.DKGRAY);
                break;
            case "RUNNING":
                holder.mJobState.setTextColor(Color.YELLOW);
                break;
            case "PREPARING":
                holder.mJobState.setTextColor(Color.GRAY);
                break;
        }

        holder.mJobStartTime.setText(job.getStartTime());
        holder.mJobEndTime.setText(job.getEndTime());

    }

    @Override
    public int getItemCount() {
        return mJobs.size();
    }


}
