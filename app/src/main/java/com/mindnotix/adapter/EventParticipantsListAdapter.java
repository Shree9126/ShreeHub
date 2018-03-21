package com.mindnotix.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mindnotix.model.events.attend_list.Eventcountmelist;
import com.mindnotix.youthhub.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 08-03-2018.
 */

public class EventParticipantsListAdapter extends RecyclerView.Adapter<EventParticipantsListAdapter.MyViewHolder> {

    private static final String TAG = EventParticipantsListAdapter.class.getSimpleName();
    ArrayList<Eventcountmelist> eventcountmelistArrayList;
    FragmentActivity activity;
    String path_image;

    public EventParticipantsListAdapter(ArrayList<Eventcountmelist> eventcountmelistArrayList, FragmentActivity activity, String path_image) {
        this.eventcountmelistArrayList = eventcountmelistArrayList;
        this.activity = activity;
        this.path_image = path_image;
    }

    @Override
    public EventParticipantsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_participants, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EventParticipantsListAdapter.MyViewHolder holder, int position) {

        holder.txtUserName.setText(eventcountmelistArrayList.get(position).getFirst_name()
                .concat(" " + eventcountmelistArrayList.get(position).getLast_name()));

        holder.txtGroupName.setText(eventcountmelistArrayList.get(position).getGroup_name());
        Picasso.with(activity)
                .load(path_image.concat(eventcountmelistArrayList.get(position).getProfile_image()))
                //this is optional the image to display while the url image is downloading
                //this is also optional if some error has occurred in downloading the image this image would be displayed
                .into(holder.ivProfile, new Callback() {
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
        return eventcountmelistArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private de.hdodenhof.circleimageview.CircleImageView ivProfile;
        private android.widget.TextView txtUserName;
        private android.widget.TextView txtGroupName;

        private android.widget.ProgressBar imgJobLists_progress;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.imgJobLists_progress = (ProgressBar) itemView.findViewById(R.id.imgJobLists_progress);
            this.txtUserName = (TextView) itemView.findViewById(R.id.txtUserName);
            this.txtGroupName = (TextView) itemView.findViewById(R.id.txtGroupName);
            this.ivProfile = (CircleImageView) itemView.findViewById(R.id.ivProfile);
        }
    }
}
