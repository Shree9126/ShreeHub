package com.mindnotix.youthhub;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mindnotix.adapter.YourProfilePostedStoriesAdapter;

/**
 * Created by Admin on 1/13/2018.
 */

public class PostedStoriesFragment extends Fragment {

    RecyclerView postedStoriesList;
    ImageView imgEducation, imgTrainning, imgMyInterest, imgEmployment, imgCommunity, imgEvents, imgVolunteer;
    YourProfilePostedStoriesAdapter adapter;
    public PostedStoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.posted_stories_fragment, container, false);
        UiInitialization(view);

        adapter = new YourProfilePostedStoriesAdapter(getActivity());
        postedStoriesList.setAdapter(adapter);
        return view;

    }

    private void UiInitialization(View view) {

        postedStoriesList = (RecyclerView) view.findViewById(R.id.postedStoriesList);
        postedStoriesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        imgCommunity = (ImageView) view.findViewById(R.id.imgCommunity);
        imgEvents = (ImageView) view.findViewById(R.id.imgEvents);
        imgVolunteer = (ImageView) view.findViewById(R.id.imgVolunteer);
        imgEmployment = (ImageView) view.findViewById(R.id.imgEmployment);
        imgMyInterest = (ImageView) view.findViewById(R.id.imgMyInterest);
        imgTrainning = (ImageView) view.findViewById(R.id.imgTrainning);
        imgEducation = (ImageView) view.findViewById(R.id.imgEducation);
    }
}
