package com.mindnotix.youthhub;

/**
 * Created by Admin on 2/8/2018.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.mindnotix.adapter.FullScreenImageAdapter;
import com.mindnotix.model.dashboard.Image;
import com.mindnotix.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class FullScreenViewActivity extends BaseActivity{

    private Utils utils;
    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;
    ArrayList<Image> imageList;
    String largePath;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_view);


        if(getIntent() != null){
            Intent i = getIntent();

             position = i.getIntExtra("position", 0);

             Log.v("rrnnrjvnrnvirv"," "+position);

             imageList = (ArrayList<Image>) i.getSerializableExtra("list");
            largePath = i.getStringExtra("largePath");
        }

        viewPager = (ViewPager) findViewById(R.id.pager);

        utils = new Utils(this);


        adapter = new FullScreenImageAdapter(FullScreenViewActivity.this,
                imageList,largePath);

        viewPager.setAdapter(adapter);

        // displaying selected image first
        viewPager.setCurrentItem(position);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!nbIsNetworkAvailable(this)){
            nbShowNoInternet(this);
        }
    }
}
