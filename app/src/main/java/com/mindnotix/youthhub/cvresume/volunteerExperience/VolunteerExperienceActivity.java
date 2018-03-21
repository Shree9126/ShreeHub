package com.mindnotix.youthhub.cvresume.volunteerExperience;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.model.resume.Education.title.Qualification;
import com.mindnotix.model.resume.volunteer.Volunteer;
import com.mindnotix.model.resume.volunteer.Volunteercause;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;
import com.mindnotix.youthhub.R;

import com.mindnotix.youthhub.cvresume.Education.EducationResumeActivity;
import com.suke.widget.SwitchButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 17-03-2018.
 */

public class VolunteerExperienceActivity extends AppCompatActivity implements View.OnClickListener {


    private Toolbar toolbar;
    private EditText edRole;
    private EditText edorganization;
    private EditText edDescription;
    private Spinner spCause;
    private SwitchButton switchButton;
    private EditText edStartDate;
    private EditText edEndDate;
    private TextView tvCancel;
    private TextView tvSave;
    ProgressDialog progressDialog;
    View viewendDate;
    View viewstartDate;
    View enDateView;

    ArrayList<Volunteercause> volunteers;
    private String spCauseSatusI = "0";
    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "dd-MM-yyyy";
    public static String statusDate = " ";

    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.GERMANY);
    DatePickerDialog.OnDateSetListener date;
    public Boolean isProgressChecked = false;
    public static String StartDateId = " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunter_experience_resume);
        initalizeVar();


    }

    private void initalizeVar() {
        this.enDateView = findViewById(R.id.enDateView);
        this.viewendDate = findViewById(R.id.viewendDate);
        this.viewstartDate = findViewById(R.id.viewstartDate);
        this.tvSave = (TextView) findViewById(R.id.tvSave);
        this.tvCancel = (TextView) findViewById(R.id.tvCancel);
        this.edEndDate = (EditText) findViewById(R.id.edEndDate);
        this.edStartDate = (EditText) findViewById(R.id.edStartDate);
        this.switchButton = (SwitchButton) findViewById(R.id.switchButton);
        this.spCause = (Spinner) findViewById(R.id.spCause);
        this.edDescription = (EditText) findViewById(R.id.edDescription);
        this.edorganization = (EditText) findViewById(R.id.edorganization);
        this.edRole = (EditText) findViewById(R.id.edRole);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);


        tvSave.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        viewstartDate.setOnClickListener(this);
        viewendDate.setOnClickListener(this);


        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

                if (isChecked) {
                    statusDate = "2";
                    isProgressChecked = true;
                    enDateView.setVisibility(View.GONE);
                } else {
                    statusDate = "1";
                    isProgressChecked = false;
                    enDateView.setVisibility(View.VISIBLE);
                }

            }
        });

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

        };


        toolbarInit();
        loadCause();


    }


    private void updateDate() {
        Log.d("updateDate", "updateDate: " + sdf.format(myCalendar.getTime()));

        if (StartDateId.equals("1")) {
            edStartDate.setText(sdf.format(myCalendar.getTime()));
        } else {
            edEndDate.setText(sdf.format(myCalendar.getTime()));
        }

    }


    private void toolbarInit() {
        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setTitle("Volunteering Experience");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onBackPressed();

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {

            case R.id.tvCancel:
                finish();
                break;

            case R.id.tvSave:

                validate();

                break;

            case R.id.viewstartDate:
                StartDateId = "1";
                calendar();


                break;

            case R.id.viewendDate:

                StartDateId = "2";
                calendar();

                break;


        }

    }

    private void validate() {


        if (!edRole.getText().toString().equals("") && !edRole.getText().toString().trim().isEmpty()) {
            if (!edorganization.getText().toString().equals("") && !edorganization.getText().toString().trim().isEmpty()) {
                if (spCause.getSelectedItemPosition() != 0) {
                    if (switchButton.isChecked()) {
                        if (!edStartDate.getText().toString().isEmpty() &&!edStartDate.getText().toString().equals("")) {
                            addVolunterApi();

                        } else {
                            Toast.makeText(this, "Please select the startdate", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (!edStartDate.getText().toString().isEmpty() && !edStartDate.getText().toString().equals("")) {
                            if (!edEndDate.getText().toString().isEmpty() && !edEndDate.getText().toString().equals("")) {
                                addVolunterApi();

                            } else {
                                Toast.makeText(this, "Please select the enddate", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(this, "Please select the startdate", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(this, "Please select the cause", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Please enter the organization", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please enter the role", Toast.LENGTH_SHORT).show();
        }


    }


    Call<Volunteer> addvolunter;

    private void addVolunterApi() {


        Map<String, String> data = new HashMap<>();

        data.put("sid", Pref.getClientId());
        data.put("title", edRole.getText().toString());
        data.put("provider", edorganization.getText().toString());
        data.put("description", edDescription.getText().toString().trim());
        data.put("category_id", String.valueOf(volunteers.get(spCause.getSelectedItemPosition()).getId()));
        data.put("status", "2");
        if (switchButton.isChecked()) {

            data.put("start_date", edStartDate.getText().toString());
            data.put("end_date", "");

        } else {
            data.put("start_date", edStartDate.getText().toString());
            data.put("end_date", edEndDate.getText().toString());

        }

        data.put("type", "4");


        Log.v("enefnfnjenfjke",new Gson().toJson(data));


        progressDialog = Utils.createProgressDialog(VolunteerExperienceActivity.this);
        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        addvolunter = apiService.addVolunter(Pref.getPreToken(), data);

        addvolunter.enqueue(new Callback<Volunteer>() {
            @Override
            public void onResponse(@NonNull Call<Volunteer> call, @NonNull Response<Volunteer> response) {

                progressDialog.dismiss();

                if (response.code() == 200 || response.code() == 201) {


                    Volunteer volunteer = response.body();

                    if (volunteer.getStatus().equals("1")) {

                        try {

                            Pref.setdevicetoken("Bearer " + volunteer.getToken());
                            finish();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else if (response.code() == 304) {

                    Toast.makeText(VolunteerExperienceActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(VolunteerExperienceActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(VolunteerExperienceActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(VolunteerExperienceActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(VolunteerExperienceActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(VolunteerExperienceActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(VolunteerExperienceActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<Volunteer> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();

            }
        });
    }


    Call<Volunteer> loadProvider;

    private void loadCause() {
        volunteers = new ArrayList<>();

        progressDialog = Utils.createProgressDialog(VolunteerExperienceActivity.this);
        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        loadProvider = apiService.loadCause(Pref.getPreToken());

        loadProvider.enqueue(new Callback<Volunteer>() {
            @Override
            public void onResponse(@NonNull Call<Volunteer> call, @NonNull Response<Volunteer> response) {

                progressDialog.dismiss();

                if (response.code() == 200 || response.code() == 201) {


                    Volunteer volunteer = response.body();

                    if (volunteer.getStatus().equals("1")) {

                        try {

                            Pref.setdevicetoken("Bearer " + volunteer.getToken());
                            volunteers.addAll(volunteer.getData().getVolunteercause());

                            spinnerCauseAdapter();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else if (response.code() == 304) {

                    Toast.makeText(VolunteerExperienceActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(VolunteerExperienceActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(VolunteerExperienceActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(VolunteerExperienceActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(VolunteerExperienceActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(VolunteerExperienceActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(VolunteerExperienceActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<Volunteer> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();

            }
        });
    }

    private void spinnerCauseAdapter() {


        Volunteercause volunteercause = new Volunteercause();
        volunteercause.setId("0");
        volunteercause.setName("--Select--");
        volunteers.add(0, volunteercause);


        ArrayAdapter<Volunteercause> regionalsArrayAdapter = new ArrayAdapter<>(VolunteerExperienceActivity.this,
                android.R.layout.simple_spinner_dropdown_item, volunteers);
        regionalsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spCause.setAdapter(regionalsArrayAdapter);
        spCause.setSelection(0);

    }


    private void calendar() {

        DatePickerDialog dialog = new DatePickerDialog(VolunteerExperienceActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        Calendar minCal = Calendar.getInstance();
        minCal.set(Calendar.YEAR, minCal.get(Calendar.YEAR) - 26);
        Calendar maxCal = Calendar.getInstance();
        maxCal.set(Calendar.YEAR, maxCal.get(Calendar.YEAR) - 13);


        dialog.getDatePicker().setMinDate(minCal.getTimeInMillis());
        dialog.getDatePicker().setMaxDate(maxCal.getTimeInMillis());


        dialog.show();

    }

}
