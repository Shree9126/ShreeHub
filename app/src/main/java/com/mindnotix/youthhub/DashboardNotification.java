package com.mindnotix.youthhub;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.adapter.NotificationAdapter;
import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.notification.DataNotificationsItems;
import com.mindnotix.model.notification.Notifications;
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

import static android.content.ContentValues.TAG;

/**
 * Created by Admin on 1/25/2018.
 */

public class DashboardNotification extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = DashboardNotification.class.getSimpleName();
    ArrayList<Notifications> notificationsArrayList;
    LinearLayoutManager linearLayoutManager;
    RecyclerViewClickListener listener;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    String NextPage = "";
    NotificationAdapter notificationAdapter;
    List<String> pageIds = new ArrayList<>();
    private RecyclerView recyclerview;
    private TextView txtEmpty;
    private RelativeLayout bottomLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NestedScrollView nestedscrollview;
    private boolean userScrolled = true;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);


        UiInitialization(view);


        listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {


            }
        };
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
                                        getNotificationList();
                                    }
                                }
        );


        nestedscrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            /*    if(scrollY > 0){

                }*/

                if(v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {
                        //code to fetch more data for endless scrolling
                        Log.d(TAG, "onScrollChange:event down");
                        loadMoreListItems();
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        NextPage = "";
        getNotificationList();
    }

    @Override
    public void onClick(View v) {

    }

    private void implementScrollListener() {

        recyclerview
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


        if (!pageIds.contains(NextPage)) {


            String token = Pref.getPreToken();


            Map<String, String> data = new HashMap<>();
            data.put("fm", NextPage);

            Log.d(TAG, "loadMoreListItems: next page " + NextPage);
            // Show Progress Layout
            bottomLayout.setVisibility(View.VISIBLE);

            for (Map.Entry<String, String> entry : data.entrySet()) {
                String key = entry.getKey().toString();
                String value = entry.getValue();
                System.out.println("getNotificationList  key, " + key + " value " + value);
            }
            pageIds.add(NextPage);


            YouthHubApi apiService =
                    ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


            Call<DataNotificationsItems> call = apiService.getNotifications(token, data);
            Log.d(TAG, "getNotificationList: url " + call.request().url());

            call.enqueue(new Callback<DataNotificationsItems>() {
                @Override
                public void onResponse(Call<DataNotificationsItems> call, Response<DataNotificationsItems> response) {
                    // Show Progress Layout
                    bottomLayout.setVisibility(View.GONE);

                    try {

                        Log.d(TAG, "onResponse:loadMoreListItems " + new Gson().toJson(response.body()));


                        if (response.code() == 200) {

                            if (response.body().getStatus().equals("1")) {


                                notificationsArrayList.addAll(response.body().getData().getNotifications());

                                Log.d(TAG, "onResponse:load notificationsArrayList size " + notificationsArrayList.size());

                                NextPage = response.body().getNextpage();

                                Log.d(TAG, "onResponse:loadMoreListItemssss Next Page " + NextPage);
                                notificationAdapter.notifyDataSetChanged();

                            } else {

                                Toast.makeText(getActivity(), "No record available", Toast.LENGTH_SHORT).show();
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

                    } catch (Exception e) {

                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<DataNotificationsItems> call, Throwable t) {
                    // progressDialog.dismiss();

                    pageIds.remove(NextPage);
                    // Show Progress Layout
                    bottomLayout.setVisibility(View.GONE);

                    t.printStackTrace();
                }
            });
        }

    }

    private void UiInitialization(View view) {

        this.swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        this.nestedscrollview = (NestedScrollView) view.findViewById(R.id.nestedscrollview);
        swipeRefreshLayout.setOnRefreshListener(this);
        this.bottomLayout = (RelativeLayout) view.findViewById(R.id.loadItemsLayout_recyclerView);
        this.txtEmpty = (TextView) view.findViewById(R.id.txtEmpty);
        this.recyclerview = (RecyclerView) view.findViewById(R.id.TimeLinePostList);


        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setNestedScrollingEnabled(false);
    }

    private void getNotificationList() {

        notificationsArrayList = new ArrayList<>();

        String token = Pref.getPreToken();


        Log.d(TAG, "getNotificationList: Next Page " + NextPage);

        Map<String, String> data = new HashMap<>();
        data.put("fm", "");


        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("getNotificationList  key, " + key + " value " + value);
        }


        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


        Call<DataNotificationsItems> call = apiService.getNotifications(token, data);
        Log.d(TAG, "getNotificationList: url " + call.request().url());

        call.enqueue(new Callback<DataNotificationsItems>() {
            @Override
            public void onResponse(Call<DataNotificationsItems> call, Response<DataNotificationsItems> response) {
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
                try {

                    Log.d(TAG, "onResponse:DataJobListItems " + new Gson().toJson(response.body()));


                    if (response.code() == 200) {

                        if (response.body().getStatus().equals("1")) {


                            Log.d(TAG, "onResponse: notificationsArrayList size " + notificationsArrayList.size());


                            txtEmpty.setVisibility(View.GONE);
                            recyclerview.setVisibility(View.VISIBLE);
                            notificationsArrayList.addAll(response.body().getData().getNotifications());

                            NextPage = response.body().getNextpage();

                            Log.d(TAG, "onResponse: next Page " + NextPage);
                            //    pageIds.add(NextPage);
                            notificationAdapter = new NotificationAdapter(notificationsArrayList, getActivity(), listener);
                            recyclerview.setAdapter(notificationAdapter);
                        } else {
                            txtEmpty.setVisibility(View.VISIBLE);
                            recyclerview.setVisibility(View.GONE);
                            //     jobListAdapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(), "No record available", Toast.LENGTH_SHORT).show();
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

                } catch (Exception e) {

                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<DataNotificationsItems> call, Throwable t) {
                // progressDialog.dismiss();
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
                t.printStackTrace();
            }
        });
    }


}
