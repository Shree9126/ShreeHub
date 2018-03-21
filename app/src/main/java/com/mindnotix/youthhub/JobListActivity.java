package com.mindnotix.youthhub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.adapter.JobListAdapter;
import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.BasicResponse;
import com.mindnotix.model.jobs.list.Data;
import com.mindnotix.model.jobs.list.DataJobListItems;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobListActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = JobListActivity.class.getSimpleName();
    private static RelativeLayout bottomLayout;
    RecyclerView recyclerViewJobList;
    Toolbar toolbar;
    JobListAdapter jobListAdapter;
    TextView txtEmpty;
    FloatingActionButton fab;
    ArrayList<Data> dataJobArrayList;
    RecyclerViewClickListener listener;
    ProgressDialog progressDialog;
    LinearLayoutManager linearLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    String NextPage = "";
    String edtSearchTxt = "";
    String spnrJobCategoryTxt_ID = "";
    String spnrJobSubCategoryTxt_ID = "";
    String spnrJobTypeTxt_ID = "";
    String spnrRegionTxt_ID = "";
    String spnrDistrictTxt_ID = "";
    String spnrSalaryRange_Type_ID = "";
    String spnrSalaryFromRange_ID = "";
    String spnrSalaryToRange_ID = "";
    String spnrSortByDate_ID = "";
    List<String> pageIdList = new ArrayList<>();
    // Variables for scroll listener
    private boolean userScrolled = true;
    private String favourites = "";
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);

        dataJobArrayList = new ArrayList<>();
        UiInitialization();

        listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

                switch (view.getId()) {
                    case R.id.imgFavoriteJob:
                        if (dataJobArrayList.get(position).getIsfavourtejob().equals("1")) {

                            new makeUnFavourite(position, view, "0").execute();

                        } else {
                            new makeUnFavourite(position, view, "1").execute();

                        }
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
                                        swipeRefreshLayout.setRefreshing(true);
                                        NextPage = "";
                                        getJobLists();
                                    }
                                }
        );
    }

    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {

            if (data != null) {
                swipeRefreshLayout.setRefreshing(true);
                edtSearchTxt = data.getStringExtra("edtSearchTxt");
                spnrJobCategoryTxt_ID = data.getStringExtra("spnrJobCategoryTxt_ID");
                spnrJobSubCategoryTxt_ID = data.getStringExtra("spnrJobSubCategoryTxt_ID");
                spnrJobTypeTxt_ID = data.getStringExtra("spnrJobTypeTxt_ID");
                spnrRegionTxt_ID = data.getStringExtra("spnrRegionTxt_ID");
                spnrDistrictTxt_ID = data.getStringExtra("spnrDistrictTxt_ID");
                spnrSalaryRange_Type_ID = data.getStringExtra("spnrSalaryRange_Type_ID");
                spnrSalaryFromRange_ID = data.getStringExtra("spnrSalaryFromRange_ID");
                spnrSalaryToRange_ID = data.getStringExtra("spnrSalaryToRange_ID");
                spnrSortByDate_ID = data.getStringExtra("spnrSortByDate_ID");
                favourites = data.getStringExtra("favourites");

                Log.d(TAG, "onActivityResult:edtSearchTxt " + edtSearchTxt);
                Log.d(TAG, "onActivityResult:spnrJobCategoryTxt_ID " + spnrJobCategoryTxt_ID);
                Log.d(TAG, "onActivityResult:spnrJobSubCategoryTxt_ID " + spnrJobSubCategoryTxt_ID);
                Log.d(TAG, "onActivityResult:spnrJobTypeTxt_ID " + spnrJobTypeTxt_ID);
                Log.d(TAG, "onActivityResult:spnrRegionTxt_ID " + spnrRegionTxt_ID);
                Log.d(TAG, "onActivityResult:spnrDistrictTxt_ID " + spnrDistrictTxt_ID);
                Log.d(TAG, "onActivityResult:spnrSalaryRange_Type_ID " + spnrSalaryRange_Type_ID);
                Log.d(TAG, "onActivityResult:spnrSalaryFromRange_ID " + spnrSalaryFromRange_ID);
                Log.d(TAG, "onActivityResult:spnrSalaryToRange_ID " + spnrSalaryToRange_ID);
                Log.d(TAG, "onActivityResult:spnrSortByDate_ID " + spnrSortByDate_ID);
                getJobLists();
            } else {
                swipeRefreshLayout.setRefreshing(true);
                NextPage = "";
                edtSearchTxt = "";
                spnrJobCategoryTxt_ID = "";
                spnrJobSubCategoryTxt_ID = "";
                spnrJobTypeTxt_ID = "";
                spnrRegionTxt_ID = "";
                spnrDistrictTxt_ID = "";
                spnrSalaryRange_Type_ID = "";
                spnrSalaryFromRange_ID = "";
                spnrSalaryToRange_ID = "";
                spnrSortByDate_ID = "";
                favourites = "";
                getJobLists();
            }


        }
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
                            loadMoreListItems();
                        }

                    }

                });
    }

    private void loadMoreListItems() {


        if (!pageIdList.contains(NextPage)) {

            Log.d(TAG, "loadMoreListItems: asdkjfksakjdfjhjsadfjhdsgfjhsadf");
            // Show Progress Layout
            bottomLayout.setVisibility(View.VISIBLE);

            Map<String, String> data = new HashMap<>();
            data.put("profileid", Pref.getClientId());
            if (NextPage != null)
                data.put("fm", NextPage);
            else
                data.put("fm", "");
            data.put("keywords", edtSearchTxt);
            data.put("category", spnrJobCategoryTxt_ID);
            data.put("subcategory", spnrJobSubCategoryTxt_ID);
            data.put("jobtype", spnrJobTypeTxt_ID);
            data.put("regional", spnrRegionTxt_ID);
            data.put("local_board", spnrDistrictTxt_ID);
            data.put("salary_type", spnrSalaryRange_Type_ID);
            data.put("salary_offered_from", spnrSalaryFromRange_ID);
            data.put("salary_offered_to", spnrSalaryToRange_ID);
            data.put("sort_by", spnrSortByDate_ID);
            data.put("favourites", favourites);
            YouthHubApi apiService =
                    ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


            //String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTU3IiwidXNlcm5hbWUiOiJzcmlkaGFyYW4ubWluZG5vdGl4QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTg2MDkwNTAsImp0aSI6IlFYVkdUVEJ3V0dOMlNGWjBkVWxVV1hKT2JEWT0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE4NjA5MDUwLCJleHAiOjE1MTkyMTM4NTB9.-1YP2zxdrvMmlc2mfH6CXyCUxdqRObTFI6GSUByJ84I";

            String token = Pref.getPreToken();
            Log.d(TAG, "loadMoreListItems: token " + token);

            Call<DataJobListItems> call = apiService.getJobLists(token, data);
            Log.d(TAG, "logMeIn: url " + call.request().url());

            call.enqueue(new Callback<DataJobListItems>() {
                @Override
                public void onResponse(Call<DataJobListItems> call, Response<DataJobListItems> response) {
                    try {

                        // dismiss Progress Layout

                        bottomLayout.setVisibility(View.GONE);
                        if (response.code() == 200) {
                            if (response.body().getStatus().equals("1")) {

                                NextPage = response.body().getNextpage();
                                pageIdList.add(NextPage);

                                if (!NextPage.equals("0")) {
                                    dataJobArrayList.addAll(response.body().getData());
                                    jobListAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(JobListActivity.this, "No more data", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                NextPage = response.body().getNextpage();
                                Toast.makeText(JobListActivity.this, "No record available.", Toast.LENGTH_SHORT).show();
                            }
                        } else if (response.code() == 304) {

                            Toast.makeText(JobListActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 400) {

                            Toast.makeText(JobListActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 401) {

                            Toast.makeText(JobListActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 403) {

                            Toast.makeText(JobListActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 404) {

                            Toast.makeText(JobListActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                        } else if (response.code() == 422) {

                            Toast.makeText(JobListActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(JobListActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<DataJobListItems> call, Throwable t) {
                    // dismiss Progress Layout
                    bottomLayout.setVisibility(View.GONE);
                    pageIdList.remove(NextPage);
                    t.printStackTrace();
                }
            });


        }

    }

    private void UiInitialization() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        bottomLayout = (RelativeLayout) findViewById(R.id.loadItemsLayout_recyclerView);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerViewJobList = (RecyclerView) findViewById(R.id.recyclerViewJobList);
        txtEmpty = (TextView) findViewById(R.id.txtEmpty);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewJobList.setHasFixedSize(true);
        recyclerViewJobList.setLayoutManager(linearLayoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);


        setSupportActionBar(toolbar);
        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Jobs");

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
/*                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent mIntent = new Intent(JobListActivity.this, JobFilterActivity.class);
                startActivityForResult(mIntent, 2);// Activity is started with requestCode 2
                break;
        }
    }

    private void getJobLists() {

        pageIdList.clear();
        dataJobArrayList = new ArrayList<>();

        //     progressDialog = Utils.createProgressDialog(this );
        String token = Pref.getPreToken();


        Map<String, String> data = new HashMap<>();
        data.put("profileid", Pref.getClientId());
        data.put("keywords", edtSearchTxt);
        data.put("category", spnrJobCategoryTxt_ID);
        data.put("subcategory", spnrJobSubCategoryTxt_ID);
        data.put("jobtype", spnrJobTypeTxt_ID);
        data.put("regional", spnrRegionTxt_ID);
        data.put("local_board", spnrDistrictTxt_ID);
        data.put("salary_type", spnrSalaryRange_Type_ID);
        data.put("salary_offered_from", spnrSalaryFromRange_ID);
        data.put("salary_offered_to", spnrSalaryToRange_ID);
        data.put("sort_by", spnrSortByDate_ID);
        data.put("favourites", favourites);
        data.put("fm", "");


        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("joblist  key, " + key + " value " + value);
        }


        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


        //token = "Bearer "+ Pref.getPreToken();


        Log.d(TAG, "changePassword: token " + token);

        Call<DataJobListItems> call = apiService.getJobLists(token, data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataJobListItems>() {
            @Override
            public void onResponse(Call<DataJobListItems> call, Response<DataJobListItems> response) {
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
                try {

                    Log.d(TAG, "onResponse:DataJobListItems " + new Gson().toJson(response.body()));

                    //   progressDialog.dismiss();
                    if (response.code() == 200) {
                        if (response.body().getStatus().equals("1")) {

                            txtEmpty.setVisibility(View.GONE);
                            recyclerViewJobList.setVisibility(View.VISIBLE);
                            dataJobArrayList.addAll(response.body().getData());

                            NextPage = response.body().getNextpage();

                            jobListAdapter = new JobListAdapter(listener, JobListActivity.this, dataJobArrayList);
                            recyclerViewJobList.setAdapter(jobListAdapter);
                            //jobListAdapter.notifyDataSetChanged();
                        } else {
                            txtEmpty.setVisibility(View.VISIBLE);
                            recyclerViewJobList.setVisibility(View.GONE);
                            //     jobListAdapter.notifyDataSetChanged();
                            // Toast.makeText(JobListActivity.this, "No record available", Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 304) {

                        Toast.makeText(JobListActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(JobListActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(JobListActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(JobListActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(JobListActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(JobListActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(JobListActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<DataJobListItems> call, Throwable t) {
                // progressDialog.dismiss();
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
                t.printStackTrace();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gps_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionMap:
                Intent intent = new Intent(JobListActivity.this, MapsActivity.class);
                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);

        NextPage = "";
        edtSearchTxt = "";
        spnrJobCategoryTxt_ID = "";
        spnrJobSubCategoryTxt_ID = "";
        spnrJobTypeTxt_ID = "";
        spnrRegionTxt_ID = "";
        spnrDistrictTxt_ID = "";
        spnrSalaryRange_Type_ID = "";
        spnrSalaryFromRange_ID = "";
        spnrSalaryToRange_ID = "";
        spnrSortByDate_ID = "";
        favourites = "";
        getJobLists();
    }

    private class makeUnFavourite extends AsyncTask<Void, Void, String> {

        int position;
        View view;
        String item;

        public makeUnFavourite(int position, View view, String status) {
            this.position = position;
            this.view = view;
            this.item = status;
        }

        @Override
        protected String doInBackground(final Void... voids) {
            Log.d(TAG, "logMeIn onResponse:  like");
            Map<String, String> data = new HashMap<>();
            data.put("jobid", dataJobArrayList.get(position).getId());
            data.put("profileid", Pref.getClientId());
            data.put("status", item);

            String token = Pref.getPreToken();
            Log.d(TAG, "logMeIn onResponse: token " + token);

            YouthHubApi apiService =
                    ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

            Call<BasicResponse> call = apiService.jobFavourite(token, data);
            Log.d(TAG, "favorioute: url " + call.request().url());

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {

                    if (response.code() == 200 || response.code() == 201) {

                        if (response.body().getStatus().equals("1")) {


                            if (response.body().getIs_favourites().equals("1")) {

                                dataJobArrayList.get(position).setIsfavourtejob("1");

                                ImageView imageView = view.findViewById(R.id.imgFavoriteJob);
                                imageView.setImageResource(R.drawable.ic_bookmark_black_24dp);
                            } else {
                                ImageView imageView = view.findViewById(R.id.imgFavoriteJob);
                                imageView.setImageResource(R.drawable.ic_bookmark_border_black_24dp);

                                dataJobArrayList.get(position).setIsfavourtejob("0");
                            }

                            jobListAdapter.notifyDataSetChanged();

                            Toast.makeText(JobListActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(JobListActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else if (response.code() == 304) {

                        Toast.makeText(JobListActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(JobListActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(JobListActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(JobListActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(JobListActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(JobListActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(JobListActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
            return null;
        }
    }
}
