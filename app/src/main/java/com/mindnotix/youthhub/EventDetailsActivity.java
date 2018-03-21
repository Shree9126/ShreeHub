package com.mindnotix.youthhub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.adapter.ViewPagerAdapter;
import com.mindnotix.listener.EventsInterface;
import com.mindnotix.model.events.count_me_in.DataCountMeInOutItems;
import com.mindnotix.model.events.views.DataEventViewDetailsItem;
import com.mindnotix.model.events.views.Photo_list;
import com.mindnotix.model.events.views.Video_list;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailsActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = EventDetailsActivity.class.getSimpleName();
    public static String description;
    public static String address;
    public static String region;
    public static String district;
    public static String contact;
    protected TabLayout tabLayout;
    ProgressDialog progressDialog;
    String event_id;
    EventsInterface eventsInterface;
    ArrayList<Photo_list> photoListArrayList = new ArrayList<>();
    ArrayList<Video_list> videoListArrayList = new ArrayList<>();
    private android.widget.RelativeLayout viewNavClick;
    private android.widget.RelativeLayout logo;
    private android.support.design.widget.AppBarLayout dummyAppbar;
    private android.widget.ProgressBar progressBar;
    private android.support.v7.widget.Toolbar toolbar;
    private android.support.design.widget.CollapsingToolbarLayout collapsingtoolbar;
    private android.support.design.widget.AppBarLayout appbar;
    private ViewPager viewpager;
    private android.support.design.widget.CoordinatorLayout maincontent;
    private android.widget.ImageButton left;
    private TabLayout tabs;
    private android.widget.ImageButton right;
    private android.widget.ImageView imgEvents;
    private android.widget.TextView txtEventTitle;
    private android.widget.TextView txtEventMobileNumber;
    private android.widget.TextView txtCountMeIn;
    private android.widget.TextView txtEventDateAndTime;
    private android.widget.TextView txtEventAddress;
    private android.widget.TextView txtEventAttending;
    private android.widget.TextView txtOrganishBy;
    private android.widget.TextView txtPostedDateOn;
    private FloatingActionButton fab;
    private String lattitude;
    private String longtitude;
    private String companyName;
    private ArrayList<String> fragInstance;
    private String CountMeStatus = "";
    private String attend_count = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        fragInstance = new ArrayList<>();
        UiInitialization();

        if (getIntent() != null) {
            event_id = getIntent().getStringExtra("event_id");
        }

    }


    public void setFragInstance(String instance) {
        Log.d("run", "setFragInstance: ");
        fragInstance.add(instance);
        getEventDetails();
    }

    private void getEventDetails() {
        //    progressDialog = Utils.createProgressDialog(this);
        //String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTU3IiwidXNlcm5hbWUiOiJzcmlkaGFyYW4ubWluZG5vdGl4QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTg2MDkwNTAsImp0aSI6IlFYVkdUVEJ3V0dOMlNGWjBkVWxVV1hKT2JEWT0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE4NjA5MDUwLCJleHAiOjE1MTkyMTM4NTB9.-1YP2zxdrvMmlc2mfH6CXyCUxdqRObTFI6GSUByJ84I";

        String token = Pref.getPreToken();

        photoListArrayList.clear();
        videoListArrayList.clear();
        Map<String, String> data = new HashMap<>();
        data.put("eventid", event_id);
        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


        //token = "Bearer "+ Pref.getPreToken();


        Log.d(TAG, "changePassword: token " + token);

        Call<DataEventViewDetailsItem> call = apiService.getEventDetialsData(token, data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataEventViewDetailsItem>() {
            @Override
            public void onResponse(Call<DataEventViewDetailsItem> call, Response<DataEventViewDetailsItem> response) {

                //  progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("1")) {

                        Log.d(TAG, "onResponse:DataJIbDetailsView " + new Gson().toJson(response.body()));

                        description = response.body().getData().getEventview().get(0).getDescription();
                        address = response.body().getData().getEventview().get(0).getEvent_address();
                        region = response.body().getData().getEventview().get(0).getReginal_name();
                        district = response.body().getData().getEventview().get(0).getLocal_board_name();
                        contact = response.body().getData().getEventview().get(0).getEvent_contact_no();

                        longtitude = response.body().getData().getEventview().get(0).getEvent_longitude();
                        lattitude = response.body().getData().getEventview().get(0).getEvent_latitude();
                        companyName = response.body().getData().getEventview().get(0).getTitle();

                        if (!response.body().getData().getEventview().get(0).getEvent_contact_no().equals("")
                                && response.body().getData().getEventview().get(0).getEvent_contact_no() != null) {
                            txtEventMobileNumber.setVisibility(View.VISIBLE);
                            txtEventMobileNumber.setText(response.body().getData().getEventview().get(0).getEvent_contact_no());
                        }

                        String path_image = response.body().getData().getEventview().get(0).getImage_path();
                        txtEventTitle.setText(response.body().getData().getEventview().get(0).getTitle());
                        txtEventAddress.setText(region.concat(" " + district));
                        Log.d(TAG, "onResponse: attending - " + response.body().getData().getEventview().get(0).getTotal_person_attending_event());

                        attend_count = response.body().getData().getEventview().get(0).getTotal_person_attending_event();
                        if (!response.body().getData().getEventview().get(0).getTotal_person_attending_event().equals("0"))
                            txtEventAttending.setText(response.body().getData().getEventview().get(0).getTotal_person_attending_event().concat(" Attending"));
                        else
                            txtEventAttending.setVisibility(View.GONE);

                        Log.d(TAG, "onResponse: countmeeventstatusid " + response.body().getData().getEventview().get(0).getCountmeeventstatusid());

                        if (response.body().getData().getEventview().get(0).getCountmeeventstatusid().equals("1")) {

                            txtCountMeIn.setVisibility(View.VISIBLE);

                            CountMeStatus = "1";
                            txtCountMeIn.setBackgroundResource(R.color.grey_500);
                            txtCountMeIn.setText("Count Me Out");
                            txtCountMeIn.setTextColor(getResources().getColor(R.color.black));


                        } else if (response.body().getData().getEventview().get(0).getCountmeeventstatusid().equals("2")) {
                            txtCountMeIn.setVisibility(View.VISIBLE);

                            CountMeStatus = "2";
                            txtCountMeIn.setBackgroundResource(R.color.medium_level_blue);
                            txtCountMeIn.setText("Count Me In");
                            txtCountMeIn.setTextColor(getResources().getColor(R.color.white));


                        } else {
                            txtCountMeIn.setVisibility(View.GONE);
                        }

                        String start_date = response.body().getData().getEventview().get(0).getStart_date();
                        String end_date = response.body().getData().getEventview().get(0).getEnd_date();
                        String start_time = response.body().getData().getEventview().get(0).getStart_time();
                        String end_time = response.body().getData().getEventview().get(0).getEnd_time();

                        // String combile_string = start_time.concat(" - " + end_time).concat("\n" + start_date).concat(" - " + end_date);
                        //String combile_string = start_time.concat(" - " + end_time).concat(" " + start_date).concat(" " + end_date);

                        String combile_string = start_date.concat(" - " + end_date).concat(" \n" + start_time).concat(("-" + end_time));
                        txtEventDateAndTime.setText(combile_string);

                        txtPostedDateOn.setText(String.format("Posted on %s", response.body().getData().getEventview().get(0).getPost_date()));
                        txtOrganishBy.setText(String.format("Organised by %s", response.body().getData().getEventview().get(0).getPost_by_firstname()));

                        Log.d(TAG, "onResponse: photo url " + path_image.concat(response.body().getData().getEventview().get(0).getExplore_logo()));
                        Picasso.with(EventDetailsActivity.this)
                                .load(path_image.concat(response.body().getData().getEventview().get(0).getExplore_logo()))
                                .placeholder(R.drawable.profile_pic) //this is optional the image to display while the url image is downloading
                                .error(R.drawable.profile_pic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                .into(imgEvents);


                        photoListArrayList.addAll(response.body().getData().getPhoto_list());
                        videoListArrayList.addAll(response.body().getData().getVideo_list());

                        Log.d(TAG, "onResponse: photoListArrayList " + photoListArrayList.size());
                        Log.d(TAG, "onResponse: photoListArrayList res " + response.body().getData().getPhoto_list().size());

                        Log.d(TAG, "onResponse: response of photolistarraylist " + new Gson().toJson(response.body().getData().getPhoto_list()));

                        if (fragInstance != null) {

                            for (String mInstance : fragInstance) {

                                Log.d(TAG, "run: " + mInstance);

                                Fragment fragment = getSupportFragmentManager().findFragmentByTag(mInstance);

                                if (fragment instanceof EventAboutFragment) {
                                    EventAboutFragment eventAboutFragment = (EventAboutFragment) getSupportFragmentManager().findFragmentByTag(String.valueOf(mInstance));
                                    eventAboutFragment.setText(description, region, district, address, contact);

                                } else if (fragment instanceof EventPhotoFragment) {

                                    EventPhotoFragment eventPhotoFragment = (EventPhotoFragment) fragment;
                                    eventPhotoFragment.setPhotoArrayList(photoListArrayList);
                                } else if (fragment instanceof EventVideoFragment) {

                                    EventVideoFragment eventVideoFragment = (EventVideoFragment) fragment;
                                    eventVideoFragment.setVideoListArrayList(videoListArrayList);
                                } else {
                                    Log.e(TAG, "run: instanceError");
                                }
                            }

                        } else {
                            Log.e(TAG, "run: instance null");
                        }

                    } else {

                        Toast.makeText(EventDetailsActivity.this, "No record available", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 304) {

                    Toast.makeText(EventDetailsActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(EventDetailsActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(EventDetailsActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(EventDetailsActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(EventDetailsActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(EventDetailsActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(EventDetailsActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<DataEventViewDetailsItem> call, Throwable t) {
                //   progressDialog.dismiss();
                t.printStackTrace();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!nbIsNetworkAvailable(this)) {
            nbShowNoInternet(this);
        }

    }


    private void UiInitialization() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        this.maincontent = (CoordinatorLayout) findViewById(R.id.main_content);
        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        this.appbar = (AppBarLayout) findViewById(R.id.appbar);
        this.collapsingtoolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //this.dummyAppbar = (AppBarLayout) findViewById(R.id.dummyAppbar);
        this.logo = (RelativeLayout) findViewById(R.id.logo);
        this.viewNavClick = (RelativeLayout) findViewById(R.id.viewNavClick);
        this.right = (ImageButton) findViewById(R.id.right);
        this.tabs = (TabLayout) findViewById(R.id.tabs);
        this.left = (ImageButton) findViewById(R.id.left);
        this.txtPostedDateOn = (TextView) findViewById(R.id.txtPostedDateOn);
        this.txtOrganishBy = (TextView) findViewById(R.id.txtOrganishBy);
        this.txtEventAttending = (TextView) findViewById(R.id.txtEventAttending);
        this.txtEventAttending.setOnClickListener(this);
        this.txtEventAddress = (TextView) findViewById(R.id.txtEventAddress);
        this.txtEventDateAndTime = (TextView) findViewById(R.id.txtEventDateAndTime);
        this.txtCountMeIn = (TextView) findViewById(R.id.txtCountMeIn);
        this.txtCountMeIn.setOnClickListener(this);
        this.txtEventTitle = (TextView) findViewById(R.id.txtEventTitle);
        this.txtEventMobileNumber = (TextView) findViewById(R.id.txtEventMobileNumber);
        this.imgEvents = (ImageView) findViewById(R.id.imgEvents);
        setupViewPager(viewpager);
        tabs.setupWithViewPager(viewpager);

        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("Events View");


        } catch (Exception e) {
            e.printStackTrace();
        }

        left.setOnClickListener(this);
        right.setOnClickListener(this);


    }

    public String getEvent_id() {
        return event_id;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new EventAboutFragment(), "About");
        adapter.addFragment(new EventPhotoFragment(), "Photo");
        adapter.addFragment(new EventVideoFragment(), "Video");
        adapter.addFragment(new EventDiscusstionBoardFragment(), "Discussion Board");

        viewPager.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        int tab_position;
        switch (v.getId()) {

            case R.id.txtEventAttending:

                Intent intent = new Intent(EventDetailsActivity.this, EventParticipantsActivity.class);
                intent.putExtra("event_id", event_id);
                startActivity(intent);

                break;

            case R.id.txtCountMeIn:

                if (CountMeStatus.equals("1")) {

                    getCountMeInOutEvent("2");
                } else {
                    getCountMeInOutEvent("1");
                }

                break;
            case R.id.fab:

                Intent getLocations = new Intent(EventDetailsActivity.this, JobsMapsActivity.class);
                getLocations.putExtra("lat", lattitude);
                getLocations.putExtra("lang", longtitude);
                getLocations.putExtra("company_name", companyName);
                startActivity(getLocations);
                break;


            case R.id.left:

                tab_position = tabs.getSelectedTabPosition();

                Log.d(TAG, "onClick:tab_position right " + tab_position);
                Log.d(TAG, "onClick:tab_position left " + tab_position--);
                if (tab_position < 3)
                    viewpager.setCurrentItem(tab_position--);

                break;
            case R.id.right:
                tab_position = tabs.getSelectedTabPosition();
                Log.d(TAG, "onClick:tab_position left " + tab_position);
                Log.d(TAG, "onClick:tab_position left " + tab_position++);
                if (tab_position >= 0)
                    viewpager.setCurrentItem(tab_position++);
                break;


        }
    }

    private void getCountMeInOutEvent(String count_me_status) {


        progressDialog = Utils.createProgressDialog(this);
        //String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTU3IiwidXNlcm5hbWUiOiJzcmlkaGFyYW4ubWluZG5vdGl4QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTg2MDkwNTAsImp0aSI6IlFYVkdUVEJ3V0dOMlNGWjBkVWxVV1hKT2JEWT0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE4NjA5MDUwLCJleHAiOjE1MTkyMTM4NTB9.-1YP2zxdrvMmlc2mfH6CXyCUxdqRObTFI6GSUByJ84I";

        String token = Pref.getPreToken();


        Map<String, String> data = new HashMap<>();
        data.put("event_id", event_id);
        data.put("status", count_me_status);
        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


        //token = "Bearer "+ Pref.getPreToken();


        Log.d(TAG, "changePassword: token " + token);

        Call<DataCountMeInOutItems> call = apiService.getCountMeInOut(token, data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataCountMeInOutItems>() {
            @Override
            public void onResponse(Call<DataCountMeInOutItems> call, Response<DataCountMeInOutItems> response) {


                try {

                    progressDialog.dismiss();
                    if (response.code() == 200) {

                        if (response.body().getStatus().equals("1")) {

                            Log.d(TAG, "onResponse: event details getCountmeout in out status " + new Gson().toJson(response.body()));

                            if (response.body().getData().getCountmeout().equals("1")) {

                                Log.d(TAG, "onResponse: getCountmeout " + response.body().getData().getCountmeout());

                                int count = Integer.parseInt(attend_count) + 1;
                                attend_count = String.valueOf(count);
                                CountMeStatus = "1";

                                txtCountMeIn.setBackgroundResource(R.color.grey_500);
                                txtCountMeIn.setText("Count Me Out");
                                txtCountMeIn.setTextColor(getResources().getColor(R.color.black));
                                txtEventAttending.setText(String.valueOf(count).concat(" Attending"));

                            } else {

                                Log.d(TAG, "onResponse: getCountmeout " + response.body().getData().getCountmeout());
                                CountMeStatus = "2";
                                int count = Integer.parseInt(attend_count) - 1;
                                attend_count = String.valueOf(count);

                                txtCountMeIn.setBackgroundResource(R.color.medium_level_blue);
                                txtCountMeIn.setText("Count Me In");
                                txtCountMeIn.setTextColor(getResources().getColor(R.color.white));

                                txtEventAttending.setText(String.valueOf(count).concat(" Attending"));
                            }
                            Toast.makeText(EventDetailsActivity.this, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {

                        }

                    } else if (response.code() == 304) {

                        Toast.makeText(EventDetailsActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(EventDetailsActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(EventDetailsActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(EventDetailsActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(EventDetailsActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(EventDetailsActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(EventDetailsActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<DataCountMeInOutItems> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });


    }


}
