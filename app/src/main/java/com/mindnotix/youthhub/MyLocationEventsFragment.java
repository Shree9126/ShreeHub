package com.mindnotix.youthhub;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.adapter.EventsListAdapter;
import com.mindnotix.listener.RecyclerViewLoadMoreScroll;
import com.mindnotix.model.events.list.DataEventsListItems;
import com.mindnotix.model.events.list.Event_list;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 2/16/2018.
 */

public class MyLocationEventsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = AllEventsFragment.class.getSimpleName();
    private static RelativeLayout bottomLayout;
    TextView edtPost, txtEmpty;
    ProgressDialog progressDialog;
    RecyclerView recyclerViewEventList;
    ArrayList<Event_list> event_listArrayList = new ArrayList<>();
    EventsListAdapter adapter;
    String NextPage = "";
    LinearLayoutManager linearLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    CircleImageView imgProfileImage;
    Button btnSubmit;
    EditText edtEventSearch;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Gson mGson;
    // Variables for scroll listener
    private boolean userScrolled = true;
    private RecyclerViewLoadMoreScroll scrollListener;

    List<String> pageIdList = new ArrayList<>();

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


        edtEventSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged: " + s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: " + s);
            }

            @Override
            public void afterTextChanged(Editable s) {

                Log.d(TAG, "afterTextChanged: " + s);
                //getSearchEventRecord(s);

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSearchEventRecord(edtEventSearch.getText().toString().trim(), v);
            }
        });



        implementScrollListener();
        return view;

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        NextPage = "";
        getAllEvents();
    }

    private void getSearchEventRecord(String search, View v) {

        progressDialog = Utils.createProgressDialog(getActivity());

        event_listArrayList = new ArrayList<>();
        Map<String, String> data = new HashMap<>();
        data.put("ismyevent", "1");
        data.put("event_name", search);

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
                     //   edtEventSearch.setText("");
                        recyclerViewEventList.setVisibility(View.VISIBLE);
                        txtEmpty.setVisibility(View.GONE);
                        event_listArrayList.addAll(response.body().getData().getEvent_list());

                        String path_image = response.body().getData().getPath_image();
                        adapter = new EventsListAdapter(getActivity(), event_listArrayList, path_image);
                        recyclerViewEventList.setAdapter(adapter);


                    } else {
                      //  edtEventSearch.setText("");
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

    private void implementScrollListener() {

        recyclerViewEventList
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


            bottomLayout.setVisibility(View.VISIBLE);

            Log.d(TAG, "loadMoreListItems: NextPage " + NextPage);

            Map<String, String> data = new HashMap<>();
            data.put("ismyevent", "1");
            data.put("fm", NextPage);

            String token = Pref.getPreToken();
            Log.d(TAG, "logMeIn onResponse: token " + token);

            final YouthHubApi apiService =
                    ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

            Call<DataEventsListItems> call = apiService.getEventsList(token, data);
            Log.d(TAG, "getTimeLinePostList: first time url " + call.request().url());

            call.enqueue(new Callback<DataEventsListItems>() {
                @Override
                public void onResponse(Call<DataEventsListItems> call, Response<DataEventsListItems> response) {
                    // dismiss Progress Layout
                    bottomLayout.setVisibility(View.GONE);


                    if (response.code() == 200) {

                        if (response.body().getStatus().equals("1")) {
                            Log.d(TAG, "onResponse: if");

                            NextPage = response.body().getNextpage();

                            pageIdList.add(NextPage);

                            Log.d(TAG, "onResponse: " + response.body().getToken());

                            Pref.setdevicetoken("Bearer " + response.body().getToken());


                            NextPage = response.body().getNextpage();

                            event_listArrayList.addAll(response.body().getData().getEvent_list());
                            Log.d(TAG, "onResponse NextPage: " + NextPage);

                            adapter.notifyDataSetChanged();


                        } else {
                            Log.d(TAG, "onResponse: else");
                            // dismiss Progress Layout
                            bottomLayout.setVisibility(View.GONE);

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
                    // dismiss Progress Layout
                    bottomLayout.setVisibility(View.GONE);

                    t.printStackTrace();
                }
            });
        }
    }


    private void UiInitialization(View view) {
        this.btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        this.txtEmpty = (TextView) view.findViewById(R.id.txtEmpty);
        this.edtEventSearch = (EditText) view.findViewById(R.id.edtEventSearch);
        this.recyclerViewEventList = (RecyclerView) view.findViewById(R.id.recyclerViewEventList);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewEventList.setHasFixedSize(true);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerViewEventList.setLayoutManager(linearLayoutManager);
        bottomLayout = (RelativeLayout) view
                .findViewById(R.id.loadItemsLayout_recyclerView);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public void onResume() {
        super.onResume();

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        Log.d(TAG, "onResume: my locations");
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        NextPage = "";
                                        getAllEvents();
                                    }
                                }
        );


    }

    private void getAllEvents() {

        event_listArrayList = new ArrayList<>();
        pageIdList.clear();
        ;
        Map<String, String> data = new HashMap<>();
        data.put("ismyevent", "1");
        data.put("fm", NextPage);

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

                        recyclerViewEventList.setVisibility(View.VISIBLE);
                        txtEmpty.setVisibility(View.GONE);
                        event_listArrayList.addAll(response.body().getData().getEvent_list());

                        NextPage = response.body().getNextpage();

                        String path_image = response.body().getData().getPath_image();
                        adapter = new EventsListAdapter(getActivity(), event_listArrayList, path_image);
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
                // progressDialog.dismiss();
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);

                recyclerViewEventList.setVisibility(View.GONE);
                txtEmpty.setVisibility(View.VISIBLE);
                t.printStackTrace();
            }
        });
    }




}
