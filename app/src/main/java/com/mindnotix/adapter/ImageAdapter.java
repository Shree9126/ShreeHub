package com.mindnotix.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.youthhub.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Admin on 1/29/2018.
 */


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.FileViewHolder> {

    public static final int SINGLE_IMAGE = 1;
    public static final int MULTI_IMAGE = 2;
    private static final String TAG = ImageAdapter.class.getSimpleName();
    private final ArrayList<String> paths;
    private final Context context;
    String Flag = "";
    private int imageSize;
    private RecyclerViewClickListener mListener;
    String image_path;

    public ImageAdapter(Context context, ArrayList<String> paths, RecyclerViewClickListener listener, String s, String path_thumb) {
        this.context = context;
        this.paths = paths;
        this.Flag = s;
        mListener = listener;
        image_path = path_thumb;
        setColumnNumber(context, 3);
    }

    private void setColumnNumber(Context context, int columnNum) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        imageSize = widthPixels / columnNum;
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

        return new FileViewHolder(view, mListener);


    }

    @Override
    public void onBindViewHolder(final FileViewHolder holder, int position) {

        if (Flag.equals("2")) {
            String path = image_path.concat(paths.get(position));
            Log.d(TAG, "onBindViewHolder:path " + path);
            Picasso.with(context)
                    .load(path)
                    .placeholder(R.drawable.no_preview)
                    .resize(imageSize,imageSize)
                    .into(holder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.imgJobLists_progress.setVisibility(View.GONE);
                            holder.imgClose.setVisibility(View.VISIBLE);

                        }

                        @Override
                        public void onError() {
                            holder.imgJobLists_progress.setVisibility(View.GONE);
                            holder.imgClose.setVisibility(View.VISIBLE);
                        }
                    });

        } else {
            String path = paths.get(position);
            Log.d(TAG, "onBindViewHolder:path " + path);
            Picasso.with(context)
                    .load(path)
                    .resize(imageSize,imageSize)
                    .placeholder(R.drawable.no_preview)
                    .into(holder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.imgJobLists_progress.setVisibility(View.GONE);
                            holder.imgClose.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError() {
                            holder.imgJobLists_progress.setVisibility(View.GONE);
                            holder.imgClose.setVisibility(View.VISIBLE);
                        }
                    });
        }

    }

    @Override
    public int getItemCount() {
        return paths.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (paths.size() == 1) {

            return super.getItemViewType(position);
        } else {

            return super.getItemViewType(position);
        }
    }

    public static class FileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView, imgClose;
        ProgressBar imgJobLists_progress;

        private RecyclerViewClickListener mListener;

        public FileViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            mListener = listener;
            imageView = itemView.findViewById(R.id.iv_photo);
            imgClose = itemView.findViewById(R.id.imgClose);
            imgJobLists_progress = itemView.findViewById(R.id.imgJobLists_progress);
            imgClose.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition());
        }
    }
}
