package com.mindnotix.youthhub.cvresume.Education;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.adapter.EntryAdapter;
import com.mindnotix.adapter.resumeAdapter.workExperience.KeyResponsibiltesAddAdapter;
import com.mindnotix.adapter.resumeAdapter.workExperience.KeyResponsiblitesEditLoadAdapter;
import com.mindnotix.listener.Item;
import com.mindnotix.model.BasicResponse;
import com.mindnotix.model.profile.update.EntryItem;
import com.mindnotix.model.profile.update.SectionItem;
import com.mindnotix.model.resume.Education.Education;
import com.mindnotix.model.resume.Education.NCEAlevel;
import com.mindnotix.model.resume.Education.Organizationcategory;
import com.mindnotix.model.resume.Education.Qualificationcategory;
import com.mindnotix.model.resume.Education.Regionals;
import com.mindnotix.model.resume.Education.provider.Provider;
import com.mindnotix.model.resume.Education.provider.Provider_ids;
import com.mindnotix.model.resume.Education.qualificationSubCatgory.QualifiactionSubCatagory;
import com.mindnotix.model.resume.Education.qualificationSubCatgory.Subcategory_ids;
import com.mindnotix.model.resume.Education.title.Qualification;
import com.mindnotix.model.resume.Education.title.Title;
import com.mindnotix.model.resume.workExperience.workExperienceEdit.WorkExperienceEdit;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;
import com.mindnotix.youthhub.R;
import com.mindnotix.youthhub.cvresume.workExperience.WorkExperienceEditActivity;
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
 * Created by Admin on 19-03-2018.
 */

public class EducationEditActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = EducationResumeActivity.class.getSimpleName();
    KeyResponsibiltesAddAdapter keyResponsibiltesAddAdapter;
    Toolbar toolbar;

    Spinner spRegion;
    Spinner spOrganizationCatagorey;
    Spinner spQualificationProvider;
    Spinner spTitleQualification;
    Spinner spNcea;
    EditText edQualification;
    Spinner spQualificationCatagorey;
    EditText edQualificationCatagorey;
    EditText edStartDate;
    EditText edEndDate;
    TextView btCancel;
    TextView btSave;
    SwitchButton switch_button;
    View layoutProgress;
    View editDateView;
    View loadSubQualifiction;

    public static String regionalId = " ";
    public static String organizationId = " ";
    public static String titleOfQualifiactionId = " ";
    public static String nceaId = " ";
    public static String providerId = "0";
    public static String statusDate = " ";
    public static String StartDateId = " ";
    private static String subQualifiactionCatagoryId = " ";
    private static String QualifiactionCatagoryId = " ";
    public Boolean isProgressChecked = false;


    ArrayList<Regionals> regionalsArrayList;
    ArrayList<Qualificationcategory> qualificationcategoryArrayList;
    ArrayList<NCEAlevel> nceAlevelsArrayList;
    ArrayList<Organizationcategory> organizationcategoryArrayList;
    ArrayList<Provider_ids> providerArrayList;
    ArrayList<Qualification> titleArrayList;
    ArrayList<Subcategory_ids> subcatagoryId;

    ArrayList<Item> items;


    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "dd-MM-yyyy";

    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.GERMANY);
    DatePickerDialog.OnDateSetListener date;

    ProgressDialog progressDialog;

    View starDateLayout;

    EditText edProviderName;
    EditText edQualifiactiontitle;
    View providerView;
    View qualifiactionView;

    String qualificationid;
    String student_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_resume);

        spRegion = findViewById(R.id.spRegion);
        spOrganizationCatagorey = findViewById(R.id.spOrganizationCatagorey);
        spQualificationProvider = findViewById(R.id.spQualificationProvider);
        spTitleQualification = findViewById(R.id.spTitleQualification);
        spNcea = findViewById(R.id.spNcea);
        edQualification = findViewById(R.id.edQualification);
        spQualificationCatagorey = findViewById(R.id.spQualificationCatagorey);
        edQualificationCatagorey = findViewById(R.id.spSubQualificationCatagorey);
        edStartDate = findViewById(R.id.edStartDate);
        edEndDate = findViewById(R.id.edEndDate);
        btCancel = findViewById(R.id.btCancel);
        layoutProgress = findViewById(R.id.layoutProgress);
        switch_button = findViewById(R.id.switch_button);
        loadSubQualifiction = findViewById(R.id.loadSubQualifiction);
        editDateView = findViewById(R.id.editDateView);
        starDateLayout = findViewById(R.id.starDateLayout);
        editDateView = findViewById(R.id.editDateView);
        btSave = findViewById(R.id.btSave);
        edProviderName = findViewById(R.id.edProviderName);
        edQualifiactiontitle = findViewById(R.id.edQualifiactiontitle);
        providerView = findViewById(R.id.providerView);
        qualifiactionView = findViewById(R.id.qualifiactionView);

        regionalsArrayList = new ArrayList<>();
        qualificationcategoryArrayList = new ArrayList<>();
        nceAlevelsArrayList = new ArrayList<>();
        organizationcategoryArrayList = new ArrayList<>();
        providerArrayList = new ArrayList<>();
        titleArrayList = new ArrayList<>();
        subcatagoryId = new ArrayList<>();

        starDateLayout.setOnClickListener(this);
        editDateView.setOnClickListener(this);
        btSave.setOnClickListener(this);
        btCancel.setOnClickListener(this);

        if (getIntent() != null) {

            qualificationid = getIntent().getStringExtra("qualificationid");
            student_id = getIntent().getStringExtra("student_id");
        }


        loadQualification();

        loadQualifiactionProviderSpinner();
        loadTitleArrayalist();


        edQualificationCatagorey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!QualifiactionCatagoryId.equals("0") && !QualifiactionCatagoryId.equals(" ")) {
                    showChangeLangDialog();
                }


            }
        });


        spRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String regionId = regionalsArrayList.get(position).getId();

                if (!regionId.equals("0")) {
                    regionalId = regionId;

                } else {
                    //loadRegionSpinner();
                    regionalId = "0";

                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spOrganizationCatagorey.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String organizationIds = organizationcategoryArrayList.get(position).getId();

                if (!organizationIds.equals("0")) {
                    organizationId = organizationIds;
                    if (!regionalId.equals("0")) {
                        loadProviderId(regionalId, organizationId);
                    }
                } else {
                    // loadQualifiactionProviderSpinner(providerArrayList);
                    organizationId = "0";

                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spQualificationProvider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String providerIds = providerArrayList.get(position).getId();

                if (!providerIds.equals("0")) {
                    providerId = providerIds;

                    if (providerId.equals("-1")) {
                        providerView.setVisibility(View.VISIBLE);
                    } else {
                        providerView.setVisibility(View.GONE);
                    }


                    loadEducationTitle(regionalId, organizationId, providerId);
                } else {
                    providerId = "0";

                }


            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spTitleQualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String titleId = titleArrayList.get(position).getId();

                if (!titleId.equals("0")) {
                    titleOfQualifiactionId = titleId;

                    if (titleOfQualifiactionId.equals("-1")) {
                        qualifiactionView.setVisibility(View.VISIBLE);
                    } else {
                        qualifiactionView.setVisibility(View.GONE);
                    }


                } else {

                    titleOfQualifiactionId = "0";
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spNcea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String nceaIds = nceAlevelsArrayList.get(position).getId();

                if (!nceaIds.equals("0")) {
                    nceaId = nceaIds;

                } else {

                    nceaId = "0";
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spQualificationCatagorey.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String qualifiactionCatagory = qualificationcategoryArrayList.get(position).getId();

                if (!qualifiactionCatagory.equals("0")) {
                    QualifiactionCatagoryId = qualifiactionCatagory;
                    edQualificationCatagorey.setText("");
                    loadQualifiactionSubCatagorey(QualifiactionCatagoryId);


                } else {

                    QualifiactionCatagoryId = "0";
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        switch_button.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
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


        toolbar = findViewById(R.id.toolbar);
        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setTitle("Education");
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


    private void updateDate() {
        Log.d("updateDate", "updateDate: " + sdf.format(myCalendar.getTime()));

        if (StartDateId.equals("1")) {
            edStartDate.setText(sdf.format(myCalendar.getTime()));
        } else {
            edEndDate.setText(sdf.format(myCalendar.getTime()));
        }

    }


    Call<Provider> loadProvider;

    private void loadProviderId(String regionalId, String organizationId) {

        providerArrayList = new ArrayList<>();

        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();

        progressDialog = Utils.createProgressDialog(EducationEditActivity.this);


        Map<String, String> data = new HashMap<>();
        data.put("regional", regionalId);
        data.put("category", organizationId);


        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        loadProvider = apiService.loadProviderData(Pref.getPreToken(), data);

        loadProvider.enqueue(new Callback<Provider>() {
            @Override
            public void onResponse(@NonNull Call<Provider> call, @NonNull Response<Provider> response) {

                //  progressDialog.cancel();

                if (response.code() == 200 || response.code() == 201) {


                    Provider provider = response.body();

                    if (provider.getStatus().equals("1")) {

                        try {


                            Pref.setdevicetoken("Bearer " + provider.getToken());

                            providerArrayList.clear();

                            providerArrayList.addAll(provider.getData().getProvider_ids());

                            loadQualifiactionProviderSpinner();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {


                    }


                } else if (response.code() == 304) {

                    Toast.makeText(EducationEditActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(EducationEditActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(EducationEditActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(EducationEditActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(EducationEditActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(EducationEditActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(EducationEditActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<Provider> call, @NonNull Throwable t) {
                //   progressDialog.cancel();
                t.printStackTrace();

            }
        });


    }


    Call<Title> loadEducation;

    private void loadEducationTitle(String regionalId, String organizationId, String providerId) {

        titleArrayList = new ArrayList<>();
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
        progressDialog = Utils.createProgressDialog(EducationEditActivity.this);


        Map<String, String> data = new HashMap<>();
        data.put("regional", regionalId);
        data.put("category", organizationId);
        data.put("provider_id", providerId);


        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        loadEducation = apiService.loadQualificationTitle(Pref.getPreToken(), data);

        loadEducation.enqueue(new Callback<Title>() {
            @Override
            public void onResponse(@NonNull Call<Title> call, @NonNull Response<Title> response) {
                progressDialog.dismiss();

                if (response.code() == 200 || response.code() == 201) {

                    Title title = response.body();

                    if (title.getStatus().equals("1")) {

                        try {


                            Pref.setdevicetoken("Bearer " + title.getToken());

                            titleArrayList.clear();

                            titleArrayList.addAll(title.getData().getQualification());

                            loadTitleArrayalist();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {


                    }


                } else if (response.code() == 304) {

                    Toast.makeText(EducationEditActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(EducationEditActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(EducationEditActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(EducationEditActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(EducationEditActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(EducationEditActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(EducationEditActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<Title> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();

            }
        });


    }


    Call<QualifiactionSubCatagory> qualificationSubCatagory;

    private void loadQualifiactionSubCatagorey(String catagoryId) {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();

        progressDialog = Utils.createProgressDialog(EducationEditActivity.this);


        Map<String, String> data = new HashMap<>();
        data.put("category_id", catagoryId);


        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        qualificationSubCatagory = apiService.loadQualificationSubcatagory(Pref.getPreToken(), data);

        qualificationSubCatagory.enqueue(new Callback<QualifiactionSubCatagory>() {
            @Override
            public void onResponse(@NonNull Call<QualifiactionSubCatagory> call, @NonNull Response<QualifiactionSubCatagory> response) {
                //  progressDialog.dismiss();

                if (response.code() == 200 || response.code() == 201) {

                    QualifiactionSubCatagory qualifiactionSubCatagory = response.body();

                    if (qualifiactionSubCatagory.getStatus().equals("1")) {

                        try {


                            Pref.setdevicetoken("Bearer " + qualifiactionSubCatagory.getToken());

                            // subcatagoryId.addAll(qualifiactionSubCatagory.getData().getSubcategory_ids());


                            items = new ArrayList<>();
                            for (int i = 0; i < qualifiactionSubCatagory.getData().getSubcategory_ids().size(); i++) {
                                items.add(new SectionItem(qualifiactionSubCatagory.getData().getSubcategory_ids().get(i).getSubcategory()));
                                Log.v("deidjiedjieooejioeiod", qualifiactionSubCatagory.getData().getSubcategory_ids().get(i).getSubcategory());
                                for (int j = 0; j < qualifiactionSubCatagory.getData().getSubcategory_ids().get(i).getSubsubcategory().size(); j++) {
                                    items.add(new EntryItem(qualifiactionSubCatagory.getData().getSubcategory_ids().get(i).getSubsubcategory().get(j).getSubcategory(),
                                            qualifiactionSubCatagory.getData().getSubcategory_ids().get(i).getSubsubcategory().get(j).getId()));
                                    Log.v("deidjiedjieooejioeiod", qualifiactionSubCatagory.getData().getSubcategory_ids().get(i).getSubsubcategory().get(j).getSubcategory());

                                    Log.v("subQualifiaction",subQualifiactionCatagoryId);
                                    Log.v("subQualifiaction",qualifiactionSubCatagory.getData().getSubcategory_ids().get(i).getSubsubcategory().get(j).getId());


                                    if (qualifiactionSubCatagory.getData().getSubcategory_ids().get(i).getSubsubcategory().get(j).getId().equals(subQualifiactionCatagoryId)) {
                                        edQualificationCatagorey.setText(qualifiactionSubCatagory.getData().getSubcategory_ids().get(i).getSubsubcategory().get(j).getSubcategory());

                                    }


                                }
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {


                    }


                } else if (response.code() == 304) {

                    Toast.makeText(EducationEditActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(EducationEditActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(EducationEditActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(EducationEditActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(EducationEditActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(EducationEditActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(EducationEditActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<QualifiactionSubCatagory> call, @NonNull Throwable t) {
                //   progressDialog.dismiss();
                t.printStackTrace();

            }
        });


    }


    private void loadQualifiactionProviderSpinner() {


        Provider_ids provider_ids = new Provider_ids();
        provider_ids.setId("0");
        provider_ids.setName("--Select--");
        providerArrayList.add(0, provider_ids);


        ArrayAdapter<Provider_ids> regionalsArrayAdapter = new ArrayAdapter<Provider_ids>(EducationEditActivity.this,
                android.R.layout.simple_spinner_dropdown_item, providerArrayList);
        regionalsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spQualificationProvider.setAdapter(regionalsArrayAdapter);
        spQualificationProvider.setSelection(0);


        for (int i = 0; i < providerArrayList.size(); i++) {

            if (providerArrayList.get(i).getId().equals(providerId)) {
                spQualificationProvider.setSelection(i);
            }

        }


    }


    private void loadTitleArrayalist() {


        Qualification qualification = new Qualification();
        qualification.setId("0");
        qualification.setName("--Select--");
        titleArrayList.add(0, qualification);


        ArrayAdapter<Qualification> regionalsArrayAdapter = new ArrayAdapter<>(EducationEditActivity.this,
                android.R.layout.simple_spinner_dropdown_item, titleArrayList);
        regionalsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spTitleQualification.setAdapter(regionalsArrayAdapter);
        spTitleQualification.setSelection(0);


        for (int i = 0; i < titleArrayList.size(); i++) {

            if (titleArrayList.get(i).getId().equals(titleOfQualifiactionId)) {
                spTitleQualification.setSelection(i);
            }

        }


    }


    Call<Education> loadDataEduactionMaster;

    private void loadDatasMaster() {

        regionalsArrayList = new ArrayList<>();
        nceAlevelsArrayList = new ArrayList<>();
        qualificationcategoryArrayList = new ArrayList<>();
        organizationcategoryArrayList = new ArrayList<>();


        layoutProgress.setVisibility(View.VISIBLE);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        loadDataEduactionMaster = apiService.loadEduactionMaster(Pref.getPreToken());

        loadDataEduactionMaster.enqueue(new Callback<Education>() {
            @Override
            public void onResponse(@NonNull Call<Education> call, @NonNull Response<Education> response) {

                if (response.code() == 200 || response.code() == 201) {

                    Education education = response.body();

                    if (education.getStatus().equals("1")) {

                        try {

                            layoutProgress.setVisibility(View.GONE);

                            Pref.setdevicetoken("Bearer " + education.getToken());

                            regionalsArrayList.addAll(education.getData().getRegionals());
                            qualificationcategoryArrayList.addAll(education.getData().getQualificationcategory());
                            nceAlevelsArrayList.addAll(education.getData().getNCEAlevel());
                            organizationcategoryArrayList.addAll(education.getData().getOrganizationcategory());

                            loadRegionSpinner();
                            loadQualifiactionSpinner();
                            nceaSpinner();
                            organizationSpinner();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        layoutProgress.setVisibility(View.VISIBLE);
                    }


                } else if (response.code() == 304) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(EducationEditActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(EducationEditActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(EducationEditActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(EducationEditActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(EducationEditActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(EducationEditActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(EducationEditActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<Education> call, @NonNull Throwable t) {
                layoutProgress.setVisibility(View.GONE);
                t.printStackTrace();

            }
        });


    }


    private void loadRegionSpinner() {


        Regionals regionals = new Regionals();
        regionals.setId("0");
        regionals.setName("--Select--");
        regionalsArrayList.add(0, regionals);

        //region load

        ArrayAdapter<Regionals> regionalsArrayAdapter = new ArrayAdapter<Regionals>(EducationEditActivity.this,
                android.R.layout.simple_spinner_dropdown_item, regionalsArrayList);
        regionalsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spRegion.setAdapter(regionalsArrayAdapter);
        spRegion.setSelection(0);


        for (int i = 0; i < regionalsArrayList.size(); i++) {

            if (regionalsArrayList.get(i).getId().equals(regionalId)) {
                spRegion.setSelection(i);
            }

        }


    }


    private void loadQualifiactionSpinner() {


        Qualificationcategory qualificationcategory = new Qualificationcategory();

        qualificationcategory.setId("0");
        qualificationcategory.setName("--Select--");
        qualificationcategoryArrayList.add(0, qualificationcategory);

        //qualification load


        ArrayAdapter<Qualificationcategory> qualificationcategoryArrayAdapter = new ArrayAdapter<Qualificationcategory>(EducationEditActivity.this,
                android.R.layout.simple_spinner_dropdown_item, qualificationcategoryArrayList);
        qualificationcategoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spQualificationCatagorey.setAdapter(qualificationcategoryArrayAdapter);
        spQualificationCatagorey.setSelection(0);

        for (int i = 0; i < qualificationcategoryArrayList.size(); i++) {

            if (qualificationcategoryArrayList.get(i).getId().equals(QualifiactionCatagoryId)) {
                spQualificationCatagorey.setSelection(i);
            }

        }


    }


    private void nceaSpinner() {


        NCEAlevel nceAlevel = new NCEAlevel();
        nceAlevel.setId("0");
        nceAlevel.setName("--Select--");
        nceAlevelsArrayList.add(0, nceAlevel);

        //ncea  load


        ArrayAdapter<NCEAlevel> nceAlevelArrayAdapter = new ArrayAdapter<NCEAlevel>(EducationEditActivity.this,
                android.R.layout.simple_spinner_dropdown_item, nceAlevelsArrayList);
        nceAlevelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spNcea.setAdapter(nceAlevelArrayAdapter);
        spNcea.setSelection(0);


        for (int i = 0; i < nceAlevelsArrayList.size(); i++) {

            if (nceAlevelsArrayList.get(i).getId().equals(nceaId)) {
                spNcea.setSelection(i);
            }

        }


    }


    private void organizationSpinner() {

        Organizationcategory organizationcategory = new Organizationcategory();
        organizationcategory.setId("0");
        organizationcategory.setName("--Select--");
        organizationcategoryArrayList.add(0, organizationcategory);

        //organization load


        ArrayAdapter<Organizationcategory> organizationcategoryArrayAdapter =
                new ArrayAdapter<Organizationcategory>(EducationEditActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, organizationcategoryArrayList);
        organizationcategoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spOrganizationCatagorey.setAdapter(organizationcategoryArrayAdapter);
        spOrganizationCatagorey.setSelection(0);


        for (int i = 0; i < organizationcategoryArrayList.size(); i++) {

            if (organizationcategoryArrayList.get(i).getId().equals(organizationId)) {
                spOrganizationCatagorey.setSelection(i);
            }

        }


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
                calendar();
                break;

            case R.id.editDateView:
                StartDateId = "0";
                calendar();
                break;

            case R.id.tvCancel:
                finish();
                break;


        }

    }

    private void validate() {
        if (!regionalId.equals("0") && !regionalId.equals(" ")) {
            if (!organizationId.equals("0") && !organizationId.equals(" ")) {
                if (!providerId.equals("0") && !providerId.equals(" ")) {
                    if (providerId.equals("-1")) {
                        if (!edProviderName.getText().toString().isEmpty()) {
                            if (!titleOfQualifiactionId.equals("0") && !titleOfQualifiactionId.equals(" ")) {
                                if (titleOfQualifiactionId.equals("-1")) {
                                    if (!edQualifiactiontitle.getText().toString().isEmpty()) {
                                        if (!nceaId.equals("0") && !nceaId.equals(" ")) {
                                            if (!edQualification.getText().toString().isEmpty() && !edQualification.getText().toString().equals(" ")) {
                                                if (!QualifiactionCatagoryId.equals("0") && !QualifiactionCatagoryId.equals(" ")) {
                                                    if (!subQualifiactionCatagoryId.equals("0") && !subQualifiactionCatagoryId.equals(" ")) {
                                                        if (switch_button.isChecked()) {
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
                                                        Toast.makeText(this, "Please select the sub qualification category", Toast.LENGTH_SHORT).show();

                                                    }

                                                } else {
                                                    Toast.makeText(this, "Please select the qualification category", Toast.LENGTH_SHORT).show();

                                                }
                                            } else {
                                                Toast.makeText(this, "Please elaborate on qualification", Toast.LENGTH_SHORT).show();

                                            }


                                        } else {
                                            Toast.makeText(this, "Please enter the NCEA", Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        Toast.makeText(this, "Please enter the qualification title", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    if (!nceaId.equals("0") && !nceaId.equals(" ")) {
                                        if (!edQualification.getText().toString().isEmpty() && !edQualification.getText().toString().equals(" ")) {
                                            if (!QualifiactionCatagoryId.equals("0") && !QualifiactionCatagoryId.equals(" ")) {
                                                if (!subQualifiactionCatagoryId.equals("0") && !subQualifiactionCatagoryId.equals(" ")) {
                                                    if (switch_button.isChecked()) {
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
                                                    Toast.makeText(this, "Please select the sub qualification category", Toast.LENGTH_SHORT).show();

                                                }

                                            } else {
                                                Toast.makeText(this, "Please select the qualification category", Toast.LENGTH_SHORT).show();

                                            }
                                        } else {
                                            Toast.makeText(this, "Please elaborate on qualifiacation", Toast.LENGTH_SHORT).show();

                                        }

                                    } else {
                                        Toast.makeText(this, "Please enter the NCEA", Toast.LENGTH_SHORT).show();
                                    }


                                }

                            } else {
                                Toast.makeText(this, "Please select the title of qualification", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(this, "Please enter the provider name", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (!titleOfQualifiactionId.equals("0") && !titleOfQualifiactionId.equals(" ")) {

                            if (titleOfQualifiactionId.equals("-1")) {
                                if (!edQualifiactiontitle.getText().toString().isEmpty()) {
                                    if (!nceaId.equals("0") && !nceaId.equals(" ")) {
                                        if (!edQualification.getText().toString().isEmpty() && !edQualification.getText().toString().equals(" ")) {
                                            if (!QualifiactionCatagoryId.equals("0") && !QualifiactionCatagoryId.equals(" ")) {
                                                if (!subQualifiactionCatagoryId.equals("0") && !subQualifiactionCatagoryId.equals(" ")) {
                                                    if (switch_button.isChecked()) {
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
                                                    Toast.makeText(this, "Please select the sub qualification category", Toast.LENGTH_SHORT).show();

                                                }

                                            } else {
                                                Toast.makeText(this, "Please select the qualification category", Toast.LENGTH_SHORT).show();

                                            }
                                        } else {
                                            Toast.makeText(this, "Please elaborate on qualifiacation", Toast.LENGTH_SHORT).show();

                                        }

                                    } else {
                                        Toast.makeText(this, "Please enter the NCEA", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(this, "Please enter the qualification title", Toast.LENGTH_SHORT).show();
                                }

                            } else {

                                if (!nceaId.equals("0") && !nceaId.equals(" ")) {
                                    if (!edQualification.getText().toString().isEmpty() && !edQualification.getText().toString().equals(" ")) {
                                        if (!QualifiactionCatagoryId.equals("0") && !QualifiactionCatagoryId.equals(" ")) {
                                            if (!subQualifiactionCatagoryId.equals("0") && !subQualifiactionCatagoryId.equals(" ")) {
                                                if (switch_button.isChecked()) {
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
                                                Toast.makeText(this, "Please select the sub qualification category", Toast.LENGTH_SHORT).show();

                                            }

                                        } else {
                                            Toast.makeText(this, "Please select the qualification category", Toast.LENGTH_SHORT).show();

                                        }
                                    } else {
                                        Toast.makeText(this, "Please elaborate on qualification", Toast.LENGTH_SHORT).show();

                                    }

                                } else {
                                    Toast.makeText(this, "Please enter the NCEA", Toast.LENGTH_SHORT).show();
                                }

                            }


                        } else {
                            Toast.makeText(this, "Please select the title of qualification", Toast.LENGTH_SHORT).show();
                        }
                    }


                } else {
                    Toast.makeText(this, "Please select the qualification provider", Toast.LENGTH_SHORT).show();
                }


            } else {
                Toast.makeText(this, "Please select the organization category", Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(this, "Please select the region", Toast.LENGTH_SHORT).show();
        }

    }


    private void addEmployemetApi() {

        progressDialog = Utils.createProgressDialog(this);


        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Map<String, String> data = new HashMap<>();

        data.put("sid", Pref.getClientId());
        data.put("regional", regionalId);
        data.put("category", organizationId);
        if (providerId.equals("-1")) {
            data.put("provider_id ", providerId);
            data.put("provider ", edProviderName.getText().toString());
        } else {
            data.put("provider_id ", providerId);
        }
        if (titleOfQualifiactionId.equals("-1")) {

            data.put("title_id ", titleOfQualifiactionId);
            data.put("title ", edQualifiactiontitle.getText().toString());

        } else {
            data.put("title_id ", titleOfQualifiactionId);
        }

        data.put("ncl_id", nceaId);
        data.put("description", edQualification.getText().toString());
        data.put("category_id", QualifiactionCatagoryId);
        data.put("subcategory_id", subQualifiactionCatagoryId);
        data.put("status", statusDate);
        data.put("start_date", statusDate);
        if (statusDate.equals("1")) {
            data.put("start_date", edStartDate.getText().toString());
            data.put("end_date", " ");
            data.put("type", "1");

        } else {
            data.put("start_date", edStartDate.getText().toString());
            data.put("end_date", edEndDate.getText().toString());
            data.put("type", "1");

        }
        Log.d(TAG, "onResponse:educationapi  " + new Gson().toJson(data));


        Call<BasicResponse> call = apiService.addEducationApi(Pref.getPreToken(), data);

        call.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                progressDialog.dismiss();

                try {


                    if (response.code() == 200 || response.code() == 201) {


                        Pref.setdevicetoken("Bearer " + response.body().getToken());


                        if (response.body().getStatus().equals("1")) {

                            finish();


                        } else {
                            Toast.makeText(EducationEditActivity.this, "failed to add work experience", Toast.LENGTH_SHORT).show();
                        }

                    } else if (response.code() == 304) {

                        Toast.makeText(EducationEditActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(EducationEditActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(EducationEditActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(EducationEditActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(EducationEditActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(EducationEditActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(EducationEditActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

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


    private void calendar() {

        DatePickerDialog dialog = new DatePickerDialog(EducationEditActivity.this, date, myCalendar
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


    private void showChangeLangDialog() {

        final Dialog dialog = new Dialog(EducationEditActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dilaog_iwi);

        final EditText filterText = (EditText) dialog.findViewById(R.id.edit1);
        final ListView listView = dialog.findViewById(R.id.jobWishList);
        EntryAdapter entryAdapter = new EntryAdapter(EducationEditActivity.this, items, "resume");
        listView.setAdapter(entryAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                for (Item item : items) {
                    Log.d("rr : ", "dededededededededede");
                    if (!item.isSection()) {
                        EntryItem entryItem = (EntryItem) items.get(i);
                        edQualificationCatagorey.setText(entryItem.getTitle());
                        subQualifiactionCatagoryId = entryItem.getId();

                    }
                }


                dialog.dismiss();
            }
        });

        dialog.show();

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

                            regionalId = workExperienceEdit.getData().getQualificationsview().getRegion_id();
                            organizationId = workExperienceEdit.getData().getQualificationsview().getOrganization_category_id();
                            QualifiactionCatagoryId = workExperienceEdit.getData().getQualificationsview().getCategory_id();
                            subQualifiactionCatagoryId = workExperienceEdit.getData().getQualificationsview().getSubcategory_id();
                            nceaId = workExperienceEdit.getData().getQualificationsview().getNcl_id();
                            providerId = workExperienceEdit.getData().getQualificationsview().getProvider_id();
                            titleOfQualifiactionId = workExperienceEdit.getData().getQualificationsview().getQualification_id();
                            edQualification.setText(workExperienceEdit.getData().getQualificationsview().getDescription());
                            edStartDate.setText(Utils.getOnlyDate(workExperienceEdit.getData().getQualificationsview().getStart_date()));

                            if (titleOfQualifiactionId.equals("-1")) {

                                edQualifiactiontitle.setText(workExperienceEdit.getData().getQualificationsview().getTitle());
                            }

                            if (workExperienceEdit.getData().getQualificationsview().getQualification_status().equals("2")) {
                                statusDate = "2";
                                editDateView.setVisibility(View.GONE);
                                switch_button.setChecked(true);
                                edEndDate.setText(" ");


                            } else {
                                switch_button.setChecked(false);
                                statusDate = "1";
                                editDateView.setVisibility(View.VISIBLE);

                                Log.v("fhvefevfgheghvegfe", workExperienceEdit.getData().getQualificationsview().getEnd_date());

                                edEndDate.setText(Utils.getOnlyDate(workExperienceEdit.getData().getQualificationsview().getEnd_date()));
                            }


//                            edjobTitle.setText(workExperienceEdit.getData().getQualificationsview().getTitle());
//                            tvEmployer.setText(workExperienceEdit.getData().getQualificationsview().getProvider_name());
//                            edjobDescription.setText(workExperienceEdit.getData().getQualificationsview().getDescription());
//                            edStartDate.setText(Utils.getOnlyDate(workExperienceEdit.getData().getQualificationsview().getStart_date()));
//
//
//                            jobTypeid = workExperienceEdit.getData().getQualificationsview().getType_id();
//                            partnerId = workExperienceEdit.getData().getQualificationsview().getCategory_id();
//                            worksubcatagoryid = workExperienceEdit.getData().getQualificationsview().getSubcategory_id();
//


                            loadDatasMaster();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                    }


                } else if (response.code() == 304) {

                    Toast.makeText(EducationEditActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(EducationEditActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(EducationEditActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(EducationEditActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(EducationEditActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(EducationEditActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(EducationEditActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<WorkExperienceEdit> call, @NonNull Throwable t) {

                t.printStackTrace();

            }
        });


    }


}

