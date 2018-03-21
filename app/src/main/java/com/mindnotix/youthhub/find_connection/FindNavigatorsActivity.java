package com.mindnotix.youthhub.find_connection;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.adapter.find_connection.YouthFindConnectionAdapter;
import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.BasicResponse;
import com.mindnotix.model.find_connection.list.DataFindConnectionList;
import com.mindnotix.model.find_connection.list.Userinfo;
import com.mindnotix.model.find_connection.master.share.DataFindNavigatorsShareMaster;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;
import com.mindnotix.youthhub.BaseActivity;
import com.mindnotix.youthhub.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 3/5/2018.
 */

public class FindNavigatorsActivity extends BaseActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = FindConnectinYouthActivity.class.getSimpleName();
    RecyclerViewClickListener listener;
    ProgressDialog progressDialog;
    GridLayoutManager linearLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    String NextPage = "";
    YouthFindConnectionAdapter youthFindConnectionAdapter;
    ArrayList<Userinfo> youthUserinfoArrayList;
    FloatingActionButton fab;
    private Toolbar toolbar;
    private RecyclerView recyclerViewYouth;
    private TextView txtEmpty;
    private RelativeLayout bottomLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    // Variables for scroll listener
    private boolean userScrolled = true;
    private String regional_ids = "";
    private String district_ids = "";
    private String edtSearchTxt = "";
    private String wishlist_ids = "";
    private ImageView imgRegpply;
    private CircleImageView ivProfile;
    private TextView txtUserName;
    private EditText edtSubject;
    private TextView txtSubmit;
    private TextView txtCancel;
    private TextView txtShare;
    private TextView txtTitle;
    private TextView txtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_connectin_youth);

        UiInitialization();
        listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

                switch (view.getId()) {

                    case R.id.removeProfilePicture:
                        ShowContactDialog(view, position);
                        break;

                    case R.id.ViewUpload:

                        Log.d(TAG, "onClick:getIsfollowing " + youthUserinfoArrayList.get(position).getIsfollowing());
                        if (youthUserinfoArrayList.get(position).getIsfollowing().equals("1")) {
                            new UnFollowEvent(position, view, "0").execute();
                        } else {
                            new UnFollowEvent(position, view, "1").execute();
                        }

                        break;

                    case R.id.txtShare:
                        Log.d(TAG, "onClick:getIsfollowing " + youthUserinfoArrayList.get(position).getIsfollowing());
                        if (!youthUserinfoArrayList.get(position).getIsshareprofile().equals("1"))
                            getShareConfirmation(position, view);


                        break;
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
                                        Log.d(TAG, "run: onRefresh oncreate");
                                        swipeRefreshLayout.setRefreshing(true);
                                        NextPage = "";
                                        getYouthFindConnectionList();
                                    }
                                }
        );

    }

    private void getShareConfirmation(final int position, final View view) {

        progressDialog = Utils.createProgressDialog(this);
        String token = Pref.getPreToken();


        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);

        Log.d(TAG, "find connections: token " + token);

        Call<DataFindNavigatorsShareMaster> call = apiService.getFindMasterShare(token);
        Log.d(TAG, " find connections: url " + call.request().url());

        call.enqueue(new Callback<DataFindNavigatorsShareMaster>() {
            @Override
            public void onResponse(Call<DataFindNavigatorsShareMaster> call, Response<DataFindNavigatorsShareMaster> response) {

                progressDialog.dismiss();
                if (response.code() == 200 || response.code() == 201) {
                    if (response.body().getStatus().equals("1")) {

                        String title = response.body().getData().getTitle();
                        String content = response.body().getData().getContent();

                        DialogReply(position, view, title, content);
                    } else {


                    }
                } else if (response.code() == 304) {

                    Toast.makeText(FindNavigatorsActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(FindNavigatorsActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(FindNavigatorsActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(FindNavigatorsActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(FindNavigatorsActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(FindNavigatorsActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(FindNavigatorsActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<DataFindNavigatorsShareMaster> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });


    }

    private void DialogReply(final int position, final View view, String title, String content) {


        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_find_nav_share);


        this.txtCancel = (TextView) dialog.findViewById(R.id.txtCancel);
        this.txtTitle = (TextView) dialog.findViewById(R.id.txtTitle);
        this.txtContent = (TextView) dialog.findViewById(R.id.txtContent);
        this.txtShare = (TextView) dialog.findViewById(R.id.txtShare);
        this.imgRegpply = (ImageView) dialog.findViewById(R.id.imgRegpply);

        txtTitle.setText(title);
        txtContent.setText(content);
        //dialog.setTitle("Title...");


        imgRegpply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // if button is clicked, close the custom dialog
        txtShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new UnShared(position, view, "1").execute();

                dialog.dismiss();

            }
        });

        dialog.show();
    }

    private void ShowContactDialog(View view, int position) {


        Log.d(TAG, "ShowContactDialog: " + position);
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_find_contact);
        this.txtCancel = (TextView) dialog.findViewById(R.id.txtCancel);
        this.txtSubmit = (TextView) dialog.findViewById(R.id.txtSubmit);
        this.edtSubject = (EditText) dialog.findViewById(R.id.edtSubject);
        this.txtUserName = (TextView) dialog.findViewById(R.id.txtUserName);
        this.ivProfile = (CircleImageView) dialog.findViewById(R.id.ivProfile);
        this.imgRegpply = (ImageView) dialog.findViewById(R.id.imgRegpply);
        edtSubject = (EditText) dialog.findViewById(R.id.edtSubject);

        if (youthUserinfoArrayList.get(position) != null) {

            txtUserName.setText(youthUserinfoArrayList.get(position).getName());


            if (!youthUserinfoArrayList.get(position).getProfile_pic_url().equals("") &&
                    youthUserinfoArrayList.get(position).getProfile_pic_url() != null)
                Picasso.with(this)
                        .load(youthUserinfoArrayList.get(position).getProfile_pic_url())
                        .placeholder(R.drawable.profile_pic) //this is optional the image to display while the url image is downloading
                        .error(R.drawable.profile_pic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .into(this.ivProfile);

        }


        //dialog.setTitle("Title...");

        imgRegpply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // if button is clicked, close the custom dialog
        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkValidation(edtSubject, dialog);

            }
        });

        dialog.show();
    }

    private void checkValidation(EditText edtSubject, Dialog dialog) {

        if (!Utils.hasText(edtSubject)) {
            Toast.makeText(this, "Type your content and then send.", Toast.LENGTH_SHORT).show();
        } else {
            sendContactMessage(dialog, edtSubject);
        }
    }

    private void sendContactMessage(final Dialog dialog, EditText edtSubject) {

        progressDialog = Utils.createProgressDialog(this);
        //  String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTU3IiwidXNlcm5hbWUiOiJzcmlkaGFyYW4ubWluZG5vdGl4QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTg2MDkwNTAsImp0aSI6IlFYVkdUVEJ3V0dOMlNGWjBkVWxVV1hKT2JEWT0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE4NjA5MDUwLCJleHAiOjE1MTkyMTM4NTB9.-1YP2zxdrvMmlc2mfH6CXyCUxdqRObTFI6GSUByJ84I";
        String token = Pref.getPreToken();


        Map<String, String> data = new HashMap<>();
        data.put("senduserid", Pref.getClientId());
        data.put("message", edtSubject.getText().toString());
        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);

        Log.d(TAG, "find connections: token " + token);

        Call<BasicResponse> call = apiService.sendContactMessage(token, data);
        Log.d(TAG, " find connections: url " + call.request().url());

        call.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                dialog.dismiss();
                progressDialog.dismiss();
                if (response.code() == 200 || response.code() == 201) {
                    if (response.body().getStatus().equals("1")) {

                        Toast.makeText(FindNavigatorsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(FindNavigatorsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 304) {

                    Toast.makeText(FindNavigatorsActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(FindNavigatorsActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(FindNavigatorsActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(FindNavigatorsActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(FindNavigatorsActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(FindNavigatorsActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(FindNavigatorsActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void UiInitialization() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        this.swipeRefreshLayout.setOnRefreshListener(this);
        this.bottomLayout = (RelativeLayout) findViewById(R.id.loadItemsLayout_recyclerView);
        this.txtEmpty = (TextView) findViewById(R.id.txtEmpty);
        this.recyclerViewYouth = (RecyclerView) findViewById(R.id.recyclerViewJobList);
        linearLayoutManager = new GridLayoutManager(this, 2);
        linearLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerViewYouth.setHasFixedSize(true);
        recyclerViewYouth.setLayoutManager(linearLayoutManager);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Navigators");

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
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fab:
/*                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent mIntent = new Intent(FindNavigatorsActivity.this, FindConnectNavigatorFilter.class);
                mIntent.putExtra("edtSearchTxt", edtSearchTxt);
                mIntent.putExtra("regional_ids", regional_ids);
                mIntent.putExtra("district_ids", district_ids);

                startActivityForResult(mIntent, 2);// Activity is started with requestCode 2
                break;
        }

    }

    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {

            if (data != null) {
                NextPage = "";

                Log.d(TAG, "FilterSubmit: edtJobSearch " + data.getStringExtra("edtSearchTxt"));
                edtSearchTxt = data.getStringExtra("edtSearchTxt");
                regional_ids = data.getStringExtra("regional_ids");
                district_ids = data.getStringExtra("district_ids");

                swipeRefreshLayout.setRefreshing(true);
                getYouthFindConnectionList();
            } else {
                district_ids = "";
                regional_ids = "";
                NextPage = "";
                edtSearchTxt = "";

             //   swipeRefreshLayout.setRefreshing(true);
             //   getYouthFindConnectionList();

            }


        }
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh: find connection");
        swipeRefreshLayout.setRefreshing(true);
        district_ids = "";
        regional_ids = "";
        NextPage = "";

        getYouthFindConnectionList();
    }

    private void getYouthFindConnectionList() {


        youthUserinfoArrayList = new ArrayList<>();
        String token = Pref.getPreToken();

        Log.d(TAG, "getYouthFindConnectionList: onRefresh ");
        //For Youth, we need to pass 8 value defaultly.

        Map<String, String> data = new HashMap<>();
        data.put("group_id", "9");
        data.put("fm", NextPage);
        data.put("title", edtSearchTxt);
        data.put("regional_id", regional_ids);
        data.put("local_board_id", district_ids);


        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("Youth  key, " + key + " value " + value);
        }


        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


        Log.d(TAG, "changePassword: token " + token);

        Call<DataFindConnectionList> call = apiService.getFindConnectionList(token, data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataFindConnectionList>() {
            @Override
            public void onResponse(Call<DataFindConnectionList> call, Response<DataFindConnectionList> response) {
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
                try {

                    Log.d(TAG, "onResponse:youthlist " + new Gson().toJson(response.body()));

                    if (response.code() == 200) {
                        if (response.body().getStatus().equals("1")) {

                            txtEmpty.setVisibility(View.GONE);
                            recyclerViewYouth.setVisibility(View.VISIBLE);

                            NextPage = response.body().getNextpage();
                            youthUserinfoArrayList.addAll(response.body().getData().getUserinfo());

                            youthFindConnectionAdapter = new YouthFindConnectionAdapter(
                                    youthUserinfoArrayList, FindNavigatorsActivity.this, listener);
                            recyclerViewYouth.setAdapter(youthFindConnectionAdapter);


                        } else {
                            txtEmpty.setVisibility(View.VISIBLE);
                            txtEmpty.setText("No record found");
                            recyclerViewYouth.setVisibility(View.GONE);
                            Toast.makeText(FindNavigatorsActivity.this, "No record found", Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 304) {

                        Toast.makeText(FindNavigatorsActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(FindNavigatorsActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(FindNavigatorsActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(FindNavigatorsActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(FindNavigatorsActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(FindNavigatorsActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(FindNavigatorsActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    swipeRefreshLayout.setRefreshing(false);
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<DataFindConnectionList> call, Throwable t) {

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
                t.printStackTrace();
            }
        });
    }

    private void implementScrollListener() {

        recyclerViewYouth
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
        bottomLayout.setVisibility(View.VISIBLE);
        String token = Pref.getPreToken();


        Map<String, String> data = new HashMap<>();
        data.put("group_id", "9");
        data.put("fm", NextPage);
        data.put("regional_id", regional_ids);
        data.put("local_board_id", district_ids);

        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("Youth  key, " + key + " value " + value);
        }


        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


        Log.d(TAG, "changePassword: token " + token);

        Call<DataFindConnectionList> call = apiService.getFindConnectionList(token, data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataFindConnectionList>() {
            @Override
            public void onResponse(Call<DataFindConnectionList> call, Response<DataFindConnectionList> response) {
                // stopping swipe refresh
                bottomLayout.setVisibility(View.GONE);
                try {

                    Log.d(TAG, "onResponse:youthlist " + new Gson().toJson(response.body()));

                    if (response.code() == 200) {
                        if (response.body().getStatus().equals("1")) {


                            NextPage = response.body().getNextpage();
                            youthUserinfoArrayList.addAll(response.body().getData().getUserinfo());
                            youthFindConnectionAdapter.notifyDataSetChanged();

                        } else {

                            Toast.makeText(FindNavigatorsActivity.this, "No record available", Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 304) {

                        Toast.makeText(FindNavigatorsActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(FindNavigatorsActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(FindNavigatorsActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(FindNavigatorsActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(FindNavigatorsActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(FindNavigatorsActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(FindNavigatorsActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<DataFindConnectionList> call, Throwable t) {

                // stopping swipe refresh
                bottomLayout.setVisibility(View.GONE);
                t.printStackTrace();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public class UnFollowEvent extends AsyncTask<Void, Void, String> {

        int position;
        View view;
        ProgressDialog progressDialog;
        String status;

        public UnFollowEvent(int position, View view, String status) {
            this.position = position;
            this.view = view;
            this.status = status;
            progressDialog = Utils.createProgressDialog(FindNavigatorsActivity.this);

        }

        @Override
        protected String doInBackground(Void... voids) {

            String token = Pref.getPreToken();


            Map<String, String> data = new HashMap<>();
            data.put("touserid", youthUserinfoArrayList.get(position).getUser_id());
            data.put("type", "following");
            data.put("updatestatus", status);
            YouthHubApi apiService =
                    ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);

            Log.d(TAG, "find connections: token " + token);

            Call<BasicResponse> call = apiService.updateFollowUnFollowStatus(token, data);
            Log.d(TAG, " find connections: url " + call.request().url());

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {

                    progressDialog.dismiss();
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body().getStatus().equals("1")) {
                            Log.d(TAG, "onResponse:getIsfollowing status out  " + youthUserinfoArrayList.get(position).getIsfollowing());
                            if (youthUserinfoArrayList.get(position).getIsfollowing().equals("1")) {
                                Log.d(TAG, "onResponse:getIsfollowing status if " + status);
                                youthUserinfoArrayList.get(position).setIsfollowing("0");

                                View ViewUpload = view.findViewById(R.id.ViewUpload);

                                ViewUpload.setBackground(getResources().getDrawable(R.drawable.follow));
                                youthFindConnectionAdapter.notifyDataSetChanged();

                            } else {

                                Log.d(TAG, "onResponse:getIsfollowing status else " + status);
                                youthUserinfoArrayList.get(position).setIsfollowing("1");
                                View ViewUpload = view.findViewById(R.id.ViewUpload);
                                ViewUpload.setBackground(getResources().getDrawable(R.drawable.unfollow));
                                youthFindConnectionAdapter.notifyDataSetChanged();
                            }


                            Toast.makeText(FindNavigatorsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(FindNavigatorsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 304) {

                        Toast.makeText(FindNavigatorsActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(FindNavigatorsActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(FindNavigatorsActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(FindNavigatorsActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(FindNavigatorsActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(FindNavigatorsActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(FindNavigatorsActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }


                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    t.printStackTrace();
                }
            });

            return null;
        }
    }


    @SuppressLint("StaticFieldLeak")
    public class UnShared extends AsyncTask<Void, Void, String> {
        int position;
        View view;
        String status;
        ProgressDialog progressDialog;


        public UnShared(int position, View view, String s) {
            this.position = position;
            this.view = view;
            this.status = s;
            progressDialog = Utils.createProgressDialog(FindNavigatorsActivity.this);

        }

        @Override
        protected String doInBackground(Void... voids) {

            String token = Pref.getPreToken();


            Map<String, String> data = new HashMap<>();
            data.put("shareuserid", youthUserinfoArrayList.get(position).getUser_id());
            data.put("isshare", "1");
            data.put("group_id", "9");
            YouthHubApi apiService =
                    ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);

            Log.d(TAG, "find connections: token " + token);

            Call<BasicResponse> call = apiService.shareNavigators(token, data);
            Log.d(TAG, " find connections: url " + call.request().url());

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {

                    progressDialog.dismiss();
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body().getStatus().equals("1")) {
                            Log.d(TAG, "onResponse:getIsshareprofile status out  " + youthUserinfoArrayList.get(position).getIsfollowing());
                            if (youthUserinfoArrayList.get(position).getIsshareprofile().equals("0")) {
                                Log.d(TAG, "onResponse:getIsshareprofile status if " + status);
                                youthUserinfoArrayList.get(position).setIsshareprofile("1");

                                TextView textView = view.findViewById(R.id.txtShare);
                                textView.setText("Shared");
                                youthFindConnectionAdapter.notifyDataSetChanged();

                            }

                            Toast.makeText(FindNavigatorsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(FindNavigatorsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 304) {

                        Toast.makeText(FindNavigatorsActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(FindNavigatorsActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(FindNavigatorsActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(FindNavigatorsActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(FindNavigatorsActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(FindNavigatorsActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(FindNavigatorsActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }


                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    t.printStackTrace();
                }
            });
            return null;
        }
    }
}
