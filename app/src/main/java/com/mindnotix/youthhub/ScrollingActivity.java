package com.mindnotix.youthhub;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity extends BaseActivity {

    private static final String TAG = "ScrollingActivity";
    ViewPager viewPager;
    TabLayout tabLayout;
    int tabPosition = 0;

    Boolean LeftBool = false;
    Boolean rightBool = false;
    ImageView imgRight, imgLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        imgRight = (ImageView) findViewById(R.id.left);
        imgLeft = (ImageView) findViewById(R.id.right);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int tab_position = tabLayout.getSelectedTabPosition();

                Log.d(TAG, "onClick:tab_position right " + tab_position);
                Log.d(TAG, "onClick:tab_position left " + tab_position--);
                if (tab_position < 7)
                    viewPager.setCurrentItem(tab_position--);

            }
        });

        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int tab_position = tabLayout.getSelectedTabPosition();
                Log.d(TAG, "onClick:tab_position left " + tab_position);
                Log.d(TAG, "onClick:tab_position left " + tab_position++);
                if (tab_position >= 0)
                    viewPager.setCurrentItem(tab_position++);

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!nbIsNetworkAvailable(this)){
            nbShowNoInternet(this);
        }
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PostedStoriesFragment(), "Posted Stories");
        adapter.addFragment(new MyStepsFragment(), "My steps");
        adapter.addFragment(new CV_ResumeFragment(), "CV/Resume");
        adapter.addFragment(new TestimonialFragment(), "Testimonial");
        adapter.addFragment(new ProjectFragment(), "Project");
        adapter.addFragment(new GalleryFragment(), "Galley");
        viewPager.setAdapter(adapter);
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
