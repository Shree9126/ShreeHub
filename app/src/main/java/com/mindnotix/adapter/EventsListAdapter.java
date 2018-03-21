package com.mindnotix.adapter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mindnotix.model.events.list.Event_list;
import com.mindnotix.youthhub.EventDetailsActivity;
import com.mindnotix.youthhub.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Admin on 2/16/2018.
 */

public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.MyViewHolder> {

    private static final String TAG = EventsListAdapter.class.getSimpleName();
    ArrayList<Event_list> event_lists;
    FragmentActivity activity;
    String path_image;

    public EventsListAdapter(FragmentActivity activity, ArrayList<Event_list> event_listArrayList, String path_image) {
        this.activity = activity;
        this.event_lists = event_listArrayList;
        this.path_image = path_image;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items_events, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.txtEventTitle.setText(event_lists.get(position).getTitle());
        holder.txtEventAddress.setText(event_lists.get(position).getLocal_board_name().concat(" " +
                event_lists.get(position).getReginal_name()));

        if(!event_lists.get(position).getTotal_person_attending_event().equals("0")){
            holder.txtEventAttending.setText(event_lists.get(position).getTotal_person_attending_event().concat(" Attending"));
            holder.txtEventAttending.setVisibility(View.VISIBLE);
        }else{
            holder.txtEventAttending.setVisibility(View.GONE);
        }


        String start_date = event_lists.get(position).getStart_date();
        String end_date = event_lists.get(position).getEnd_date();
        String start_time = event_lists.get(position).getStart_time();
        String end_time = event_lists.get(position).getEnd_time();

        //String combile_string = start_time.concat(" - " + end_time).concat(" \n" + start_date).concat(("-" + end_date));
        String combile_string = start_date.concat(" - " + end_date).concat(" \n" + start_time).concat(("-" + end_time));
        //String combile_string = start_time.concat(" - " + end_time).concat(" " + start_date).concat(" " + end_date);
        holder.txtEventDateAndTime.setText(combile_string);

        holder.txtPostedDateOn.setText(String.format("Posted on %s ", event_lists.get(position).getPost_date()));
        holder.txtOrganishBy.setText(String.format("Organised by %s", event_lists.get(position).getPost_by_firstname()));

        Log.d(TAG, "onCreateView: profile path = " + path_image.concat(event_lists.get(position).getExplore_logo()));
        Picasso.with(activity)
                .load(path_image.concat(event_lists.get(position).getExplore_logo()))
               //this is optional the image to display while the url image is downloading
                        //this is also optional if some error has occurred in downloading the image this image would be displayed
                .into(holder.imgEvents, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.imgJobLists_progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        holder.imgJobLists_progress.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return event_lists.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private android.widget.ImageView imgEvents;
        private android.widget.TextView txtEventTitle;
        private android.widget.TextView txtMoreDetails;
        private android.widget.TextView txtEventDateAndTime;
        private android.widget.TextView txtEventAddress;
        private android.widget.TextView txtEventAttending;
        private android.widget.TextView txtOrganishBy;
        private android.widget.TextView txtPostedDateOn;
        private android.widget.ProgressBar imgJobLists_progress;


        public MyViewHolder(View view) {
            super(view);

            view.setOnClickListener(this);

            this.txtPostedDateOn = (TextView) view.findViewById(R.id.txtPostedDateOn);
            this.imgJobLists_progress = (ProgressBar) view.findViewById(R.id.imgJobLists_progress);
            this.txtOrganishBy = (TextView) view.findViewById(R.id.txtOrganishBy);
            this.txtEventAttending = (TextView) view.findViewById(R.id.txtEventAttending);
            this.txtEventAddress = (TextView) view.findViewById(R.id.txtEventAddress);
            this.txtEventDateAndTime = (TextView) view.findViewById(R.id.txtEventDateAndTime);
            this.txtMoreDetails = (TextView) view.findViewById(R.id.txtMoreDetails);
            this.txtEventTitle = (TextView) view.findViewById(R.id.txtEventTitle);
            this.imgEvents = (ImageView) view.findViewById(R.id.imgEvents);
        }

        @Override
        public void onClick(View v) {

            Intent mIntent = new Intent(activity, EventDetailsActivity.class);
            mIntent.putExtra("event_id", event_lists.get(getAdapterPosition()).getEvent_id());
            activity.startActivity(mIntent);
        }
    }
}
