package com.mindnotix.youthhub.ExploreFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindnotix.adapter.TagListAdapter;
import com.mindnotix.model.explore.explorerView.Topic_list;
import com.mindnotix.youthhub.ExploreViewActivity;
import com.mindnotix.youthhub.R;

import java.util.ArrayList;

/**
 * Created by Admin on 22-02-2018.
 */

public class TagListFragment extends Fragment {

    RecyclerView recyclerView;
    TagListAdapter tagListAdapter;
    ExploreViewActivity exploreViewActivity;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.activity_my_tag_list, container, false);

        exploreViewActivity = (ExploreViewActivity) getActivity();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;
    }

    ArrayList<Topic_list> topic_lists;

    public void load(ArrayList<Topic_list> topicList) {
        this.topic_lists = topicList;

        tagListAdapter = new TagListAdapter(getActivity(), topic_lists);
        recyclerView.setAdapter(tagListAdapter);


    }


    @Override
    public void onResume() {
        exploreViewActivity.loadViewExplorer(ExploreViewActivity.explorerId);
        super.onResume();
    }


    @Override
    public void onDestroyView() {
        if(topic_lists!=null){
            if(topic_lists.size()>0)
            {
                topic_lists.clear();
            }
        }

        ExploreViewActivity.status=true;
        super.onDestroyView();
    }


}
