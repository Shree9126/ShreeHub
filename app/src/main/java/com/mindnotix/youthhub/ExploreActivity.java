package com.mindnotix.youthhub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.mindnotix.adapter.ViewPagerAdapter;
import com.mindnotix.model.explore.filter_master.Course_sortby;
import com.mindnotix.model.explore.filter_master.DataExploreFilterItems;
import com.mindnotix.model.explore.list.DataExploreListItems;
import com.mindnotix.model.explore.list.Explore_list;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sridharan on 2/19/2018.
 */

public class ExploreActivity extends BaseActivity implements View.OnClickListener {


    private static final String TAG = ExploreActivity.class.getSimpleName();
    private Toolbar toolbar;
    private LinearLayout linearSharedExplore;
    private View view1;
    private LinearLayout linearMyExplore;
    private View view2;
    private LinearLayout tabs;
    private ViewPager viewpager;
    private FloatingActionButton fab;

    TextView sharedExplorer;
    TextView myExplorer;

    ExploreShareExploreFragment exploreShareExploreFragment;
    ExploreMyExploreFragment exploreMyExploreFragment;

    protected ProgressDialog progressDialog;

    ArrayList<Course_sortby> course_sortby;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_explore);

        UiInitialization();

        setupViewPager(viewpager);

        sharedExplorer.setTextColor(getResources().getColor(R.color.medium_level_blue));
        myExplorer.setTextColor(getResources().getColor(R.color.black));


        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Log.d(TAG, "onPageSelected: " + position);
                switch (position) {
                    case 0:


                        sharedExplorer.setTextColor(getResources().getColor(R.color.medium_level_blue));
                        myExplorer.setTextColor(getResources().getColor(R.color.black));

                        viewpager.setCurrentItem(0);
                        view1.setVisibility(View.VISIBLE);
                        view2.setVisibility(View.INVISIBLE);

                        break;
                    case 1:
                        sharedExplorer.setTextColor(getResources().getColor(R.color.black));
                        myExplorer.setTextColor(getResources().getColor(R.color.medium_level_blue));

                        viewpager.setCurrentItem(1);
                        view1.setVisibility(View.INVISIBLE);
                        view2.setVisibility(View.VISIBLE);

                        break;


                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {

            if (data != null) {

            }


        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!nbIsNetworkAvailable(this)) {
            nbShowNoInternet(this);
        }

    }

    private void UiInitialization() {

        this.fab = findViewById(R.id.fab);
        this.fab.setOnClickListener(this);


        this.viewpager = findViewById(R.id.viewpager);
        this.tabs = findViewById(R.id.tabs);
        this.view2 = findViewById(R.id.view2);
        this.view2.setOnClickListener(this);
        this.linearMyExplore = findViewById(R.id.linearMyExplore);
        this.linearMyExplore.setOnClickListener(this);
        this.view1 = findViewById(R.id.view1);
        this.view1.setOnClickListener(this);
        this.linearSharedExplore = findViewById(R.id.linearSharedExplore);
        this.linearSharedExplore.setOnClickListener(this);
        this.toolbar = findViewById(R.id.toolbar);
        this.sharedExplorer = findViewById(R.id.sharedExplorer);
        this.myExplorer = findViewById(R.id.myExplorer);

        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("Explore");


        } catch (Exception e) {
            e.printStackTrace();
        }
        exploreFilterMaster();
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getSupportFragmentManager());

        exploreShareExploreFragment = new ExploreShareExploreFragment();
        exploreMyExploreFragment = new ExploreMyExploreFragment();

        adapter.addFragment(exploreShareExploreFragment, "Shared Explore");
        adapter.addFragment(exploreMyExploreFragment, "My Explore");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearSharedExplore:
                viewpager.setCurrentItem(0);
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.INVISIBLE);

                break;
            case R.id.linearMyExplore:
                viewpager.setCurrentItem(1);
                view1.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.VISIBLE);
                break;

            case R.id.fab:

                Intent mIntent = new Intent(ExploreActivity.this, ExploreFilterActivity.class);
                startActivityForResult(mIntent, 2);// Activity is started with requestCode 2
                break;
        }
    }


    public void exploreFilterMaster() {

       // progressDialog = Utils.createProgressDialog(this);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataExploreFilterItems> call = apiService.getExploreMasterFilter(Pref.getPreToken());

        call.enqueue(new Callback<DataExploreFilterItems>() {
            @Override
            public void onResponse(Call<DataExploreFilterItems> call, Response<DataExploreFilterItems> response) {

               // progressDialog.dismiss();

                try {
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body().getStatus().equals("1")) {

                            Pref.setdevicetoken("Bearer " + response.body().getToken());

                            course_sortby=new ArrayList<>();

                            course_sortby.addAll(response.body().getData().getCourse_sortby());

                            Course_sortby genders = new Course_sortby();
                            genders.setId("0");
                            genders.setName("All");
                            course_sortby.add(genders);


                            Collections.reverse(course_sortby);
                            exploreShareExploreFragment.loadFilterData(course_sortby);
                            exploreMyExploreFragment.loadFilterData(course_sortby);


                        } else {

                            Toast.makeText(ExploreActivity.this, "No record available", Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 304) {

                        Toast.makeText(ExploreActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(ExploreActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(ExploreActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(ExploreActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(ExploreActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(ExploreActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(ExploreActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<DataExploreFilterItems> call, Throwable t) {
              //  progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }





}
