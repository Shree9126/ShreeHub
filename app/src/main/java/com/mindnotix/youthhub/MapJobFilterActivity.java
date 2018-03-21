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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.adapter.MapJobFIlterAdapter;
import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.jobs.filter_master.Categoryview;
import com.mindnotix.model.jobs.filter_master.DataJobMasterFilter;
import com.mindnotix.model.jobs.filter_master.Regional_council_list;
import com.mindnotix.model.jobs.filter_master.local_board.DataLocalBoardList;
import com.mindnotix.model.jobs.filter_master.local_board.Local_board_list;
import com.mindnotix.model.jobs.list.Data;
import com.mindnotix.model.jobs.list.DataJobListItems;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
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

public class MapJobFilterActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = MapJobFilterActivity.class.getSimpleName();
    MapJobFIlterAdapter mapJobFIlterAdapter;
    ProgressDialog progressDialog;
    String lattitude, longtitude, companyName;
    ArrayList<Data> dataJobArrayList;
    RecyclerViewClickListener listener;
    ArrayAdapter<Regional_council_list> regional_council_listArrayAdapter;
    ArrayList<Local_board_list> local_board_listArrayList = new ArrayList<>();
    ArrayAdapter<Local_board_list> local_board_listArrayAdapter;
    ArrayList<Regional_council_list> regional_council_listArrayList = new ArrayList<>();
    ArrayList<Categoryview> categoryviewArrayList = new ArrayList<>();
    ArrayAdapter<Categoryview> categoryviewArrayAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private android.support.v7.widget.Toolbar toolbar;
    private android.widget.TextView txtjosss;
    private android.widget.ImageView imgFilter;
    private android.support.v7.widget.RecyclerView recyclerViewJobList;
    private android.widget.Button btnSearch;
    private android.widget.Spinner spnrDistrictCity;
    private android.widget.Spinner spnrRegionLocation;
    private android.widget.Spinner spnrJobCategory;
    private android.widget.EditText edtJobSearch;
    private View nojobfoundlayout;

    private String edtJobSearchText = "";
    private int spnrJobCategoryText = 0;
    private int spnrRegionLocationText = 0;
    private int spnrDistrictCityText = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_job_filter_activity);
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


       /* Categoryview categoryview = new Categoryview();
        categoryview.setId("0");
        categoryview.setName("All");
        categoryviewArrayList.add(0, categoryview);
        categoryviewArrayAdapter = new ArrayAdapter<Categoryview>(this,
                android.R.layout.simple_spinner_item, categoryviewArrayList);
        categoryviewArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrJobCategory.setAdapter(categoryviewArrayAdapter);
*/


        imgFilter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MapJobFilterActivity.this);
                dialog.setContentView(R.layout.dialog_job_map_filter);
                // Set dialog title
                dialog.setTitle("Custom Dialog");

                btnSearch = (Button) dialog.findViewById(R.id.btnSearch);
                spnrDistrictCity = (Spinner) dialog.findViewById(R.id.spnrDistrictCity);
                spnrRegionLocation = (Spinner) dialog.findViewById(R.id.spnrRegionLocation);
                spnrJobCategory = (Spinner) dialog.findViewById(R.id.spnrJobCategory);
                edtJobSearch = (EditText) dialog.findViewById(R.id.edtJobSearch);
                dialog.show();

                edtJobSearch.setText(edtJobSearchText);


                categoryviewArrayAdapter = new ArrayAdapter<Categoryview>(MapJobFilterActivity.this,
                        android.R.layout.simple_spinner_item, categoryviewArrayList);
                categoryviewArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrJobCategory.setAdapter(categoryviewArrayAdapter);


                regional_council_listArrayAdapter = new ArrayAdapter<Regional_council_list>(MapJobFilterActivity.this,
                        android.R.layout.simple_spinner_item, regional_council_listArrayList);
                regional_council_listArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrRegionLocation.setAdapter(regional_council_listArrayAdapter);


                spnrJobCategory.setSelection(spnrJobCategoryText);
                spnrRegionLocation.setSelection(spnrRegionLocationText);

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
                            local_board_listArrayAdapter = new ArrayAdapter<Local_board_list>(MapJobFilterActivity.this,
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
                        getJobListSearch(edtJobSearch.getText().toString());
                    }
                });

            }
        });


    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        getJobList();
    }

    private void getLocalBoardList(String name) {
        local_board_listArrayList.clear();

        Log.d(TAG, "getLocalBoardList: " + name);
        // progressDialog = Utils.createProgressDialog(this);
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

                //  progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("1")) {
                        local_board_listArrayList.addAll(response.body().getData().getLocal_board_list());

                        Local_board_list sortby = new Local_board_list();
                        sortby.setId("0");
                        sortby.setName("All");
                        local_board_listArrayList.add(0, sortby);
                        local_board_listArrayAdapter = new ArrayAdapter<Local_board_list>(MapJobFilterActivity.this,
                                android.R.layout.simple_spinner_item, local_board_listArrayList);
                        local_board_listArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnrDistrictCity.setAdapter(local_board_listArrayAdapter);


                    } else {

                        Toast.makeText(MapJobFilterActivity.this, "No record available", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 304) {

                    Toast.makeText(MapJobFilterActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(MapJobFilterActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(MapJobFilterActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(MapJobFilterActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(MapJobFilterActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(MapJobFilterActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(MapJobFilterActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

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
        this.recyclerViewJobList.setLayoutManager(new LinearLayoutManager(this));
        this.imgFilter = (ImageView) findViewById(R.id.imgFilter);
        this.txtjosss = (TextView) findViewById(R.id.txtjosss);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.nojobfoundlayout = findViewById(R.id.nojobfoundlayout);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Jobs Filter");

        } catch (Exception e) {
            e.printStackTrace();
        }
        getMasterData();
    }

    private void getMasterData() {


        //progressDialog = Utils.createProgressDialog(this);
        //  String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTU3IiwidXNlcm5hbWUiOiJzcmlkaGFyYW4ubWluZG5vdGl4QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTg2MDkwNTAsImp0aSI6IlFYVkdUVEJ3V0dOMlNGWjBkVWxVV1hKT2JEWT0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE4NjA5MDUwLCJleHAiOjE1MTkyMTM4NTB9.-1YP2zxdrvMmlc2mfH6CXyCUxdqRObTFI6GSUByJ84I";

        String token = Pref.getPreToken();


        Map<String, String> data = new HashMap<>();
        data.put("profileid", "14557");
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

                        Categoryview categoryview = new Categoryview();
                        categoryview.setId("0");
                        categoryview.setName("All");
                        categoryviewArrayList.add(0, categoryview);

                        categoryviewArrayList.addAll(response.body().getData().getCategoryview());

                        final Regional_council_list regional_council_list = new Regional_council_list();
                        regional_council_list.setId("0");
                        regional_council_list.setName("All");
                        regional_council_listArrayList.add(0, regional_council_list);

                        regional_council_listArrayList.addAll(response.body().getData().getRegional_council_list());

                        if (regional_council_listArrayAdapter != null)
                            regional_council_listArrayAdapter.notifyDataSetChanged();

                        getJobList();
                    } else {

                        Toast.makeText(MapJobFilterActivity.this, "No record available", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 304) {

                    Toast.makeText(MapJobFilterActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(MapJobFilterActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(MapJobFilterActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(MapJobFilterActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(MapJobFilterActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(MapJobFilterActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(MapJobFilterActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<DataJobMasterFilter> call, Throwable t) {
                //  progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void getJobList() {


        //dataJobArrayList.clear();
        dataJobArrayList = new ArrayList<>();
        // progressDialog = Utils.createProgressDialog(this);
        String token = Pref.getPreToken();


        Map<String, String> data = new HashMap<>();
        data.put("profileid", Pref.getClientId());
        data.put("ismap", "1");


        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("MapJobAcitivity  key, " + key + " value " + value);
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

                    //      progressDialog.dismiss();
                    if (response.code() == 200) {
                        if (response.body().getStatus().equals("1")) {

                            nojobfoundlayout.setVisibility(View.GONE);
                            dataJobArrayList.addAll(response.body().getData());
                            mapJobFIlterAdapter = new MapJobFIlterAdapter(listener, MapJobFilterActivity.this, dataJobArrayList);
                            recyclerViewJobList.setAdapter(mapJobFIlterAdapter);

                            //mapJobFIlterAdapter.notifyDataSetChanged();

                        } else {
                            nojobfoundlayout.setVisibility(View.VISIBLE);
                            Toast.makeText(MapJobFilterActivity.this, "No Jobs Found", Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 304) {

                        Toast.makeText(MapJobFilterActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(MapJobFilterActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(MapJobFilterActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(MapJobFilterActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(MapJobFilterActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(MapJobFilterActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(MapJobFilterActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    nojobfoundlayout.setVisibility(View.GONE);
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<DataJobListItems> call, Throwable t) {
                //  progressDialog.dismiss();
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
                t.printStackTrace();
            }
        });

    }

    private void getJobListSearch(String edtSearchTxt) {

        String spnrJobCategoryTxt_ID = "";
        String spnrJobSubCategoryTxt_ID = "";
        String spnrJobTypeTxt_ID = "";
        String spnrRegionTxt_ID = "";
        String spnrDistrictTxt_ID = "";
        String spnrSalaryRange_Type_ID = "";
        String spnrSalaryFromRange_ID = "";
        String spnrSalaryToRange_ID = "";
        String spnrSortByDate_ID = "";
        dataJobArrayList = new ArrayList<>();
        progressDialog = Utils.createProgressDialog(this);
        String token = Pref.getPreToken();
        if (!categoryviewArrayList.get(spnrJobCategory.getSelectedItemPosition()).getName().equals("All"))
            spnrJobCategoryTxt_ID = categoryviewArrayList.get(spnrJobCategory.getSelectedItemPosition()).getId();
        if (!regional_council_listArrayList.get(spnrRegionLocation.getSelectedItemPosition()).getName().equals("All"))
            spnrRegionTxt_ID = regional_council_listArrayList.get(spnrRegionLocation.getSelectedItemPosition()).getId();

        if (local_board_listArrayList.size() > 0) {
            if (!local_board_listArrayList.get(spnrDistrictCity.getSelectedItemPosition()).getName().equals("All"))
                spnrDistrictTxt_ID = local_board_listArrayList.get(spnrDistrictCity.getSelectedItemPosition()).getId();
        }


        Map<String, String> data = new HashMap<>();
        data.put("profileid", Pref.getClientId());
        data.put("ismap", "1");
        data.put("keywords", edtSearchTxt);
        data.put("category", spnrJobCategoryTxt_ID);
        data.put("regional", spnrRegionTxt_ID);
        data.put("local_board", spnrDistrictTxt_ID);

        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("MapJobAcitivity  key, " + key + " value " + value);
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

                try {

                    Log.d(TAG, "onResponse:DataJobListItems " + new Gson().toJson(response.body()));

                    progressDialog.dismiss();
                    if (response.code() == 200) {
                        if (response.body().getStatus().equals("1")) {


                            dataJobArrayList.addAll(response.body().getData());
                            mapJobFIlterAdapter.notifyDataSetChanged();

                            nojobfoundlayout.setVisibility(View.GONE);

                            mapJobFIlterAdapter = new MapJobFIlterAdapter(listener, MapJobFilterActivity.this, dataJobArrayList);
                            recyclerViewJobList.setAdapter(mapJobFIlterAdapter);


                        } else {
                            nojobfoundlayout.setVisibility(View.VISIBLE);
                            Toast.makeText(MapJobFilterActivity.this, "No record available", Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 304) {

                        Toast.makeText(MapJobFilterActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(MapJobFilterActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(MapJobFilterActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(MapJobFilterActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(MapJobFilterActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(MapJobFilterActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(MapJobFilterActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    nojobfoundlayout.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<DataJobListItems> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });


        edtJobSearchText = edtSearchTxt;
        spnrJobCategoryText = spnrJobCategory.getSelectedItemPosition();
        spnrRegionLocationText = spnrRegionLocation.getSelectedItemPosition();

    }

    /*private void getJobListSearch(String edtSearchTxt) {

        String spnrJobCategoryTxt_ID = "";
        String spnrJobSubCategoryTxt_ID = "";
        String spnrJobTypeTxt_ID = "";
        String spnrRegionTxt_ID = "";
        String spnrDistrictTxt_ID = "";
        String spnrSalaryRange_Type_ID = "";
        String spnrSalaryFromRange_ID = "";
        String spnrSalaryToRange_ID = "";
        String spnrSortByDate_ID = "";
        //dataJobArrayList.clear();
        dataJobArrayList = new ArrayList<>();
        progressDialog = Utils.createProgressDialog(this);
        String token = Pref.getPreToken();
        if (!categoryviewArrayList.get(spnrJobCategory.getSelectedItemPosition()).getName().equals("All"))
            spnrJobCategoryTxt_ID = categoryviewArrayList.get(spnrJobCategory.getSelectedItemPosition()).getId();
        if (!regional_council_listArrayList.get(spnrRegionLocation.getSelectedItemPosition()).getName().equals("All"))
            spnrRegionTxt_ID = regional_council_listArrayList.get(spnrRegionLocation.getSelectedItemPosition()).getId();

        if (local_board_listArrayList.size() > 0) {
            if (!local_board_listArrayList.get(spnrDistrictCity.getSelectedItemPosition()).getName().equals("All"))
                spnrDistrictTxt_ID = local_board_listArrayList.get(spnrDistrictCity.getSelectedItemPosition()).getId();
        }


        Map<String, String> data = new HashMap<>();
        data.put("profileid", Pref.getClientId());
        data.put("ismap", "1");
        data.put("keywords", edtSearchTxt);
        data.put("category", spnrJobCategoryTxt_ID);
        data.put("regional", spnrRegionTxt_ID);
        data.put("local_board", spnrDistrictTxt_ID);

        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("MapJobAcitivity  key, " + key + " value " + value);
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

                try {

                    Log.d(TAG, "onResponse:DataJobListItems " + new Gson().toJson(response.body()));

                    progressDialog.dismiss();
                    if (response.code() == 200) {
                        if (response.body().getStatus().equals("1")) {


                            dataJobArrayList.addAll(response.body().getData());
                            mapJobFIlterAdapter.notifyDataSetChanged();

                        } else {

                            Toast.makeText(MapJobFilterActivity.this, "No record available", Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 304) {

                        Toast.makeText(MapJobFilterActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(MapJobFilterActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(MapJobFilterActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(MapJobFilterActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(MapJobFilterActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(MapJobFilterActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(MapJobFilterActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<DataJobListItems> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });

    }*/


}
