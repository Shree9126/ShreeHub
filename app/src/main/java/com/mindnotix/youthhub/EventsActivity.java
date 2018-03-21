package com.mindnotix.youthhub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mindnotix.adapter.ViewPagerAdapter;
import com.mindnotix.listener.RecyclerViewLoadMoreScroll;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sridharan on 2/16/2018.
 */

public class EventsActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = EventsActivity.class.getSimpleName();
    SearchView searchView = null;
    MyLocationEventsFragment myLocationEventsFragment;
    MyEventsFragment myEventsFragment;
    AllEventsFragment allEventsFragment;
    View view1, view2;
    LinearLayout linearAllEvent, linearMyLocationEvent;
    private Toolbar toolbar;
    private EditText edtEventSearch;
    private TabLayout tabs;
    TextView tvMylocations;
    TextView tvAllEvents;
    ViewPagerAdapter adapter;
    private ViewPager viewpager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        UiIntialization();
        setupViewPager(viewpager);

        tvMylocations.setTextColor(getResources().getColor(R.color.medium_level_blue));
        tvAllEvents.setTextColor(getResources().getColor(R.color.black));

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Log.d(TAG, "onPageSelected: " + position);
                switch (position) {
                    case 0:
                        adapter.notifyDataSetChanged();
                        tvMylocations.setTextColor(getResources().getColor(R.color.medium_level_blue));
                        tvAllEvents.setTextColor(getResources().getColor(R.color.black));
                        viewpager.setCurrentItem(0);
                        view1.setVisibility(View.VISIBLE);
                        view2.setVisibility(View.INVISIBLE);

                        break;
                    case 1:
                        adapter.notifyDataSetChanged();
                        tvMylocations.setTextColor(getResources().getColor(R.color.black));
                        tvAllEvents.setTextColor(getResources().getColor(R.color.medium_level_blue));
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


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gps_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionMap:
                Intent intent=new Intent(EventsActivity.this,EventListMapActivity.class);
                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void UiIntialization() {

        this.viewpager = (ViewPager) findViewById(R.id.viewpager);

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.edtEventSearch = (EditText) findViewById(R.id.edtEventSearch);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        linearAllEvent = findViewById(R.id.linearAllEvents);
        linearAllEvent.setOnClickListener(this);
        linearMyLocationEvent = findViewById(R.id.linearMyLocationEvent);
        tvMylocations = findViewById(R.id.tvMylocations);
        tvAllEvents = findViewById(R.id.tvAllEvents);
        linearMyLocationEvent.setOnClickListener(this);

        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("Events");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void setupViewPager(ViewPager viewPager) {
         adapter = new ViewPagerAdapter(
                getSupportFragmentManager());
        adapter.addFragment(new MyLocationEventsFragment(), "My Location");
        adapter.addFragment(new AllEventsFragment(), "All Events");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearMyLocationEvent:
                viewpager.setCurrentItem(0);
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.INVISIBLE);

                break;
            case R.id.linearAllEvents:
                viewpager.setCurrentItem(1);
                view1.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.VISIBLE);
                break;
        }
    }



}
