package com.mindnotix.youthhub;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mindnotix.adapter.PostLikeAdapter;
import com.mindnotix.model.dashboard.like_comments_contribute.DataLikeCommentsContriubes;
import com.mindnotix.model.dashboard.like_comments_contribute.Tweaks;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Sridharan on 2/5/2018.
 */

public class PostLikeActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    ProgressDialog progressDialog;
    ArrayList<Tweaks> tweaksArrayList = new ArrayList<Tweaks>();
    String post_id, nextPage;
    int position;
    PostLikeAdapter postLikeAdapter;
    private android.support.v7.widget.Toolbar toolbar;
    private android.widget.TextView likebox;
    private android.view.View divider;
    private android.support.v7.widget.RecyclerView myrecyclerview;
    private android.view.View divider2;
    private android.widget.EditText commenttext;
    private android.widget.ImageView send;
    private android.widget.LinearLayout sendlinear;
    private android.support.v4.widget.SwipeRefreshLayout swiperefreshlayout;
    private int flag = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comments);
        UiInitialization();

        if (getIntent() != null) {

            post_id = getIntent().getStringExtra("post_id");
            position = getIntent().getIntExtra("position", 0);

        }

        postLikeAdapter = new PostLikeAdapter(PostLikeActivity.this, tweaksArrayList);
        myrecyclerview.setAdapter(postLikeAdapter);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swiperefreshlayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swiperefreshlayout.setRefreshing(true);
                                        flag = 0;

                                    }
                                }
        );

        getLikeList();

    }

    private void getLikeList() {

        /*  progressDialog = Utils.createProgressDialog(this);*/
        Map<String, String> data = new HashMap<>();
        data.put("pid", post_id);
        data.put("typ", "1");

        //   String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTQyIiwidXNlcm5hbWUiOiJnb3d0aGFtODc2QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTY2MTQzNDgsImp0aSI6ImJHZHpVVVJRWlhSUmVqbDNNelp0YjFnMFdGST0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE2NjE0MzQ4LCJleHAiOjE1MTcyMTkxNDh9.BYBvL9AFaq3hAQMPUDt77ANgwcfeBzpdTbK7gd-S3N0";
        //   RXRetro.getInstance().retrofitEnque(ApiClient.getInterface().getTimeLinePostLists(token,data), this, 1);
        String token = Pref.getPreToken();
        Log.d(TAG, "logMeIn onResponse: token " + token);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataLikeCommentsContriubes> call = apiService.getpoststweaks(token, data);
        Log.d(TAG, "getpoststweaks: url " + call.request().url());

        call.enqueue(new Callback<DataLikeCommentsContriubes>() {
            @Override
            public void onResponse(Call<DataLikeCommentsContriubes> call, Response<DataLikeCommentsContriubes> response) {
                //  progressDialog.dismiss();

                // stopping swipe refresh
                swiperefreshlayout.setRefreshing(false);

                Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));
                if (response.body().getStatus().equals("1")) {

                    nextPage = response.body().getNextpage();
                    Log.d(TAG, "onResponse:next page " + nextPage);

                    tweaksArrayList.addAll(response.body().getData().getTweaks());
                    postLikeAdapter.notifyDataSetChanged();


                } else {
                }
            }

            @Override
            public void onFailure(Call<DataLikeCommentsContriubes> call, Throwable t) {
                // progressDialog.dismiss();
                // stopping swipe refresh
                swiperefreshlayout.setRefreshing(false);
            }
        });


    }

    private void UiInitialization() {
        this.swiperefreshlayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swiperefreshlayout.setOnRefreshListener(this);
        this.sendlinear = (LinearLayout) findViewById(R.id.send_linear);
        this.sendlinear.setVisibility(View.GONE);
        this.send = (ImageView) findViewById(R.id.send);
        this.commenttext = (EditText) findViewById(R.id.commenttext);
        this.divider2 = (View) findViewById(R.id.divider2);
        this.myrecyclerview = (RecyclerView) findViewById(R.id.my_recycler_view);
        this.divider = (View) findViewById(R.id.divider);
        this.likebox = (TextView) findViewById(R.id.like_box);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);

        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Encouragers");

        } catch (Exception e) {
            e.printStackTrace();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //  layoutManager.setReverseLayout(true);
        // layoutManager.setStackFromEnd(true);

        this.myrecyclerview.setLayoutManager(layoutManager);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onRefresh() {

        Log.d(TAG, "onRefresh:nextPage " + nextPage + " post_id" + post_id);
        getLikeLists(post_id, nextPage);
    }

    private void getLikeLists(String post_id, String nextPages) {

        Map<String, String> data = new HashMap<>();
        data.put("pid", post_id);
        data.put("typ", "1");
        data.put("fm", nextPages);

        //   String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTQyIiwidXNlcm5hbWUiOiJnb3d0aGFtODc2QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTY2MTQzNDgsImp0aSI6ImJHZHpVVVJRWlhSUmVqbDNNelp0YjFnMFdGST0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE2NjE0MzQ4LCJleHAiOjE1MTcyMTkxNDh9.BYBvL9AFaq3hAQMPUDt77ANgwcfeBzpdTbK7gd-S3N0";
        //   RXRetro.getInstance().retrofitEnque(ApiClient.getInterface().getTimeLinePostLists(token,data), this, 1);
        String token = Pref.getPreToken();
        Log.d(TAG, "logMeIn onResponse: token " + token);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataLikeCommentsContriubes> call = apiService.getpoststweaks(token, data);
        Log.d(TAG, "getpoststweaks: url " + call.request().url());

        call.enqueue(new Callback<DataLikeCommentsContriubes>() {
            @Override
            public void onResponse(Call<DataLikeCommentsContriubes> call, Response<DataLikeCommentsContriubes> response) {
                //  progressDialog.dismiss();

                // stopping swipe refresh
                swiperefreshlayout.setRefreshing(false);

                Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));
                if (response.body().getStatus().equals("1")) {

                    nextPage = response.body().getNextpage();

                    tweaksArrayList.addAll(response.body().getData().getTweaks());
                    postLikeAdapter.notifyDataSetChanged();


                } else {

                }
            }

            @Override
            public void onFailure(Call<DataLikeCommentsContriubes> call, Throwable t) {
                // progressDialog.dismiss();
                // stopping swipe refresh
                swiperefreshlayout.setRefreshing(false);
            }
        });

    }
}
