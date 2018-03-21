package com.mindnotix.adapter;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.codewaves.youtubethumbnailview.ImageLoader;
import com.codewaves.youtubethumbnailview.ThumbnailView;
import com.mindnotix.listener.RecyclerViewClickListener;

import com.mindnotix.model.profile.Gallerylist;
import com.mindnotix.youthhub.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Admin on 3/1/2018.
 */

public class ProfileGalleryFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int GALLERY_IMAGE = 1;
    public static final int GALLERY_YOUTUBE = 2;
    public static final int GALLERY_VIDEO = 3;
    private static final String TAG = ProfileGalleryFragmentAdapter.class.getSimpleName();
    ArrayList<Gallerylist> gallerylistArrayList;
    FragmentActivity activity;
    String image_medium;
    String video_image_medium;
    String video_medium;
    private RecyclerViewClickListener mListener;

    public ProfileGalleryFragmentAdapter(FragmentActivity activity, ArrayList<Gallerylist> gallerylistArrayList, RecyclerViewClickListener mListener) {
        this.gallerylistArrayList = gallerylistArrayList;
        this.activity = activity;
        this.mListener = mListener;
    }


    public ProfileGalleryFragmentAdapter(FragmentActivity activity, ArrayList<Gallerylist> gallerylistArrayList, RecyclerViewClickListener mListener, String image_medium, String video_image_medium, String video_medium) {
        this.gallerylistArrayList = gallerylistArrayList;
        this.activity = activity;
        this.mListener = mListener;
        this.image_medium = image_medium;
        this.video_image_medium = video_image_medium;
        this.video_medium = video_medium;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        Log.d(TAG, "onCreateViewHolder: viewType " + viewType);

        switch (viewType) {
            case GALLERY_IMAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_gallery_image_thumnail, parent, false);
                return new ImageViewHolder(view);
            case GALLERY_YOUTUBE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_row_layout, parent, false);
                return new YoutubeViewHolder(view);
            case GALLERY_VIDEO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_gallery_image_thumnail, parent, false);
                return new VideoImageViewHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        int Pg_Type;
        Pg_Type = Integer.parseInt(gallerylistArrayList.get(position).getPg_type());
        Log.d(TAG, "onBindViewHolder: usertype " + Pg_Type);
        bindSwitchCase(Pg_Type, holder, position);
    }

    private void bindSwitchCase(int pg_type, RecyclerView.ViewHolder holder, int position) {

        switch (pg_type) {

            case GALLERY_IMAGE:
                final ImageViewHolder imageViewHolder = (ImageViewHolder) holder;

                String temp = image_medium.concat(gallerylistArrayList.get(position).getPg_name());
                temp = temp.replaceAll(" ", "%20");
                Log.d(TAG, "bindSwitchCase: temp " + temp);
                Picasso.with(activity)
                        .load(temp)
                            //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .into(imageViewHolder.image, new Callback() {
                            @Override
                            public void onSuccess() {
                                imageViewHolder.imgJobLists_progress.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                imageViewHolder.imgJobLists_progress.setVisibility(View.GONE);
                            }
                        });

                break;

            case GALLERY_YOUTUBE:


                final YoutubeViewHolder youtubeViewHolder = (YoutubeViewHolder) holder;
                final String youtubeURL = gallerylistArrayList.get(position).getPg_name();

                Log.d(TAG, "onBindViewHolder: youtubeURL " + youtubeURL);

                youtubeViewHolder.thumb.loadThumbnail(youtubeURL, new ImageLoader() {
                    @Override
                    public Bitmap load(String url) throws IOException {
                        return Picasso.with(activity).load(url).get();
                    }
                });


                break;
            case GALLERY_VIDEO:
                final VideoImageViewHolder videoImageViewHolder = (VideoImageViewHolder) holder;


                String video_image_mediums = video_image_medium.concat(gallerylistArrayList.get(position).getPg_name());
                video_image_mediums = video_image_medium.replaceAll(" ", "%20");
                Log.d(TAG, "bindSwitchCase:video_image_mediums " + video_image_mediums);
                Picasso.with(activity)
                        .load(video_image_mediums)
                            //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .into(videoImageViewHolder.image, new Callback() {
                            @Override
                            public void onSuccess() {
                                videoImageViewHolder.imgJobLists_progress.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                videoImageViewHolder.imgJobLists_progress.setVisibility(View.GONE);
                            }
                        });


                break;
        }
    }

    @Override
    public int getItemCount() {
        return gallerylistArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (gallerylistArrayList != null) {
            Gallerylist object = gallerylistArrayList.get(position);
            if (object != null) {
                return Integer.parseInt(object.getPg_type());
            }
        }
        return 0;
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        ProgressBar imgJobLists_progress;

        public ImageViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            imgJobLists_progress = (ProgressBar) itemView.findViewById(R.id.imgJobLists_progress);
        }
    }

    private class YoutubeViewHolder extends RecyclerView.ViewHolder {
        ThumbnailView thumb;

        public YoutubeViewHolder(View itemView) {
            super(itemView);
            thumb = (ThumbnailView) itemView.findViewById(R.id.thumbnail);

        }
    }

    private class VideoImageViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        ProgressBar imgJobLists_progress;

        public VideoImageViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image);
            imgJobLists_progress = (ProgressBar) itemView.findViewById(R.id.imgJobLists_progress);

        }
    }
}
