package com.mindnotix.youthhub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.model.jobs.jobview.DataJIbDetailsView;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetailsActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = JobDetailsActivity.class.getSimpleName();
    ProgressDialog progressDialog;
    String joblist_id = "";
    String istrademe = "";
    String isJobApply = "";
    String lattitude = "";
    String companyName = "";
    String longtitude = "";
    String tm_applyviatrademe = "";
    FloatingActionButton fab;
    View detailsView;
    private android.support.v7.widget.Toolbar toolbar;
    private android.widget.TextView txtJobTitle;
    private android.widget.TextView txtJobApply;
    private de.hdodenhof.circleimageview.CircleImageView imgCompanyProfilePic;
    private android.widget.TextView txtCompanyName;
    private android.widget.TextView txtCompanyWebsite;
    private android.widget.TextView txtJobType;
    private android.widget.TextView txtJobSalaryRange;
    private android.widget.TextView txtDistrictCity;
    private android.widget.TextView txtWorkHoursDays;
    private android.widget.TextView ConstructionId;
    private android.widget.TextView VacanciesId;
    private android.widget.TextView txtPostedDatetitle;
    private android.widget.TextView txtPostedDate;
    private android.widget.TextView txtReferenceNo;
    private android.widget.TextView txtSummaryTitle;
    private android.widget.TextView txtSummary;
    private android.widget.TextView txtDescriptionTitle;
    private android.widget.TextView txtDescription;
    private android.widget.TextView txtRequirementTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details_main);
        // setContentView(R.layout.fragment_event_about);

        UiInitialization();


        if (getIntent() != null) {
            joblist_id = getIntent().getStringExtra("joblist_id");
        }

        getDetailsOfJOB();
    }

    private void UiInitialization() {

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        this.txtRequirementTitle = (TextView) findViewById(R.id.txtRequirementTitle);
        this.txtDescription = (TextView) findViewById(R.id.txtDescription);
        this.txtDescriptionTitle = (TextView) findViewById(R.id.txtDescriptionTitle);
        this.ConstructionId = (TextView) findViewById(R.id.ConstructionId);
        this.txtSummary = (TextView) findViewById(R.id.txtSummary);
        this.txtSummaryTitle = (TextView) findViewById(R.id.txtSummaryTitle);
        this.txtReferenceNo = (TextView) findViewById(R.id.txtReferenceNo);
        this.txtPostedDate = (TextView) findViewById(R.id.txtPostedDate);
        //this.txtPostedDatetitle = (TextView) findViewById(R.id.txtPostedDate_title);
        this.txtWorkHoursDays = (TextView) findViewById(R.id.txtWorkHoursDays);
        this.VacanciesId = (TextView) findViewById(R.id.VacanciesId);
        this.txtDistrictCity = (TextView) findViewById(R.id.txtDistrictCity);
        this.txtJobSalaryRange = (TextView) findViewById(R.id.txtJobSalaryRange);
        this.txtJobType = (TextView) findViewById(R.id.txtJobType);
        this.txtCompanyWebsite = (TextView) findViewById(R.id.txtCompanyWebsite);
        this.txtCompanyName = (TextView) findViewById(R.id.txtCompanyName);
        this.imgCompanyProfilePic = (CircleImageView) findViewById(R.id.imgCompanyProfilePic);
        this.txtJobApply = (TextView) findViewById(R.id.txtJobApply);
        this.txtJobApply.setOnClickListener(this);
        this.txtJobTitle = (TextView) findViewById(R.id.txtJobTitle);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.detailsView = findViewById(R.id.detailsView);


        setSupportActionBar(toolbar);
        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Job Details");

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
                Intent getLocations = new Intent(JobDetailsActivity.this, JobsMapsActivity.class);
                getLocations.putExtra("lat", lattitude);
                getLocations.putExtra("lang", longtitude);
                getLocations.putExtra("company_name", companyName);
                startActivity(getLocations);
                break;

            case R.id.txtJobApply:

                if (istrademe.equals("1")) {

                    Intent mIntent = new Intent(JobDetailsActivity.this, TradeJobViewActivity.class);
                    mIntent.putExtra("trade_url", tm_applyviatrademe);
                    startActivity(mIntent);

                } else {

                    if (!isJobApply.equals("1")) {
                        Intent mIntent = new Intent(JobDetailsActivity.this, JobApplyActivity.class);
                        mIntent.putExtra("joblist_id", joblist_id);
                        startActivity(mIntent);
                        finish();
                    } else {
                        Intent mIntent = new Intent(JobDetailsActivity.this, MyJobsActivity.class);
                        startActivity(mIntent);

                    }

                }


                break;
        }
    }


    private void getDetailsOfJOB() {

        progressDialog = Utils.createProgressDialog(this);
        //String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTU3IiwidXNlcm5hbWUiOiJzcmlkaGFyYW4ubWluZG5vdGl4QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTg2MDkwNTAsImp0aSI6IlFYVkdUVEJ3V0dOMlNGWjBkVWxVV1hKT2JEWT0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE4NjA5MDUwLCJleHAiOjE1MTkyMTM4NTB9.-1YP2zxdrvMmlc2mfH6CXyCUxdqRObTFI6GSUByJ84I";

        String token = Pref.getPreToken();


        Map<String, String> data = new HashMap<>();
        data.put("profileid", Pref.getClientId());
        if (joblist_id != null)
            data.put("jobid", joblist_id);
        else
            data.put("jobid", "");
        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


        //token = "Bearer "+ Pref.getPreToken();


        Log.d(TAG, "changePassword: token " + token);

        Call<DataJIbDetailsView> call = apiService.getJobDetailsView(token, data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataJIbDetailsView>() {
            @Override
            public void onResponse(Call<DataJIbDetailsView> call, Response<DataJIbDetailsView> response) {

                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("1")) {

                        detailsView.setVisibility(View.VISIBLE);

                        Log.d(TAG, "onResponse:DataJIbDetailsView " + new Gson().toJson(response.body()));

                        txtJobTitle.setText(response.body().getData().getJobview().getTitle());

                        if (!response.body().getData().getJobview().getHours_per_week().equals(""))
                            txtWorkHoursDays.setText(response.body().getData().getJobview().getHours_per_week());
                        else
                            txtWorkHoursDays.setVisibility(View.GONE);
                        txtPostedDate.setText(response.body().getData().getJobview().getStart_date()
                                .concat(" - " + response.body().getData().getJobview().getEnd_date()));
                        txtReferenceNo.setText("Reference No : " + response.body().getData().getJobview().getCode());
                        txtCompanyName.setText(response.body().getData().getJobview().getCompany_name());
                        txtRequirementTitle.setText(response.body().getData().getJobview().getTitle());
                        if (!response.body().getData().getJobview().getAddress().equals(""))
                            txtDistrictCity.setText("Company Location : " + response.body().getData().getJobview().getAddress());
                        else
                            txtDistrictCity.setVisibility(View.GONE);

                        txtCompanyWebsite.setText(response.body().getData().getJobview().getWebsite_address());
                        if (!response.body().getData().getJobview().getSalary().equals(""))
                            txtJobSalaryRange.setText(response.body().getData().getJobview().getSalary());
                        else
                            txtJobSalaryRange.setVisibility(View.GONE);
                        txtJobType.setText(response.body().getData().getJobview().getJobtypename());

                        if (!response.body().getData().getJobview().getCategoryname().equals(""))
                            ConstructionId.setText(response.body().getData().getJobview().getCategoryname()
                                    .concat(" | " + response.body().getData().getJobview().getSubcategoryname()));
                        else
                            ConstructionId.setVisibility(View.GONE);

                        txtDescription.setText(Html.fromHtml(response.body().getData().getJobview().getDescription()));
                        Log.d(TAG, "onResponse:ssss getIstrademe " + response.body().getData().getJobview().getIstrademe());
                        Log.d(TAG, "onResponse:ssss getTm_applyviatrademe " + response.body().getData().getJobview().getTm_applyviatrademe());
                        istrademe = response.body().getData().getJobview().getIstrademe();
                        isJobApply = response.body().getData().getJobview().getIsapplied();
                        tm_applyviatrademe = response.body().getData().getJobview().getTm_applyviatrademe();
                        longtitude = response.body().getData().getJobview().getLongitude();
                        lattitude = response.body().getData().getJobview().getLatitude();
                        companyName = response.body().getData().getJobview().getCategoryname();
                        if (!response.body().getData().getJobview().getNo_of_vacancies().equals("0"))
                            VacanciesId.setText(response.body().getData().getJobview().getNo_of_vacancies().concat(" Vacancies"));
                        else
                            VacanciesId.setVisibility(View.GONE);

                        Log.d(TAG, "onResponse:isJobApply " + isJobApply);
                        if (response.body().getData().getJobview().getIsapplied().equals("1")) {
                            Log.d(TAG, "onResponse:isJobApply if " + isJobApply);
                            txtJobApply.setText("Applied");
                            txtJobApply.setTextColor(getResources().getColor(R.color.medium_level_blue));

                        } else {
                            Log.d(TAG, "onResponse:isJobApply else " + isJobApply);
                        }

                        Picasso.with(JobDetailsActivity.this)
                                .load(response.body().getData().getJobview().getCompany_logo())
                                .placeholder(R.drawable.noprofile) //this is optional the image to display while the url image is downloading
                                .error(R.drawable.noprofile)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                .into(imgCompanyProfilePic);


                    } else {

                        // Toast.makeText(JobDetailsActivity.this, "No record available", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 304) {

                    Toast.makeText(JobDetailsActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(JobDetailsActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(JobDetailsActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(JobDetailsActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(JobDetailsActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(JobDetailsActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(JobDetailsActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<DataJIbDetailsView> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }


}
