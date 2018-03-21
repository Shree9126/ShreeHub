package com.mindnotix.youthhub.cvresume.achievementsAward;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.model.resume.volunteer.Volunteer;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;
import com.mindnotix.youthhub.R;
import com.mindnotix.youthhub.cvresume.volunteerExperience.VolunteerExperienceActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 17-03-2018.
 */

public class AchievementsActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvSave;
    TextView tvCancel;
    EditText edEndDate;
    EditText edDescription;
    EditText edIssuedBy;
    EditText edTitle;
    EditText edOccupation;
    Toolbar toolbar;
    ProgressDialog progressDialog;

    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "dd-MM-yyyy";
    public static String statusDate = " ";

    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.GERMANY);
    DatePickerDialog.OnDateSetListener date;

    View endDateView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        initUi();


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


    }

    private void updateDate() {

        edEndDate.setText(sdf.format(myCalendar.getTime()));


    }


    private void initUi() {
        tvSave = (TextView) findViewById(R.id.tvSave);
        endDateView =  findViewById(R.id.endDateView);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        edEndDate = (EditText) findViewById(R.id.edEndDate);
        edDescription = (EditText) findViewById(R.id.edDescription);
        edIssuedBy = (EditText) findViewById(R.id.edIssuedBy);
        edOccupation = (EditText) findViewById(R.id.edOccupation);
        edTitle = (EditText) findViewById(R.id.edTitle);
        toolbar = findViewById(R.id.toolbar);

        toolbarInit();

        tvSave.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        endDateView.setOnClickListener(this);

    }


    private void toolbarInit() {
        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setTitle("Achievements & Awards");
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
            case R.id.tvSave:

                validate();

                break;

            case R.id.tvCancel:

                finish();

                break;


            case R.id.endDateView:

                calendar();

                break;






        }

    }

    private void validate() {

        if (!edTitle.getText().toString().equals("") && !edTitle.getText().toString().trim().isEmpty()) {
            if (!edOccupation.getText().toString().equals("") && !edOccupation.getText().toString().trim().isEmpty()) {
                if (!edIssuedBy.getText().toString().equals("") && !edIssuedBy.getText().toString().trim().isEmpty()) {
                    if (!edEndDate.getText().toString().equals("") && !edEndDate.getText().toString().trim().isEmpty()) {

                        addVolunterApi();

                    } else {
                        Toast.makeText(this, "Please enter the enddate", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(this, "Please enter the issuedby", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Please enter the occupation", Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(this, "Please enter the title", Toast.LENGTH_SHORT).show();
        }


    }


    Call<Volunteer> addvolunter;

    private void addVolunterApi() {


        Map<String, String> data = new HashMap<>();

        data.put("sid", Pref.getClientId());
        data.put("title", edTitle.getText().toString());
        data.put("occupation", edOccupation.getText().toString());
        data.put("description", edDescription.getText().toString().trim());
        data.put("provider", edIssuedBy.getText().toString());
        data.put("end_date", edEndDate.getText().toString());
        data.put("type", "5");


        Log.v("enefnfnjenfjke", new Gson().toJson(data));


        progressDialog = Utils.createProgressDialog(AchievementsActivity.this);
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

                    Toast.makeText(AchievementsActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(AchievementsActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(AchievementsActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(AchievementsActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(AchievementsActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(AchievementsActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(AchievementsActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<Volunteer> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();

            }
        });
    }


    private void calendar() {

        DatePickerDialog dialog = new DatePickerDialog(AchievementsActivity.this, date, myCalendar
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
