package com.mindnotix.youthhub;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.mindnotix.application.MyApplication;
import com.mindnotix.model.BasicResponse;
import com.mindnotix.model.NotificationCount;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavDrawerActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = NavDrawerActivity.class.getSimpleName();
    public static int flagFirstTime = 0;
    protected View viewNavClick;
    protected View invite_Users;
    protected View viewProfile;
    protected View viewYouthSupport;
    protected DrawerLayout mDrawerLayout;
    protected ProgressDialog progressDialog;
    protected String tvDecriptions;
    protected String tvTile;
    protected CircleImageView circleImageView;
    protected ImageView normalImage, imgParentJobArrow;
    protected TextView tvFirstName;
    protected View viewDashboard;
    protected View viewJobs;
    protected View linearFindConnectionActivity;
    protected View viewEvents;
    protected View viewJobsSearch;
    protected View viewParentJobs;
    protected View viewMyJobs;
    protected View viewChangePassword;
    int viewParentJobsFlag = 0;
    ViewPager viewpager;
    LinearLayout tabs, linearTimeLine, linearMyPost, linearContactSupport,
            linearExplore, linearFindConnection, linearMessage, linearNotifcation, linearLogout;
    Toolbar toolbar;
    TextView badgenotification;
    View view1, view2, view3, view4, view5;
    Call<BasicResponse> inviteUsersDilaog;
    Call<BasicResponse> inviteUsers;
    ProgressBar imageUploadProgress;


    private void loadNotififcationCount() {


        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


        Call<NotificationCount> call = apiService.getNotificationCount(Pref.getPreToken());
        Log.d(TAG, "getNotificationList: url " + call.request().url());

        call.enqueue(new Callback<NotificationCount>() {
            @Override
            public void onResponse(Call<NotificationCount> call, Response<NotificationCount> response) {


                try {

                    if (response.code() == 200) {

                        if (response.body().getStatus().equals("1")) {
                            badgenotification.setText(response.body().getUnreadcount());

                        } else {

                        }

                    } else if (response.code() == 304) {

                        Toast.makeText(NavDrawerActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(NavDrawerActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(NavDrawerActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(NavDrawerActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(NavDrawerActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(NavDrawerActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(NavDrawerActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<NotificationCount> call, Throwable t) {

                t.printStackTrace();
            }
        });


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        initUi();
        setupViewPager(viewpager);
        loadNotififcationCount();


        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Log.d(TAG, "onPageSelected: " + position);
                switch (position) {
                    case 0:
                        viewpager.setCurrentItem(0);
                        view1.setVisibility(View.VISIBLE);
                        view2.setVisibility(View.INVISIBLE);
                        view3.setVisibility(View.INVISIBLE);
                        view4.setVisibility(View.INVISIBLE);
                        view5.setVisibility(View.INVISIBLE);

                        break;
                    case 1:
                        viewpager.setCurrentItem(1);
                        view1.setVisibility(View.INVISIBLE);
                        view2.setVisibility(View.VISIBLE);
                        view3.setVisibility(View.INVISIBLE);
                        view4.setVisibility(View.INVISIBLE);
                        view5.setVisibility(View.INVISIBLE);
                        flagFirstTime = 1;
                        break;
                    case 2:

                        Log.d(TAG, "onClick: event listener...viewpager ");
                        viewpager.setCurrentItem(2);

                        view1.setVisibility(View.INVISIBLE);
                        view2.setVisibility(View.INVISIBLE);
                        view3.setVisibility(View.VISIBLE);
                        view4.setVisibility(View.INVISIBLE);
                        view5.setVisibility(View.INVISIBLE);

                        flagFirstTime = 2;
                        break;
                    case 3:
                        viewpager.setCurrentItem(3);

                        view1.setVisibility(View.INVISIBLE);
                        view2.setVisibility(View.INVISIBLE);
                        view3.setVisibility(View.INVISIBLE);
                        view4.setVisibility(View.VISIBLE);
                        view5.setVisibility(View.INVISIBLE);

                        flagFirstTime = 3;
                        break;
                    case 4:
                        viewpager.setCurrentItem(4);

                        view1.setVisibility(View.INVISIBLE);
                        view2.setVisibility(View.INVISIBLE);
                        view3.setVisibility(View.INVISIBLE);
                        view4.setVisibility(View.INVISIBLE);
                        view5.setVisibility(View.VISIBLE);

                        flagFirstTime = 4;
                        break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void navDashTime(){

        viewpager.setCurrentItem(0);
        view1.setVisibility(View.VISIBLE);
        view2.setVisibility(View.INVISIBLE);
        view3.setVisibility(View.INVISIBLE);
        view4.setVisibility(View.INVISIBLE);
        view5.setVisibility(View.INVISIBLE);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DashboardTimeLine(), "TimeLine");
        adapter.addFragment(new DashboardFindConnection(), "connection");
        adapter.addFragment(new DashboardMyStep(), "my stept");
        adapter.addFragment(new DashboardMessage(), "message");
        adapter.addFragment(new DashboardNotification(), "notification");
        viewPager.setAdapter(adapter);
    }

    private void initUi() {

        viewNavClick = findViewById(R.id.viewNavClick);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        invite_Users = findViewById(R.id.inviteUsers);
        viewYouthSupport = findViewById(R.id.viewYouthSupport);
        viewProfile = findViewById(R.id.viewProfile);
        circleImageView = findViewById(R.id.imageCircular);
        normalImage = findViewById(R.id.normalImage);
        tvFirstName = findViewById(R.id.tvFirstName);
        linearExplore = findViewById(R.id.linearExplore);
        viewDashboard = findViewById(R.id.viewDashboard);
        viewJobs = findViewById(R.id.viewJobs);
        linearFindConnectionActivity = findViewById(R.id.linearFindConnectionActivity);
        viewEvents = findViewById(R.id.viewEvents);
        viewJobsSearch = findViewById(R.id.viewJobsSearch);
        imgParentJobArrow = findViewById(R.id.imgParentJobArrow);
        viewParentJobs = findViewById(R.id.viewParentJobs);
        viewMyJobs = findViewById(R.id.viewMyJobs);
        viewChangePassword = findViewById(R.id.viewChangePassword);
        imageUploadProgress = findViewById(R.id.imageUploadProgress);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        viewNavClick.setOnClickListener(this);
        invite_Users.setOnClickListener(this);
        viewYouthSupport.setOnClickListener(this);
        viewProfile.setOnClickListener(this);
        viewDashboard.setOnClickListener(this);
        viewJobs.setOnClickListener(this);
        viewEvents.setOnClickListener(this);
        linearFindConnectionActivity.setOnClickListener(this);
        viewMyJobs.setOnClickListener(this);
        linearExplore.setOnClickListener(this);
        viewJobsSearch.setOnClickListener(this);
        viewParentJobs.setOnClickListener(this);
        viewChangePassword.setOnClickListener(this);


        viewpager = (ViewPager) findViewById(R.id.viewpager);
        tabs = (LinearLayout) findViewById(R.id.tabs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        linearNotifcation = (LinearLayout) findViewById(R.id.linearNotifcation);
        linearLogout = (LinearLayout) findViewById(R.id.linearLogout);
        linearNotifcation.setOnClickListener(this);
        linearLogout.setOnClickListener(this);
        badgenotification = (TextView) findViewById(R.id.badge_notification_2);
        linearMessage = (LinearLayout) findViewById(R.id.linearMessage);
        linearMessage.setOnClickListener(this);
        linearMyPost = (LinearLayout) findViewById(R.id.linearMyPost);
        linearMyPost.setOnClickListener(this);
        linearFindConnection = (LinearLayout) findViewById(R.id.linearFindConnection);
        linearFindConnection.setOnClickListener(this);
        linearTimeLine = (LinearLayout) findViewById(R.id.linearTimeLine);

        linearContactSupport = (LinearLayout) findViewById(R.id.linearContactSupport);
        linearTimeLine.setOnClickListener(this);
        linearContactSupport.setOnClickListener(this);

        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
        view4 = findViewById(R.id.view4);
        view5 = findViewById(R.id.view5);

        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {


            case R.id.linearExplore:
                mDrawerLayout.closeDrawer(GravityCompat.END);

                Intent explore = new Intent(NavDrawerActivity.this, ExploreActivity.class);
                startActivity(explore);

                break;
            case R.id.viewDashboard:
                mDrawerLayout.closeDrawer(GravityCompat.END);
                Intent mIntent = new Intent(NavDrawerActivity.this, NavDrawerActivity.class);
                mIntent.putExtra("position_number", 0);
                startActivity(mIntent);
                finish();
                break;

            case R.id.linearFindConnectionActivity:

                mDrawerLayout.closeDrawer(GravityCompat.END);
                viewpager.setCurrentItem(1);
                view1.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.VISIBLE);
                view3.setVisibility(View.INVISIBLE);
                view4.setVisibility(View.INVISIBLE);
                view5.setVisibility(View.INVISIBLE);
                flagFirstTime = 1;
                break;

            case R.id.viewNavClick:
                mDrawerLayout.openDrawer(GravityCompat.END);

                break;
            case R.id.inviteUsers:
                mDrawerLayout.closeDrawer(GravityCompat.END);
                postInviteUsersDilaog();

                break;
            case R.id.viewYouthSupport:
                mDrawerLayout.closeDrawer(GravityCompat.END);
                Intent intent = new Intent(NavDrawerActivity.this, SupportRequestActivity.class);
                startActivity(intent);
                break;

            case R.id.viewProfile:
                mDrawerLayout.closeDrawer(GravityCompat.END);
                Intent profile = new Intent(NavDrawerActivity.this, ProfileActivity.class);
                startActivity(profile);

                break;

            case R.id.viewEvents:
                mDrawerLayout.closeDrawer(GravityCompat.END);
                Intent events = new Intent(NavDrawerActivity.this, EventsActivity.class);
                startActivity(events);

                break;

            case R.id.viewJobs:
                if (viewParentJobsFlag == 0) {
                    viewParentJobs.setVisibility(View.VISIBLE);
                    viewParentJobsFlag = 1;
                    imgParentJobArrow.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                } else {
                    viewParentJobs.setVisibility(View.GONE);
                    viewParentJobsFlag = 0;
                    imgParentJobArrow.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                }


                break;
            case R.id.viewJobsSearch:
                mDrawerLayout.closeDrawer(GravityCompat.END);


                MyApplication.handleFilterPojo.setJopCatagoreyPosition("0");
                MyApplication.handleFilterPojo.setEdtJobSearch("");
                MyApplication.handleFilterPojo.setChkfav(false);
                MyApplication.handleFilterPojo.setSpsubcategories("0");
                MyApplication.handleFilterPojo.setSpregion("0");
                MyApplication.handleFilterPojo.setSpdistrict("0");
                MyApplication.handleFilterPojo.setSpjobtype("0");
                MyApplication.handleFilterPojo.setSpfrom("0");
                MyApplication.handleFilterPojo.setSpsalary("0");
                MyApplication.handleFilterPojo.setSpto("0");
                MyApplication.handleFilterPojo.setSpsort("0");

                Intent job = new Intent(NavDrawerActivity.this, JobListActivity.class);
                startActivity(job);

                break;

            case R.id.viewMyJobs:
                mDrawerLayout.closeDrawer(GravityCompat.END);
                Intent myjobs = new Intent(NavDrawerActivity.this, MyJobsActivity.class);
                startActivity(myjobs);

                break;

            case R.id.viewChangePassword:
                mDrawerLayout.closeDrawer(GravityCompat.END);
                Intent intents = new Intent(NavDrawerActivity.this, ChangePasswordActivity.class);
                startActivity(intents);

                break;

            case R.id.linearTimeLine:
                viewpager.setCurrentItem(0);
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.INVISIBLE);
                view3.setVisibility(View.INVISIBLE);
                view4.setVisibility(View.INVISIBLE);
                view5.setVisibility(View.INVISIBLE);
                break;
            case R.id.linearFindConnection:
                viewpager.setCurrentItem(1);
                view1.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.VISIBLE);
                view3.setVisibility(View.INVISIBLE);
                view4.setVisibility(View.INVISIBLE);
                view5.setVisibility(View.INVISIBLE);
                break;
            case R.id.linearMyPost:
                Log.d(TAG, "onClick: event listener...");
                viewpager.setCurrentItem(2);


                view1.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.INVISIBLE);
                view3.setVisibility(View.VISIBLE);
                view4.setVisibility(View.INVISIBLE);
                view5.setVisibility(View.INVISIBLE);

                break;
            case R.id.linearMessage:
                viewpager.setCurrentItem(3);

                view1.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.INVISIBLE);
                view3.setVisibility(View.INVISIBLE);
                view4.setVisibility(View.VISIBLE);
                view5.setVisibility(View.INVISIBLE);

                break;
            case R.id.linearNotifcation:
                viewpager.setCurrentItem(4);

                view1.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.INVISIBLE);
                view3.setVisibility(View.INVISIBLE);
                view4.setVisibility(View.INVISIBLE);
                view5.setVisibility(View.VISIBLE);

                break;

            case R.id.linearLogout:
                mDrawerLayout.closeDrawer(GravityCompat.END);
                Intent mIntent1 = new Intent(NavDrawerActivity.this, LoginActivity.class);
                startActivity(mIntent1);
                finish();
                break;

            case R.id.linearContactSupport:
                mDrawerLayout.closeDrawer(GravityCompat.END);

                Intent contactIntent1 = new Intent(NavDrawerActivity.this, ContactSupportActivity.class);
                startActivity(contactIntent1);
                break;


        }
        //mDrawerLayout.openDrawer(GravityCompat.END);
    }

    private void postInviteUsersDilaog() {
        progressDialog = Utils.createProgressDialog(this);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        inviteUsersDilaog = apiService.inviteUsersDilaog(Pref.getPreToken());

        inviteUsersDilaog.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(@NonNull Call<BasicResponse> call, @NonNull Response<BasicResponse> response) {

                if (response.code() == 200 || response.code() == 201) {

                    BasicResponse basicResponse = response.body();

                    if (basicResponse.getStatus().equals("1")) {
                        progressDialog.dismiss();

                        try {

                            Pref.setdevicetoken("Bearer " + basicResponse.getToken());


                            tvTile = basicResponse.getData().getTitle();
                            tvDecriptions = basicResponse.getData().getDiscretion();

                            loadDilaog();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(NavDrawerActivity.this, basicResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else if (response.code() == 304) {
                    progressDialog.dismiss();

                    Toast.makeText(NavDrawerActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    progressDialog.dismiss();

                    Toast.makeText(NavDrawerActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    progressDialog.dismiss();

                    Toast.makeText(NavDrawerActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    progressDialog.dismiss();
                    Toast.makeText(NavDrawerActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    progressDialog.dismiss();
                    Toast.makeText(NavDrawerActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    progressDialog.dismiss();
                    Toast.makeText(NavDrawerActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    progressDialog.dismiss();
                    Toast.makeText(NavDrawerActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {

                progressDialog.dismiss();
            }


        });
    }


    private void loadDilaog() {

        final Dialog dialog = new Dialog(NavDrawerActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dilaog_invite_users);

        final EditText edEmail = dialog.findViewById(R.id.edEmail);
        final TextView tvDecription = dialog.findViewById(R.id.tvDecription);
        final TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        Button btInvite = dialog.findViewById(R.id.btInvite);
        Button btCancel = dialog.findViewById(R.id.btCancel);
        View icClose = dialog.findViewById(R.id.icClose);

        tvTitle.setText(tvTile);
        tvDecription.setText(tvDecriptions);

        btInvite.setOnClickListener(new View.OnClickListener() {

            boolean isvalid = false;

            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(Gravity.END);

                if (!Utils.hasText(edEmail)) {
                    Toast.makeText(NavDrawerActivity.this, "Please enter the email id", Toast.LENGTH_SHORT).show();
                    return;
                }
                String[] test = edEmail.getText().toString().split(",");
                for (int i = 0; i < test.length; i++) {
                    if (!Utils.isEmailValid(test[i])) {
                        Toast.makeText(NavDrawerActivity.this, "Enter a valid email( " + test[i] + " )", Toast.LENGTH_SHORT).show();
                        isvalid = true;
                        break;
                    } /*else {
                        postInviteUsers(edEmail.getText().toString(), edEmail);
                    }*/
                    isvalid = false;
                }

                if (!isvalid) {
                    postInviteUsers(edEmail.getText().toString(), edEmail, dialog);
                }


            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();


            }
        });
        icClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();


            }
        });


        dialog.show();

    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.END)) {
            mDrawerLayout.closeDrawer(Gravity.END);
        } else {
            super.onBackPressed();
        }
    }

    private void postInviteUsers(String emailid, final EditText edEmail1, final Dialog dialog) {


        progressDialog = Utils.createProgressDialog(this);

        Map<String, String> data = new HashMap<>();
        data.put("emails", emailid);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        inviteUsers = apiService.inviteUsers(Pref.getPreToken(), data);

        inviteUsers.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(@NonNull Call<BasicResponse> call, @NonNull Response<BasicResponse> response) {

                if (response.code() == 200 || response.code() == 201) {

                    BasicResponse basicResponse = response.body();

                    if (basicResponse.getStatus().equals("1")) {
                        progressDialog.dismiss();
                        dialog.dismiss();
                        edEmail1.setText("");
                        try {
                            ;
                            Pref.setdevicetoken("Bearer " + basicResponse.getToken());
                            Toast.makeText(NavDrawerActivity.this, basicResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        edEmail1.setText("");
                        progressDialog.dismiss();
                        Toast.makeText(NavDrawerActivity.this, basicResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else if (response.code() == 304) {
                    edEmail1.setText("");
                    progressDialog.dismiss();
                    Toast.makeText(NavDrawerActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    edEmail1.setText("");
                    progressDialog.dismiss();
                    Toast.makeText(NavDrawerActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    edEmail1.setText("");
                    progressDialog.dismiss();
                    Toast.makeText(NavDrawerActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    edEmail1.setText("");
                    progressDialog.dismiss();
                    Toast.makeText(NavDrawerActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    edEmail1.setText("");
                    progressDialog.dismiss();
                    Toast.makeText(NavDrawerActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {
                    edEmail1.setText("");
                    progressDialog.dismiss();
                    Toast.makeText(NavDrawerActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {
                    progressDialog.dismiss();
                    edEmail1.setText("");
                    Toast.makeText(NavDrawerActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                edEmail1.setText("");
                progressDialog.dismiss();
            }


        });
    }

    @Override
    protected void onResume() {

        tvFirstName.setText(Pref.getClientFirstname());

        if (Pref.getProfileImage() != null)
            System.out.println("asdfadsfsadfadsf" + Pref.getProfileImage());
        else
            System.out.println("asdfadsfsadfadsf nulll");
        if (Pref.getClientProfilePictureName() != null) {
            if (!Pref.getClientProfilePictureName().equals("")) {

                Picasso.with(NavDrawerActivity.this).
                        load(Pref.getClientImageThumbnailPath().concat(Pref.getClientProfilePictureName()))
                        .into(circleImageView, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                imageUploadProgress.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                imageUploadProgress.setVisibility(View.GONE);
                            }
                        });


                circleImageView.setVisibility(View.VISIBLE);
                normalImage.setVisibility(View.GONE);

            } else {


                circleImageView.setVisibility(View.GONE);
                normalImage.setVisibility(View.VISIBLE);


                loadTextDrawable();
            }

        } else {

            circleImageView.setVisibility(View.GONE);
            normalImage.setVisibility(View.VISIBLE);
            loadTextDrawable();

        }

        super.onResume();
    }

    private void loadTextDrawable() {

        try{

            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(String.valueOf(Pref.getPreferenceUser().substring(0, 1).toUpperCase().charAt(0)),
                            getResources().getColor(R.color.medium_level_blue));

            normalImage.setImageDrawable(drawable);

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
