package com.mindnotix.youthhub;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.adapter.EventParticipantsListAdapter;
import com.mindnotix.adapter.JobListAdapter;
import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.events.attend_list.DataCounMeInAttendList;
import com.mindnotix.model.events.attend_list.Eventcountmelist;
import com.mindnotix.model.jobs.list.DataJobListItems;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Pref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventParticipantsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = EventParticipantsActivity.class.getSimpleName();
    ArrayList<Eventcountmelist> eventcountmelistArrayList;
    private android.support.v7.widget.Toolbar toolbar;
    private android.support.v7.widget.RecyclerView recyclerViewParticipantsList;
    private android.widget.TextView txtEmpty;
    private RelativeLayout loadItemsLayoutrecyclerView;
    private android.support.v4.widget.SwipeRefreshLayout swipeRefreshLayout;
    EventParticipantsListAdapter eventParticipantsListAdapter;
    List<String> pageIdList = new ArrayList<>();
    RecyclerViewClickListener listener;
    ProgressDialog progressDialog;
    LinearLayoutManager linearLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    String NextPage = "";
    // Variables for scroll listener
    private boolean userScrolled = true;

    String event_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_participants);
        UiInitialization();

        if (getIntent() != null) {
            event_id = getIntent().getStringExtra("event_id");
        }
        implementScrollListener();
        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        NextPage = "";
                                        getParticipantsList();
                                    }
                                }
        );


    }

    private void UiInitialization() {

        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        this.loadItemsLayoutrecyclerView = (RelativeLayout) findViewById(R.id.loadItemsLayout_recyclerView);
        this.txtEmpty = (TextView) findViewById(R.id.txtEmpty);
        this.recyclerViewParticipantsList = (RecyclerView) findViewById(R.id.recyclerViewParticipantsList);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewParticipantsList.setHasFixedSize(true);
        recyclerViewParticipantsList.setLayoutManager(linearLayoutManager);


        swipeRefreshLayout.setOnRefreshListener(this);

        setSupportActionBar(toolbar);
        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();

        if (!nbIsNetworkAvailable(this)) {
            nbShowNoInternet(this);
        }

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        NextPage = "";
        getParticipantsList();
    }

    private void implementScrollListener() {

        recyclerViewParticipantsList
                .addOnScrollListener(new RecyclerView.OnScrollListener() {

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView,
                                                     int newState) {

                        super.onScrollStateChanged(recyclerView, newState);

                        // If scroll state is touch scroll then set userScrolled
                        // true
                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                            userScrolled = true;

                        }

                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx,
                                           int dy) {

                        super.onScrolled(recyclerView, dx, dy);
                        // Here get the child count, item count and visibleitems
                        // from layout manager

                        visibleItemCount = linearLayoutManager.getChildCount();
                        totalItemCount = linearLayoutManager.getItemCount();
                        pastVisiblesItems = linearLayoutManager
                                .findFirstVisibleItemPosition();

                        // Now check if userScrolled is true and also check if
                        // the item is end then update recycler view and set
                        // userScrolled to false
                        if (userScrolled
                                && (visibleItemCount + pastVisiblesItems) == totalItemCount) {
                            userScrolled = false;
                            loadMoreListItems();
                        }

                    }

                });
    }

    private void loadMoreListItems() {

        if (!pageIdList.contains(NextPage)) {

            String token = Pref.getPreToken();

            loadItemsLayoutrecyclerView.setVisibility(View.VISIBLE);
            Map<String, String> data = new HashMap<>();
            data.put("event_id", event_id);
            data.put("fm", NextPage);

            pageIdList.add(NextPage);

            for (Map.Entry<String, String> entry : data.entrySet()) {
                String key = entry.getKey().toString();
                String value = entry.getValue();
                System.out.println("getParticipantsList  key, " + key + " value " + value);
            }


            YouthHubApi apiService =
                    ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


            //token = "Bearer "+ Pref.getPreToken();


            Log.d(TAG, "changePassword: token " + token);

            Call<DataCounMeInAttendList> call = apiService.getEventParticipantsLists(token, data);
            Log.d(TAG, "logMeIn: url " + call.request().url());

            call.enqueue(new Callback<DataCounMeInAttendList>() {
                @Override
                public void onResponse(Call<DataCounMeInAttendList> call, Response<DataCounMeInAttendList> response) {
                    // stopping swipe refresh
                    loadItemsLayoutrecyclerView.setVisibility(View.GONE);
                    try {

                        Log.d(TAG, "onResponse:DataJobListItems " + new Gson().toJson(response.body()));

                        //   progressDialog.dismiss();
                        if (response.code() == 200) {
                            if (response.body().getStatus().equals("1")) {


                                eventcountmelistArrayList.addAll(response.body().getData().getEventcountmelist());

                                NextPage = response.body().getNextpage();
                                eventParticipantsListAdapter.notifyDataSetChanged();


                            }
                        } else if (response.code() == 304) {

                            Toast.makeText(EventParticipantsActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 400) {

                            Toast.makeText(EventParticipantsActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 401) {

                            Toast.makeText(EventParticipantsActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 403) {

                            Toast.makeText(EventParticipantsActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 404) {

                            Toast.makeText(EventParticipantsActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                        } else if (response.code() == 422) {

                            Toast.makeText(EventParticipantsActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(EventParticipantsActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<DataCounMeInAttendList> call, Throwable t) {
                    // progressDialog.dismiss();
                    // stopping swipe refresh
                    pageIdList.remove(NextPage);
                    loadItemsLayoutrecyclerView.setVisibility(View.GONE);
                    t.printStackTrace();
                }
            });
        }

    }


    private void getParticipantsList() {

        pageIdList.clear();
        eventcountmelistArrayList = new ArrayList<>();

        //     progressDialog = Utils.createProgressDialog(this );
        String token = Pref.getPreToken();


        Map<String, String> data = new HashMap<>();
        data.put("event_id", event_id);
        data.put("fm", "");


        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("getParticipantsList  key, " + key + " value " + value);
        }


        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


        //token = "Bearer "+ Pref.getPreToken();


        Log.d(TAG, "changePassword: token " + token);

        Call<DataCounMeInAttendList> call = apiService.getEventParticipantsLists(token, data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataCounMeInAttendList>() {
            @Override
            public void onResponse(Call<DataCounMeInAttendList> call, Response<DataCounMeInAttendList> response) {
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
                try {

                    Log.d(TAG, "onResponse:DataJobListItems " + new Gson().toJson(response.body()));

                    //   progressDialog.dismiss();
                    if (response.code() == 200) {
                        if (response.body().getStatus().equals("1")) {

                            txtEmpty.setVisibility(View.GONE);
                            recyclerViewParticipantsList.setVisibility(View.VISIBLE);
                            eventcountmelistArrayList.addAll(response.body().getData().getEventcountmelist());

                            NextPage = response.body().getNextpage();

                            String image_path = response.body().getData().getImage_path();

                            eventParticipantsListAdapter = new EventParticipantsListAdapter(eventcountmelistArrayList,
                                    EventParticipantsActivity.this, image_path);
                            recyclerViewParticipantsList.setAdapter(eventParticipantsListAdapter);

                        } else {
                            txtEmpty.setVisibility(View.VISIBLE);
                            recyclerViewParticipantsList.setVisibility(View.GONE);

                        }
                    } else if (response.code() == 304) {

                        Toast.makeText(EventParticipantsActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(EventParticipantsActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(EventParticipantsActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(EventParticipantsActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(EventParticipantsActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(EventParticipantsActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(EventParticipantsActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<DataCounMeInAttendList> call, Throwable t) {
                // progressDialog.dismiss();
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
                t.printStackTrace();
            }
        });
    }


}
