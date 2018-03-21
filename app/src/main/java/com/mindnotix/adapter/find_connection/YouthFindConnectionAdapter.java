package com.mindnotix.adapter.find_connection;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.find_connection.list.Userinfo;
import com.mindnotix.youthhub.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sridharan on 3/2/2018.
 */

public class YouthFindConnectionAdapter extends RecyclerView.Adapter<YouthFindConnectionAdapter.MyViewHolder> {

    private static final String TAG = YouthFindConnectionAdapter.class.getSimpleName();
    ArrayList<Userinfo> youthUserinfoArrayList;
    Activity activity;
    private RecyclerViewClickListener mListener;


    public YouthFindConnectionAdapter(ArrayList<Userinfo> youthUserinfoArrayList, Activity activity, RecyclerViewClickListener mListener) {
        this.youthUserinfoArrayList = youthUserinfoArrayList;
        this.activity = activity;
        this.mListener = mListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_find_connect_youth, parent, false);

        return new MyViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.tvName.setText(youthUserinfoArrayList.get(position).getName());
        holder.txtTags.setText(youthUserinfoArrayList.get(position).getTags());
        holder.txtRegion.setText(youthUserinfoArrayList.get(position).getSubtype());
        holder.tvWorker.setText(youthUserinfoArrayList.get(position).getGroup_name());


        if (!youthUserinfoArrayList.get(position).getType().equals(""))
            holder.txtType.setText(youthUserinfoArrayList.get(position).getType());
        else
            holder.txtType.setVisibility(View.GONE);


        if (youthUserinfoArrayList.get(position).getGroup_id().equals("9"))
            holder.linearShare.setVisibility(View.VISIBLE);

        if (youthUserinfoArrayList.get(position).getAbout_me() != null &&
                !youthUserinfoArrayList.get(position).equals("")) {
            holder.txtAbout.setVisibility(View.VISIBLE);
            holder.txtAbout.setText(youthUserinfoArrayList.get(position).getAbout_me());
        } else {
            holder.txtAbout.setVisibility(View.GONE);
        }

        Log.d(TAG, "onBindViewHolder: is shared  " + youthUserinfoArrayList.get(position).getIsshareprofile());
        if (youthUserinfoArrayList.get(position).getIsshareprofile().equals("1"))
            holder.txtShare.setText("Shared");
        else
            holder.txtShare.setText("Share");


        if (youthUserinfoArrayList.get(position).getIsfollowing().equals("1")) {
            holder.ViewUpload.setBackground(activity.getResources().getDrawable(R.drawable.follow));
        } else {
            holder.ViewUpload.setBackground(activity.getResources().getDrawable(R.drawable.unfollow));
        }

        if (!youthUserinfoArrayList.get(position).getProfile_pic_url().equals("") &&
                youthUserinfoArrayList.get(position).getProfile_pic_url() != null) {

            holder.circleImageView.setVisibility(View.VISIBLE);
            holder.imgProfileImage.setVisibility(View.GONE);
            Picasso.with(activity)
                    .load(youthUserinfoArrayList.get(position).getProfile_pic_url())
                    //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(holder.circleImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.imgProfileImageProgress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            holder.imgProfileImageProgress.setVisibility(View.GONE);
                        }
                    });
        } else {
            holder.circleImageView.setVisibility(View.GONE);
            holder.imgProfileImage.setVisibility(View.VISIBLE);


            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(youthUserinfoArrayList.get(position).getName_code(), color);

            holder.imgProfileImage.setImageDrawable(drawable);


        }


    }

    @Override
    public int getItemCount() {
        return youthUserinfoArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RecyclerViewClickListener mListener;
        CircleImageView circleImageView;
        private ImageView imgProfileImage;
        private ProgressBar imgProfileImageProgress;
        private View removeProfilePicture;
        private View ViewUpload;
        private View linearShare;
        private TextView tvName;
        private TextView txtType;
        private TextView txtAbout;
        private TextView tvWorker;
        private TextView txtRegion;
        private TextView txtShare;
        private TextView txtTags;

        public MyViewHolder(View itemView, RecyclerViewClickListener mListener) {
            super(itemView);
            this.mListener = mListener;

            this.txtTags = (TextView) itemView.findViewById(R.id.txtTags);
            this.txtRegion = (TextView) itemView.findViewById(R.id.txtRegion);
            this.txtShare = (TextView) itemView.findViewById(R.id.txtShare);
            this.txtShare.setOnClickListener(this);
            this.tvWorker = (TextView) itemView.findViewById(R.id.tvWorker);
            this.tvName = (TextView) itemView.findViewById(R.id.tvName);
            this.ViewUpload = (View) itemView.findViewById(R.id.ViewUpload);
            this.ViewUpload.setOnClickListener(this);
            this.linearShare = (View) itemView.findViewById(R.id.linearShare);
            this.txtAbout = (TextView) itemView.findViewById(R.id.txtAbout);
            this.txtType = (TextView) itemView.findViewById(R.id.txtType);
            this.removeProfilePicture = (View) itemView.findViewById(R.id.removeProfilePicture);
            this.removeProfilePicture.setOnClickListener(this);
            this.imgProfileImageProgress = (ProgressBar) itemView.findViewById(R.id.imgProfileImageProgress);
            this.imgProfileImage = (ImageView) itemView.findViewById(R.id.imgProfileImage_txt);
            this.circleImageView = (CircleImageView) itemView.findViewById(R.id.imgProfileImage);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.removeProfilePicture:
                    mListener.onClick(v, getAdapterPosition());
                    break;

                case R.id.txtShare:
                    mListener.onClick(v, getAdapterPosition());
                    break;

                case R.id.ViewUpload:
                    mListener.onClick(v, getAdapterPosition());
                    break;
            }
        }
    }

}
