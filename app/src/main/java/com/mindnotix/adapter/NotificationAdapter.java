package com.mindnotix.adapter;

import android.app.Activity;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.notification.Notifications;
import com.mindnotix.youthhub.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 3/6/2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private static final String TAG = NotificationAdapter.class.getSimpleName();
    ArrayList<Notifications> notifications;
    Activity activity;
    private RecyclerViewClickListener mListener;

    public NotificationAdapter(ArrayList<Notifications> notifications, Activity activity, RecyclerViewClickListener mListener) {
        this.notifications = notifications;
        this.activity = activity;
        this.mListener = mListener;
        Log.d(TAG, "NotificationAdapter:s " + notifications.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);


        return new MyViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {


        if (!notifications.get(position).getProfile_pic().equals("") &&
                notifications.get(position).getProfile_pic() != null)
            Picasso.with(activity).
                    load(notifications.get(position).getProfile_pic())
                    .into(holder.ivProfile, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.imgJobListsprogress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            holder.imgJobListsprogress.setVisibility(View.GONE);
                        }
                    });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.txtMessage.setText(Html.fromHtml(notifications.get(position).getMessage(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.txtMessage.setText(Html.fromHtml(notifications.get(position).getMessage()));
        }

        holder.timestamp.setText(notifications.get(position).getNm_date());


    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RecyclerViewClickListener mListener;

        private CircleImageView ivProfile;
        private ProgressBar imgJobListsprogress;
        private TextView txtMessage;
        private TextView timestamp;

        public MyViewHolder(View itemView, RecyclerViewClickListener mListener) {
            super(itemView);
            this.mListener = mListener;

            this.timestamp = (TextView) itemView.findViewById(R.id.timestamp);
            this.txtMessage = (TextView) itemView.findViewById(R.id.txtMessage);
            this.imgJobListsprogress = (ProgressBar) itemView.findViewById(R.id.imgJobLists_progress);
            this.ivProfile = (CircleImageView) itemView.findViewById(R.id.ivProfile);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
