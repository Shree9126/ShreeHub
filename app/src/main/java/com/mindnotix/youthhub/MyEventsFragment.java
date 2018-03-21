package com.mindnotix.youthhub;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.adapter.EventsListAdapter;
import com.mindnotix.model.events.list.DataEventsListItems;
import com.mindnotix.model.events.list.Event_list;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 2/16/2018.
 */

public class MyEventsFragment extends Fragment {



    private static final String TAG = AllEventsFragment.class.getSimpleName();
    TextView edtPost, txtEmpty;
    ProgressDialog progressDialog;
    RecyclerView recyclerViewEventList;
    ArrayList<Event_list> event_listArrayList = new ArrayList<>();
    EventsListAdapter adapter;
    private Gson mGson;

    protected Gson getGson() {
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //return inflater.inflate(R.layout.all_events_fragment, container, false);
        View view = inflater.inflate(R.layout.all_events_fragment, container, false);

        UiInitialization(view);
        return view;

    }

    private void UiInitialization(View view) {

        this.txtEmpty = (TextView) view.findViewById(R.id.txtEmpty);
        this.recyclerViewEventList = (RecyclerView) view.findViewById(R.id.recyclerViewEventList);
        this.recyclerViewEventList.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerViewEventList.setHasFixedSize(true);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public void onResume() {
        super.onResume();

        getAllEvents();
    }

    private void getAllEvents() {

        progressDialog = Utils.createProgressDialog(getActivity());

        event_listArrayList.clear();
        Map<String, String> data = new HashMap<>();
        data.put("ismyevent", "3");

        String token = Pref.getPreToken();
        Log.d(TAG, "logMeIn onResponse: token " + token);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataEventsListItems> call = apiService.getEventsList(token, data);
        Log.d(TAG, "DataEventsListItems: first time url " + call.request().url());

        call.enqueue(new Callback<DataEventsListItems>() {
            @Override
            public void onResponse(Call<DataEventsListItems> call, Response<DataEventsListItems> response) {
                progressDialog.dismiss();


                Log.d(TAG, "onResponse:DataDashboardTimeLine " + new Gson().toJson(response.body()));
                if (response.code() == 200) {

                    if (response.body().getStatus().equals("1")) {
                        event_listArrayList.addAll(response.body().getData().getEvent_list());


                        String    path_image = response.body().getData().getPath_image();
                        adapter = new EventsListAdapter(getActivity(), event_listArrayList,path_image);
                        recyclerViewEventList.setAdapter(adapter);

                    } else {
                        Log.d(TAG, "onResponse: else");
                        recyclerViewEventList.setVisibility(View.GONE);
                        txtEmpty.setVisibility(View.VISIBLE);
                    }
                } else if (response.code() == 304) {

                    Toast.makeText(getActivity(), "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(getActivity(), "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(getActivity(), "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(getActivity(), "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(getActivity(), "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(getActivity(), "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getActivity(), "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<DataEventsListItems> call, Throwable t) {
                progressDialog.dismiss();
                recyclerViewEventList.setVisibility(View.GONE);
                txtEmpty.setVisibility(View.VISIBLE);
                t.printStackTrace();
            }
        });
    }

}
