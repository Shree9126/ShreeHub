package com.mindnotix.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.codewaves.youtubethumbnailview.ThumbnailLoader;
import com.mindnotix.model.HandleFilterPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sridharan on 22-11-2017.
 */

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";
    public static List<Activity> activities = new ArrayList<>();
    public static SharedPreferences sharedpreferences;
    private static MyApplication mInstance;
    public static ArrayList<HandleFilterPojo> JobFilter ;
    public static HandleFilterPojo handleFilterPojo;
    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // MultiDex.install(this);
        ThumbnailLoader.initialize(getApplicationContext());
        sharedpreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        JobFilter = new ArrayList<>();
        handleFilterPojo=new HandleFilterPojo();
        handleFilterPojo.setJopCatagoreyPosition("0");
        handleFilterPojo.setEdtJobSearch("");
        handleFilterPojo.setChkfav(false);
        handleFilterPojo.setSpsubcategories("0");
        handleFilterPojo.setSpregion("0");
        handleFilterPojo.setSpdistrict("0");
        handleFilterPojo.setSpjobtype("0");
        handleFilterPojo.setSpfrom("0");
        handleFilterPojo.setSpsalary("0");
        handleFilterPojo.setSpto("0");
        handleFilterPojo.setSpsort("0");
        mInstance = this;


    }


}
