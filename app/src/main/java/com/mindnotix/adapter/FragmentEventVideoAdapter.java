package com.mindnotix.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codewaves.youtubethumbnailview.ImageLoader;
import com.codewaves.youtubethumbnailview.ThumbnailView;
import com.mindnotix.model.events.views.Video_list;
import com.mindnotix.youthhub.R;
import com.mindnotix.youthhub.YoutubeVideoActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Admin on 2/22/2018.
 */

public class FragmentEventVideoAdapter extends RecyclerView.Adapter<FragmentEventVideoAdapter.MyViewHolder> {

    private static final String TAG = FragmentEventVideoAdapter.class.getSimpleName();
    ArrayList<Video_list> videoListArrayList;
    Activity activity;

    public FragmentEventVideoAdapter(ArrayList<Video_list> videoListArrayList, Activity activity) {
        this.videoListArrayList = videoListArrayList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_row_layout, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final String youtubeURL = videoListArrayList.get(position).getVideo_url();

        Log.d(TAG, "onBindViewHolder: youtubeURL " + youtubeURL);

        holder.thumb.loadThumbnail(youtubeURL, new ImageLoader() {
            @Override
            public Bitmap load(String url) throws IOException {
                return Picasso.with(activity).load(url).get();
            }
        });

        holder.thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeURL)));
                Log.i("Video", "Video Playing....");
                Intent intent = new Intent(activity, YoutubeVideoActivity.class);
                intent.putExtra("youtube_url", videoListArrayList.get(position).getVideo_url());
                activity.startActivity(intent);

            }
        });

        /*holder.thumb.loadThumbnail("https://www.youtube.com/watch?v=iCkYw3cRwLo", new ThumbnailLoadingListener() {
            @Override
            public void onLoadingStarted(@NonNull String url, @NonNull View view) {
                Log.i(TAG, "Thumbnail load started.");
            }

            @Override
            public void onLoadingComplete(@NonNull String url, @NonNull View view) {
                Log.i(TAG, "Thumbnail load finished.");
            }

            @Override
            public void onLoadingCanceled(@NonNull String url, @NonNull View view) {
                Log.i(TAG, "Thumbnail load canceled.");
            }

            @Override
            public void onLoadingFailed(@NonNull String url, @NonNull View view, Throwable error) {
                Log.e(TAG, "Thumbnail load failed. " + error.getMessage());
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return videoListArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        final ThumbnailView thumb;
        private android.widget.ImageView image;
        private android.widget.TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.name);
            thumb = (ThumbnailView) itemView.findViewById(R.id.thumbnail);
        }
    }
}
