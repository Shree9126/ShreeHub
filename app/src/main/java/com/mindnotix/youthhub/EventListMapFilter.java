package com.mindnotix.youthhub;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.adapter.EventListMapFilterAdapter;
import com.mindnotix.adapter.MapJobFIlterAdapter;
import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.events.list.DataEventsListItems;
import com.mindnotix.model.events.list.Event_list;
import com.mindnotix.model.jobs.filter_master.Categoryview;
import com.mindnotix.model.jobs.filter_master.DataJobMasterFilter;
import com.mindnotix.model.jobs.filter_master.Regional_council_list;
import com.mindnotix.model.jobs.filter_master.local_board.DataLocalBoardList;
import com.mindnotix.model.jobs.filter_master.local_board.Local_board_list;
import com.mindnotix.model.jobs.list.Data;
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
 * Created by Admin on 2/23/2018.
 */

public class EventListMapFilter extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = MapJobFilterActivity.class.getSimpleName();
    private static RelativeLayout bottomLayout;
    MapJobFIlterAdapter mapJobFIlterAdapter;
    ProgressDialog progressDialog;
    String lattitude, longtitude, companyName;
    ArrayList<Event_list> event_listArrayList;
    ArrayList<Data> dataJobArrayList = new ArrayList<>();
    RecyclerViewClickListener listener;
    ArrayAdapter<Regional_council_list> regional_council_listArrayAdapter;
    ArrayList<Local_board_list> local_board_listArrayList = new ArrayList<>();
    ArrayAdapter<Local_board_list> local_board_listArrayAdapter;
    ArrayList<Regional_council_list> regional_council_listArrayList = new ArrayList<>();
    ArrayList<Categoryview> categoryviewArrayList = new ArrayList<>();
    ArrayAdapter<Categoryview> categoryviewArrayAdapter;
    EventListMapFilterAdapter eventListMapFilterAdapter;
    LinearLayoutManager linearLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    String NextPage = "";
    private android.support.v7.widget.Toolbar toolbar;
    private android.widget.TextView txtjosss;
    private android.widget.ImageView imgFilter;
    private android.support.v7.widget.RecyclerView recyclerViewJobList;
    private android.widget.Button btnSearch;
    private android.widget.Spinner spnrDistrictCity;
    private android.widget.Spinner spnrRegionLocation;
    private android.widget.Spinner spnrJobCategory;
    private android.widget.EditText edtJobSearch;
    private boolean userScrolled = true;
    private SwipeRefreshLayout swipeRefreshLayout;

    private String jobSearch="";
    private int region=0;

    @Override
    public void onRefresh() {

        swipeRefreshLayout.setRefreshing(true);
        getEventList();
    }

    private void implementScrollListener() {

        recyclerViewJobList
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
                            //loadMoreListItems();
                        }

                    }

                });
    }

    private void loadMoreListItems() {

        // Show Progress Layout
        bottomLayout.setVisibility(View.VISIBLE);

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
                //   progressDialog.dismiss();
                // Show Progress Layout
                bottomLayout.setVisibility(View.GONE);

                // stopping swipe refresh
                // swipeRefreshLayout.setRefreshing(false);

                Log.d(TAG, "onResponse:DataDashboardTimeLine " + new Gson().toJson(response.body()));
                if (response.code() == 200) {

                    if (response.body().getStatus().equals("1")) {
                        event_listArrayList.addAll(response.body().getData().getEvent_list());

                        //    eventListMapFilterAdapter.notifyDataSetChanged();
                        eventListMapFilterAdapter = new EventListMapFilterAdapter(listener, EventListMapFilter.this, event_listArrayList);
                        recyclerViewJobList.setAdapter(eventListMapFilterAdapter);

                    } else {
                        Log.d(TAG, "onResponse: else");

                    }
                } else if (response.code() == 304) {

                    Toast.makeText(EventListMapFilter.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(EventListMapFilter.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(EventListMapFilter.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(EventListMapFilter.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(EventListMapFilter.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(EventListMapFilter.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    // Toast.makeText(getActivity(), "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<DataEventsListItems> call, Throwable t) {
                //   progressDialog.dismiss();
                // stopping swipe refresh
                // swipeRefreshLayout.setRefreshing(false);
                // Show Progress Layout
                bottomLayout.setVisibility(View.GONE);

                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_event_filter_activity);
        UiInitialization();

        listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {


            }
        };


        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                    }
                                }
        );


        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(EventListMapFilter.this);
                dialog.setContentView(R.layout.dialog_event_map_filter);
                // Set dialog title
                dialog.setTitle("Custom Dialog");

                btnSearch = (Button) dialog.findViewById(R.id.btnSearch);
                spnrDistrictCity = (Spinner) dialog.findViewById(R.id.spnrDistrictCity);
                spnrRegionLocation = (Spinner) dialog.findViewById(R.id.spnrRegionLocation);
                edtJobSearch = (EditText) dialog.findViewById(R.id.edtJobSearch);
                dialog.show();


                regional_council_listArrayAdapter = new ArrayAdapter<Regional_council_list>(EventListMapFilter.this,
                        android.R.layout.simple_spinner_item, regional_council_listArrayList);
                regional_council_listArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrRegionLocation.setAdapter(regional_council_listArrayAdapter);


                edtJobSearch.setText(jobSearch);

                Log.v("spinnerSelecte"," "+region);

                spnrRegionLocation.setSelection(region);


                spnrRegionLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        if (!regional_council_listArrayList.get(position).getId().equals("0")) {
                            getLocalBoardList(regional_council_listArrayList.get(position).getId());
                        } else {

                            local_board_listArrayList.clear();

                            Local_board_list sortby = new Local_board_list();
                            sortby.setId("0");
                            sortby.setName("All");
                            local_board_listArrayList.add(0, sortby);
                            local_board_listArrayAdapter = new ArrayAdapter<Local_board_list>(EventListMapFilter.this,
                                    android.R.layout.simple_spinner_item, local_board_listArrayList);
                            local_board_listArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spnrDistrictCity.setAdapter(local_board_listArrayAdapter);

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                btnSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        getEventListSearch(edtJobSearch.getText().toString());
                    }
                });
            }
        });
    }


    private void getLocalBoardList(String name) {
        local_board_listArrayList.clear();

        Log.d(TAG, "getLocalBoardList: " + name);
//        progressDialog = Utils.createProgressDialog(this);
        //     String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTU3IiwidXNlcm5hbWUiOiJzcmlkaGFyYW4ubWluZG5vdGl4QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTg2MDkwNTAsImp0aSI6IlFYVkdUVEJ3V0dOMlNGWjBkVWxVV1hKT2JEWT0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE4NjA5MDUwLCJleHAiOjE1MTkyMTM4NTB9.-1YP2zxdrvMmlc2mfH6CXyCUxdqRObTFI6GSUByJ84I";

        String token = Pref.getPreToken();


        Map<String, String> data = new HashMap<>();
        data.put("regional_id", name);
        //   data.put("is_user_id", "0");
        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


        //token = "Bearer "+ Pref.getPreToken();


        Log.d(TAG, "changePassword: token " + token);

        Call<DataLocalBoardList> call = apiService.getLocalBoardList(token, data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataLocalBoardList>() {
            @Override
            public void onResponse(Call<DataLocalBoardList> call, Response<DataLocalBoardList> response) {

               // progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("1")) {
                        local_board_listArrayList.addAll(response.body().getData().getLocal_board_list());

                        Local_board_list sortby = new Local_board_list();
                        sortby.setId("0");
                        sortby.setName("All");
                        local_board_listArrayList.add(0, sortby);
                        local_board_listArrayAdapter = new ArrayAdapter<Local_board_list>(EventListMapFilter.this,
                                android.R.layout.simple_spinner_item, local_board_listArrayList);
                        local_board_listArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnrDistrictCity.setAdapter(local_board_listArrayAdapter);


                    } else {

                        Toast.makeText(EventListMapFilter.this, "No record available", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 304) {

                    Toast.makeText(EventListMapFilter.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(EventListMapFilter.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(EventListMapFilter.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(EventListMapFilter.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(EventListMapFilter.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(EventListMapFilter.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(EventListMapFilter.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<DataLocalBoardList> call, Throwable t) {
               // progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }


    private void UiInitialization() {

        this.recyclerViewJobList = (RecyclerView) findViewById(R.id.recyclerViewJobList);

        bottomLayout = (RelativeLayout) findViewById(R.id.loadItemsLayout_recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewJobList.setHasFixedSize(true);
        recyclerViewJobList.setLayoutManager(linearLayoutManager);
        this.imgFilter = (ImageView) findViewById(R.id.imgFilter);
        this.txtjosss = (TextView) findViewById(R.id.txtjosss);
        this.txtjosss.setText("Browse Events");
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Events Filter");

        } catch (Exception e) {
            e.printStackTrace();
        }


        getMasterData();
    }

    private void getMasterData() {


       // progressDialog = Utils.createProgressDialog(this);
        //  String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTU3IiwidXNlcm5hbWUiOiJzcmlkaGFyYW4ubWluZG5vdGl4QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTg2MDkwNTAsImp0aSI6IlFYVkdUVEJ3V0dOMlNGWjBkVWxVV1hKT2JEWT0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE4NjA5MDUwLCJleHAiOjE1MTkyMTM4NTB9.-1YP2zxdrvMmlc2mfH6CXyCUxdqRObTFI6GSUByJ84I";

        String token = Pref.getPreToken();


        Map<String, String> data = new HashMap<>();
        data.put("profileid", Pref.getClientId());
        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


        //token = "Bearer "+ Pref.getPreToken();


        Log.d(TAG, "changePassword: token " + token);

        Call<DataJobMasterFilter> call = apiService.getJobMasterFilter(token);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataJobMasterFilter>() {
            @Override
            public void onResponse(Call<DataJobMasterFilter> call, Response<DataJobMasterFilter> response) {

               // progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("1")) {

                        categoryviewArrayList.addAll(response.body().getData().getCategoryview());


                        regional_council_listArrayList.addAll(response.body().getData().getRegional_council_list());
                        final Regional_council_list regional_council_list = new Regional_council_list();
                        regional_council_list.setId("0");
                        regional_council_list.setName("All");
                        regional_council_listArrayList.add(0, regional_council_list);

                        if (regional_council_listArrayAdapter != null)
                            regional_council_listArrayAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        getEventList();
                    } else {

                        Toast.makeText(EventListMapFilter.this, "No record available", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 304) {

                    Toast.makeText(EventListMapFilter.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(EventListMapFilter.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(EventListMapFilter.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(EventListMapFilter.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(EventListMapFilter.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(EventListMapFilter.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(EventListMapFilter.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<DataJobMasterFilter> call, Throwable t) {
              //  progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void getEventList() {


        //progressDialog = Utils.createProgressDialog(this);
        event_listArrayList = new ArrayList<>();
        event_listArrayList.clear();
        Map<String, String> data = new HashMap<>();
        data.put("ismyevent", "3");
        data.put("ismap ", "1");

        String token = Pref.getPreToken();
        Log.d(TAG, "logMeIn onResponse: token " + token);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataEventsListItems> call = apiService.getEventsList(token, data);
        Log.d(TAG, "DataEventsListItems: first time url " + call.request().url());

        call.enqueue(new Callback<DataEventsListItems>() {
            @Override
            public void onResponse(Call<DataEventsListItems> call, Response<DataEventsListItems> response) {
                //   progressDialog.dismiss();

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);

                Log.d(TAG, "onResponse:DataDashboardTimeLine " + new Gson().toJson(response.body()));
                if (response.code() == 200) {

                    if (response.body().getStatus().equals("1")) {
                        event_listArrayList.addAll(response.body().getData().getEvent_list());

                        //    eventListMapFilterAdapter.notifyDataSetChanged();
                        eventListMapFilterAdapter = new EventListMapFilterAdapter(listener, EventListMapFilter.this, event_listArrayList);
                        recyclerViewJobList.setAdapter(eventListMapFilterAdapter);

                    } else {
                        Log.d(TAG, "onResponse: else");

                    }
                } else if (response.code() == 304) {

                    Toast.makeText(EventListMapFilter.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(EventListMapFilter.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(EventListMapFilter.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(EventListMapFilter.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(EventListMapFilter.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(EventListMapFilter.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    // Toast.makeText(getActivity(), "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<DataEventsListItems> call, Throwable t) {
                //   progressDialog.dismiss();
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);

                t.printStackTrace();
            }
        });
    }


    private void getEventListSearch(String search) {


        String spnrRegionTxt_ID = "";
        String spnrDistrictTxt_ID = "";


        Log.d(TAG, "getEventListSearch: dsfsdf " + regional_council_listArrayList.get(spnrRegionLocation.getSelectedItemPosition()).getId());

        if (!regional_council_listArrayList.get(spnrRegionLocation.getSelectedItemPosition()).getName().equals("All"))
            spnrRegionTxt_ID = regional_council_listArrayList.get(spnrRegionLocation.getSelectedItemPosition()).getId();

        if (local_board_listArrayList.size() > 0) {
            if (!local_board_listArrayList.get(spnrDistrictCity.getSelectedItemPosition()).getName().equals("All"))
                spnrDistrictTxt_ID = local_board_listArrayList.get(spnrDistrictCity.getSelectedItemPosition()).getId();
        }

        swipeRefreshLayout.setRefreshing(true);
   //     progressDialog = Utils.createProgressDialog(this);

        event_listArrayList = new ArrayList<>();
        Map<String, String> data = new HashMap<>();
        data.put("ismyevent", "3");
        data.put("ismap", "1");
        data.put("event_name", search);
        data.put("region_id", spnrRegionTxt_ID);
        data.put("local_board_id", spnrDistrictTxt_ID);

        String token = Pref.getPreToken();
        Log.d(TAG, "logMeIn onResponse: token " + token);


        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("  getEventListSearch  key, " + key + " value " + value);
        }
        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataEventsListItems> call = apiService.getEventsList(token, data);
        Log.d(TAG, "DataEventsListItems: first time url " + call.request().url());

        call.enqueue(new Callback<DataEventsListItems>() {
            @Override
            public void onResponse(Call<DataEventsListItems> call, Response<DataEventsListItems> response) {
              //  progressDialog.dismiss();

                swipeRefreshLayout.setRefreshing(false);
                Log.d(TAG, "onResponse:DataDashboardTimeLine " + new Gson().toJson(response.body()));
                if (response.code() == 200) {

                    if (response.body().getStatus().equals("1")) {



                        event_listArrayList.addAll(response.body().getData().getEvent_list());
                        Log.d(TAG, "onResponse:aaaaaaaaaaaa size " + event_listArrayList.size());
                        eventListMapFilterAdapter = new EventListMapFilterAdapter(listener, EventListMapFilter.this, event_listArrayList);
                        recyclerViewJobList.setAdapter(eventListMapFilterAdapter);

                    } else {
                        Log.d(TAG, "onResponse: else");

                    }
                } else if (response.code() == 304) {

                    Toast.makeText(EventListMapFilter.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(EventListMapFilter.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(EventListMapFilter.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(EventListMapFilter.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(EventListMapFilter.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(EventListMapFilter.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    // Toast.makeText(getActivity(), "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<DataEventsListItems> call, Throwable t) {
               // progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                t.printStackTrace();
            }
        });


        jobSearch=search;
        region= spnrRegionLocation.getSelectedItemPosition();

    }


}