package com.mindnotix.youthhub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sridharan on 1/22/2018.
 */

public class DashBoardActivity extends NavDrawerActivity implements View.OnClickListener {

    private static final String TAG = "DashBoardActivity";
    ViewPager viewpager;
    LinearLayout tabs, linearTimeLine, linearMyPost, linearFindConnection, linearMessage, linearNotifcation;
    Toolbar toolbar;
    TextView badgenotification;
    View view1, view2, view3, view4, view5;

    public static int flagFirstTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);


        UiInitialization();
        setupViewPager(viewpager);


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


    private void UiInitialization() {
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        tabs = (LinearLayout) findViewById(R.id.tabs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        linearNotifcation = (LinearLayout) findViewById(R.id.linearNotifcation);
        linearNotifcation.setOnClickListener(this);
        badgenotification = (TextView) findViewById(R.id.badge_notification_2);
        linearMessage = (LinearLayout) findViewById(R.id.linearMessage);
        linearMessage.setOnClickListener(this);
        linearMyPost = (LinearLayout) findViewById(R.id.linearMyPost);
        linearMyPost.setOnClickListener(this);
        linearFindConnection = (LinearLayout) findViewById(R.id.linearFindConnection);
        linearFindConnection.setOnClickListener(this);
        linearTimeLine = (LinearLayout) findViewById(R.id.linearTimeLine);
        linearTimeLine.setOnClickListener(this);

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
    public void onClick(View v) {

        switch (v.getId()) {
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

        }

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

    @Override
    public void onSuccess(String completed, int i) {
        super.onSuccess(completed, i);

        switch (i) {

            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
    }



    //Time line post


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

    public void switchTab(int tab) {
        viewpager.setCurrentItem(tab);
    }

}
