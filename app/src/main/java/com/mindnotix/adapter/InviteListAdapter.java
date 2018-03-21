package com.mindnotix.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mindnotix.model.jobs.my_jobs.InvitedList;
import com.mindnotix.youthhub.JobDetailsActivity;
import com.mindnotix.youthhub.R;

import java.util.ArrayList;

/**
 * Created by Admin on 15-02-2018.
 */

public class InviteListAdapter extends RecyclerView.Adapter<InviteListAdapter.MyViewHolder> {

    Context context;
    ArrayList<InvitedList> invitedLists;

    public InviteListAdapter(Context context, ArrayList<InvitedList> invitedLists) {
        this.context = context;
        this.invitedLists = invitedLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_invite_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        InvitedList invitedList = invitedLists.get(position);
        holder.tvTitle.setText(invitedList.getJob_title());
        holder.tvLocation.setText(invitedList.getBusiness_name());
        holder.tvCompany.setText(invitedList.getInvited_on());

    }

    @Override
    public int getItemCount() {
        return invitedLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTitle;
        private TextView tvLocation;
        private TextView tvCompany;


        public MyViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvCompany = itemView.findViewById(R.id.tvCompany);


        }


        @Override
        public void onClick(View v) {

            Intent mIntent = new Intent(context, JobDetailsActivity.class);
            mIntent.putExtra("joblist_id", invitedLists.get(getAdapterPosition()).getJob_id());
            context.startActivity(mIntent);
        }
    }
}

