package com.mindnotix.youthhub.dashboard_message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.mindnotix.adapter.message_adapter.MessageInboxOutboxAdapter;
import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.message.chat_history.Chat_list_users;
import com.mindnotix.model.message.chat_history.DataChatMessageHistoryItems;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Pref;
import com.mindnotix.youthhub.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 3/14/2018.
 */

public class DashboardMessageInbox extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    private static final String TAG = DashboardMessageUsers.class.getSimpleName();
    ArrayList<Chat_list_users> chatUsersListArrayList;
    MessageInboxOutboxAdapter messageUserListAdapter;

    RecyclerViewClickListener listener;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    String NextPage = "";
    String image_path = "";
    LinearLayoutManager linearLayoutManager;
    EditText edtEventSearch;
    Button btnSearch;
    private android.support.v7.widget.RecyclerView recyclerViewUserList;
    private android.widget.TextView txtEmpty;
    private RelativeLayout loadItemsLayoutrecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean userScrolled = true;
    private String Search = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //    return
        View view = inflater.inflate(R.layout.fragment_message_user, container, false);

        UiInitialization(view);

        listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {


                switch (view.getId()) {
                    default:
                        Intent mIntent = new Intent(getActivity(), ChatActivity.class);
                        mIntent.putExtra("chat_id", chatUsersListArrayList.get(position).getChat_id());
                        mIntent.putExtra("user_id", chatUsersListArrayList.get(position).getUser_id());
                        mIntent.putExtra("isonline", chatUsersListArrayList.get(position).getIsonline());
                        mIntent.putExtra("status", chatUsersListArrayList.get(position).getStatus());
                        mIntent.putExtra("last_name", chatUsersListArrayList.get(position).getLast_name());
                        mIntent.putExtra("first_name", chatUsersListArrayList.get(position).getFirst_name());
                        mIntent.putExtra("profile_image", chatUsersListArrayList.get(position).getProfile_image());
                        mIntent.putExtra("image_path", image_path);
                        startActivity(mIntent);
                }
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

                                        NextPage = "";
                                        Search = "";
                                        getUserList(Search);
                                    }
                                }
        );

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NextPage = "";
                Search = edtEventSearch.getText().toString();
                getUserList(Search);
            }
        });
        return view;
    }

    private void UiInitialization(View view) {

        edtEventSearch = (EditText) view.findViewById(R.id.edtEventSearch);
        btnSearch = (Button) view.findViewById(R.id.btnSubmit);

        this.swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        this.swipeRefreshLayout.setOnRefreshListener(this);
        this.loadItemsLayoutrecyclerView = (RelativeLayout) view.findViewById(R.id.loadItemsLayout_recyclerView);
        this.txtEmpty = (TextView) view.findViewById(R.id.txtEmpty);
        this.recyclerViewUserList = (RecyclerView) view.findViewById(R.id.recyclerViewUserList);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewUserList.setHasFixedSize(true);
        recyclerViewUserList.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onRefresh() {

        NextPage = "";
        Search = "";
        getUserList(Search);
    }

    @Override
    public void onClick(View v) {

    }

    private void getUserList(String search) {

        swipeRefreshLayout.setRefreshing(true);
        chatUsersListArrayList = new ArrayList<>();

        String token = Pref.getPreToken();


        Log.d(TAG, "getUserList: Next Page " + NextPage);

        Map<String, String> data = new HashMap<>();
        data.put("filter_name", search);
        data.put("chat_part", "1");
        data.put("fm", NextPage);


        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("getUserList  key, " + key + " value " + value);
        }


        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


        Call<DataChatMessageHistoryItems> call = apiService.getChatHistoryMessageList(token, data);
        Log.d(TAG, "getUserList: url " + call.request().url());

        call.enqueue(new Callback<DataChatMessageHistoryItems>() {
            @Override
            public void onResponse(Call<DataChatMessageHistoryItems> call, Response<DataChatMessageHistoryItems> response) {
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
                edtEventSearch.setText("");
                try {

                    Log.d(TAG, "onResponse:getUserList " + new Gson().toJson(response.body()));


                    if (response.code() == 200) {

                        if (response.body().getStatus().equals("1")) {


                            Log.d(TAG, "onResponse: getUserList size " + chatUsersListArrayList.size());


                            txtEmpty.setVisibility(View.GONE);
                            recyclerViewUserList.setVisibility(View.VISIBLE);
                            chatUsersListArrayList.addAll(response.body().getData().getChat_list_users());

                            NextPage = response.body().getNextpage();
                            //    pageIds.add(NextPage);
                            image_path = response.body().getData().getImage_path();
                            messageUserListAdapter = new MessageInboxOutboxAdapter(getActivity(), chatUsersListArrayList, listener, image_path);
                            recyclerViewUserList.setAdapter(messageUserListAdapter);
                        } else {
                            txtEmpty.setVisibility(View.VISIBLE);
                            recyclerViewUserList.setVisibility(View.GONE);
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
            public void onFailure(Call<DataChatMessageHistoryItems> call, Throwable t) {
                // progressDialog.dismiss();
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
                edtEventSearch.setText("");
                t.printStackTrace();
            }
        });
    }

    private void implementScrollListener() {

        recyclerViewUserList
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


}

