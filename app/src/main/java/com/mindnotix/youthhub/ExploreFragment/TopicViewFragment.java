package com.mindnotix.youthhub.ExploreFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mindnotix.model.explore.explorerView.Content_list;
import com.mindnotix.model.explore.explorerView.ExplorerBasicView;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.youthhub.ExploreViewActivity;
import com.mindnotix.youthhub.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 22-02-2018.
 */

public class TopicViewFragment extends Fragment {

    RecyclerView recyclerView;
    ExploreViewActivity exploreViewActivity;
    TagListFragment tagListFragment;
    View progressBarLayout;
    ArrayList<Content_list> content_lists;
    Call<ExplorerBasicView> loadViewExplorerAndTag;
    TopicViewAdapter topicViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_my_topic_view_list, container, false);

        exploreViewActivity = (ExploreViewActivity) getActivity();



        recyclerView = view.findViewById(R.id.recyclerView);
        progressBarLayout = view.findViewById(R.id.progressBarLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;
    }

    public void loadViewExplorer(String explorerId, String topicId) {
        recyclerView.setVisibility(View.GONE);

        content_lists = new ArrayList<>();


        progressBarLayout.setVisibility(View.GONE);
        Map<String, String> data = new HashMap<>();
        data.put("exploreid", explorerId);
        data.put("event_topic_id", topicId);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        loadViewExplorerAndTag = apiService.loadExplorerView(Pref.getPreToken(), data);

        loadViewExplorerAndTag.enqueue(new Callback<ExplorerBasicView>() {
            @Override
            public void onResponse(@NonNull Call<ExplorerBasicView> call, @NonNull Response<ExplorerBasicView> response) {

                progressBarLayout.setVisibility(View.GONE);

                try {

                    if (response.code() == 200 || response.code() == 201) {


                        ExplorerBasicView explorerBasicView = response.body();

                        Pref.setdevicetoken("Bearer " + explorerBasicView.getToken());

                        if (explorerBasicView.getStatus().equals("1")) {

                            progressBarLayout.setVisibility(View.GONE);
                            content_lists.addAll(explorerBasicView.getData().getContent_list());


                            TopicViewAdapter topicViewAdapter = new TopicViewAdapter(getActivity(), content_lists,
                                    explorerBasicView.getData().getPath_image(), explorerBasicView.getData().getPath_pdf());
                            recyclerView.setAdapter(topicViewAdapter);

                            recyclerView.setVisibility(View.VISIBLE);

                        }
                    } else if (response.code() == 304) {
                        progressBarLayout.setVisibility(View.GONE);

                        Toast.makeText(getActivity(), "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {
                        progressBarLayout.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {
                        progressBarLayout.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {
                        progressBarLayout.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {
                        progressBarLayout.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {
                        progressBarLayout.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {
                        progressBarLayout.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    progressBarLayout.setVisibility(View.GONE);
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(@NonNull Call<ExplorerBasicView> call, @NonNull Throwable t) {
                progressBarLayout.setVisibility(View.GONE);
                t.printStackTrace();

            }
        });


    }


    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onDetach() {

        super.onDetach();
    }


    @Override
    public void onStop() {

        super.onStop();
    }


}