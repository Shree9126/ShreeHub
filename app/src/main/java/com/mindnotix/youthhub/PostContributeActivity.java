package com.mindnotix.youthhub;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.adapter.PostCommentsAdapter;
import com.mindnotix.eventbus.Events;
import com.mindnotix.eventbus.GlobalBus;
import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.dashboard.like_comments_contribute.DataLikeCommentsContriubes;
import com.mindnotix.model.dashboard.like_comments_contribute.Post_user;
import com.mindnotix.model.dashboard.like_comments_contribute.Posts;
import com.mindnotix.model.dashboard.like_comments_contribute.Tweaks;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PostContributeActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    ProgressDialog progressDialog;
    ArrayList<Tweaks> tweaksArrayList;
    ArrayList<Tweaks> tweaksArrayListLoad = new ArrayList<>();
    String post_id, nextPage;
    int position;
    PostCommentsAdapter postCommentsAdapter;
    int flag = 1;
    RecyclerViewClickListener listener;
    private TextView likebox;
    private View divider;
    private RecyclerView myrecyclerview;
    private View divider2;
    private EditText commenttext;
    private ImageView send;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comments);

        UiInitialization();


        if (getIntent() != null) {

            post_id = getIntent().getStringExtra("post_id");
            position = getIntent().getIntExtra("position", 0);

        }
        listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

                DeleteDilaog(position);

            }
        };
        Log.d(TAG, "onCreate: post_id " + post_id);
        postCommentsAdapter = new PostCommentsAdapter(PostContributeActivity.this, tweaksArrayList, listener);
        myrecyclerview.setAdapter(postCommentsAdapter);

        //getContribute(post_id);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        flag = 0;
                                        nextPage = "";
                                        getContribute(post_id);

                                        //getContribute(post_id,nextPage);
                                    }
                                }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void DeleteDilaog(final int adapterPosition) {

        final String post_id = tweaksArrayList.get(adapterPosition).getPosts().getPf_pm_post_id();
        final String tweakID = tweaksArrayList.get(adapterPosition).getPosts().getPf_feed_id();



        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dilaog_remove);
        Button btCancel = dialog.findViewById(R.id.btCancel);
        TextView txtTitle = dialog.findViewById(R.id.text_dialog);
        TextView txtTitle_head = dialog.findViewById(R.id.txtTitle);
        txtTitle_head.setText("Remove contribute");
        txtTitle.setText("Are you sure want to remove?");
        txtTitle.setAllCaps(false);
        Button btOkay = dialog.findViewById(R.id.btOkay);
        ImageView imageView = dialog.findViewById(R.id.imgRegpply);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        btOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                deletePost(post_id, tweakID, adapterPosition);
                dialog.dismiss();
            }
        });
        dialog.show();

        /*  final Dialog dialog = new Dialog(PostContributeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_delete);
        Button btCancel = dialog.findViewById(R.id.btCancel);
        Button btOkay = dialog.findViewById(R.id.btOkay);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        btOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePost(post_id, tweakID, adapterPosition);
                dialog.dismiss();
            }
        });
        dialog.show();*/

    }

    private void deletePost(String post_id, String tweakID, final int positions) {

        Log.d(TAG, "deletePost:post_id " + post_id);
        Log.d(TAG, "deletePost:tweakID " + tweakID);
        Map<String, String> data = new HashMap<>();
        data.put("postid", post_id);
        data.put("tweakid", tweakID);

        String token = Pref.getPreToken();
        Log.d(TAG, "logMeIn onResponse: token " + token);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataLikeCommentsContriubes> call = apiService.getPostCommentDelete(token, data);
        Log.d(TAG, "getpoststweaks: url " + call.request().url());

        call.enqueue(new Callback<DataLikeCommentsContriubes>() {
            @Override
            public void onResponse(Call<DataLikeCommentsContriubes> call, Response<DataLikeCommentsContriubes> response) {
                //  progressDialog.dismiss();


                Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));
                if (response.body().getStatus().equals("1")) {

                    tweaksArrayList.remove(positions);
                    postCommentsAdapter.notifyDataSetChanged();

                    Events.ActivityRefreshCommandsContributeCount activityRefreshCommandsCount =
                            new Events.ActivityRefreshCommandsContributeCount(1, position, "2");

                    GlobalBus.getBus().postSticky(activityRefreshCommandsCount);


                    Toast.makeText(PostContributeActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PostContributeActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataLikeCommentsContriubes> call, Throwable t) {
                // progressDialog.dismiss();
                // stopping swipe refresh
                t.printStackTrace();
            }
        });


    }

    private void getContribute(String post_id) {

        //  tweaksArrayList=new ArrayList<>();

        swipeRefreshLayout.setRefreshing(true);

        /*  progressDialog = Utils.createProgressDialog(this);*/
        Map<String, String> data = new HashMap<>();
        data.put("pid", post_id);
        data.put("typ", "3");
        data.put("fm", nextPage);

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
                swipeRefreshLayout.setRefreshing(false);

                Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));
                if (response.body().getStatus().equals("1")) {

                    nextPage = response.body().getNextpage();

                    tweaksArrayList.addAll(response.body().getData().getTweaks());
                    Collections.reverse(tweaksArrayList);
                    postCommentsAdapter.notifyDataSetChanged();

                } else {
                    nextPage = "";
                }
            }

            @Override
            public void onFailure(Call<DataLikeCommentsContriubes> call, Throwable t) {
                // progressDialog.dismiss();
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }


    private void getContributes(String post_id, String nextPages) {

        if (nextPages.equals(""))
            tweaksArrayList.clear();

        tweaksArrayListLoad.clear();


        Map<String, String> data = new HashMap<>();
        data.put("pid", post_id);
        data.put("typ", "3");
        data.put("fm", nextPages);

        String token = Pref.getPreToken();
        Log.d(TAG, "logMeIn onResponse: token " + token);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataLikeCommentsContriubes> call = apiService.getpoststweaks(token, data);
        Log.d(TAG, "getpoststweaks: url " + call.request().url());

        call.enqueue(new Callback<DataLikeCommentsContriubes>() {
            @Override
            public void onResponse(Call<DataLikeCommentsContriubes> call, Response<DataLikeCommentsContriubes> response) {

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);

                Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));
                if (response.body().getStatus().equals("1")) {

                    nextPage = response.body().getNextpage();

/*
                    if (nextPage.equals("0"))
                        nextPage = "";*/


                    tweaksArrayListLoad.addAll(response.body().getData().getTweaks());
                    //tweaksArrayList.addAll(response.body().getData().getTweaks());

                    Collections.reverse(tweaksArrayListLoad);
                    tweaksArrayList.addAll(0, tweaksArrayListLoad);


                    postCommentsAdapter.notifyDataSetChanged();

                  //  myrecyclerview.getLayoutManager().scrollToPosition(tweaksArrayListLoad.size());
                    myrecyclerview.getLayoutManager().scrollToPosition(tweaksArrayListLoad.size() +
                            myrecyclerview.getLayoutManager().getChildCount() -2);


                } else {

                    nextPage = "0";
                }
            }

            @Override
            public void onFailure(Call<DataLikeCommentsContriubes> call, Throwable t) {

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    private void UiInitialization() {

        this.send = (ImageView) findViewById(R.id.send);
        this.send.setOnClickListener(this);
        this.commenttext = (EditText) findViewById(R.id.commenttext);
        this.commenttext.setHint("I would like to contribute by...");
        this.divider2 = (View) findViewById(R.id.divider2);
        this.myrecyclerview = (RecyclerView) findViewById(R.id.my_recycler_view);
        this.divider = (View) findViewById(R.id.divider);
        this.likebox = (TextView) findViewById(R.id.like_box);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);

        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Contributes");

        } catch (Exception e) {
            e.printStackTrace();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //  layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        this.myrecyclerview.setLayoutManager(layoutManager);

        tweaksArrayList = new ArrayList<>();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.send:
                if (!Utils.hasText(commenttext)) {
                    Toast.makeText(this, "Enter your contributes", Toast.LENGTH_SHORT).show();
                } else {

                    String comments = commenttext.getText().toString().trim();

                    commenttext.setText("");
                    //new SendContributes(this).execute();
                    new SendCommands(this, comments).execute();
                }

                break;
        }
    }

    @Override
    public void onRefresh() {
        flag = 0;
        // nextPage = "";
        getContributes(post_id, nextPage);
    }

    private class SendCommands extends AsyncTask<Void, Void, String> {

        PostContributeActivity postContributeActivity;
        String comments;

        public SendCommands(PostContributeActivity postContributeActivity, String comments) {
            this.postContributeActivity = postContributeActivity;
            this.comments = comments;
        }

        @Override
        protected String doInBackground(Void... voids) {


            Map<String, String> data = new HashMap<>();
            data.put("pid", post_id);
            data.put("typ", "3");
            data.put("comment", comments);

            //   String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTQyIiwidXNlcm5hbWUiOiJnb3d0aGFtODc2QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTY2MTQzNDgsImp0aSI6ImJHZHpVVVJRWlhSUmVqbDNNelp0YjFnMFdGST0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE2NjE0MzQ4LCJleHAiOjE1MTcyMTkxNDh9.BYBvL9AFaq3hAQMPUDt77ANgwcfeBzpdTbK7gd-S3N0";

            String token = Pref.getPreToken();
            Log.d(TAG, "getpostsnewtweaks onResponse: token " + token);

            YouthHubApi apiService =
                    ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

            Call<DataLikeCommentsContriubes> call = apiService.getpostsnewtweaks(token, data);
            Log.d(TAG, "getpoststweaks: url " + call.request().url());

            call.enqueue(new Callback<DataLikeCommentsContriubes>() {
                @Override
                public void onResponse(Call<DataLikeCommentsContriubes> call, Response<DataLikeCommentsContriubes> response) {
                    commenttext.setText("");
                    Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    if (response.body().getStatus().equals("1")) {

                        Log.d(TAG, "onResponse: " + response.body().getData().getPf_feed_id());


                        Pref.setdevicetoken("Bearer ".concat(response.body().getToken()));

                        Posts posts = new Posts();
                        posts.setPf_feed_id(response.body().getData().getPf_feed_id());
                        posts.setPf_message(response.body().getData().getPf_message());
                        posts.setPf_pm_post_id(response.body().getData().getPf_pm_post_id());
                        posts.setPf_type(response.body().getData().getPf_type());
                        posts.setPf_user_id(response.body().getData().getPf_user_id());
                        posts.setPf_created_on(response.body().getData().getPf_created_on());


                        String username = Pref.getPreferenceUser();
                        Log.d(TAG, "onResponse: username ssss " + username);
                        String part[] = username.split(" ");

                        Post_user post_user = new Post_user();
                        post_user.setFirst_name(part[0]);
                        post_user.setLast_name(part[1]);
                        post_user.setProfile_pic(Pref.getClientProfilePictureName());


/*
                        post_user.setFirst_name(Pref.getClientFirstname());
                        post_user.setLast_name(Pref.getClientLastname());
*/
                        Tweaks tweaks = new Tweaks(posts, post_user);
                        tweaksArrayList.add(tweaks);

                        if (postCommentsAdapter != null)
                            postCommentsAdapter.notifyDataSetChanged();


                        myrecyclerview.scrollToPosition(tweaksArrayList.size() - 1);

                        Events.ActivityRefreshCommandsContributeCount activityRefreshCommandsCount =
                                new Events.ActivityRefreshCommandsContributeCount(1, position, "1");

                        GlobalBus.getBus().postSticky(activityRefreshCommandsCount);


                    } else {

                    }
                }

                @Override
                public void onFailure(Call<DataLikeCommentsContriubes> call, Throwable t) {

                }
            });
            return null;
        }
    }
}
