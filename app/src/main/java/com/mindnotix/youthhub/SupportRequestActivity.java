package com.mindnotix.youthhub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mindnotix.adapter.SupportRequestAdapter;
import com.mindnotix.model.BasicResponse;
import com.mindnotix.model.youth_support.ShareYouth;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 07-02-2018.
 */

public class SupportRequestActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    View noDataFound;
    SupportRequestAdapter supportRequestAdapter;
    ProgressDialog progressDialog;
    ArrayList<ShareYouth> shareYouths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_request);

        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Support Request");

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  onBackPressed();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        shareYouths = new ArrayList<>();

        loadSupportRequest();


        recyclerView = findViewById(R.id.recyclerView);
        noDataFound = findViewById(R.id.noDataFound);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


    Call<BasicResponse> inviteUsersDilaog;

    private void loadSupportRequest() {
        progressDialog = Utils.createProgressDialog(this);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        inviteUsersDilaog = apiService.inviteUsersTitle(Pref.getPreToken());

        inviteUsersDilaog.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(@NonNull Call<BasicResponse> call, @NonNull Response<BasicResponse> response) {

                if (response.code() == 200 || response.code() == 201) {

                    BasicResponse basicResponse = response.body();

                    if (basicResponse.getStatus().equals("1")) {
                        progressDialog.dismiss();

                        try {

                            Pref.setdevicetoken("Bearer " + basicResponse.getToken());


                            shareYouths.addAll(basicResponse.getData().getShare_youth());
                            if (shareYouths.size() > 0) {
                                supportRequestAdapter = new SupportRequestAdapter(SupportRequestActivity.this, shareYouths);
                                recyclerView.setAdapter(supportRequestAdapter);
                                recyclerView.setVisibility(View.VISIBLE);
                                noDataFound.setVisibility(View.GONE);
                            } else {
                                recyclerView.setVisibility(View.GONE);
                                noDataFound.setVisibility(View.VISIBLE);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(SupportRequestActivity.this, basicResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else if (response.code() == 304) {
                    progressDialog.dismiss();

                    Toast.makeText(SupportRequestActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    progressDialog.dismiss();

                    Toast.makeText(SupportRequestActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    progressDialog.dismiss();

                    Toast.makeText(SupportRequestActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    progressDialog.dismiss();
                    Toast.makeText(SupportRequestActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    progressDialog.dismiss();
                    Toast.makeText(SupportRequestActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    progressDialog.dismiss();
                    Toast.makeText(SupportRequestActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    progressDialog.dismiss();
                    Toast.makeText(SupportRequestActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {

                progressDialog.dismiss();
            }


        });
    }



}