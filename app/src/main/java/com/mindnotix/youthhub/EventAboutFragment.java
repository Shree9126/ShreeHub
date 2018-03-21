package com.mindnotix.youthhub;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mindnotix.eventbus.Events;
import com.mindnotix.eventbus.GlobalBus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class EventAboutFragment extends Fragment {

    private static final String TAG = EventAboutFragment.class.getSimpleName();
    boolean isCreate = false;
    private TextView txtEventDescription;
    private TextView txtDescriptionTitle;
    private TextView txtEventAddress;
    private TextView txtEventDistrict;
    private TextView txtEventRegion;
    private TextView txtEventContact;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("run", "onCreateView: ");
        if (getActivity() != null) {
            ((EventDetailsActivity) getActivity()).setFragInstance(getTag());
        }
        //    return
        View view = inflater.inflate(R.layout.fragment_event_about, container, false);
        UiInitialization(view);
        isCreate = true;
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        GlobalBus.getBus().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "onPause: ");
        Events.AboutFragmentEvent activityActivityMessageEvent = EventBus.getDefault().getStickyEvent(Events.AboutFragmentEvent.class);
        // Better check that an event was actually posted before
        if (activityActivityMessageEvent != null) {
            // "Consume" the sticky event
            EventBus.getDefault().removeStickyEvent(activityActivityMessageEvent);
            // Now do something with it
        }
        GlobalBus.getBus().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");

        Events.AboutFragmentEvent activityActivityMessageEvent = EventBus.getDefault().getStickyEvent(Events.AboutFragmentEvent.class);
        // Better check that an event was actually posted before
        if (activityActivityMessageEvent != null) {
            // "Consume" the sticky event
            EventBus.getDefault().removeStickyEvent(activityActivityMessageEvent);
            // Now do something with it
        }
        GlobalBus.getBus().unregister(this);
    }

    private void UiInitialization(View view) {

        this.txtEventContact = (TextView) view.findViewById(R.id.txtEventContact);
        this.txtEventRegion = (TextView) view.findViewById(R.id.txtEventRegion);
        this.txtEventDistrict = (TextView) view.findViewById(R.id.txtEventDistrict);
        this.txtEventAddress = (TextView) view.findViewById(R.id.txtEventAddress);
        this.txtEventDescription = (TextView) view.findViewById(R.id.txtEventDescription);
        this.txtDescriptionTitle = (TextView) view.findViewById(R.id.txtDescriptionTitle);

        txtEventDescription.setText(EventDetailsActivity.description);
        txtEventRegion.setText(EventDetailsActivity.description);
        txtEventDistrict.setText(EventDetailsActivity.description);
        txtEventAddress.setText(EventDetailsActivity.description);
        txtEventContact.setText(EventDetailsActivity.description);
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getMessage(Events.AboutFragmentEvent activityRefreshCommandsCount) {

        Log.d(TAG, "getMessage:Chat state " + activityRefreshCommandsCount.getDescription());
        txtEventDescription.setText(activityRefreshCommandsCount.getDescription());
        txtEventRegion.setText(activityRefreshCommandsCount.getRegion());
        txtEventDistrict.setText(activityRefreshCommandsCount.getDistrict());
        txtEventAddress.setText(activityRefreshCommandsCount.getAddress());
        txtEventContact.setText(activityRefreshCommandsCount.getContact());
    }


    public void setText(String description, String region, String district, String address, String contact) {
        Log.d(TAG, "setText: " + description);

        if (!description.equals("") && description != null)
            txtEventDescription.setText(description);
        else
            txtDescriptionTitle.setVisibility(View.GONE);

        txtEventRegion.setText(region);
        txtEventDistrict.setText(district);
        txtEventAddress.setText(address);
        txtEventContact.setText(contact);
    }
}
