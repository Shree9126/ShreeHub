package com.mindnotix.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mindnotix.model.jobs.my_jobs.AppliedList;
import com.mindnotix.youthhub.JobDetailsActivity;
import com.mindnotix.youthhub.R;

import java.util.ArrayList;

/**
 * Created by Admin on 15-02-2018.
 */

public class AppliedListAdapter extends RecyclerView.Adapter<AppliedListAdapter.MyViewHolder> {

    Context context;
    ArrayList<AppliedList> appliedLists;

    public AppliedListAdapter(Context context, ArrayList<AppliedList> appliedLists) {
        this.context = context;
        this.appliedLists = appliedLists;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_job_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        AppliedList appliedList = appliedLists.get(position);
        holder.tvTitle.setText(appliedList.getJobtitle());
        holder.tvDate.setText(appliedList.getApplied_date());
        holder.tvLocation.setText(appliedList.getLocal_boardname());
        holder.tvCompany.setText(appliedList.getCompany_name());
        holder.tvStatus.setText(appliedList.getJob_status_name());

    }

    @Override
    public int getItemCount() {
        return appliedLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTitle;
        private TextView tvDate;
        private TextView tvLocation;
        private TextView tvCompany;
        private TextView tvStatus;


        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvCompany = itemView.findViewById(R.id.tvCompany);
            tvStatus = itemView.findViewById(R.id.tvStatus);

        }


        @Override
        public void onClick(View v) {

            Intent mIntent = new Intent(context, JobDetailsActivity.class);
            mIntent.putExtra("joblist_id", appliedLists.get(getAdapterPosition()).getJob_id());
            context.startActivity(mIntent);
        }
    }
}