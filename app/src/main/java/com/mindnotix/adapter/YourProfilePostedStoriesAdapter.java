package com.mindnotix.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindnotix.youthhub.R;

/**
 * Created by Sridharan on 1/16/2018.
 */

public class YourProfilePostedStoriesAdapter extends RecyclerView.Adapter<YourProfilePostedStoriesAdapter.MyViewHolder> {
    FragmentActivity activity;

    public YourProfilePostedStoriesAdapter(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public YourProfilePostedStoriesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_feed_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(YourProfilePostedStoriesAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);

        }
    }
}
