package com.mindnotix.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.events.discussion_board.list.Event_chat_list;
import com.mindnotix.utils.Pref;
import com.mindnotix.youthhub.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 2/17/2018.
 */

public class EventDiscussionBoardAdapter extends RecyclerView.Adapter<EventDiscussionBoardAdapter.MyViewHolder> {
    private static final String TAG = EventDiscussionBoardAdapter.class.getSimpleName();
    Activity activity;
    ArrayList<Event_chat_list> event_chat_listArrayList;
    private RecyclerViewClickListener mListener;

    public EventDiscussionBoardAdapter(Activity activity, ArrayList<Event_chat_list> tweaksArrayList, RecyclerViewClickListener listener) {
        this.activity = activity;
        this.event_chat_listArrayList = tweaksArrayList;
        mListener = listener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_items, parent, false);


        return new MyViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.txtComments.setText(event_chat_listArrayList.get(position).getMessage());
        String firtname = event_chat_listArrayList.get(position).getFirst_name();
        String lastname = event_chat_listArrayList.get(position).getLast_name();
        holder.txtUserName.setText(firtname.concat(" " + lastname));

        holder.txtDate.setText(event_chat_listArrayList.get(position).getDate().concat(" " + event_chat_listArrayList.get(position).getTime()));

        if (event_chat_listArrayList.get(position).getUser_id().equals(Pref.getClientId()))
            holder.imgDelete.setVisibility(View.VISIBLE);
        else
            holder.imgDelete.setVisibility(View.INVISIBLE);


        Log.d(TAG, "onBindViewHolder: " + event_chat_listArrayList.get(position).getProfile_image());

        if (event_chat_listArrayList.get(position).getProfile_image() != null)
            Picasso.with(activity)
                    .load("https://images.youthhub.co.nz/profile/thumbnail/".concat(event_chat_listArrayList.get(position).getProfile_image()))
                    .placeholder(R.drawable.profile_pic) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.profile_pic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(holder.ivProfile);

    }

    @Override
    public int getItemCount() {
        return event_chat_listArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private de.hdodenhof.circleimageview.CircleImageView ivProfile;
        private android.widget.TextView txtUserName;
        private android.widget.TextView txtDate;
        private android.widget.TextView txtComments;
        private android.widget.RelativeLayout rlParent;
        private android.widget.ImageView imgDelete;
        private RecyclerViewClickListener mListener;

        public MyViewHolder(View itemView, RecyclerViewClickListener mListener) {
            super(itemView);
            this.mListener = mListener;
            this.rlParent = (RelativeLayout) itemView.findViewById(R.id.rlParent);
            this.txtComments = (TextView) itemView.findViewById(R.id.txtComments);
            this.txtUserName = (TextView) itemView.findViewById(R.id.txtUserName);
            this.txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            this.imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            this.imgDelete.setOnClickListener(this);
            this.ivProfile = (CircleImageView) itemView.findViewById(R.id.ivProfile);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgDelete:
                    mListener.onClick(v, getAdapterPosition());
                    break;
            }
        }
    }
}