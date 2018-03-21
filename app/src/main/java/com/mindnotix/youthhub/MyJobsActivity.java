package com.mindnotix.youthhub;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mindnotix.adapter.ViewPagerAdapter;
import com.mindnotix.model.BasicResponse;
import com.mindnotix.model.jobs.my_jobs.AppliedList;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 15-02-2018.
 */

public class MyJobsActivity extends AppCompatActivity {

    TextView appliedCount;
    TextView shortlistedCount;
    TextView selectedCount;
    TextView declinedCount;
    View appliedJobsTab;
    View appliedJobTabLine;
    View InviteJobsTab;
    View invitedJobsLine;
    View mainView;
    ViewPager viewPager;
    Toolbar toolbar;
    ArrayList<AppliedList> appliedLists;
    Call<BasicResponse> loadjobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_jobs);

        mainView =  findViewById(R.id.mainView);
        appliedCount = findViewById(R.id.appliedCount);
        shortlistedCount = findViewById(R.id.shortlistedCount);
        selectedCount = findViewById(R.id.selectedCount);
        declinedCount = findViewById(R.id.declinedCount);
        appliedJobsTab = findViewById(R.id.AppliedJobsTab);
        appliedJobTabLine = findViewById(R.id.appliedJobTabLine);
        InviteJobsTab = findViewById(R.id.InviteJobsTab);
        invitedJobsLine = findViewById(R.id.invitedJobsLine);
        viewPager = findViewById(R.id.viewPager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("My Jobs");

        } catch (Exception e) {
            e.printStackTrace();
        }
        setupViewPager(viewPager);

        viewPager.setCurrentItem(0);


        appliedJobsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewPager.setCurrentItem(0);
                appliedJobTabLine.setBackgroundColor(getResources().getColor(R.color.medium_level_blue));
                invitedJobsLine.setBackgroundColor(getResources().getColor(R.color.grey_500));

            }
        });

        InviteJobsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewPager.setCurrentItem(1);
                appliedJobTabLine.setBackgroundColor(getResources().getColor(R.color.grey_500));
                invitedJobsLine.setBackgroundColor(getResources().getColor(R.color.medium_level_blue));

            }
        });


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 1) {
                    appliedJobTabLine.setBackgroundColor(getResources().getColor(R.color.grey_500));
                    invitedJobsLine.setBackgroundColor(getResources().getColor(R.color.medium_level_blue));

                } else {
                    appliedJobTabLine.setBackgroundColor(getResources().getColor(R.color.medium_level_blue));
                    invitedJobsLine.setBackgroundColor(getResources().getColor(R.color.grey_500));
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        appliedLists = new ArrayList<>();

        loadMyJobs();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new AppliedListFragmet(), "AppliedList");
        adapter.addFragment(new InviteListFragment(), "inviteList");

        viewPager.setAdapter(adapter);
    }

    private void loadMyJobs() {


        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        loadjobs = apiService.loadMyJobs(Pref.getPreToken());

        loadjobs.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(@NonNull Call<BasicResponse> call, @NonNull Response<BasicResponse> response) {

                if (response.code() == 200) {

                    BasicResponse basicResponse = response.body();

                    if (basicResponse.getStatus().equals("1")) {

                        mainView.setVisibility(View.VISIBLE);

                        try {


                            if (basicResponse.getData().getApplied_list_count() != null &&
                                    !basicResponse.getData().getApplied_list_count().isEmpty()) {
                                appliedCount.setText(basicResponse.getData().getApplied_list_count());
                            } else {

                                appliedCount.setText("0");
                            }

                            if (basicResponse.getData().getShorlisted_count() != null && !
                                    basicResponse.getData().getShorlisted_count().isEmpty()) {
                                shortlistedCount.setText(basicResponse.getData().getShorlisted_count());
                            } else {

                                shortlistedCount.setText("0");
                            }

                            if (basicResponse.getData().getSelected_count() != null && !
                                    basicResponse.getData().getSelected_count().isEmpty()) {
                                selectedCount.setText(basicResponse.getData().getSelected_count());
                            } else {

                                selectedCount.setText("0");
                            }

                            if (basicResponse.getData().getDeclined_count() != null && !
                                    basicResponse.getData().getDeclined_count().isEmpty()) {
                                declinedCount.setText(basicResponse.getData().getDeclined_count());
                            } else {

                                declinedCount.setText("0");
                            }


//                            if (!basicResponse.getData().getApplied_list_count().equals("0")
//                                    && !basicResponse.getData().getApplied_list_count().isEmpty()) {
//
//                                appliedLists.addAll(basicResponse.getData().getApplied_list());
//
//
//
//                            }
//
//                            if (!basicResponse.getData().getApplied_list_count().equals("0")
//                                    && !basicResponse.getData().getApplied_list_count().isEmpty()) {
//
//                                appliedLists.addAll(basicResponse.getData().getApplied_list());
//
//                            }
//

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        mainView.setVisibility(View.GONE);

                        Toast.makeText(MyJobsActivity.this, basicResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else if (response.code() == 201) {
                    mainView.setVisibility(View.GONE);

                    Toast.makeText(MyJobsActivity.this, "201 Created", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 304) {
                    mainView.setVisibility(View.GONE);

                    Toast.makeText(MyJobsActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 400) {
                    mainView.setVisibility(View.GONE);

                    Toast.makeText(MyJobsActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 401) {
                    mainView.setVisibility(View.GONE);

                    Toast.makeText(MyJobsActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 403) {

                    Toast.makeText(MyJobsActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 404) {
                    mainView.setVisibility(View.GONE);

                    Toast.makeText(MyJobsActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();


                } else if (response.code() == 422) {
                    mainView.setVisibility(View.GONE);

                    Toast.makeText(MyJobsActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {
                    mainView.setVisibility(View.GONE);
                    Toast.makeText(MyJobsActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();


                }

            }

            @Override
            public void onFailure(@NonNull Call<BasicResponse> call, @NonNull Throwable t) {
                mainView.setVisibility(View.GONE);
                t.printStackTrace();


            }
        });


    }


}