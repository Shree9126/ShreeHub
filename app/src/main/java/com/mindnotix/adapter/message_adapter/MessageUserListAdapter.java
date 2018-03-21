package com.mindnotix.adapter.message_adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.message.user_list.Chat_users_list;
import com.mindnotix.youthhub.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 3/14/2018.
 */

public class MessageUserListAdapter extends RecyclerView.Adapter<MessageUserListAdapter.MyViewHolder> {

    Activity activity;
    ArrayList<Chat_users_list> chatUsersListArrayList;
    String Image_path = "";
    private RecyclerViewClickListener mListener;


    public MessageUserListAdapter(Activity activity, ArrayList<Chat_users_list> chatUsersListArrayList, RecyclerViewClickListener mListener,
                                  String Image_path) {
        this.activity = activity;
        this.chatUsersListArrayList = chatUsersListArrayList;
        this.mListener = mListener;
        this.Image_path = Image_path;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message_user_list_fragment, parent, false);


        return new MyViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.txtLastMsg.setText(chatUsersListArrayList.get(position).getGroup_name());
        holder.txtUname.setText(chatUsersListArrayList.get(position).getFirst_name()
                .concat(" " + chatUsersListArrayList.get(position).getLast_name()));


        if (chatUsersListArrayList.get(position).getProfile_image() != null)
            Picasso.with(activity)
                    .load(Image_path.concat(chatUsersListArrayList.get(position).getProfile_image()))
                    .placeholder(R.drawable.profile_pic) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.profile_pic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(holder.profilePic);
    }

    @Override
    public int getItemCount() {
        return chatUsersListArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RecyclerViewClickListener mListener;
        private de.hdodenhof.circleimageview.CircleImageView profilePic;
        private android.widget.TextView txtUname;
        private android.widget.TextView txtLastMsg;


        public MyViewHolder(View itemView, RecyclerViewClickListener mListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.mListener = mListener;


            this.txtLastMsg = (TextView) itemView.findViewById(R.id.txtLastMsg);
            this.txtUname = (TextView) itemView.findViewById(R.id.txtUname);
            this.profilePic = (CircleImageView) itemView.findViewById(R.id.profilePic);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                default:
                    mListener.onClick(v,getAdapterPosition());
            }

        }
    }
}
