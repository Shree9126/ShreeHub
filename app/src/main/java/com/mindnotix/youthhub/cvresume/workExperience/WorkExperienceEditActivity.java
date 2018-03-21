package com.mindnotix.youthhub.cvresume.workExperience;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mindnotix.adapter.resumeAdapter.workExperience.KeyResponsibiltesAddAdapter;
import com.mindnotix.adapter.resumeAdapter.workExperience.KeyResponsiblitesEditLoadAdapter;
import com.mindnotix.model.BasicResponse;
import com.mindnotix.model.SubPartnerType.SubPartner;
import com.mindnotix.model.SubPartnerType.Subpartnertype;
import com.mindnotix.model.resume.Education.KeyResponsibiltites;
import com.mindnotix.model.resume.workExperience.keyresponsibiltiesConfirmation.DeleteConfirmation;
import com.mindnotix.model.resume.workExperience.Example;
import com.mindnotix.model.resume.workExperience.Job_types;
import com.mindnotix.model.resume.workExperience.Partner_types;
import com.mindnotix.model.resume.workExperience.workExperienceEdit.Keyresponsibilities;
import com.mindnotix.model.resume.workExperience.workExperienceEdit.WorkExperienceEdit;
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
 * Created by Admin on 14-03-2018.
 */

public class WorkExperienceEditActivity extends AppCompatActivity implements View.OnClickListener {

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
    //   View tvCancel;

    TextView addKeyResponsibilties;
    RecyclerView recyclerView;

    KeyResponsibiltites keyResponsibiltitesModel;

    ArrayList<KeyResponsibiltites> keyResponsibiltites;

    ProgressDialog progressDialog;

    LinearLayoutManager linearLayoutManager;

    RecyclerView recylerviewLoadkeyResponse;

    String qualificationid;
    String student_id;

    KeyResponsiblitesEditLoadAdapter keyResponsiblitesEditLoadAdapter;

    ArrayList<Keyresponsibilities> keyresponsibilities;

    View keyResponsibiltesView;

    String title;
    String content;


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
        btSave.setText("Update Changes");
        starDateLayout = findViewById(R.id.starDateLayout);
        addKeyResponsibilties = findViewById(R.id.addKeyResponsibilties);
        edKeyResponsibiltites = findViewById(R.id.edKeyResponsibiltites);
        recylerviewLoadkeyResponse = findViewById(R.id.recylerviewLoadkeyResponse);
        keyResponsibiltesView = findViewById(R.id.keyResponsibiltesView);

        keyResponsibiltites = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        recylerviewLoadkeyResponse = findViewById(R.id.recylerviewLoadkeyResponse);


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


        if (getIntent() != null) {

            qualificationid = getIntent().getStringExtra("qualificationid");
            student_id = getIntent().getStringExtra("student_id");
        }


        loadQualification();

        spWorkIndustryid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String partnerType = partner_types.get(position).getId();

                if (!partnerType.equals("0")) {
                    partnerId = partnerType;
                    loadSubPartnerType(partnerId);

                } else {
                    partnerId = "0";
                    //loadSubPartner();
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
                    //loadSubWorkAdapter();
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
        // tvCancel.setOnClickListener(this);


        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

                if (isChecked) {
                    statusDate = "2";
                    editDateView.setVisibility(View.GONE);
                } else {
                    statusDate = "1";
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


        toolbar = findViewById(R.id.toolbar);
        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setTitle("Work Experience");

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


//        spRegion = findViewById(R.id.spRegion);
//        spRegion = findViewById(R.id.spOrganizationCatagorey);
//        spQualificationProvider = findViewById(R.id.spQualificationProvider);
//        spTitleQualification = findViewById(R.id.spTitleQualification);
//        spNcea = findViewById(R.id.spNcea);
//        edQualification = findViewById(R.id.edQualification);
//        spQualificationCatagorey = findViewById(R.id.spQualificationCatagorey);
//        spSubQualificationCatagorey = findViewById(R.id.spSubQualificationCatagorey);
//        edStartDate = findViewById(R.id.edStartDate);
//        edEndDate = findViewById(R.id.edEndDate);
//        btCancel = findViewById(R.id.btCancel);
        btSave = findViewById(R.id.btSave);

        addKeyResponsibilties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                keyResponsibiltitesModel = new KeyResponsibiltites("");
                keyResponsibiltites.add(keyResponsibiltitesModel);

                keyResponsibiltesAddAdapter = new KeyResponsibiltesAddAdapter(WorkExperienceEditActivity.this,
                        keyResponsibiltites);
                recyclerView.setAdapter(keyResponsibiltesAddAdapter);
                keyResponsibiltesAddAdapter.notifyDataSetChanged();
            }
        });


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

        ArrayAdapter<Subpartnertype> loadsubPartner = new ArrayAdapter<Subpartnertype>(WorkExperienceEditActivity.this,
                android.R.layout.simple_spinner_dropdown_item, subPartners);
        loadsubPartner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spSubWorkIndustry.setAdapter(loadsubPartner);
        spSubWorkIndustry.setSelection(0);
    }


    Call<WorkExperienceEdit> loadqualificationData;

    private void loadQualification() {

        Map<String, String> data = new HashMap<>();
        data.put("qualificationid", qualificationid);
        data.put("student_id", student_id);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        loadqualificationData = apiService.loadQualification(Pref.getPreToken(), data);

        loadqualificationData.enqueue(new Callback<WorkExperienceEdit>() {
            @Override
            public void onResponse(@NonNull Call<WorkExperienceEdit> call, @NonNull Response<WorkExperienceEdit> response) {

                if (response.code() == 200 || response.code() == 201) {

                    WorkExperienceEdit workExperienceEdit = response.body();

                    if (workExperienceEdit.getStatus().equals("1")) {

                        try {

                            Pref.setdevicetoken("Bearer " + workExperienceEdit.getToken());

                            edjobTitle.setText(workExperienceEdit.getData().getQualificationsview().getTitle());
                            tvEmployer.setText(workExperienceEdit.getData().getQualificationsview().getProvider_name());
                            edjobDescription.setText(workExperienceEdit.getData().getQualificationsview().getDescription());
                            edStartDate.setText(Utils.getOnlyDate(workExperienceEdit.getData().getQualificationsview().getStart_date()));


                            jobTypeid = workExperienceEdit.getData().getQualificationsview().getType_id();
                            partnerId = workExperienceEdit.getData().getQualificationsview().getCategory_id();
                            worksubcatagoryid = workExperienceEdit.getData().getQualificationsview().getSubcategory_id();

                            if (workExperienceEdit.getData().getQualificationsview().getQualification_status().equals("2")) {
                                statusDate = "2";
                                editDateView.setVisibility(View.GONE);
                                switchButton.setChecked(true);
                                edEndDate.setText(" ");


                            } else {
                                switchButton.setChecked(false);
                                statusDate = "1";
                                editDateView.setVisibility(View.VISIBLE);

                                Log.v("fhvefevfgheghvegfe",workExperienceEdit.getData().getQualificationsview().getEnd_date());

                                edEndDate.setText(Utils.getOnlyDate(workExperienceEdit.getData().getQualificationsview().getEnd_date()));
                            }


                            loadDatasMaster();

                            deleteConfirmationDialog();


                            keyresponsibilities = new ArrayList<>();

                            if (workExperienceEdit.getData().getKeyresponsibilities() != null) {


                                keyResponsibiltesView.setVisibility(View.VISIBLE);

                                keyresponsibilities.addAll(workExperienceEdit.getData().getKeyresponsibilities());


                                recylerviewLoadkeyResponse.setLayoutManager(new LinearLayoutManager(WorkExperienceEditActivity.this));
                                keyResponsiblitesEditLoadAdapter = new KeyResponsiblitesEditLoadAdapter(WorkExperienceEditActivity.this
                                        , keyresponsibilities);

                                recylerviewLoadkeyResponse.setAdapter(keyResponsiblitesEditLoadAdapter);
                            } else {

                                keyResponsibiltesView.setVisibility(View.GONE);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                    }


                } else if (response.code() == 304) {

                    Toast.makeText(WorkExperienceEditActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(WorkExperienceEditActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(WorkExperienceEditActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(WorkExperienceEditActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(WorkExperienceEditActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(WorkExperienceEditActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(WorkExperienceEditActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<WorkExperienceEdit> call, @NonNull Throwable t) {

                t.printStackTrace();

            }
        });


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


                            for (int i = 0; i < job_types.size(); i++) {
                                if (job_types.get(i).getId().equals(jobTypeid)) {
                                    spJobType.setSelection(i);
                                }
                            }


                            for (int i = 0; i < partner_types.size(); i++) {
                                if (partner_types.get(i).getId().equals(partnerId)) {
                                    spWorkIndustryid.setSelection(i);
                                }
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        layoutProgress.setVisibility(View.VISIBLE);
                    }


                } else if (response.code() == 304) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceEditActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceEditActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceEditActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceEditActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceEditActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceEditActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceEditActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

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

        Map<String, String> data = new HashMap<>();
        data.put("partnertypeid", partnerId);


        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        loadSubMaster = apiService.getPartnerSubId(Pref.getPreToken(), data);

        loadSubMaster.enqueue(new Callback<SubPartner>() {
            @Override
            public void onResponse(@NonNull Call<SubPartner> call, @NonNull Response<SubPartner> response) {

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

                    Toast.makeText(WorkExperienceEditActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(WorkExperienceEditActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(WorkExperienceEditActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(WorkExperienceEditActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(WorkExperienceEditActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(WorkExperienceEditActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(WorkExperienceEditActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<SubPartner> call, @NonNull Throwable t) {

                t.printStackTrace();

            }
        });


    }

    private void loadSubWorkAdapter() {

        ArrayAdapter<Subpartnertype> loadsubPartner = new ArrayAdapter<Subpartnertype>(WorkExperienceEditActivity.this,
                android.R.layout.simple_spinner_dropdown_item, subPartners);
        loadsubPartner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spSubWorkIndustry.setAdapter(loadsubPartner);
        spSubWorkIndustry.setSelection(0);

        Log.v("fefegefgefefef", " " + subPartners.size());


        for (int i = 0; i < subPartners.size(); i++) {

            Log.v("getiddd", " " + subPartners.get(i).getId());

            if (subPartners.get(i).getId().equals(worksubcatagoryid)) {

                Log.v("efhefefhehfofhefheuf", subPartners.get(i).getId() + " " + " " + worksubcatagoryid);

                spSubWorkIndustry.setSelection(i);
            } else {
                Log.v("else", subPartners.get(i).getId() + " " + " " + worksubcatagoryid);
            }
        }


    }


    private void setSpinner() {


        ArrayAdapter<Partner_types> dataAdapters = new ArrayAdapter<Partner_types>(WorkExperienceEditActivity.this,
                android.R.layout.simple_spinner_dropdown_item, partner_types);
        dataAdapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spWorkIndustryid.setAdapter(dataAdapters);
        spWorkIndustryid.setSelection(0);


    }

    private void loadJobSpinner() {

        ArrayAdapter<Job_types> loadjobadapter = new ArrayAdapter<Job_types>(WorkExperienceEditActivity.this,
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

                                        UpdateEmployemetApi();

                                    } else {
                                        Toast.makeText(this, "Please enter the start date", Toast.LENGTH_SHORT).show();
                                    }

                                } else {

                                    statusDate = "1";
                                    if (!edStartDate.getText().toString().trim().isEmpty()) {

                                        if (!edEndDate.getText().toString().trim().isEmpty()) {
                                            UpdateEmployemetApi();
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


    private void UpdateEmployemetApi() {

        progressDialog = Utils.createProgressDialog(this);


        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Map<String, String> data = new HashMap<>();

        String responsibilities = "responsibilities";


        data.put("qid", qualificationid);
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


        Call<BasicResponse> call = apiService.UpdateEmployement(Pref.getPreToken(), data);

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


                            Toast.makeText(WorkExperienceEditActivity.this, "Work experience added", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(WorkExperienceEditActivity.this, "failed to add work experience", Toast.LENGTH_SHORT).show();
                        }

                    } else if (response.code() == 304) {

                        Toast.makeText(WorkExperienceEditActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(WorkExperienceEditActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(WorkExperienceEditActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(WorkExperienceEditActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(WorkExperienceEditActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(WorkExperienceEditActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(WorkExperienceEditActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

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

    long diff = 0;

    private void calendar(String startDateId) {

        DatePickerDialog dialog = new DatePickerDialog(WorkExperienceEditActivity.this, date, myCalendar
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
            System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (startDateId.equals("1")) {

            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());


        } else {

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, (int) -diff);
            Date result = cal.getTime();
            dialog.getDatePicker().setMinDate(result.getTime());
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        }


        //dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        // dialog.getDatePicker().setMinDate(result.getTime());

        dialog.show();

    }


    Call<DeleteConfirmation> deleteConfirmationApiCall;

    private void deleteConfirmationDialog() {


        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        deleteConfirmationApiCall = apiService.DeleteConfirmation(Pref.getPreToken());

        deleteConfirmationApiCall.enqueue(new Callback<DeleteConfirmation>() {
            @Override
            public void onResponse(@NonNull Call<DeleteConfirmation> call, @NonNull Response<DeleteConfirmation> response) {

                if (response.code() == 200 || response.code() == 201) {

                    DeleteConfirmation deleteConfirmation = response.body();

                    if (deleteConfirmation.getStatus().equals("1")) {

                        try {


                            Pref.setdevicetoken("Bearer " + deleteConfirmation.getToken());

                            title = deleteConfirmation.getData().getTitle();
                            content = deleteConfirmation.getData().getContent();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        Toast.makeText(WorkExperienceEditActivity.this, "Failure to delete", Toast.LENGTH_SHORT).show();
                    }


                } else if (response.code() == 304) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceEditActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceEditActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceEditActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceEditActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceEditActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceEditActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceEditActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<DeleteConfirmation> call, @NonNull Throwable t) {
                t.printStackTrace();

            }
        });


    }


    public void keyConfirmationDilaog(final int adapterPosition, final String id, final String stu_qualification_id) {


        final Dialog dialog = new Dialog(WorkExperienceEditActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dilaog_remove);
        Button btCancel = dialog.findViewById(R.id.btCancel);
        TextView text_dialog = dialog.findViewById(R.id.text_dialog);
        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        txtTitle.setText(title);
        text_dialog.setText(content);

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

                keyResponsibiltesDelete(adapterPosition, id, stu_qualification_id);

                dialog.dismiss();
            }
        });
        dialog.show();


    }


    Call<BasicResponse> keyResponsibiltiesDelete;

    private void keyResponsibiltesDelete(final int adapterPosition, String id, String stu_qualification_id) {


        progressDialog = Utils.createProgressDialog(WorkExperienceEditActivity.this);


        Map<String, String> data = new HashMap<>();
        data.put("qid", stu_qualification_id);
        data.put("keyid", id);


        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        keyResponsibiltiesDelete = apiService.deleteKeyResponsibiltes(Pref.getPreToken(), data);

        keyResponsibiltiesDelete.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(@NonNull Call<BasicResponse> call, @NonNull Response<BasicResponse> response) {
                progressDialog.dismiss();


                if (response.code() == 200 || response.code() == 201) {

                    BasicResponse basicResponse = response.body();

                    if (basicResponse.getStatus().equals("1")) {


                        try {
                            Pref.setdevicetoken("Bearer " + basicResponse.getToken());

                            keyResponsiblitesEditLoadAdapter.notifyItemRemoved(adapterPosition);
                            keyresponsibilities.remove(adapterPosition);
                            Toast.makeText(WorkExperienceEditActivity.this, basicResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            if (keyresponsibilities.size() == 0) {
                                keyResponsibiltesView.setVisibility(View.GONE);

                            } else {
                                keyResponsibiltesView.setVisibility(View.VISIBLE);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        Toast.makeText(WorkExperienceEditActivity.this, basicResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else if (response.code() == 304) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceEditActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceEditActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceEditActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceEditActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceEditActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceEditActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(WorkExperienceEditActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<BasicResponse> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();

            }
        });


    }


}
