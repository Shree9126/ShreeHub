package com.mindnotix.adapter;

/**
 * Created by Admin on 2/8/2018.
 */


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.mindnotix.model.dashboard.Image;
import com.mindnotix.youthhub.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FullScreenImageAdapter extends PagerAdapter {

    private static final String TAG = FullScreenImageAdapter.class.getSimpleName();
    String largePath;
    private Activity _activity;
    private ArrayList<Image> _imagePaths;
    private LayoutInflater inflater;

    // constructor
    public FullScreenImageAdapter(Activity activity,
                                  ArrayList<Image> imagePaths, String largePath) {
        this._activity = activity;
        this._imagePaths = imagePaths;
        this.largePath = largePath;
    }

    @Override
    public int getCount() {
        return this._imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imgDisplay;
        ImageView btnClose;
        final ProgressBar imgJobLists_progress;

        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                false);

        imgDisplay = (ImageView) viewLayout.findViewById(R.id.imgDisplay);
        btnClose = (ImageView) viewLayout.findViewById(R.id.btnClose);
        imgJobLists_progress = viewLayout.findViewById(R.id.imgJobLists_progress);

        Log.d(TAG, "instantiateItem: String largePath " + largePath);
        Log.d(TAG, "instantiateItem: String largePath " + largePath.concat(_imagePaths.get(position).getPgName()));

       /* BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;


        Bitmap bitmap = BitmapFactory.decodeFile(largePath.concat(_imagePaths.get(position).getPgName()), options);
        imgDisplay.setImageBitmap(bitmap);*/


        Picasso.with(_activity)
                .load(largePath.concat(_imagePaths.get(position).getPgName()))
                //this is also optional if some error has occurred in downloading the image this image would be displayed

                .into(imgDisplay, new Callback() {
                    @Override
                    public void onSuccess() {
                        imgJobLists_progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        imgJobLists_progress.setVisibility(View.GONE);
                    }
                });


        // close button click event
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _activity.finish();
            }
        });

        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((FrameLayout) object);

    }
}