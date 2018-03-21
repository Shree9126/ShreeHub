package com.mindnotix.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mindnotix.model.events.views.Photo_list;
import com.mindnotix.youthhub.FullScreenViewActivity;
import com.mindnotix.youthhub.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Admin on 2/22/2018.
 */

public class FragmentEventPhotoAdapter extends RecyclerView.Adapter<FragmentEventPhotoAdapter.MyViewHolder> {
    private static final String TAG = FragmentEventPhotoAdapter.class.getSimpleName();
    ArrayList<Photo_list> photo_listArrayList;
    Activity activity;

    public FragmentEventPhotoAdapter(ArrayList<Photo_list> photo_listArrayList, Activity activity) {
        this.photo_listArrayList = photo_listArrayList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        String image_path = photo_listArrayList.get(position).getImage_path();
        String filename = photo_listArrayList.get(position).getPhoto_name();
        Log.d(TAG, "onBindViewHolder:image path " + image_path.concat(filename));

        Picasso.with(activity)
                .load(image_path.concat(filename))
                .placeholder(R.drawable.no_preview) //this is optional the image to display while the url image is downloading
                .error(R.drawable.no_preview)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                .into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* Intent image4 = new Intent(activity, FullScreenViewActivity.class);
                image4.putExtra("position", position);
                image4.putExtra("largePath", photo_listArrayList.get(position).getImage_path());
                image4.putExtra("list", (Serializable) photo_listArrayList);
                activity.startActivity(image4);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return photo_listArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }


}