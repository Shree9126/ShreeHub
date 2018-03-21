package com.mindnotix.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mindnotix.model.explore.explorerView.Topic_list;
import com.mindnotix.youthhub.ExploreViewActivity;
import com.mindnotix.youthhub.R;

import java.util.ArrayList;

/**
 * Created by Admin on 22-02-2018.
 */

public class TagListAdapter extends RecyclerView.Adapter<TagListAdapter.MyViewHolder> {

    ArrayList<Topic_list> topic_lists;
    Context context;


    public TagListAdapter(Context context, ArrayList<Topic_list> topic_lists) {
        this.topic_lists = topic_lists;
        this.context = context;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_tag, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Topic_list topic_list = topic_lists.get(position);

        holder.TvTopics.setText(topic_list.getTopic_name());

    }

    @Override
    public int getItemCount() {
        return topic_lists.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView TvTopics;


        public MyViewHolder(View view) {
            super(view);

            TvTopics = view.findViewById(R.id.TvTopics);
            TvTopics.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            ExploreViewActivity.topicId=topic_lists.get(getAdapterPosition()).getTopic_id();
            switchTabInActivity(1,topic_lists.get(getAdapterPosition()).getTopic_id());


        }
    }

    public void switchTabInActivity(int indexTabToSwitchTo,  String topic_id) {

        ((ExploreViewActivity) context).switchTab(indexTabToSwitchTo,topic_id);

    }
}


