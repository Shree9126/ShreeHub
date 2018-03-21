package com.mindnotix.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.dashboard.like_comments_contribute.Tweaks;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.youthhub.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sridharan on 1/24/2018.
 */

public class PostCommentsAdapter extends RecyclerView.Adapter<PostCommentsAdapter.MyViewHolder> {
    Activity activity;
    ArrayList<Tweaks> tweaksArrayList;
    private RecyclerViewClickListener mListener;

    public PostCommentsAdapter(Activity activity, ArrayList<Tweaks> tweaksArrayList, RecyclerViewClickListener listener) {
        this.activity = activity;
        this.tweaksArrayList = tweaksArrayList;
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

        holder.txtComments.setText(tweaksArrayList.get(position).getPosts().getPf_message());
        String firtname = tweaksArrayList.get(position).getPost_user().getFirst_name();
        String lastname = tweaksArrayList.get(position).getPost_user().getLast_name();
        holder.txtUserName.setText(firtname.concat(" " + lastname));
        holder.txtComments.setText(tweaksArrayList.get(position).getPosts().getPf_message());
        holder.txtDate.setText(tweaksArrayList.get(position).getPosts().getPf_created_on());

        if (tweaksArrayList.get(position).getPosts().getPf_user_id().equals(Pref.getClientId()))
            holder.imgDelete.setVisibility(View.VISIBLE);
        else
            holder.imgDelete.setVisibility(View.INVISIBLE);


        if (tweaksArrayList.get(position).getPost_user().getProfile_pic() != null)
            Picasso.with(activity)
                    .load(Constant.BASE_URL_PROFILE_THUMBNAIL.concat(tweaksArrayList.get(position).getPost_user().getProfile_pic()))
                    .placeholder(R.drawable.profile_pic) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.profile_pic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(holder.ivProfile);


    }

    @Override
    public int getItemCount() {
        return tweaksArrayList.size();
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