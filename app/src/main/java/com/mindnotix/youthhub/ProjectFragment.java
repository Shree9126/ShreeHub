package com.mindnotix.youthhub;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindnotix.adapter.YourProfilePostedStoriesAdapter;
import com.mindnotix.adapter.YourProfileProjectsAdatper;

/**
 * Created by Admin on 1/13/2018.
 */

public class ProjectFragment extends Fragment {


    RecyclerView projectList;
    YourProfileProjectsAdatper adapter;
    public ProjectFragment() {
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


        View view = inflater.inflate(R.layout.project_fragment, container, false);
        UiInitialization(view);

        adapter = new YourProfileProjectsAdatper(getActivity());
        projectList.setAdapter(adapter);
        return view;
    }

    private void UiInitialization(View view) {

        projectList = (RecyclerView) view.findViewById(R.id.yourProfileprojectList);
        projectList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }
}
