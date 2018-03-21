package com.mindnotix.youthhub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Admin on 2/17/2018.
 */

public class SampleFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d("SampleFragment", "onCreateView: ");
        if (getActivity() != null) {
            ((EventDetailsActivity) getActivity()).setFragInstance(getTag());
        }

        return inflater.inflate(R.layout.fragment_event_about, container, false);
    }

    public void setText(String description, String region, String district, String address, String contact) {
        Log.d("SampleFragment", "setText: reached "+ description);
    }
}
