package com.mindnotix.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.jobs.list.Data;
import com.mindnotix.youthhub.JobsMapsActivity;
import com.mindnotix.youthhub.R;

import java.util.ArrayList;

/**
 * Created by Admin on 2/23/2018.
 */

public class MapJobFIlterAdapter extends RecyclerView.Adapter<MapJobFIlterAdapter.MyViewHolder> {

    Activity activity;
    ArrayList<Data> dataJobArrayList;
    private RecyclerViewClickListener mListener;


    public MapJobFIlterAdapter(RecyclerViewClickListener mListener, Activity activity, ArrayList<Data> dataJobArrayList) {
        this.mListener = mListener;
        this.activity = activity;
        this.dataJobArrayList = dataJobArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.map_job_filter_item, parent, false);

        return new MyViewHolder(itemView, mListener);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        holder.setIsRecyclable(false);

        holder.txtJobTitle.setText(dataJobArrayList.get(position).getTitle());
        holder.txtJobPostDate.setText(dataJobArrayList.get(position).getPost_date());
        holder.txtJobRegionDistrict.setText(dataJobArrayList.get(position).getRegionalname()
                .concat(", " + dataJobArrayList.get(position).getLocalboardname()));

    }

    @Override
    public int getItemCount() {
        return dataJobArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerViewClickListener mListener;
        private TextView txtJobTitle;
        private TextView txtJobPostDate;
        private TextView txtJobRegionDistrict;

        public MyViewHolder(View itemView, RecyclerViewClickListener mListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.mListener = mListener;
            this.txtJobRegionDistrict = (TextView) itemView.findViewById(R.id.txtJobRegionDistrict);
            this.txtJobPostDate = (TextView) itemView.findViewById(R.id.txtJobPostDate);
            this.txtJobTitle = (TextView) itemView.findViewById(R.id.txtJobTitle);

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                default:
                    Intent getLocations = new Intent(activity, JobsMapsActivity.class);
                    getLocations.putExtra("lat", dataJobArrayList.get(getAdapterPosition()).getLatitude());
                    getLocations.putExtra("lang", dataJobArrayList.get(getAdapterPosition()).getLongitude());
                    getLocations.putExtra("company_name", dataJobArrayList.get(getAdapterPosition()).getCompany_name());
                    activity.startActivity(getLocations);
            }

        }
    }
}
