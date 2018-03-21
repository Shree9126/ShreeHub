package com.mindnotix.youthhub;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mindnotix.adapter.EntryAdapter;
import com.mindnotix.listener.Item;
import com.mindnotix.model.BasicResponse;
import com.mindnotix.model.DataEmailVerify;
import com.mindnotix.model.Ethnicity;
import com.mindnotix.model.Local_board_list;
import com.mindnotix.model.Regional_council_list;
import com.mindnotix.model.profile.update.CurrentQualificationType;
import com.mindnotix.model.profile.update.EntryItem;
import com.mindnotix.model.profile.update.Gender;
import com.mindnotix.model.profile.update.IntendedDestination;
import com.mindnotix.model.profile.update.Iwi;
import com.mindnotix.model.profile.update.LicenseType;
import com.mindnotix.model.profile.update.SectionItem;
import com.mindnotix.model.profile.update.Surub;
import com.mindnotix.model.profile.update.WhatVisaHave;
import com.mindnotix.model.profile.update.WorkAvailablity;
import com.mindnotix.model.profile.update.WorkExperience;
import com.mindnotix.model.profile.update.WorkStatus;
import com.mindnotix.model.profile.update.WorkType;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mabbas007.tagsedittext.TagsEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileUpdateActivity extends BaseActivity implements View.OnClickListener,
        TagsEditText.TagsEditListener {

    private static String local_board_id = " ";
    private static String gender_id = " ";
    private static String regional_id = " ";
    private static String visa_expire_month = " ";
    private static String visa_expire_year = " ";
    private static String suburb_id = " ";
    private static String current_education_id = " ";
    private static String ethinity_string = " ";
    private static String workstatus_id = " ";
    private static String work_availabilty_id = " ";
    private static String work_experience_id = " ";
    private static String preffered_region_id = " ";
    private static String hours_required_string_editext = " ";
    private static String prefered_district_to_work_id = " ";
    private static String fututre_intented_destination_name = " ";
    private static String license_and_transport_name = " ";
    private static String residency_status_id = " ";
    private static String what_visa_id = " ";
    private static String iwi_id = " ";
    private static String isprivacy = " ";

    protected Toolbar toolbar;
    protected EditText edFirstName;
    protected EditText edLastName;
    protected EditText edDob;
    protected Spinner spGender;
    protected Spinner spRegion;
    protected Spinner spDistrictCity;
    protected Spinner spsUrb;
    protected EditText edStreet;
    protected EditText edPostCode;
    protected Spinner spCurrentEducation;
    protected Spinner spEthinicity;
    protected EditText edIwi;
    protected EditText edEmail;
    protected EditText edMobileNumber;
    protected Spinner spCurrentWorkStatus;
    protected Spinner spWorkAvailabity;
    protected Spinner spWorkExperience;
    protected EditText edPreferedWorkRegion;
    protected Spinner spPreferedDistrict;
    protected Spinner spFutureIntended;
    protected Spinner spLicenseTransport;
    protected Spinner spPrefferedRegionToWork;
    protected Spinner spResidency;
    protected Spinner spVisa;
    protected EditText edInstagram;
    protected EditText edFacebook;
    protected EditText edTwitter;
    protected EditText edGooglePlus;
    protected EditText edlinkedIn;
    protected EditText edGithub;
    protected EditText edBehance;
    protected EditText edAbout;
    protected EditText edVisaMonth;
    protected EditText edVisaYear;
    protected Button btShow;
    protected Button btShow_white;
    protected Button btHide_White;
    protected Button btHide;
    protected Button btProfile;
    protected View viewCalendar;
    protected View workInfo;
    protected View communicationInfo;

    protected View progressBar;
    protected View layoutMainView;
    protected View viewMonthYear;
    protected View visaMonthYearLayout;

    ArrayList<Gender> gender;
    ArrayList<Regional_council_list> regionalCouncilLists;
    ArrayList<Local_board_list> localBoardLists;
    ArrayList<Ethnicity> ethnicities;
    ArrayList<CurrentQualificationType> currentQualificationTypeArrayList;
    ArrayList<WorkStatus> ResidencyStatusArrayList;
    ArrayList<WorkAvailablity> WorkavailabiltyArraylist;
    ArrayList<WorkExperience> WorkexperienceArraylist;
    ArrayList<IntendedDestination> IndenededArraylist;
    ArrayList<LicenseType> LicenseType;
    ArrayList<WorkType> WorkType;
    ArrayList<Surub> SurubLoad;
    ArrayList<Iwi> iwis;
    ArrayList<WhatVisaHave> whatVisaArrayList;
    ArrayList<Local_board_list> PreferedDistrictArrayList;


    ArrayList<Item> items;

    DatePickerDialog dpd;
    ProgressDialog progressDialog;
    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "dd/MM/yyyy";

    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.GERMANY);
    DatePickerDialog.OnDateSetListener date;

    String workHelper;
    String communications_helper;
    Call<DataEmailVerify> loadSubrub;
    Call<DataEmailVerify> loaddistrict;
    Call<BasicResponse> updateProfile;
    Call<BasicResponse> loadprofileapicall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        intiUi();
        loadProfileData();


    }

    private void intiUi() {

        edVisaYear = findViewById(R.id.edVisaYear);
        edVisaMonth = findViewById(R.id.edVisaMonth);
        spCurrentEducation = findViewById(R.id.spCurrentEducation);
        spEthinicity = findViewById(R.id.spEthinicity);
        edIwi = findViewById(R.id.edIwi);
        edEmail = findViewById(R.id.edEmail);
        edMobileNumber = findViewById(R.id.edMobileNumber);
        spCurrentWorkStatus = findViewById(R.id.spCurrentWorkStatus);
        spWorkAvailabity = findViewById(R.id.spWorkAvailabity);
        spWorkExperience = findViewById(R.id.spWorkExperience);
        spPrefferedRegionToWork = findViewById(R.id.spPrefferedRegionToWork);
        edPreferedWorkRegion = findViewById(R.id.edPreferedWorkRegion);
        spPreferedDistrict = findViewById(R.id.spPreferedDistrict);
        spFutureIntended = findViewById(R.id.spFutureIntended);
        spLicenseTransport = findViewById(R.id.spLicenseTransport);
        spResidency = findViewById(R.id.spResidency);
        edInstagram = findViewById(R.id.edInstagram);
        edFirstName = findViewById(R.id.edFirstName);
        edLastName = findViewById(R.id.edLastName);
        edDob = findViewById(R.id.edDob);
        spGender = findViewById(R.id.spGender);
        spRegion = findViewById(R.id.spRegion);
        spDistrictCity = findViewById(R.id.spDistrictCity);
        spsUrb = findViewById(R.id.spsUrb);
        edStreet = findViewById(R.id.edStreet);
        edPostCode = findViewById(R.id.edPostCode);
        workInfo = findViewById(R.id.workInfo);
        communicationInfo = findViewById(R.id.communicationInfo);

        edFacebook = findViewById(R.id.edFacebook);
        edTwitter = findViewById(R.id.edTwitter);
        edGooglePlus = findViewById(R.id.edGooglePlus);
        edlinkedIn = findViewById(R.id.edlinkedIn);
        edGithub = findViewById(R.id.edGithub);
        edBehance = findViewById(R.id.edBehance);
        edAbout = findViewById(R.id.edAbout);
        btShow = findViewById(R.id.btShow);
        btShow_white = findViewById(R.id.btShow_white);
        btHide_White = findViewById(R.id.btHide_White);
        btHide = findViewById(R.id.btHide);
        btProfile = findViewById(R.id.btProfile);
        viewCalendar = findViewById(R.id.viewCalendar);

        progressBar = findViewById(R.id.progressBar);
        layoutMainView = findViewById(R.id.layoutMainView);
        viewMonthYear = findViewById(R.id.viewMonthYear);
        visaMonthYearLayout = findViewById(R.id.visaMonthYearLayout);
        spVisa = findViewById(R.id.spVisa);
        toolbar = findViewById(R.id.toolbar);

        gender = new ArrayList<>();
        regionalCouncilLists = new ArrayList<>();
        localBoardLists = new ArrayList<>();
        ethnicities = new ArrayList<>();
        currentQualificationTypeArrayList = new ArrayList<>();
        ResidencyStatusArrayList = new ArrayList<>();
        WorkavailabiltyArraylist = new ArrayList<>();
        WorkexperienceArraylist = new ArrayList<>();
        IndenededArraylist = new ArrayList<>();
        LicenseType = new ArrayList<>();
        WorkType = new ArrayList<>();
        SurubLoad = new ArrayList<>();
        iwis = new ArrayList<>();
        PreferedDistrictArrayList = new ArrayList<>();
        whatVisaArrayList = new ArrayList<>();

        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
        }


        progressDialog=Utils.createProgressDialog(this);

        viewCalendar.setOnClickListener(this);
        btProfile.setOnClickListener(this);
        edIwi.setOnClickListener(this);
        workInfo.setOnClickListener(this);
        communicationInfo.setOnClickListener(this);
        btShow.setOnClickListener(this);
        btHide_White.setOnClickListener(this);
        btShow_white.setOnClickListener(this);


        spRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String RegionalID = regionalCouncilLists.get(position).getId();

                if (!RegionalID.equals("0")) {
                    regional_id = RegionalID;
                    loadDistrict(RegionalID, "0", "0");
                } else {
                    regional_id = " ";
                    localBoardLists.clear();
                    setdistrictsAdapter();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String GenderID = gender.get(position).getKey();

                if (!GenderID.equals("0")) {
                    gender_id = GenderID;

                } else {
                    gender_id = " ";
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spDistrictCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String local_board_ids = localBoardLists.get(position).getId();
                Log.v("kfnefefefnefje", " " + local_board_ids);

                if (!local_board_ids.equals("0")) {
                    local_board_id = local_board_ids;
                    loadSuburb(local_board_id);
                } else {
                    local_board_id = " ";
                    SurubLoad.clear();
                    setSurubAdapter();
                }


            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spPrefferedRegionToWork.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String preferedDistrictId = regionalCouncilLists.get(position).getId();

                if (!preferedDistrictId.equals("0")) {
                    preffered_region_id = preferedDistrictId;
                    loadDistrict(preferedDistrictId, "0", "1");
                } else {
                    preffered_region_id = " ";
                    PreferedDistrictArrayList.clear();
                    setWorkPrefereddistrictsAdapter();
                }


            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spsUrb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String suburb_ids = SurubLoad.get(position).getId();

                if (!suburb_ids.equals("0")) {
                    suburb_id = suburb_ids;

                } else {
                    suburb_id = " ";
                }


            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCurrentEducation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spCurrentEducations = currentQualificationTypeArrayList.get(position).getKey();

                if (!spCurrentEducations.equals("0")) {
                    current_education_id = spCurrentEducations;

                } else {
                    current_education_id = " ";
                }


            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spEthinicity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spCurrentEducations = ethnicities.get(position).getKey();

                if (!spCurrentEducations.equals("0")) {
                    current_education_id = spCurrentEducations;

                } else {
                    current_education_id = " ";
                }


            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spEthinicity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ethinity_strings = ethnicities.get(position).getKey();

                if (!ethinity_strings.equals("0")) {
                    ethinity_string = ethinity_strings;

                } else {
                    ethinity_string = " ";
                }


            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spCurrentWorkStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String currentworkstatus = WorkType.get(position).getId();

                if (!currentworkstatus.equals("0")) {
                    workstatus_id = currentworkstatus;


                } else {
                    workstatus_id = " ";
                }


            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spResidency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String residency = ResidencyStatusArrayList.get(position).getKey();


                if (!residency.equals("0")) {
                    residency_status_id = residency;

                    if (residency_status_id.equals("2")) {

                        viewMonthYear.setVisibility(View.VISIBLE);
                    } else {
                        viewMonthYear.setVisibility(View.GONE);
                    }


                } else {
                    residency_status_id = " ";
                }


            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spWorkAvailabity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String workavailabilty = WorkavailabiltyArraylist.get(position).getKey();

                if (!workavailabilty.equals("0")) {
                    work_availabilty_id = workavailabilty;

                } else {
                    work_availabilty_id = " ";
                }


            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spWorkExperience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String workexperience_id = WorkexperienceArraylist.get(position).getKey();

                if (!workexperience_id.equals("0")) {
                    work_experience_id = workexperience_id;

                } else {
                    work_experience_id = " ";
                }


            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spPreferedDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String prefered_district_id = PreferedDistrictArrayList.get(position).getId();

                if (!prefered_district_id.equals("0")) {
                    prefered_district_to_work_id = prefered_district_id;

                } else {
                    prefered_district_to_work_id = " ";
                }


            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spFutureIntended.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String intended_id = IndenededArraylist.get(position).getKey();

                if (!intended_id.equals("0")) {
                    fututre_intented_destination_name = intended_id;

                } else {
                    fututre_intented_destination_name = " ";
                }


            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spLicenseTransport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String license_id = LicenseType.get(position).getKey();

                if (!license_id.equals("0")) {
                    license_and_transport_name = license_id;

                } else {
                    license_and_transport_name = " ";
                }


            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spVisa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String visa_ids = whatVisaArrayList.get(position).getKey();


                if (!visa_ids.equals("0")) {
                    what_visa_id = visa_ids;
                    if (visa_ids.equals("1")) {
                        viewMonthYear.setVisibility(View.VISIBLE);
                        visaMonthYearLayout.setVisibility(View.GONE);
                    } else {
                        visaMonthYearLayout.setVisibility(View.VISIBLE);
                        viewMonthYear.setVisibility(View.VISIBLE);
                    }

                } else {
                    what_visa_id = " ";
                }


            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // set calendar date and update editDate
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
        Log.d("updateDate", "updateDate: " + sdf.format(myCalendar.getTime()));
        edDob.setText(sdf.format(myCalendar.getTime()));
    }

    private void loadSuburb(String ID) {



        if(progressDialog!=null && !progressDialog.isShowing()) {
            progressDialog.show();
        }


        SurubLoad = new ArrayList<>();

        Map<String, String> data = new HashMap<>();
        data.put("local_board_id", ID);

        Log.v("kednednendedje", " " + "yhthttht");

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        loadSubrub = apiService.getSurubs(data);

        loadSubrub.enqueue(new Callback<DataEmailVerify>() {
            @Override
            public void onResponse(@NonNull Call<DataEmailVerify> call, @NonNull Response<DataEmailVerify> response) {

                progressDialog.dismiss();

                if (response.code() == 200 || response.code() == 201) {

                    DataEmailVerify dataEmailVerify = response.body();

                    if (dataEmailVerify.getStatus().equals("1")) {

                        try {
                            SurubLoad.clear();

                            SurubLoad.addAll(dataEmailVerify.getData().getSuburbs_list());

                            setSurubAdapter();

                            Log.v("suburub", " " + suburb_id);


                            for (int i = 0; i < SurubLoad.size(); i++) {
                                if (suburb_id.equals(SurubLoad.get(i).getId())) {
                                    spsUrb.setSelection(i);

                                    Log.v("kednednendedje", " " + SurubLoad.get(i).getId());
                                }
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                } else if (response.code() == 304) {

                    Toast.makeText(ProfileUpdateActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(ProfileUpdateActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(ProfileUpdateActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(ProfileUpdateActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(ProfileUpdateActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(ProfileUpdateActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(ProfileUpdateActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<DataEmailVerify> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();

            }
        });
    }

    private void loadDistrict(String regionalId, String userID, final String status) {

        if(progressDialog!=null && !progressDialog.isShowing()) {
            progressDialog.show();
        }


        Map<String, String> data = new HashMap<>();
        data.put("regional_id", regionalId);
        data.put("is_user_id", userID);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        loaddistrict = apiService.getLocalBoardDistrict(data);

        loaddistrict.enqueue(new Callback<DataEmailVerify>() {
            @Override
            public void onResponse(@NonNull Call<DataEmailVerify> call, @NonNull Response<DataEmailVerify> response) {

                progressDialog.dismiss();

                if (response.code() == 200 || response.code() == 201) {

                    DataEmailVerify dataEmailVerify = response.body();

                    if (dataEmailVerify.getStatus().equals("1")) {

                        try {

                            if (status.equals("0")) {

                                localBoardLists = new ArrayList<>();
                                localBoardLists.clear();
                                localBoardLists.addAll(dataEmailVerify.getData().getLocal_board_list());

                                setdistrictsAdapter();

                                Log.v("kednednendedje", " " + local_board_id);

                                for (int i = 0; i < localBoardLists.size(); i++) {
                                    if (local_board_id.equals(localBoardLists.get(i).getId())) {
                                        spDistrictCity.setSelection(i);
                                        Log.v("kednednendedje", " " + localBoardLists.get(i).getId());
                                    }
                                }
                            } else {

                                PreferedDistrictArrayList = new ArrayList<>();
                                PreferedDistrictArrayList.clear();
                                PreferedDistrictArrayList.addAll(dataEmailVerify.getData().getLocal_board_list());

                                setWorkPrefereddistrictsAdapter();

                                Log.v("kednednendedje", " " + local_board_id);

                                for (int i = 0; i < PreferedDistrictArrayList.size(); i++) {
                                    if (prefered_district_to_work_id.equals(PreferedDistrictArrayList.get(i).getId())) {
                                        spPreferedDistrict.setSelection(i);
                                        Log.v("kednednendedje", " " + PreferedDistrictArrayList.get(i).getId());
                                    }
                                }
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                } else if (response.code() == 304) {

                    Toast.makeText(ProfileUpdateActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(ProfileUpdateActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(ProfileUpdateActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(ProfileUpdateActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    Toast.makeText(ProfileUpdateActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(ProfileUpdateActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(ProfileUpdateActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<DataEmailVerify> call, @NonNull Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }

    private void setdistrictsAdapter() {

        Local_board_list Local_board_list = new Local_board_list();
        Local_board_list.setId("0");
        Local_board_list.setName("--select--");
        localBoardLists.add(0, Local_board_list);

        ArrayAdapter<Local_board_list> local_board_listArrayAdapter =
                new ArrayAdapter<Local_board_list>(ProfileUpdateActivity.this, android.R.layout.simple_spinner_dropdown_item,
                        localBoardLists);
        local_board_listArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spDistrictCity.setAdapter(local_board_listArrayAdapter);
        spDistrictCity.setSelection(0);


    }

    private void setWorkPrefereddistrictsAdapter() {

        Local_board_list Local_board_list = new Local_board_list();
        Local_board_list.setId("0");
        Local_board_list.setName("--select--");
        PreferedDistrictArrayList.add(0, Local_board_list);

        ArrayAdapter<Local_board_list> PreffereddistrictAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, PreferedDistrictArrayList);
        PreffereddistrictAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spPreferedDistrict.setAdapter(PreffereddistrictAdapter);
        spPreferedDistrict.setSelection(0);


    }

    private void setSurubAdapter() {

        Surub surub = new Surub();
        surub.setId("0");
        surub.setName("--select--");
        SurubLoad.add(0, surub);

        ArrayAdapter<Surub> SurubArrayAdapter =
                new ArrayAdapter<Surub>(ProfileUpdateActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        SurubLoad);
        SurubArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spsUrb.setAdapter(SurubArrayAdapter);
        spsUrb.setSelection(0);


    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {
            case R.id.btShow:
                isprivacy = "0";
                btShow.setVisibility(View.GONE);
                btShow_white.setVisibility(View.VISIBLE);
                btHide_White.setVisibility(View.VISIBLE);
                btHide.setVisibility(View.GONE);

                break;


            case R.id.btHide_White:
                isprivacy = "1";
                btHide.setVisibility(View.VISIBLE);
                btShow_white.setVisibility(View.GONE);
                btShow.setVisibility(View.VISIBLE);
                btHide_White.setVisibility(View.GONE);

                break;

            case R.id.viewCalendar:
                calendar();
                break;

            case R.id.btProfile:
                validate();

                break;

            case R.id.edIwi:
                showChangeLangDialog();
                break;


            case R.id.workInfo:
                loadDilaog(workHelper);
                break;

            case R.id.communicationInfo:
                communicationDilaog(communications_helper);
                break;


        }


    }

    private void loadDilaog(String workHelper) {

        final Dialog dialog = new Dialog(ProfileUpdateActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_work_info);

        TextView text = dialog.findViewById(R.id.text_dialog);
        ImageView imageView = dialog.findViewById(R.id.icClose);
        text.setText(workHelper);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void communicationDilaog(String commuicationHelper) {

        final Dialog dialog = new Dialog(ProfileUpdateActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_communication_connections);

        TextView text = dialog.findViewById(R.id.text_dialog);
        View view = dialog.findViewById(R.id.icClose);
        text.setText(commuicationHelper);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void showChangeLangDialog() {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dilaog_iwi, null);
        dialogBuilder.setView(dialogView);

        final EditText filterText = (EditText) dialogView.findViewById(R.id.edit1);
        final ListView listView = dialogView.findViewById(R.id.jobWishList);
        EntryAdapter entryAdapter = new EntryAdapter(ProfileUpdateActivity.this, items);
        listView.setAdapter(entryAdapter);
        dialogBuilder.setTitle("Iwi");


        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                iwi_id = "";

                String sep = "";
                String iwi_data_string = "";
                String sep1 = "";

                Log.d("ssssssssssssss : ", "dededededededededede");
                for (Item item : items) {
                    Log.d("rr : ", "dededededededededede");
                    if (!item.isSection()) {
                        Log.d("aa : ", "dededededededededede");
                        EntryItem entryItem = (EntryItem) item;
                        if (entryItem.getSetchecked()) {

                            iwi_id = iwi_id + sep + entryItem.getId();
                            sep = ",";
                            iwi_data_string = iwi_data_string + sep1 + entryItem.getTitle();
                            sep1 = ",";

                        }
                    }
                }

                Log.d("ITEEM : " + iwi_id, "ddddd");
                edIwi.setText(iwi_data_string);

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();

    }

    private void validate() {

        if (!edFirstName.getText().toString().isEmpty() && !edFirstName.getText().toString().equals(" ")) {
            if (!edLastName.getText().toString().isEmpty() && !edLastName.getText().toString().equals(" ")) {
                if (!edDob.getText().toString().isEmpty() && !edDob.getText().toString().equals(" ")) {
                    if (!edEmail.getText().toString().isEmpty() && !edEmail.getText().toString().equals(" ")) {
                        if (Utils.isEmailValid(edEmail.getText().toString())) {
                            if (!edMobileNumber.getText().toString().isEmpty() && !edMobileNumber.getText().toString().equals(" ")) {
                                if (Utils.validateMobileNZ(edMobileNumber.getText().toString())) {
                                    updateProfile();
                                } else {
                                    Toast.makeText(ProfileUpdateActivity.this, "Enter the valid mobile number", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(ProfileUpdateActivity.this, "Enter the mobile number", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ProfileUpdateActivity.this, "Enter the valid emailId ", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(ProfileUpdateActivity.this, "Enter the emailId", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(ProfileUpdateActivity.this, "Enter the date of birth", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ProfileUpdateActivity.this, "Enter the last name", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ProfileUpdateActivity.this, "Enter the first name", Toast.LENGTH_SHORT).show();
        }


    }

    private void updateProfile() {


        int edGooglePlusFlag = 0;
        int edlinkedInFlag = 0;
        int edGithubFlag = 0;
        int edTwitterFlag = 0;
        int edFacebookFlag = 0;
        int edInstagramFlag = 0;
        int behance_urlFlag = 0;


        progressDialog = Utils.createProgressDialog(ProfileUpdateActivity.this);
        Map<String, String> data = new HashMap<>();
        data.put("profileid", Pref.getClientId());
        data.put("firstname", edFirstName.getText().toString());
        data.put("lastname", edLastName.getText().toString());
        data.put("gender", gender_id);
        data.put("date_of_birth", edDob.getText().toString());
        data.put("regional_id", regional_id);
        data.put("street", edStreet.getText().toString());
        data.put("local_board_id", local_board_id);
        data.put("suburb", suburb_id);
        data.put("postcode", edPostCode.getText().toString());
        data.put("email", edEmail.getText().toString());


        if (!edInstagram.getText().toString().trim().isEmpty()) {


            String googlePlusUrl = "^(https?:)?(www.)?instagram.com[a-zA-Z0-9(.?)?]";
            Pattern p = Patterns.WEB_URL;
            Matcher m = p.matcher(edInstagram.getText().toString().trim());
            boolean b = m.matches();
            if (b) {
                data.put("instagram_url", edInstagram.getText().toString());

            } else {

                edInstagramFlag = 1;
                Toast.makeText(this, "Invalid instagram url id. Try again..", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                return;
            }
        } else {
            data.put("instagram_url", "");
        }


        if (!edFacebook.getText().toString().trim().isEmpty()) {

            String googlePlusUrl = "^(https?:)?(www.)?facebook.com[a-zA-Z0-9(.?)?]";
            Pattern p = Pattern.compile(googlePlusUrl);
            Matcher m = p.matcher(edFacebook.getText().toString().trim());
            boolean b = m.matches();
            if (b) {
                data.put("facebook", edFacebook.getText().toString());

            } else {

                edFacebookFlag = 1;
                Toast.makeText(this, "Invalid facebook url. Try again..", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                return;
            }
        } else {
            data.put("facebook", "");
        }

        if (!edTwitter.getText().toString().trim().isEmpty()) {

            String googlePlusUrl = "^(https?:)?(www.)?twitter.com[a-zA-Z0-9(.?)?]";
            Pattern p = Pattern.compile(googlePlusUrl);
            Matcher m = p.matcher(edTwitter.getText().toString().trim());
            boolean b = m.matches();
            if (b) {
                data.put("twitter", edTwitter.getText().toString());

            } else {

                edTwitterFlag = 1;
                Toast.makeText(this, "Invalid twitter url. Try again..", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                return;
            }
        } else {
            data.put("twitter", "");
        }

        if (!edGooglePlus.getText().toString().trim().isEmpty()) {

            String googlePlusUrl = "^(https?:)?(www.)?plus.google.com[a-zA-Z0-9(.?)?]";
            Pattern p = Pattern.compile(googlePlusUrl);
            Matcher m = p.matcher(edGooglePlus.getText().toString().trim());
            boolean b = m.matches();
            if (b) {

                data.put("googleplus", edGooglePlus.getText().toString());

            } else {

                edGooglePlusFlag = 1;
                Toast.makeText(this, "Invalid google plus id. Try again..", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

                return;
            }
        } else {
            data.put("googleplus", "");
        }


        if (!edlinkedIn.getText().toString().trim().isEmpty()) {

            String googlePlusUrl = "^(https?:)?(www.)?linkedin.com[a-zA-Z0-9(.?)?]";
            Pattern p = Pattern.compile(googlePlusUrl);
            Matcher m = p.matcher(edlinkedIn.getText().toString().trim());
            boolean b = m.matches();
            if (b) {
                data.put("linkedin", edlinkedIn.getText().toString());

            } else {

                edlinkedInFlag = 1;
                Toast.makeText(this, "Invalid linkedin url. Try again..", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                return;
            }
        } else {
            data.put("linkedin", "");
        }

        if (!edGithub.getText().toString().trim().isEmpty()) {

            String googlePlusUrl = "^(https?:)?(www.)?github.com[a-zA-Z0-9(.?)?]";
            Pattern p = Pattern.compile(googlePlusUrl);
            Matcher m = p.matcher(edGithub.getText().toString().trim());
            boolean b = m.matches();
            if (b) {
                data.put("github", edGithub.getText().toString());

            } else {

                edGithubFlag = 1;
                Toast.makeText(this, "Invalid github url. Try again..", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                return;
            }
        } else {
            data.put("github", "");
        }

        if (!edBehance.getText().toString().trim().isEmpty()) {

            String googlePlusUrl = "^(https?:)?(www.)?behance.com[a-zA-Z0-9(.?)?]";
            Pattern p = Pattern.compile(googlePlusUrl);
            Matcher m = p.matcher(edBehance.getText().toString().trim());
            boolean b = m.matches();
            if (b) {
                data.put("behance_url", edBehance.getText().toString());


            } else {

                behance_urlFlag = 1;
                Toast.makeText(this, "Invalid behance url. Try again..", Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
                return;
            }
        } else {
            data.put("behance_url", "");
        }


        data.put("mobile_no", edMobileNumber.getText().toString());
        data.put("ethnicity", ethinity_string);
        data.put("iwi", iwi_id);
        data.put("license_type", license_and_transport_name);
        data.put("work_experience", work_experience_id);
        data.put("hours_required", edPreferedWorkRegion.getText().toString());
        data.put("general_availability", work_availabilty_id);
        data.put("work_situation", workstatus_id);
        data.put("what_visa_have", what_visa_id);
        data.put("visa_expire_month", edVisaMonth.getText().toString());
        data.put("visa_expire_year", edVisaYear.getText().toString());
        data.put("prefered_regional", preffered_region_id);
        data.put("prefered_local_board", prefered_district_to_work_id);
        data.put("work_status", residency_status_id);
        data.put("intended_destination", fututre_intented_destination_name);
        data.put("aboutme", edAbout.getText().toString());
        data.put("isprivacy", isprivacy);
        data.put("current_qualification_type", current_education_id);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);


        updateProfile = apiService.updateProfile(Pref.getPreToken(), data);

        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("key, " + key + " value " + value);
        }


        if (edInstagramFlag == 0 && edlinkedInFlag == 0 && edGithubFlag == 0 && edTwitterFlag == 0 &&
                edFacebookFlag == 0 && edInstagramFlag == 0 && behance_urlFlag == 0 && edGooglePlusFlag == 0) {
            updateProfile.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(@NonNull Call<BasicResponse> call, @NonNull Response<BasicResponse> response) {

                    if (response.code() == 200 || response.code() == 201) {

                        BasicResponse basicResponse = response.body();

                        if (basicResponse.getStatus().equals("1")) {
                            progressDialog.dismiss();

                            try {

                                Pref.setClientFirstname(edFirstName.getText().toString());
                                Pref.setdevicetoken("Bearer " + basicResponse.getToken());
                                Toast.makeText(ProfileUpdateActivity.this, basicResponse.getMessage(), Toast.LENGTH_SHORT).show();

//                                Intent intent = new Intent(ProfileUpdateActivity.this, ProfileActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(intent);
                                finish();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(ProfileUpdateActivity.this, basicResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }


                    } else if (response.code() == 304) {
                        progressDialog.dismiss();
                        Toast.makeText(ProfileUpdateActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {
                        progressDialog.dismiss();
                        Toast.makeText(ProfileUpdateActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {
                        progressDialog.dismiss();
                        Toast.makeText(ProfileUpdateActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {
                        progressDialog.dismiss();
                        Toast.makeText(ProfileUpdateActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {
                        progressDialog.dismiss();
                        Toast.makeText(ProfileUpdateActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {
                        progressDialog.dismiss();
                        Toast.makeText(ProfileUpdateActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(ProfileUpdateActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }


                }

                @Override
                public void onFailure(@NonNull Call<BasicResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    progressDialog.dismiss();
                }
            });
        } else {
            progressDialog.dismiss();
        }


    }

    public void loadProfileData() {


        progressBar.setVisibility(View.VISIBLE);


        Map<String, String> data = new HashMap<>();
        data.put("profileid", Pref.getClientId());

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        loadprofileapicall = apiService.loadProfile(Pref.getPreToken(), data);

        loadprofileapicall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(@NonNull Call<BasicResponse> call, @NonNull Response<BasicResponse> response) {

                if (response.code() == 200 || response.code() == 201) {

                    BasicResponse basicResponse = response.body();

                    if (basicResponse.getStatus().equals("1")) {

                        try {

                            Pref.setdevicetoken("Bearer " + basicResponse.getToken());

                            progressBar.setVisibility(View.GONE);
                            layoutMainView.setVisibility(View.VISIBLE);
                            edFirstName.setText(basicResponse.getData().getYouthuser().getFirst_name());
                            edLastName.setText(basicResponse.getData().getYouthuser().getLast_name());
                            edDob.setText(basicResponse.getData().getYouth().getDate_of_birth());
                            edEmail.setText(basicResponse.getData().getYouth().getEmail());
                            edMobileNumber.setText(basicResponse.getData().getYouth().getMobile_no());
                            if (basicResponse.getData().getYouth().getStreet() != null &&
                                    !basicResponse.getData().getYouth().getStreet().isEmpty()) {
                                edStreet.setText(basicResponse.getData().getYouth().getStreet());
                            }

                            if (basicResponse.getData().getYouth().getPostcode() != null &&
                                    !basicResponse.getData().getYouth().getPostcode().isEmpty()) {
                                edPostCode.setText(basicResponse.getData().getYouth().getPostcode());
                            }

                            if (basicResponse.getData().getYouth().getInstagram_url() != null &&
                                    !basicResponse.getData().getYouth().getInstagram_url().isEmpty()) {
                                edInstagram.setText(basicResponse.getData().getYouth().getInstagram_url());
                            }

                            if (basicResponse.getData().getYouth().getFacebook() != null &&
                                    !basicResponse.getData().getYouth().getFacebook().isEmpty()) {

                                edFacebook.setText(basicResponse.getData().getYouth().getFacebook());

                                Log.v("edbhehefhehfrhf", basicResponse.getData().getYouth().getFacebook());
                            }

                            if (basicResponse.getData().getYouth().getTwitter() != null &&
                                    !basicResponse.getData().getYouth().getTwitter().isEmpty()) {
                                edTwitter.setText(basicResponse.getData().getYouth().getTwitter());
                            }

                            if (basicResponse.getData().getYouth().getGoogleplus() != null &&
                                    !basicResponse.getData().getYouth().getGoogleplus().isEmpty()) {
                                edGooglePlus.setText(basicResponse.getData().getYouth().getGoogleplus());
                            }

                            if (basicResponse.getData().getYouth().getLinkedin() != null &&
                                    !basicResponse.getData().getYouth().getLinkedin().isEmpty()) {
                                edlinkedIn.setText(basicResponse.getData().getYouth().getLinkedin());
                            }

                            if (basicResponse.getData().getYouth().getGithub() != null &&
                                    !basicResponse.getData().getYouth().getGithub().isEmpty()) {
                                edGithub.setText(basicResponse.getData().getYouth().getGithub());
                            }

                            if (basicResponse.getData().getYouth().getBehance_url() != null &&
                                    !basicResponse.getData().getYouth().getBehance_url().isEmpty()) {
                                edBehance.setText(basicResponse.getData().getYouth().getBehance_url());
                            }

                            if (basicResponse.getData().getYouthuser().getAbout_me() != null &&
                                    !basicResponse.getData().getYouthuser().getAbout_me().isEmpty()) {
                                edAbout.setText(basicResponse.getData().getYouthuser().getAbout_me());
                            }


                            gender.addAll(basicResponse.getData().getGender());
                            regionalCouncilLists.addAll(basicResponse.getData().getRegional_council_list());
                            ethnicities.addAll(basicResponse.getData().getEthnicity());
                            currentQualificationTypeArrayList.addAll(basicResponse.getData().getCurrent_qualification_type());
                            ResidencyStatusArrayList.addAll(basicResponse.getData().getWork_status());
                            WorkavailabiltyArraylist.addAll(basicResponse.getData().getGeneral_availability());
                            WorkexperienceArraylist.addAll(basicResponse.getData().getWork_experience());
                            IndenededArraylist.addAll(basicResponse.getData().getIntended_destination());
                            LicenseType.addAll(basicResponse.getData().getLicense_type());

                            //its current work status
                            WorkType.addAll(basicResponse.getData().getWork_type());

                            iwis.addAll(basicResponse.getData().getIwi());
                            whatVisaArrayList.addAll(basicResponse.getData().getWhat_visa_have());
                            workHelper = basicResponse.getData().getWork_information_helper();
                            communications_helper = basicResponse.getData().getCommunications_helper();
                            items = new ArrayList<>();
                            for (int i = 0; i < basicResponse.getData().getIwi().size(); i++) {
                                items.add(new SectionItem(basicResponse.getData().getIwi().get(i).getName()));
                                Log.v("deidjiedjieooejioeiod", basicResponse.getData().getIwi().get(i).getName());
                                for (int j = 0; j < basicResponse.getData().getIwi().get(i).getSubiwi().size(); j++) {
                                    items.add(new EntryItem(basicResponse.getData().getIwi().get(i).getSubiwi().get(j).getName(),
                                            basicResponse.getData().getIwi().get(i).getSubiwi().get(j).getKey()));
                                    Log.v("deidjiedjieooejioeiod", basicResponse.getData().getIwi().get(i).getSubiwi().get(j).getName());

                                }


                            }


                            //bind data
                            setSpinnerAdapter();

                            if (what_visa_id.equals("2")) {

                                visa_expire_month = basicResponse.getData().getYouth().getVisa_expire_month();
                                edVisaMonth.setText(visa_expire_month);

                                visa_expire_year = basicResponse.getData().getYouth().getVisa_expire_year();
                                edVisaYear.setText(visa_expire_year);

                            } else {

                                visa_expire_month = basicResponse.getData().getYouth().getVisa_expire_month();
                                edVisaMonth.setText(visa_expire_month);

                                visa_expire_year = basicResponse.getData().getYouth().getVisa_expire_year();
                                edVisaYear.setText(visa_expire_year);

                            }


                            regional_id = basicResponse.getData().getYouth().getRegional_id();

                            gender_id = basicResponse.getData().getYouth().getGender();


                            //district
                            local_board_id = basicResponse.getData().getYouth().getLocal_board_id();


                            //surub_id
                            suburb_id = basicResponse.getData().getYouth().getSuburb();

                            //current qualification type id send
                            current_education_id = basicResponse.getData().getYouth().getCurrent_qualification_type();


                            //string key text send
                            ethinity_string = basicResponse.getData().getYouth().getEthnicity();

                            //id work status current work status
                            workstatus_id = basicResponse.getData().getYouth().getWork_situation();

                            //id work availabilty
                            work_availabilty_id = basicResponse.getData().getYouth().getGeneral_availability();

                            //id workexperience_availabilty
                            work_experience_id = basicResponse.getData().getYouth().getWork_experience();

                            //id prefered_region_id
                            preffered_region_id = basicResponse.getData().getYouth().getPrefered_regional();

                            //hours required_string
                            hours_required_string_editext = basicResponse.getData().getYouth().getHours_required();

                            //id prefered_district_to_work_id
                            prefered_district_to_work_id = basicResponse.getData().getYouth().getPrefered_local_board();

                            //fututre_intented_destination_name
                            fututre_intented_destination_name = basicResponse.getData().getYouth().getIntended_destination();

                            //license_and_transport_name
                            license_and_transport_name = basicResponse.getData().getYouth().getLicense_type();

                            //residency_status_name
                            residency_status_id = basicResponse.getData().getYouth().getWork_status();

                            //what_visa_id
                            what_visa_id = basicResponse.getData().getYouth().getWhat_visa_have();

                            Log.v("jeefuheuufhef visais", what_visa_id);

                            //iwi_id
                            iwi_id = basicResponse.getData().getYouth().getIwi();


                            Log.v("jeefuheuufhef iwi_id", iwi_id);

                            //what_visa_id
                            isprivacy = basicResponse.getData().getYouth().getIsprivacy();

                            Log.v("fefkefefefhef", isprivacy);


                            if (isprivacy != null && !isprivacy.equals(" ")) {

                                if (isprivacy.equals("0")) {

                                    btShow_white.setVisibility(View.VISIBLE);//color
                                    btShow.setVisibility(View.GONE);//uncolored
                                    btHide_White.setVisibility(View.VISIBLE);//uncolored
                                    btHide.setVisibility(View.GONE);//colored

                                } else {

                                    btHide.setVisibility(View.VISIBLE);
                                    btHide_White.setVisibility(View.GONE);
                                    btShow.setVisibility(View.VISIBLE);
                                    btShow_white.setVisibility(View.GONE);
                                }


                            }


                            //iwi_set

                            String data = iwi_id;
                            String[] itemss = data.split(",");
                            String tags_id = "";
                            int x = 0;

                            for (int c = 0; c < itemss.length; c++) {
                                for (Item item1 : items) {
                                    if (!item1.isSection()) {
                                        EntryItem entryItem = (EntryItem) item1;
                                        Log.v("jeefuheuufhef entryItem", entryItem.getId());
                                        Log.v("jeefuheuufhef itemss[x]", itemss[c]);
                                        if (itemss[c].equals(entryItem.getId())) {

                                            tags_id = tags_id + entryItem.getTitle() + ",";
                                            Log.v("jeefuheuufheftag", tags_id);

                                        }

                                        // for (int i = 0; i < itemss.length; i++) {

                                        //}

                                    }

                                }
                            }
                            if (tags_id != null && tags_id.length() > 0 && tags_id.charAt(tags_id.length() - 1) == ',') {
                                tags_id = tags_id.substring(0, tags_id.length() - 1);
                            }

                            edIwi.setText(tags_id);

                            //gender

                            for (int i = 0; i < gender.size(); i++) {
                                if (gender_id.equals(gender.get(i).getKey())) {
                                    spGender.setSelection(i);
                                }
                            }

                            //visa have


                            for (int i = 0; i < whatVisaArrayList.size(); i++) {
                                if (what_visa_id.equals(whatVisaArrayList.get(i).getKey())) {
                                    spVisa.setSelection(i);
                                }
                            }


                            //current_education

                            for (int i = 0; i < currentQualificationTypeArrayList.size(); i++) {
                                if (current_education_id.equals(currentQualificationTypeArrayList.get(i).getKey())) {
                                    spCurrentEducation.setSelection(i);
                                }
                            }


                            //ethinitity
                            for (int i = 0; i < ethnicities.size(); i++) {
                                if (ethinity_string.equals(ethnicities.get(i).getKey())) {
                                    spEthinicity.setSelection(i);
                                }
                            }


                            //workstatus
                            for (int i = 0; i < WorkType.size(); i++) {
                                if (workstatus_id.equals(WorkType.get(i).getId())) {
                                    spCurrentWorkStatus.setSelection(i);


                                }
                            }

                            //workavailablity
                            for (int i = 0; i < WorkavailabiltyArraylist.size(); i++) {
                                if (work_availabilty_id.equals(WorkavailabiltyArraylist.get(i).getKey())) {
                                    spWorkAvailabity.setSelection(i);
                                }
                            }

                            //workexperience
                            for (int i = 0; i < WorkexperienceArraylist.size(); i++) {
                                if (work_experience_id.equals(WorkexperienceArraylist.get(i).getKey())) {
                                    spWorkExperience.setSelection(i);
                                }
                            }

                            //prefered_region_to_work
                            for (int i = 0; i < regionalCouncilLists.size(); i++) {
                                if (preffered_region_id.equals(regionalCouncilLists.get(i).getId())) {
                                    spPrefferedRegionToWork.setSelection(i);


                                }
                            }


                            //region

                            for (int i = 0; i < regionalCouncilLists.size(); i++) {
                                if (regional_id.equals(regionalCouncilLists.get(i).getId())) {
                                    spRegion.setSelection(i);
                                }
                            }


                            //hours_required_string_editext

                            if (hours_required_string_editext != null &&
                                    !hours_required_string_editext.isEmpty()) {
                                edPreferedWorkRegion.setText(hours_required_string_editext);

                            }


                            // fututre_intentended_destionation

                            for (int i = 0; i < LicenseType.size(); i++) {
                                if (fututre_intented_destination_name.equals(IndenededArraylist.get(i).getKey())) {
                                    spFutureIntended.setSelection(i);
                                }
                            }


                            // license_and_transport_name

                            for (int i = 0; i < LicenseType.size(); i++) {
                                if (license_and_transport_name.equals(LicenseType.get(i).getKey())) {
                                    spLicenseTransport.setSelection(i);
                                }
                            }

                            // workstatus

                            for (int i = 0; i < ResidencyStatusArrayList.size(); i++) {
                                if (residency_status_id.equals(ResidencyStatusArrayList.get(i).getKey())) {
                                    spResidency.setSelection(i);
                                }
                            }


                            //prefereddistrictload
                            for (int i = 0; i < PreferedDistrictArrayList.size(); i++) {
                                if (prefered_district_to_work_id.equals(PreferedDistrictArrayList.get(i).getId())) {
                                    spPreferedDistrict.setSelection(i);
                                }
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                } else if (response.code() == 304)

                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileUpdateActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400)

                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileUpdateActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401)

                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileUpdateActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403)

                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileUpdateActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404)

                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileUpdateActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422)

                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileUpdateActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else

                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileUpdateActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<BasicResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);
            }
        });


    }


    private void setSpinnerAdapter() {

        Gender genders = new Gender();
        genders.setKey("0");
        genders.setName("--select--");
        gender.add(0, genders);

        ArrayAdapter<Gender> dataAdapters = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, gender);
        dataAdapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spGender.setAdapter(dataAdapters);
        spGender.setSelection(0);

//        -------------------------------------------------------------


        Regional_council_list regional_council_list = new Regional_council_list();
        regional_council_list.setId("0");
        regional_council_list.setName("--select--");
        regionalCouncilLists.add(0, regional_council_list);

        ArrayAdapter<Regional_council_list> adapterRegional =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, regionalCouncilLists);
        adapterRegional.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spRegion.setAdapter(adapterRegional);
        spRegion.setSelection(0);

//        -------------------------------------------------------------


        Ethnicity ethnicity = new Ethnicity();
        ethnicity.setName("--Select a Ethnicity--");
        ethnicity.setKey("0");
        ethnicities.add(0, ethnicity);

        ArrayAdapter<Ethnicity> ethnicityArrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ethnicities);
        ethnicityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spEthinicity.setAdapter(ethnicityArrayAdapter);
        spEthinicity.setSelection(0);

//        -------------------------------------------------------------


        CurrentQualificationType currentQualificationType = new CurrentQualificationType();
        currentQualificationType.setName("--Select--");
        currentQualificationType.setKey("0");
        currentQualificationTypeArrayList.add(0, currentQualificationType);

        ArrayAdapter<CurrentQualificationType> currentQualificationAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, currentQualificationTypeArrayList);
        currentQualificationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spCurrentEducation.setAdapter(currentQualificationAdapter);
        spCurrentEducation.setSelection(0);
//        -------------------------------------------------------------


        WorkType worktype = new WorkType();
        worktype.setName("-Select-");
        worktype.setId("0");
        WorkType.add(0, worktype);


        ArrayAdapter<WorkType> workStatusAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, WorkType);
        workStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spCurrentWorkStatus.setAdapter(workStatusAdapter);
        spCurrentWorkStatus.setSelection(0);


//        -------------------------------------------------------------

        WorkAvailablity workAvailablity = new WorkAvailablity();
        workAvailablity.setName("--Select--");
        workAvailablity.setKey("0");

        WorkavailabiltyArraylist.add(0, workAvailablity);

        ArrayAdapter<WorkAvailablity> workAvailablityArrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, WorkavailabiltyArraylist);
        workAvailablityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spWorkAvailabity.setAdapter(workAvailablityArrayAdapter);
        spWorkAvailabity.setSelection(0);

//        -------------------------------------------------------------


        WorkExperience workExperience = new WorkExperience();
        workExperience.setName("--Select--");
        workExperience.setKey("0");
        WorkexperienceArraylist.add(0, workExperience);

        ArrayAdapter<WorkExperience> workExperienceArrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, WorkexperienceArraylist);
        workExperienceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spWorkExperience.setAdapter(workExperienceArrayAdapter);
        spWorkExperience.setSelection(0);


//        -------------------------------------------------------------


        IntendedDestination intendedDestination = new IntendedDestination();
        intendedDestination.setName("--Select--");
        intendedDestination.setKey("0");
        IndenededArraylist.add(0, intendedDestination);

        ArrayAdapter<IntendedDestination> intendedDestinationArrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, IndenededArraylist);
        intendedDestinationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spFutureIntended.setAdapter(intendedDestinationArrayAdapter);
        spFutureIntended.setSelection(0);


//        -------------------------------------------------------------

        LicenseType licenseType = new LicenseType();
        licenseType.setName("Select a License Type");
        licenseType.setKey("0");
        LicenseType.add(0, licenseType);

        ArrayAdapter<LicenseType> LicenseTypeArrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, LicenseType);
        LicenseTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spLicenseTransport.setAdapter(LicenseTypeArrayAdapter);
        spLicenseTransport.setSelection(0);


        //        -------------------------------------------------------------


        ArrayAdapter<Regional_council_list> PrefferedRegionWorkAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, regionalCouncilLists);
        PrefferedRegionWorkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spPrefferedRegionToWork.setAdapter(PrefferedRegionWorkAdapter);
        spPrefferedRegionToWork.setSelection(0);


        //        -------------------------------------------------------------


        WorkStatus workStatus = new WorkStatus();
        workStatus.setName("-Select-");
        workStatus.setKey("0");
        ResidencyStatusArrayList.add(0, workStatus);

        ArrayAdapter<WorkStatus> WorkTypeArrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ResidencyStatusArrayList);
        WorkTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spResidency.setAdapter(WorkTypeArrayAdapter);
        spResidency.setSelection(0);

        //        -------------------------------------------------------------

        WhatVisaHave whatVisaHave = new WhatVisaHave();
        whatVisaHave.setName("-Select-");
        whatVisaHave.setKey("0");
        whatVisaArrayList.add(0, whatVisaHave);

        ArrayAdapter<WhatVisaHave> WhatvisaAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, whatVisaArrayList);
        WhatvisaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spVisa.setAdapter(WhatvisaAdapter);
        spVisa.setSelection(0);


    }


    private void calendar() {

        DatePickerDialog dialog = new DatePickerDialog(ProfileUpdateActivity.this, date, myCalendar
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


    @Override
    public void onTagsChanged(Collection<String> collection) {

    }

    @Override
    public void onEditingFinished() {

    }


}
