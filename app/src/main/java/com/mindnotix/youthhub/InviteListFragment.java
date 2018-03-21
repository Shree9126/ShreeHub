package com.mindnotix.youthhub;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mindnotix.adapter.AppliedListAdapter;
import com.mindnotix.adapter.InviteListAdapter;

import com.mindnotix.model.BasicResponse;

import com.mindnotix.model.jobs.my_jobs.InvitedList;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 15-02-2018.
 */

public class InviteListFragment extends Fragment {

    ProgressBar progressBar;
    View progressLayout;
    ArrayList<InvitedList> invitedLists;
    InviteListAdapter inviteListAdapter;
    RecyclerView recyclerView;
    View noJobsLayout;
    View mainLoadView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_invite_list_fragment, container, false);
        intiUi(view);
        return view;
    }

    private void intiUi(View view) {

        progressBar = view.findViewById(R.id.progressBar);
        progressLayout = view.findViewById(R.id.progressLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        noJobsLayout = view.findViewById(R.id.noJobsLayout);
        mainLoadView = view.findViewById(R.id.mainLoadView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        invitedLists = new ArrayList<>();

        loadMyJobs();
    }


    Call<BasicResponse> loadjobs;


    private void loadMyJobs() {
        progressLayout.setVisibility(View.VISIBLE);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        loadjobs = apiService.loadMyJobs(Pref.getPreToken());

        loadjobs.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(@NonNull Call<BasicResponse> call, @NonNull Response<BasicResponse> response) {

                if (response.code() == 200) {
                    progressLayout.setVisibility(View.GONE);
                    BasicResponse basicResponse = response.body();

                    if (basicResponse.getStatus().equals("1")) {

                        try {

                            if (!basicResponse.getData().getInvited_list_count().equals("0")
                                    && !basicResponse.getData().getInvited_list_count().isEmpty()) {


                                progressLayout.setVisibility(View.GONE);
                                noJobsLayout.setVisibility(View.GONE);
                                mainLoadView.setVisibility(View.VISIBLE);

                                invitedLists.addAll(basicResponse.getData().getInvited_list());

                                inviteListAdapter = new InviteListAdapter(getActivity(), invitedLists);
                                recyclerView.setAdapter(inviteListAdapter);

                            } else {
                                noJobsLayout.setVisibility(View.VISIBLE);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        Toast.makeText(getActivity(), basicResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else if (response.code() == 201) {
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "201 Created", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 304) {
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "304 Not Modified", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 400) {
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "400 Bad Request", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 401) {
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "401 Unauthorized", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 403) {
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "403 Forbidden", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 404) {
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "404 Not Found", Toast.LENGTH_SHORT).show();


                } else if (response.code() == 422) {
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "500 Internal Server Error", Toast.LENGTH_SHORT).show();


                }

            }

            @Override
            public void onFailure(@NonNull Call<BasicResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                progressLayout.setVisibility(View.GONE);
            }
        });


    }
}
