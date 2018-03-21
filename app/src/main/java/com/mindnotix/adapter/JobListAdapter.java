package com.mindnotix.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.jobs.list.Data;
import com.mindnotix.youthhub.JobDetailsActivity;
import com.mindnotix.youthhub.R;

import java.util.ArrayList;

/**
 * Created by Admin on 2/7/2018.
 */

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.MyViewHolder> {

    Activity activity;
    ArrayList<Data> dataJobArrayList;
    private RecyclerViewClickListener mListener;


    public JobListAdapter(RecyclerViewClickListener mListener, Activity activity, ArrayList<Data> dataJobArrayList) {
        this.mListener = mListener;
        this.activity = activity;
        this.dataJobArrayList = dataJobArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_list_items, parent, false);
        return new MyViewHolder(itemView, mListener);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.setIsRecyclable(false);


        holder.txtTitle.setText(dataJobArrayList.get(position).getTitle());
        holder.txtCompanyName.setText(dataJobArrayList.get(position).getCompany_name());
        holder.txtSalary.setText(dataJobArrayList.get(position).getStart_date());
        holder.txtJobType.setText(dataJobArrayList.get(position).getJobtypename());
        holder.txtEndDate.setText("End date: " + dataJobArrayList.get(position).getEnd_date());
        holder.txtDistrict.setText(dataJobArrayList.get(position).getLocalboardname());

        holder.txtPostDate.setText(dataJobArrayList.get(position).getPost_date());

        if (dataJobArrayList.get(position).getIsfavourtejob().equals("1"))
            holder.imgFavoriteJob.setImageResource(R.drawable.ic_bookmark_black_24dp);
        else
            holder.imgFavoriteJob.setImageResource(R.drawable.ic_bookmark_border_black_24dp);


      /*  holder.imgFavoriteJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dataJobArrayList.get(position).getIsfavourtejob().equals("1")) {
                    holder.imgFavoriteJob.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                } else {
                    holder.imgFavoriteJob.setImageResource(R.drawable.ic_favorite_black_24dp);
                }
            }
        });*/
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            holder.txtDescription.setText(Html.fromHtml(dataJobArrayList.get(position).getDescription(), Html.FROM_HTML_MODE_LEGACY));

        } else {
            holder.txtDescription.setText(Html.fromHtml(dataJobArrayList.get(position).getDescription()));
        }
    }

    @Override
    public int getItemCount() {
        return dataJobArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerViewClickListener mListener;
        private android.widget.TextView txtTitle;
        private android.widget.TextView txtJobType;
        private android.widget.TextView txtCompanyName;
        private android.widget.TextView txtEndDate;
        private android.widget.TextView txtDistrict;
        private android.widget.TextView txtSalary;
        private android.widget.TextView txtDescription;
        private android.widget.TextView txtPostDate;
        private android.widget.ImageView imgFavoriteJob;

        public MyViewHolder(View itemView, RecyclerViewClickListener mListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.mListener = mListener;
            this.txtPostDate = (TextView) itemView.findViewById(R.id.txtPostDate);
            this.imgFavoriteJob = (ImageView) itemView.findViewById(R.id.imgFavoriteJob);
            this.txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            this.txtSalary = (TextView) itemView.findViewById(R.id.txtSalary);
            this.txtDistrict = (TextView) itemView.findViewById(R.id.txtDistrict);
            this.txtEndDate = (TextView) itemView.findViewById(R.id.txtEndDate);
            this.txtCompanyName = (TextView) itemView.findViewById(R.id.txtCompanyName);
            this.txtJobType = (TextView) itemView.findViewById(R.id.txtJobType);
            this.txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            imgFavoriteJob.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.imgFavoriteJob:
                    mListener.onClick(v, getAdapterPosition());

                    break;
                default:
                    Intent mIntent = new Intent(activity, JobDetailsActivity.class);
                    mIntent.putExtra("joblist_id", dataJobArrayList.get(getAdapterPosition()).getId());
                    activity.startActivity(mIntent);
            }

        }
    }
}
