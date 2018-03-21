package com.mindnotix.youthhub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.adapter.JobQuestionsListAdapter;
import com.mindnotix.adapter.JobResumeListAdapter;
import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.BasicResponse;
import com.mindnotix.model.jobs.apply_master.DataJobApplyMaster;
import com.mindnotix.model.jobs.apply_master.Job_questions;
import com.mindnotix.model.jobs.apply_master.Resumeattached;
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

public class JobApplyActivity extends BaseActivity {

    private static final String TAG = JobApplyActivity.class.getSimpleName();
    public ArrayList<Resumeattached> resumeattachedArrayList = new ArrayList<>();
    JobQuestionsListAdapter jobQuestionsListAdapter;
    JobResumeListAdapter jobResumeListAdapter;
    ProgressDialog progressDialog;
    RecyclerViewClickListener listener;
    ArrayList<Job_questions> job_questionsArrayList = new ArrayList<>();
    String joblist_id;
    private android.support.v7.widget.Toolbar toolbar;
    private android.widget.EditText edtMessages;
    private android.widget.TextView txtResumeTitle;
    private android.support.v7.widget.RecyclerView recyclerViewResumeList;
    private android.support.v7.widget.RecyclerView recyclerViewQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_apply);

        UiInitialization();

        if (getIntent() != null) {
            joblist_id = getIntent().getStringExtra("joblist_id");
        }

        listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {


            }
        };


        getJobApplyMasterData();
    }

    private void UiInitialization() {
        this.recyclerViewQuestions = (RecyclerView) findViewById(R.id.recyclerViewQuestions);
        this.recyclerViewQuestions.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerViewResumeList = (RecyclerView) findViewById(R.id.recyclerViewResumeList);
        this.recyclerViewResumeList.setLayoutManager(new LinearLayoutManager(this));
        this.edtMessages = (EditText) findViewById(R.id.edtMessages);
        this.txtResumeTitle = (TextView) findViewById(R.id.txtResumeTitle);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Apply for job");

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

    private void getJobApplyMasterData() {


        progressDialog = Utils.createProgressDialog(this);
        //  String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTU3IiwidXNlcm5hbWUiOiJzcmlkaGFyYW4ubWluZG5vdGl4QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTg2MDkwNTAsImp0aSI6IlFYVkdUVEJ3V0dOMlNGWjBkVWxVV1hKT2JEWT0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE4NjA5MDUwLCJleHAiOjE1MTkyMTM4NTB9.-1YP2zxdrvMmlc2mfH6CXyCUxdqRObTFI6GSUByJ84I";
        String token = Pref.getPreToken();


        Map<String, String> data = new HashMap<>();
        data.put("profileid", Pref.getClientId());
        data.put("jobid", joblist_id);
        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);

        Log.d(TAG, "changePassword: token " + token);

        Call<DataJobApplyMaster> call = apiService.getJobApplyMaster(token, data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataJobApplyMaster>() {
            @Override
            public void onResponse(Call<DataJobApplyMaster> call, Response<DataJobApplyMaster> response) {

                progressDialog.dismiss();
                if (response.code() == 200 || response.code() == 201) {
                    if (response.body().getStatus().equals("1")) {

                        Log.d(TAG, "onResponse: dataaaaaa" + new Gson().toJson(response.body()));

                        if (!response.body().getData().getResumeattached_count().equals("0"))
                            resumeattachedArrayList.addAll(response.body().getData().getResumeattached());

                        if (!response.body().getData().getJob_questions_count().equals("0"))
                            job_questionsArrayList.addAll(response.body().getData().getJob_questions());
                        Log.d(TAG, "onResponse: resumeattachedArrayList " + resumeattachedArrayList.size());
                        Log.d(TAG, "onResponse: job_questionsArrayList " + job_questionsArrayList.size());

                        jobQuestionsListAdapter = new JobQuestionsListAdapter(listener, JobApplyActivity.this, job_questionsArrayList);
                        recyclerViewQuestions.setAdapter(jobQuestionsListAdapter);

                        if (resumeattachedArrayList.size() == 0) {
                            txtResumeTitle.setVisibility(View.GONE);
                        } else {
                            jobResumeListAdapter = new JobResumeListAdapter(listener, JobApplyActivity.this, resumeattachedArrayList);
                            recyclerViewResumeList.setAdapter(jobResumeListAdapter);

                        }


                    } else {

                        Toast.makeText(JobApplyActivity.this, "No record available", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 304) {

                    Toast.makeText(JobApplyActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(JobApplyActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(JobApplyActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(JobApplyActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(JobApplyActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(JobApplyActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(JobApplyActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<DataJobApplyMaster> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }


    public void applyJob() {

        String attachedfile = "";
        String question_ans = "question_ans_";
        progressDialog = Utils.createProgressDialog(this);
        //String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTU3IiwidXNlcm5hbWUiOiJzcmlkaGFyYW4ubWluZG5vdGl4QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTg2MDkwNTAsImp0aSI6IlFYVkdUVEJ3V0dOMlNGWjBkVWxVV1hKT2JEWT0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE4NjA5MDUwLCJleHAiOjE1MTkyMTM4NTB9.-1YP2zxdrvMmlc2mfH6CXyCUxdqRObTFI6GSUByJ84I";
        String token = Pref.getPreToken();

        for (int i = 0; i < resumeattachedArrayList.size(); i++) {

            if (resumeattachedArrayList.get(i).getStatus().equals("1"))
                attachedfile = attachedfile + resumeattachedArrayList.get(i).getId();

        }
        Log.d(TAG, "applyJob:attachedfile " + attachedfile);


        Map<String, String> data = new HashMap<>();
        data.put("profileid", Pref.getClientId());
        data.put("jobid", joblist_id);
        data.put("istrademe", "0");
        data.put("sendmessage", edtMessages.getText().toString().trim());
        data.put("attachedfile", attachedfile);
        for (int i = 0; i < job_questionsArrayList.size(); i++) {
            data.put(question_ans.concat(String.valueOf(i)), job_questionsArrayList.get(i).getEdt_value());
            Log.d(TAG, "applyJob: " + question_ans.concat(String.valueOf(i)) + " values "
                    + job_questionsArrayList.get(i).getEdt_value());
        }


        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);

        Log.d(TAG, "changePassword: token " + token);

        Call<BasicResponse> call = apiService.putApplyJobSubmit(token, data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {

                progressDialog.dismiss();
                if (response.code() == 200 || response.code() == 201) {
                    if (response.body().getStatus().equals("1")) {

                        Toast.makeText(JobApplyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent mIntent = new Intent(JobApplyActivity.this, JobDetailsActivity.class);
                        mIntent.putExtra("joblist_id", joblist_id);
                        startActivity(mIntent);
                        finish();
                    } else {

                        Toast.makeText(JobApplyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 304) {

                    Toast.makeText(JobApplyActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(JobApplyActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(JobApplyActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(JobApplyActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(JobApplyActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(JobApplyActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(JobApplyActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }
}
