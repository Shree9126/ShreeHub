package com.mindnotix.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codewaves.youtubethumbnailview.ImageLoader;
import com.codewaves.youtubethumbnailview.ThumbnailView;
import com.mindnotix.model.explore.list.Explore_list;
import com.mindnotix.youthhub.ExploreShareExploreFragment;
import com.mindnotix.youthhub.ExploreViewActivity;
import com.mindnotix.youthhub.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Admin on 2/20/2018.
 */

public class ExploreListAdapter extends RecyclerView.Adapter<ExploreListAdapter.MyViewHolder> {

    ArrayList<Explore_list> explore_listArrayList;
    Context context;


    public ExploreListAdapter(Context context, ArrayList<Explore_list> explore_listArrayList) {
        this.explore_listArrayList = explore_listArrayList;
        this.context = context;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_explore, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        Explore_list explore_list = explore_listArrayList.get(position);

        holder.exploreId.setText(explore_list.getTitle());
        holder.exploreDescription.setText(explore_list.getDescription());
        holder.exploreDate.setText(explore_list.getUpdate_on());
        holder.explorePostedBy.setText(explore_list.getPost_by_firstname() + " " + explore_list.getPost_by_lastname());
        holder.exploreRating.setText(explore_list.getRating_name());

        Log.v("hhbbhjhhh",explore_list.getMedia().get(0).getContent());


        if (explore_list.getMedia().get(0).getContent_type().equals("2")) {


            Picasso.with(context).
                    load(ExploreShareExploreFragment.path_image + explore_list.getMedia().get(0).getContent())
                    .into(holder.imageView);

            holder.thumbnail.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.VISIBLE);
            holder.noImage.setVisibility(View.GONE);



        } else if (explore_list.getMedia().get(0).getContent_type().equals("3")) {

            holder.thumbnail.loadThumbnail(explore_list.getMedia().get(0).getContent(), new ImageLoader() {
                @Override
                public Bitmap load(String url) throws IOException {
                    return Picasso.with(context).load(url).get();
                }
            });

            holder.thumbnail.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.GONE);
            holder.noImage.setVisibility(View.GONE);


        } else{

            holder.thumbnail.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.GONE);
            holder.noImage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return explore_listArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView exploreId;
        TextView exploreSecondaryName;
        TextView exploreStatus;
        TextView explorenName;
        TextView exploreRating;
        TextView exploreDate;
        TextView exploreDescription;
        Button btViewMore;
        TextView explorePostedBy;
        ImageView imageView;
        ThumbnailView thumbnail;
        View noImage;

        public MyViewHolder(View view) {
            super(view);

            exploreId = view.findViewById(R.id.exploreId);
            exploreDate = view.findViewById(R.id.exploreDate);
            btViewMore = view.findViewById(R.id.btViewMore);
            exploreDescription = view.findViewById(R.id.exploreDescription);
            explorePostedBy = view.findViewById(R.id.explorePostedBy);
            exploreRating = view.findViewById(R.id.exploreRating);
            imageView = view.findViewById(R.id.imageView);
            thumbnail = view.findViewById(R.id.thumbnail);
            noImage = view.findViewById(R.id.noImage);

            btViewMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ExploreViewActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("explorer_id", explore_listArrayList.get(getAdapterPosition()).getPost_id());
                    intent.putExtra("ismyexplore", explore_listArrayList.get(getAdapterPosition()).getIsmyexplore());
                    context.startActivity(intent);

                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}

