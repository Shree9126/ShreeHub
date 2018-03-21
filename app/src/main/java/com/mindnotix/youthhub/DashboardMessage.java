package com.mindnotix.youthhub;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindnotix.adapter.ViewPagerAdapter;
import com.mindnotix.youthhub.dashboard_message.DashboardMessageInbox;
import com.mindnotix.youthhub.dashboard_message.DashboardMessageOutbox;
import com.mindnotix.youthhub.dashboard_message.DashboardMessageUsers;

/**
 * Created by Admin on 1/25/2018.
 */

public class DashboardMessage extends Fragment implements View.OnClickListener {

    private static final String TAG = DashboardMessage.class.getSimpleName();
    private View view4;
    private android.widget.TextView txtinbox;
    private android.widget.TextView txtOutbox;
    private android.widget.LinearLayout linearInbox;
    private View view1;
    private android.widget.TextView tvMylocations;
    private android.widget.LinearLayout linearOutBox;
    private View view2;
    private android.widget.TextView txtusers;
    private android.widget.LinearLayout linearUsers;
    private View view3;
    private android.support.v4.view.ViewPager viewpager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.my_steps_fragment, container, false);

        UiInitialization(view);
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

                        break;
                    case 1:

                        viewpager.setCurrentItem(1);
                        view1.setVisibility(View.INVISIBLE);
                        view2.setVisibility(View.VISIBLE);
                        view3.setVisibility(View.INVISIBLE);

                        break;
                    case 2:

                        viewpager.setCurrentItem(2);
                        view1.setVisibility(View.INVISIBLE);
                        view2.setVisibility(View.INVISIBLE);
                        view3.setVisibility(View.VISIBLE);

                        break;


                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    private void setupViewPager(ViewPager viewpager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getChildFragmentManager());
        adapter.addFragment(new DashboardMessageInbox(), "Inbox");
        adapter.addFragment(new DashboardMessageOutbox(), "Outbox");
        adapter.addFragment(new DashboardMessageUsers(), "Users");
        viewpager.setAdapter(adapter);
    }

    private void UiInitialization(View view) {

        this.viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        this.view3 = (View) view.findViewById(R.id.view3);
        this.linearUsers = (LinearLayout) view.findViewById(R.id.linearUsers);
        this.linearUsers.setOnClickListener(this);
        this.txtusers = (TextView) view.findViewById(R.id.txt_users);
        this.view2 = (View) view.findViewById(R.id.view2);
        this.linearOutBox = (LinearLayout) view.findViewById(R.id.linearOutBox);
        this.linearOutBox.setOnClickListener(this);
        this.tvMylocations = (TextView) view.findViewById(R.id.tvMylocations);
        this.view1 = (View) view.findViewById(R.id.view1);
        this.linearInbox = (LinearLayout) view.findViewById(R.id.linearInbox);
        this.linearInbox.setOnClickListener(this);
        this.txtinbox = (TextView) view.findViewById(R.id.txt_inbox);
        this.txtOutbox = (TextView) view.findViewById(R.id.txt_inbox);
        this.view4 = (View) view.findViewById(R.id.view4);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearInbox:
                viewpager.setCurrentItem(0);
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.INVISIBLE);
                view3.setVisibility(View.INVISIBLE);

                break;
            case R.id.linearOutBox:
                viewpager.setCurrentItem(1);
                view1.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.VISIBLE);
                view3.setVisibility(View.INVISIBLE);
                break;
            case R.id.linearUsers:
                viewpager.setCurrentItem(2);
                view1.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.INVISIBLE);
                view3.setVisibility(View.VISIBLE);
                break;
        }
    }
}
