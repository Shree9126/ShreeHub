package com.mindnotix.youthhub.cvresume.workExperience;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mindnotix.adapter.resumeAdapter.workExperience.KeyResponsibiltesAddAdapter;
import com.mindnotix.model.BasicResponse;
import com.mindnotix.model.SubPartnerType.SubPartner;
import com.mindnotix.model.SubPartnerType.Subpartnertype;
import com.mindnotix.model.resume.Education.KeyResponsibiltites;
import com.mindnotix.model.resume.workExperience.Example;
import com.mindnotix.model.resume.workExperience.Job_types;
import com.mindnotix.model.resume.workExperience.Partner_types;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;
import com.mindnotix.youthhub.R;
import com.mindnotix.youthhub.cvresume.CvResumeFragment;
import com.suke.widget.SwitchButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 26-02-2018.
 */

public class WorkExperienceActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = WorkExperienceActivity.class.getSimpleName();
    KeyResponsibiltesAddAdapter keyResponsibiltesAddAdapter;
    Toolbar toolbar;

    public static String partnerId = " ";
    public static String worksubcatagoryid = " ";
    public static String statusDate = " ";
    public static String jobTypeid = " ";
    public static String StartDateId = " ";
    public Boolean isProgressChecked = false;

    EditText edKeyResponsibiltites;
    EditText edjobTitle;
    EditText tvEmployer;
    EditText edjobDescription;
    EditText edStartDate;
    EditText edEndDate;
    Spinner spJobType;
    Spinner spSubWorkIndustry;
    Spinner spWorkIndustryid;

    SwitchButton switchButton;

    View layoutProgress;

    ArrayList<Partner_types> partner_types;
    ArrayList<Job_types> job_types;
    ArrayList<Subpartnertype> subPartners;

    TextView btSave;

    View editDateView;

    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "dd-MM-yyyy";

    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.GERMANY);
    DatePickerDialog.OnDateSetListener date;

    View starDateLayout;
    TextView tvCancel;

    TextView addKeyResponsibilties;
    RecyclerView recyclerView;

    KeyResponsibiltites keyResponsibiltitesModel;

    ArrayList<KeyResponsibiltites> keyResponsibiltites;

    ProgressDialog progressDialog;

    LinearLayoutManager linearLayoutManager;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_work_experience);

        edjobTitle = findViewById(R.id.edjobTitle);
        tvEmployer = findViewById(R.id.tvEmployer);
        edjobDescription = findViewById(R.id.edjobDescription);
        edEndDate = findViewById(R.id.edEndDate);
        edStartDate = findViewById(R.id.edStartDate);
        spJobType = findViewById(R.id.spJobType);
        spSubWorkIndustry = findViewById(R.id.spSubWorkIndustry);
        spWorkIndustryid = findViewById(R.id.spWorkIndustryid);
        layoutProgress = findViewById(R.id.layoutProgress);
        switchButton = findViewById(R.id.switch_button);
        editDateView = findViewById(R.id.editDateView);
        btSave = findViewById(R.id.btSave);
        starDateLayout = findViewById(R.id.starDateLayout);
        addKeyResponsibilties = findViewById(R.id.addKeyResponsibilties);
        edKeyResponsibiltites = findViewById(R.id.edKeyResponsibiltites);
        tvCancel = findViewById(R.id.tvCancel);
        toolbar = findViewById(R.id.toolbar);

        keyResponsibiltites = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        partner_types = new ArrayList<>();
        job_types = new ArrayList<>();
        subPartners = new ArrayList<>();

        Partner_types partner_types_add = new Partner_types();
        partner_types_add.setId("0");
        partner_types_add.setName("--Select--");
        partner_types.add(partner_types_add);


        Job_types job_types_model = new Job_types();
        job_types_model.setId("0");
        job_types_model.setName("--Select--");
        job_types.add(job_types_model);


        Subpartnertype subpartnertype = new Subpartnertype();
        subpartnertype.setId("0");
        subpartnertype.setName("--Select--");
        subPartners.add(subpartnertype);


        loadDatasMaster();



        spWorkIndustryid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String partnerType = partner_types.get(position).getId();

                if (!partnerType.equals("0")) {
                    partnerId = partnerType;
                    loadSubPartnerType(partnerId);
                } else {
                    partnerId = "0";
                    loadSubPartner();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spSubWorkIndustry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String subworkcatagoreyid = subPartners.get(position).getId();

                if (!subworkcatagoreyid.equals("0")) {
                    worksubcatagoryid = subworkcatagoreyid;

                } else {
                    worksubcatagoryid = "0";
                    loadSubWorkAdapter();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spJobType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String job_id_type = job_types.get(position).getId();

                if (!job_id_type.equals("0")) {
                    jobTypeid = job_id_type;

                } else {
                    jobTypeid = "0";
                    loadSubPartner();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btSave.setOnClickListener(this);
        starDateLayout.setOnClickListener(this);
        editDateView.setOnClickListener(this);
        tvCancel.setOnClickListener(this);


        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

                if (isChecked) {
                    statusDate = "2";
                    isProgressChecked = true;
                    editDateView.setVisibility(View.GONE);
                } else {
                    statusDate = "1";
                    isProgressChecked = false;
                    editDateView.setVisibility(View.VISIBLE);
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

        btSave = findViewById(R.id.btSave);

        addKeyResponsibilties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                keyResponsibiltitesModel = new KeyResponsibiltites("");
                keyResponsibiltites.add(keyResponsibiltitesModel);

                keyResponsibiltesAddAdapter = new KeyResponsibiltesAddAdapter(WorkExperienceActivity.this,
                        keyResponsibiltites);
                recyclerView.setAdapter(keyResponsibiltesAddAdapter);
                keyResponsibiltesAddAdapter.notifyDataSetChanged();
            }
        });



        toolbarInit();


    }


    private void toolbarInit() {
        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);

            getSupportActionBar().setTitle("Work Experience");

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


    private void updateDate() {
        Log.d("updateDate", "updateDate: " + sdf.format(myCalendar.getTime()));

        if (StartDateId.equals("1")) {
            edStartDate.setText(sdf.format(myCalendar.getTime()));
        } else {
            edEndDate.setText(sdf.format(myCalendar.getTime()));
        }

    }


    private void loadSubPartner() {

        ArrayAdapter<Subpartnertype> loadsubPartner = new ArrayAdapter<Subpartnertype>(WorkExperienceActivity.this,
                android.R.layout.simple_spinner_dropdown_item, subPartners);
        loadsubPartner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spSubWorkIndustry.setAdapter(loadsubPartner);
        spSubWorkIndustry.setSelection(0);
    }


    Call<Example> loadDataMaster;

    private void loadDatasMaster() {


        layoutProgress.setVisibility(View.VISIBLE);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        loadDataMaster = apiService.loadMaster(Pref.getPreToken());

        loadDataMaster.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(@NonNull Call<Example> call, @NonNull Response<Example> response) {

                if (response.code() == 200 || response.code() == 201) {

                    Example example = response.body();

                    if (example.getStatus().equals("1")) {

                        try {

                            layoutProgress.setVisibility(View.GONE);

                            Pref.setdevicetoken("Bearer " + example.getToken());

                            partner_types.addAll(example.getData().getPartner_types());
                            job_types.addAll(example.getData().getJob_types());

                            setSpinner();
                            loadJobSpinner();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        layoutProgress.setVisibility(View.VISIBLE);
                    }


                } else if (response.code() == 304) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<Example> call, @NonNull Throwable t) {
                layoutProgress.setVisibility(View.GONE);
                t.printStackTrace();

            }
        });


    }

    Call<SubPartner> loadSubMaster;

    private void loadSubPartnerType(final String partnerId) {

        progressDialog=Utils.createProgressDialog(WorkExperienceActivity.this);

        Map<String, String> data = new HashMap<>();
        data.put("partnertypeid", partnerId);


        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        loadSubMaster = apiService.getPartnerSubId(Pref.getPreToken(), data);

        loadSubMaster.enqueue(new Callback<SubPartner>() {
            @Override
            public void onResponse(@NonNull Call<SubPartner> call, @NonNull Response<SubPartner> response) {

                progressDialog.dismiss();

                if (response.code() == 200 || response.code() == 201) {

                    SubPartner subpartnertype = response.body();

                    assert subpartnertype != null;
                    if (subpartnertype.getStatus().equals("1")) {

                        try {

                            Pref.setdevicetoken("Bearer " + subpartnertype.getToken());

                            subPartners.addAll(subpartnertype.getData().getSubpartnertype());

                            loadSubWorkAdapter();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        layoutProgress.setVisibility(View.VISIBLE);
                    }


                } else if (response.code() == 304) {

                    Toast.makeText(WorkExperienceActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(WorkExperienceActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(WorkExperienceActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(WorkExperienceActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(WorkExperienceActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(WorkExperienceActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(WorkExperienceActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<SubPartner> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();

            }
        });


    }

    private void loadSubWorkAdapter() {

        ArrayAdapter<Subpartnertype> loadsubPartner = new ArrayAdapter<Subpartnertype>(WorkExperienceActivity.this,
                android.R.layout.simple_spinner_dropdown_item, subPartners);
        loadsubPartner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spSubWorkIndustry.setAdapter(loadsubPartner);
        spSubWorkIndustry.setSelection(0);
    }


    private void setSpinner() {


        ArrayAdapter<Partner_types> dataAdapters = new ArrayAdapter<Partner_types>(WorkExperienceActivity.this,
                android.R.layout.simple_spinner_dropdown_item, partner_types);
        dataAdapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spWorkIndustryid.setAdapter(dataAdapters);
        spWorkIndustryid.setSelection(0);


    }

    private void loadJobSpinner() {

        ArrayAdapter<Job_types> loadjobadapter = new ArrayAdapter<Job_types>(WorkExperienceActivity.this,
                android.R.layout.simple_spinner_dropdown_item, job_types);
        loadjobadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spJobType.setAdapter(loadjobadapter);
        spJobType.setSelection(0);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {

            case R.id.btSave:
                validate();
                break;

            case R.id.starDateLayout:
                StartDateId = "1";
                calendar(StartDateId);
                break;

            case R.id.editDateView:
                StartDateId = "0";
                calendar(StartDateId);
                break;

            case R.id.tvCancel:
                finish();
                break;


        }

    }

    private void validate() {
        if (!edjobTitle.getText().toString().trim().isEmpty()) {
            if (!tvEmployer.getText().toString().trim().isEmpty()) {
                if (!edjobDescription.getText().toString().trim().isEmpty()) {
                    if (!partnerId.equals("0")) {
                        if (!worksubcatagoryid.equals("0")) {
                            if (!jobTypeid.equals("0")) {
                                if (switchButton.isChecked()) {
                                    if (!edStartDate.getText().toString().trim().isEmpty()) {

                                        addEmployemetApi();

                                    } else {
                                        Toast.makeText(this, "Please enter the start date", Toast.LENGTH_SHORT).show();
                                    }

                                } else {

                                    statusDate = "1";
                                    if (!edStartDate.getText().toString().trim().isEmpty()) {

                                        if (!edEndDate.getText().toString().trim().isEmpty()) {
                                            addEmployemetApi();
                                        } else {
                                            Toast.makeText(this, "Please enter the end date", Toast.LENGTH_SHORT).show();
                                        }


                                    } else {
                                        Toast.makeText(this, "Please enter the start date", Toast.LENGTH_SHORT).show();
                                    }

                                }

                            } else {
                                Toast.makeText(this, "Please select the job type", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(this, "Please select the sub work industry", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(this, "Please select the work industry", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(this, "Please enter the Job description", Toast.LENGTH_SHORT).show();
                }


            } else {
                Toast.makeText(this, "Please enter the employer", Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(this, "Please enter the job title", Toast.LENGTH_SHORT).show();
        }

    }


    private void addEmployemetApi() {

        progressDialog = Utils.createProgressDialog(this);


        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Map<String, String> data = new HashMap<>();

        String responsibilities = "responsibilities";


        data.put("sid", Pref.getClientId());
        data.put("title", edjobTitle.getText().toString());
        data.put("provider", tvEmployer.getText().toString());
        data.put("provider_id ", "0");
        data.put("description", edjobDescription.getText().toString());
        data.put("category_id", partnerId);
        data.put("subcategory_id", worksubcatagoryid);
        data.put("type_id", jobTypeid);
        data.put("status", statusDate);
        if (statusDate.equals("1")) {
            data.put("start_date", edStartDate.getText().toString());
            data.put("end_date", " ");
            data.put("type", "3");
            data.put("noofresponsibilities", String.valueOf(keyResponsibiltites.size() + 1));
            data.put("responsibilities1", edKeyResponsibiltites.getText().toString());

            for (int i = 1; i <= keyResponsibiltites.size(); i++) {

                data.put(responsibilities.concat(String.valueOf(i + 1)), keyResponsibiltites.get(i - 1).getKeydata());

                // Log.d(TAG, "applyJob: " + responsibilities.concat(String.valueOf(i+1)) + " values "
                // + keyResponsibiltites.get(i).getKeydata());
            }


        } else {
            data.put("start_date", edStartDate.getText().toString());
            data.put("end_date", edEndDate.getText().toString());
            data.put("type", "3");
            data.put("noofresponsibilities", String.valueOf(keyResponsibiltites.size() + 1));
            data.put("responsibilities1", edKeyResponsibiltites.getText().toString());

            for (int i = 1; i <= keyResponsibiltites.size(); i++) {

                data.put(responsibilities.concat(String.valueOf(i + 1)), keyResponsibiltites.get(i - 1).getKeydata());

                Log.d(TAG, "applyJob: " + responsibilities.concat(String.valueOf(i + 1)) + " values "
                        + keyResponsibiltites.get(i - 1).getKeydata());
            }
        }


        Call<BasicResponse> call = apiService.addEmployement(Pref.getPreToken(), data);

        call.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                //Log.d(TAG, "onResponse:getProfileGalleryList  " + new Gson().toJson(response.body()));

                try {

                    progressDialog.dismiss();

                    if (response.code() == 200 || response.code() == 201) {


                        Pref.setdevicetoken("Bearer " + response.body().getToken());


                        if (response.body().getStatus().equals("1")) {

                            finish();

                            CvResumeFragment cvResumeFragment = new CvResumeFragment();
                            cvResumeFragment.loadResumeData();


                            Toast.makeText(WorkExperienceActivity.this, "Work experience added", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(WorkExperienceActivity.this, "failed to add work experience", Toast.LENGTH_SHORT).show();
                        }

                    } else if (response.code() == 304) {

                        Toast.makeText(WorkExperienceActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(WorkExperienceActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(WorkExperienceActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(WorkExperienceActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(WorkExperienceActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(WorkExperienceActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(WorkExperienceActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });


    }

    long diff=0;
    private void calendar(String startDateId) {

        DatePickerDialog dialog = new DatePickerDialog(WorkExperienceActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        Calendar minCal = Calendar.getInstance();
        minCal.set(Calendar.YEAR, minCal.get(Calendar.YEAR));
        Calendar maxCal = Calendar.getInstance();
        maxCal.set(Calendar.YEAR, maxCal.get(Calendar.YEAR));


        String dateStr = edStartDate.getText().toString();
        String dateStr2 = edEndDate.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date date = sdf.parse(dateStr);
            Date date2 = sdf.parse(dateStr2);
            diff = date.getTime() - date2.getTime();
            System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (startDateId.equals("1")) {

            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());


        } else {

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, (int) -diff);
            Date result = cal.getTime();
          // dialog.getDatePicker().setMinDate(System.currentTimeMillis());
            dialog.getDatePicker().setMinDate(System.currentTimeMillis()-result.getTime());
           // dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        }


        //dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        // dialog.getDatePicker().setMinDate(result.getTime());

        dialog.show();

    }

}
