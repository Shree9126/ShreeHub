package com.mindnotix.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.jobs.apply_master.Resumeattached;
import com.mindnotix.youthhub.JobApplyActivity;
import com.mindnotix.youthhub.R;

import java.util.ArrayList;

/**
 * Created by Sridharan on 2/14/2018.
 */

public class JobResumeListAdapter extends RecyclerView.Adapter<JobResumeListAdapter.MyViewHolder> {

    private static final String TAG =JobResumeListAdapter.class.getSimpleName() ;
    JobApplyActivity jobApplyActivity;
    ArrayList<Resumeattached> resumeattachedArrayList;
    private RecyclerViewClickListener mListener;

    public JobResumeListAdapter(RecyclerViewClickListener listener, JobApplyActivity jobApplyActivity, ArrayList<Resumeattached> resumeattachedArrayList) {
        this.mListener = listener;
        this.jobApplyActivity = jobApplyActivity;
        this.resumeattachedArrayList = resumeattachedArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_apply_resume_items, parent, false);
        return new MyViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.radioResume.setText(resumeattachedArrayList.get(position).getFile_name());
        if (!resumeattachedArrayList.get(position).getStatus().equals("0")) {

            holder.radioResume.setChecked(true);
        } else {
            holder.radioResume.setChecked(false);
        }
        holder.radioResume.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged: isChecked "+isChecked);
                if (isChecked) {
                    for (int i = 0; i < resumeattachedArrayList.size(); i++) {

                        Log.d(TAG, "onCheckedChanged: "+resumeattachedArrayList.get(i).getId());
                        if (!resumeattachedArrayList.get(i).getId().equals(resumeattachedArrayList.get(position).getId())) {
                            resumeattachedArrayList.get(i).setStatus("0");
                            jobApplyActivity.resumeattachedArrayList.get(i).setStatus("0");
                        } else {
                            resumeattachedArrayList.get(i).setStatus("1");
                            jobApplyActivity.resumeattachedArrayList.get(i).setStatus("1");
                        }
                    }
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return resumeattachedArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RecyclerViewClickListener mListener;
        private android.widget.RadioButton radioResume;

        public MyViewHolder(View itemView, RecyclerViewClickListener mListener) {
            super(itemView);

            this.mListener = mListener;
            this.radioResume = (RadioButton) itemView.findViewById(R.id.radioResume);


        }

        @Override
        public void onClick(View v) {

        }
    }
}
