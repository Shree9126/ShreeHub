package com.mindnotix.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mindnotix.model.dashboard.like_comments_contribute.Tweaks;
import com.mindnotix.utils.Constant;
import com.mindnotix.youthhub.PostLikeActivity;
import com.mindnotix.youthhub.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sridharan on 2/5/2018.
 */

public class PostLikeAdapter extends RecyclerView.Adapter<PostLikeAdapter.MyViewHolder> {
    PostLikeActivity postLikeActivity;
    ArrayList<Tweaks> tweaksArrayList;

    public PostLikeAdapter(PostLikeActivity postLikeActivity, ArrayList<Tweaks> tweaksArrayList) {
        this.postLikeActivity = postLikeActivity;
        this.tweaksArrayList = tweaksArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_like_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.txtUserName.setText(tweaksArrayList.get(position).getPost_user().getFirst_name()
                .concat(" " + tweaksArrayList.get(position).getPost_user().getLast_name()));

        if (tweaksArrayList.get(position).getPost_user().getProfile_pic() != null)
            Picasso.with(postLikeActivity)
                    .load(Constant.BASE_URL_PROFILE_THUMBNAIL.concat(tweaksArrayList.get(position).getPost_user().getProfile_pic()))
                    .placeholder(R.drawable.profile_pic) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.profile_pic)         //this is also optional if some error has occurred in downloading the image this image would be displayed

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

        holder.timestamp.setText(tweaksArrayList.get(position).getPosts().getPf_created_on());

    }

    @Override
    public int getItemCount() {
        return tweaksArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private de.hdodenhof.circleimageview.CircleImageView ivProfile;
        private android.widget.TextView txtUserName;
        private android.widget.TextView timestamp;
        private android.widget.ProgressBar imgJobLists_progress;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txtUserName = (TextView) itemView.findViewById(R.id.txtUserName);
            this.timestamp = (TextView) itemView.findViewById(R.id.timestamp);
            this.ivProfile = (CircleImageView) itemView.findViewById(R.id.ivProfile);
            this.imgJobLists_progress =  itemView.findViewById(R.id.imgJobLists_progress);
        }
    }
}
