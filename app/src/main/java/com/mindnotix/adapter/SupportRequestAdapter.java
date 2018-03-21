package com.mindnotix.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mindnotix.model.youth_support.ShareYouth;
import com.mindnotix.youthhub.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 07-02-2018.
 */

public class SupportRequestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    ArrayList<ShareYouth> shareYouths;
    Context context;

    public SupportRequestAdapter(Context context, ArrayList<ShareYouth> shareYouths) {
        this.context = context;
        this.shareYouths = shareYouths;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_support_request, parent, false);
            return new GenericViewHolder(v);
    }

    private ShareYouth getItem(int position) {
        return shareYouths.get(position);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            ShareYouth currentItem =shareYouths.get(position);
            GenericViewHolder genericViewHolder = (GenericViewHolder) holder;
            try {

                genericViewHolder.tvTitleSupport.setText(currentItem.getShared_firstname()
                        .concat(" " + currentItem.getShared_lastname()));
                genericViewHolder.timestamp.setText(currentItem.getPosted_date());
                if (currentItem.getStatus().equals("Accepted")) {
                    genericViewHolder.tvStatus.setTextColor(Color.parseColor("#4CAF50"));
                    genericViewHolder.tvStatus.setText(currentItem.getStatus());
                } else if (currentItem.getStatus().equals("New")) {

                    genericViewHolder.tvStatus.setTextColor(Color.parseColor("#0a80d1"));
                    genericViewHolder.tvStatus.setText(currentItem.getStatus());
                } else {

                    genericViewHolder.tvStatus.setTextColor(Color.parseColor("#f2100c"));
                    genericViewHolder.tvStatus.setText(currentItem.getStatus());
                }


                Picasso.with(context)
                        .load(currentItem.getProfile_pic())
                        //this is optional the image to display while the url image is downloading
                        //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .into(genericViewHolder.profilePic);

            } catch (Exception e) {
                e.printStackTrace();
            }



    }




    @Override
    public int getItemCount() {
        return shareYouths.size();
    }


    class GenericViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitleSupport;
        TextView timestamp;
        CircleImageView profilePic;
        TextView tvStatus;

        public GenericViewHolder(View itemView) {
            super(itemView);
            this.tvTitleSupport = itemView.findViewById(R.id.tvTitleSupport);
            this.timestamp = itemView.findViewById(R.id.timestamp);
            this.profilePic = itemView.findViewById(R.id.profilePic);
            this.tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}