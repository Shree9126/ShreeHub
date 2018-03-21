package com.mindnotix.youthhub.cvresume.addExtraActivites;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.mindnotix.adapter.resumeAdapter.technicalSkills.TechnicalSkillsAddAdapter;
import com.mindnotix.model.resume.loadResume.Technicalskills;
import com.mindnotix.model.resume.technicalSkills.TechnicalSkillsAdd;
import com.mindnotix.model.resume.volunteer.Volunteer;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;
import com.mindnotix.youthhub.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 17-03-2018.
 */

public class TechnicalSkillsActivity extends AppCompatActivity implements View.OnClickListener {

    TextView btSave;
    TextView btCancel;
    TextView tvAddSkills;
    EditText edTechnicalSkills;
    EditText edSkills;
    EditText edLevel;
    RecyclerView recyclerTechnicalSkills;

    ProgressDialog progressDialog;
    Toolbar toolbar;
    TechnicalSkillsAdd technicalSkillsAdd;
    ArrayList<TechnicalSkillsAdd> technicalSkillsAddArrayList;
    ArrayList<Technicalskills>technicalskillsArrayList;

    TechnicalSkillsAddAdapter technicalSkillsAddAdapter;

    String technicalId="";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical_skills);

        initalizeVar();

        if(getIntent()!=null){
            if(getIntent().getStringExtra("update").equals("1")){
                edSkills.setText(getIntent().getStringExtra("title"));
                edLevel.setText(getIntent().getStringExtra("skills"));
                btSave.setText("Update Changes");
                technicalId=getIntent().getStringExtra("id");
            }

        }

    }



    private void initalizeVar() {

        btSave = (TextView) findViewById(R.id.btSave);
        edLevel = findViewById(R.id.edLevel);
        edSkills = findViewById(R.id.edSkills);
        btCancel = (TextView) findViewById(R.id.btCancel);
        tvAddSkills = (TextView) findViewById(R.id.tvAddSkills);
        recyclerTechnicalSkills =  findViewById(R.id.recyclerTechnicalSkills);
        toolbar =  findViewById(R.id.toolbar);
        technicalskillsArrayList=new ArrayList<>();
        technicalSkillsAddArrayList=new ArrayList<>();

        btSave.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        tvAddSkills.setOnClickListener(this);

        toolbarInit();



//        technicalSkillsAddAdapter = new TechnicalSkillsAddAdapter(TechnicalSkillsActivity.this,
//                technicalSkillsAddArrayList);
//        recyclerTechnicalSkills.setLayoutManager(new LinearLayoutManager(this));
//        recyclerTechnicalSkills.setAdapter(technicalSkillsAddAdapter);

        //addLayout();






    }



    private void toolbarInit() {
        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setTitle("Technical Skills");
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

            case R.id.btSave:



                validate();

                break;

            case R.id.btCancel:
                break;

            case R.id.tvAddSkills:

                //addLayout();

                break;


        }

    }

    private void addLayout() {

        TechnicalSkillsAdd technicalSkillsAdd=new TechnicalSkillsAdd();
        technicalSkillsAddArrayList.add(technicalSkillsAdd);
        technicalSkillsAddAdapter.notifyDataSetChanged();

        recyclerTechnicalSkills.post(new Runnable() {
            @Override
            public void run() {
                // Call smooth scroll
                recyclerTechnicalSkills.smoothScrollToPosition(technicalSkillsAddAdapter.getItemCount());
            }
        });



    }

    private void validate() {

        if(!edSkills.getText().toString().trim().isEmpty())
        {

            if(technicalId.equals("1")){
                UpdateTechnicalSkills();

            }else{
                addTechnicalSkills();

            }



        }else{
            Toast.makeText(this, "Please enter the technical skills", Toast.LENGTH_SHORT).show();
        }

    }


    Call<Volunteer> addvolunter;

    private void addTechnicalSkills() {


        Map<String, String> data = new HashMap<>();

        data.put("sid", Pref.getClientId());
        data.put("title", edSkills.getText().toString());
        data.put("type", "1");
        data.put("skilllevel", edLevel.getText().toString());

        Log.v("enefnfnjenfjke",new Gson().toJson(data));


        progressDialog = Utils.createProgressDialog(TechnicalSkillsActivity.this);
        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        addvolunter = apiService.addTechnicalSkills(Pref.getPreToken(), data);

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
                    }else{

                            Toast.makeText(TechnicalSkillsActivity.this, volunteer.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                } else if (response.code() == 304) {

                    Toast.makeText(TechnicalSkillsActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(TechnicalSkillsActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(TechnicalSkillsActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(TechnicalSkillsActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(TechnicalSkillsActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(TechnicalSkillsActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(TechnicalSkillsActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<Volunteer> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();

            }
        });
    }


    Call<Volunteer> updateExtraActivties;

    private void UpdateTechnicalSkills() {


        Map<String, String> data = new HashMap<>();

        data.put("id", technicalId);
        data.put("sid", Pref.getClientId());
        data.put("title", edSkills.getText().toString());
        data.put("type", "1");
        data.put("skilllevel", edLevel.getText().toString());

        Log.v("enefnfnjenfjke",new Gson().toJson(data));


        progressDialog = Utils.createProgressDialog(TechnicalSkillsActivity.this);
        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        updateExtraActivties = apiService.updateTechnicalSkills(Pref.getPreToken(), data);

        updateExtraActivties.enqueue(new Callback<Volunteer>() {
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
                    }else{

                        Toast.makeText(TechnicalSkillsActivity.this, volunteer.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                } else if (response.code() == 304) {

                    Toast.makeText(TechnicalSkillsActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(TechnicalSkillsActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(TechnicalSkillsActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(TechnicalSkillsActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(TechnicalSkillsActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(TechnicalSkillsActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(TechnicalSkillsActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<Volunteer> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();

            }
        });
    }
}
