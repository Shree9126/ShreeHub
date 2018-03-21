package com.mindnotix.youthhub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mindnotix.application.MyApplication;
import com.mindnotix.model.HandleFilterPojo;
import com.mindnotix.model.jobs.filter_master.Categoryview;
import com.mindnotix.model.jobs.filter_master.DataJobMasterFilter;
import com.mindnotix.model.jobs.filter_master.Jobtypeview;
import com.mindnotix.model.jobs.filter_master.Regional_council_list;
import com.mindnotix.model.jobs.filter_master.Salarytype;
import com.mindnotix.model.jobs.filter_master.Salarytype_anumax;
import com.mindnotix.model.jobs.filter_master.Salarytype_anumin;
import com.mindnotix.model.jobs.filter_master.Salarytype_hrmax;
import com.mindnotix.model.jobs.filter_master.Salarytype_hrmin;
import com.mindnotix.model.jobs.filter_master.Sortby;
import com.mindnotix.model.jobs.filter_master.local_board.DataLocalBoardList;
import com.mindnotix.model.jobs.filter_master.local_board.Local_board_list;
import com.mindnotix.model.jobs.filter_master.sub_category.DataJobSubCategory;
import com.mindnotix.model.jobs.filter_master.sub_category.Subcategoryview;
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

public class JobFilterActivity extends BaseActivity {

    private static final String TAG = JobFilterActivity.class.getSimpleName();

    ArrayList<Jobtypeview> jobtypeviewArrayList = new ArrayList<>();
    ArrayList<Local_board_list> local_board_listArrayList = new ArrayList<>();
    ArrayList<Subcategoryview> subcategoryviewArrayList = new ArrayList<>();
    ArrayList<Salarytype_anumax> salarytype_anumaxArrayList = new ArrayList<>();
    ArrayList<Salarytype_anumin> salarytype_anuminArrayList = new ArrayList<>();
    ArrayList<Categoryview> categoryviewArrayList = new ArrayList<>();
    ArrayList<Salarytype_hrmin> salarytype_hrminArrayList = new ArrayList<>();
    ArrayList<Salarytype_hrmax> salarytype_hrmaxArrayList = new ArrayList<>();
    ArrayList<Sortby> sortbyArrayList = new ArrayList<>();
    ArrayList<Salarytype> salarytypeArrayList = new ArrayList<>();
    ArrayList<Regional_council_list> regional_council_listArrayList = new ArrayList<>();
    ArrayAdapter<Categoryview> categoryviewArrayAdapter;
    ArrayAdapter<Jobtypeview> jobtypeviewArrayAdapter;
    ArrayAdapter<Regional_council_list> regional_council_listArrayAdapter;
    ArrayAdapter<Subcategoryview> subcategoryviewArrayAdapter;
    ArrayAdapter<Salarytype> salarytypeArrayAdapter;
    ArrayAdapter<Salarytype_anumax> salarytype_anumaxArrayAdapter;
    ArrayAdapter<Salarytype_anumin> salarytype_anuminArrayAdapter;
    ArrayAdapter<Salarytype_hrmin> salarytype_hrminArrayAdapter;
    ArrayAdapter<Salarytype_hrmax> salarytype_hrmaxArrayAdapter;
    ArrayAdapter<Sortby> sortbyArrayAdapter;
    ArrayAdapter<Local_board_list> local_board_listArrayAdapter;
    String edtSearchTxt = "";
    String spnrJobCategoryTxt_ID = "";
    String spnrJobSubCategoryTxt_ID = "";
    String spnrJobTypeTxt_ID = "";
    String spnrRegionTxt_ID = "";
    String spnrDistrictTxt_ID = "";
    String spnrSalaryRange_Type_ID = "";
    String spnrSalaryFromRange_ID = "";
    String spnrSalaryToRange_ID = "";
    String spnrSortByDate_ID = "";
    private android.support.v7.widget.Toolbar toolbar;
    private android.widget.TextView txtjosss;
    private android.widget.TextView txtReset;
    private android.widget.EditText edtJobSearch;
    private android.widget.CheckBox chkFavorite;
    private android.widget.Spinner spnrJobCategory;
    private android.widget.Spinner spnrJobSubCategory;
    private android.widget.Spinner spnrJobTypes;
    private android.widget.Spinner spnrRegionLocation;
    private android.widget.Spinner spnrDistrictCity;
    private android.widget.Spinner spnrSalaryRangeType;
    private android.widget.Spinner spnrFromSalaryRange;
    private android.widget.Spinner spnrToSalaryRange;
    private android.widget.Spinner spnrSortByDate;
    private String favorite = "";
    private String favourites = "";

    HandleFilterPojo handleFilterPojo;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_filter);

        handleFilterPojo = new HandleFilterPojo();


        UiInitialization();


        Categoryview categoryview = new Categoryview();
        categoryview.setId("0");
        categoryview.setName("All");
        categoryviewArrayList.add(0, categoryview);
        categoryviewArrayAdapter = new ArrayAdapter<Categoryview>(this,
                android.R.layout.simple_spinner_item, categoryviewArrayList);
        categoryviewArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrJobCategory.setAdapter(categoryviewArrayAdapter);


        Jobtypeview jobtypeview = new Jobtypeview();
        jobtypeview.setId("0");
        jobtypeview.setName("All");
        jobtypeviewArrayList.add(0, jobtypeview);
        jobtypeviewArrayAdapter = new ArrayAdapter<Jobtypeview>(this,
                android.R.layout.simple_spinner_item, jobtypeviewArrayList);
        jobtypeviewArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrJobTypes.setAdapter(jobtypeviewArrayAdapter);


        final Regional_council_list regional_council_list = new Regional_council_list();
        regional_council_list.setId("0");
        regional_council_list.setName("All");
        regional_council_listArrayList.add(0, regional_council_list);
        regional_council_listArrayAdapter = new ArrayAdapter<Regional_council_list>(this,
                android.R.layout.simple_spinner_item, regional_council_listArrayList);
        regional_council_listArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrRegionLocation.setAdapter(regional_council_listArrayAdapter);


        Salarytype salarytype = new Salarytype();
        salarytype.setId("0");
        salarytype.setName("All");
        salarytypeArrayList.add(0, salarytype);

        salarytypeArrayAdapter = new ArrayAdapter<Salarytype>(this,
                android.R.layout.simple_spinner_item, salarytypeArrayList);
        salarytypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrSalaryRangeType.setAdapter(salarytypeArrayAdapter);


        Salarytype_anumax salarytype_anumax = new Salarytype_anumax();
        salarytype_anumax.setJob_salary_id("0");
        salarytype_anumax.setJob_salary_amount("ALL");
        salarytype_anumaxArrayList.add(0, salarytype_anumax);

        Salarytype_anumin salarytype_anumin = new Salarytype_anumin();
        salarytype_anumin.setJob_salary_value("0");
        salarytype_anumin.setJob_salary_amount("ALL");
        salarytype_anuminArrayList.add(0, salarytype_anumin);

        Salarytype_hrmax salarytype_hrmax = new Salarytype_hrmax();
        salarytype_hrmax.setJob_salary_value("0");
        salarytype_hrmax.setJob_salary_amount("ALL");
        salarytype_hrmaxArrayList.add(0, salarytype_hrmax);

        Salarytype_hrmin salarytype_hrmin = new Salarytype_hrmin();
        salarytype_hrmin.setJob_salary_value("0");
        salarytype_hrmin.setJob_salary_amount("ALL");
        salarytype_hrminArrayList.add(0, salarytype_hrmin);




        spnrSalaryRangeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here


                try {

                    if (salarytypeArrayList.get(position).getId().equals("1")) {


                        salarytype_anumaxArrayAdapter = new ArrayAdapter<Salarytype_anumax>(JobFilterActivity.this,
                                android.R.layout.simple_spinner_item, salarytype_anumaxArrayList);
                        salarytype_anumaxArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnrToSalaryRange.setAdapter(salarytype_anumaxArrayAdapter);
                        //spnrToSalaryRange.setSelection(salarytype_anumaxArrayList.size() - 1);


                        salarytype_anuminArrayAdapter = new ArrayAdapter<Salarytype_anumin>(JobFilterActivity.this,
                                android.R.layout.simple_spinner_item, salarytype_anuminArrayList);
                        salarytype_anuminArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnrFromSalaryRange.setAdapter(salarytype_anuminArrayAdapter);
                        //  spnrFromSalaryRange.setSelection(0);

                    } else {


                        salarytype_hrmaxArrayAdapter = new ArrayAdapter<Salarytype_hrmax>(JobFilterActivity.this,
                                android.R.layout.simple_spinner_item, salarytype_hrmaxArrayList);
                        salarytype_hrmaxArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnrToSalaryRange.setAdapter(salarytype_hrmaxArrayAdapter);
                        // spnrToSalaryRange.setSelection(salarytype_hrmaxArrayList.size() - 1);


                        salarytype_hrminArrayAdapter = new ArrayAdapter<Salarytype_hrmin>(JobFilterActivity.this,
                                android.R.layout.simple_spinner_item, salarytype_hrminArrayList);
                        salarytype_hrminArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnrFromSalaryRange.setAdapter(salarytype_hrminArrayAdapter);
                        // spnrFromSalaryRange.setSelection(0);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });



        spnrFromSalaryRange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!categoryviewArrayList.get(position).getId().equals("0")) {
                    getSubCategory(categoryviewArrayList.get(position).getId());
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spnrToSalaryRange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!categoryviewArrayList.get(position).getId().equals("0")) {
                    getSubCategory(categoryviewArrayList.get(position).getId());
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        chkFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    favourites = "1";
                } else {
                    favourites = "0";
                }
            }
        });

        Sortby sortby = new Sortby();
        sortby.setId("0");
        sortby.setName("All");
        sortbyArrayList.add(0, sortby);
        sortbyArrayAdapter = new ArrayAdapter<Sortby>(this,
                android.R.layout.simple_spinner_item, sortbyArrayList);
        sortbyArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrSortByDate.setAdapter(sortbyArrayAdapter);


        spnrJobCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!categoryviewArrayList.get(position).getId().equals("0")) {
                    getSubCategory(categoryviewArrayList.get(position).getId());
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnrRegionLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!regional_council_listArrayList.get(position).getId().equals("0")) {
                    getLocalBoardList(regional_council_listArrayList.get(position).getId());
                } else {

                    Local_board_list sortby = new Local_board_list();
                    sortby.setId("0");
                    sortby.setName("All");
                    local_board_listArrayList.add(0, sortby);
                    local_board_listArrayAdapter = new ArrayAdapter<Local_board_list>(JobFilterActivity.this,
                            android.R.layout.simple_spinner_item, local_board_listArrayList);
                    local_board_listArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnrDistrictCity.setAdapter(local_board_listArrayAdapter);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        txtReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spnrSortByDate.setSelection(0);
                spnrToSalaryRange.setSelection(0);
                spnrFromSalaryRange.setSelection(0);
                spnrSalaryRangeType.setSelection(0);
                spnrDistrictCity.setSelection(0);
                spnrRegionLocation.setSelection(0);
                spnrJobTypes.setSelection(0);
                spnrJobSubCategory.setSelection(0);
                spnrJobCategory.setSelection(0);
                edtJobSearch.setText("");
                chkFavorite.setChecked(false);


                MyApplication.handleFilterPojo.setJopCatagoreyPosition("0");
                MyApplication.handleFilterPojo.setEdtJobSearch("");
                MyApplication.handleFilterPojo.setChkfav(false);
                MyApplication.handleFilterPojo.setSpsubcategories("0");
                MyApplication.handleFilterPojo.setSpregion("0");
                MyApplication.handleFilterPojo.setSpdistrict("0");
                MyApplication.handleFilterPojo.setSpjobtype("0");
                MyApplication.handleFilterPojo.setSpfrom("0");
                MyApplication.handleFilterPojo.setSpsalary("0");
                MyApplication.handleFilterPojo.setSpto("0");
                MyApplication.handleFilterPojo.setSpsort("0");

            }
        });
        Log.d(TAG, "onCreate: ");
        getMasterData();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_job_filter, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_submit:
                filterSubmit();
                //   Toast.makeText(getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG).show();
                return true;

         /*   case R.id.txtReset:
                filterSubmit();
                Toast.makeText(getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG).show();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void filterSubmit() {

        Log.d(TAG, "filterSubmit:spnrJobCategory "+String.valueOf(spnrJobCategory.getSelectedItemPosition()));
        MyApplication.handleFilterPojo.setEdtJobSearch(edtJobSearch.getText().toString().trim());
        MyApplication.handleFilterPojo.setJopCatagoreyPosition(String.valueOf(spnrJobCategory.getSelectedItemPosition()));
        MyApplication.handleFilterPojo.setSpdistrict(String.valueOf(spnrDistrictCity.getSelectedItemPosition()));
        MyApplication.handleFilterPojo.setSpsort(String.valueOf(spnrSortByDate.getSelectedItemPosition()));
        MyApplication.handleFilterPojo.setSpto(String.valueOf(spnrToSalaryRange.getSelectedItemPosition()));
        MyApplication.handleFilterPojo.setSpfrom(String.valueOf(spnrFromSalaryRange.getSelectedItemPosition()));
        MyApplication.handleFilterPojo.setSpsalary(String.valueOf(spnrSalaryRangeType.getSelectedItemPosition()));
        MyApplication.handleFilterPojo.setSpregion(String.valueOf(spnrRegionLocation.getSelectedItemPosition()));
        MyApplication.handleFilterPojo.setSpsubcategories(String.valueOf(spnrJobSubCategory.getSelectedItemPosition()));
        MyApplication.handleFilterPojo.setSpjobtype(String.valueOf(spnrJobTypes.getSelectedItemPosition()));

        if (favourites.equals("1"))
            MyApplication.handleFilterPojo.setChkfav(true);
        else
            MyApplication.handleFilterPojo.setChkfav(false);


        handleFilterPojo.setEdtJobSearch(edtSearchTxt);

        edtSearchTxt = edtJobSearch.getText().toString().trim();
        if (!categoryviewArrayList.get(spnrJobCategory.getSelectedItemPosition()).getName().equals("All"))
            spnrJobCategoryTxt_ID = categoryviewArrayList.get(spnrJobCategory.getSelectedItemPosition()).getId();

        if (subcategoryviewArrayList.size() > 0) {

            if (!subcategoryviewArrayList.get(spnrJobSubCategory.getSelectedItemPosition()).getName().equals("All"))
                spnrJobSubCategoryTxt_ID = subcategoryviewArrayList.get(spnrJobSubCategory.getSelectedItemPosition()).getId();
        }


        if (!jobtypeviewArrayList.get(spnrJobTypes.getSelectedItemPosition()).getName().equals("All"))
            spnrJobTypeTxt_ID = jobtypeviewArrayList.get(spnrJobTypes.getSelectedItemPosition()).getId();

        if (!regional_council_listArrayList.get(spnrRegionLocation.getSelectedItemPosition()).getName().equals("All"))
            spnrRegionTxt_ID = regional_council_listArrayList.get(spnrRegionLocation.getSelectedItemPosition()).getId();

        if (local_board_listArrayList.size() > 0) {
            if (!local_board_listArrayList.get(spnrDistrictCity.getSelectedItemPosition()).getName().equals("All"))
                spnrDistrictTxt_ID = local_board_listArrayList.get(spnrDistrictCity.getSelectedItemPosition()).getId();
        }


        if (!salarytypeArrayList.get(spnrSalaryRangeType.getSelectedItemPosition()).getName().equals("All"))
            spnrSalaryRange_Type_ID = salarytypeArrayList.get(spnrSalaryRangeType.getSelectedItemPosition()).getId();

        if (spnrSalaryRange_Type_ID.equals("1")) {
            if (!salarytype_anuminArrayList.get(spnrFromSalaryRange.getSelectedItemPosition()).getJob_salary_amount().equals("All"))
                spnrSalaryFromRange_ID = salarytype_anuminArrayList.get(spnrFromSalaryRange.getSelectedItemPosition()).getJob_salary_value();

            if (!salarytype_anumaxArrayList.get(spnrToSalaryRange.getSelectedItemPosition()).getJob_salary_amount().equals("All"))
                spnrSalaryToRange_ID = salarytype_anumaxArrayList.get(spnrToSalaryRange.getSelectedItemPosition()).getJob_salary_id();

        } else if (spnrSalaryRange_Type_ID.equals("2")) {

            if (!salarytype_hrminArrayList.get(spnrFromSalaryRange.getSelectedItemPosition()).getJob_salary_amount().equals("All"))
                spnrSalaryFromRange_ID = salarytype_hrminArrayList.get(spnrFromSalaryRange.getSelectedItemPosition()).getJob_salary_value();

            if (!salarytype_hrmaxArrayList.get(spnrToSalaryRange.getSelectedItemPosition()).getJob_salary_amount().equals("All"))
                spnrSalaryToRange_ID = salarytype_hrmaxArrayList.get(spnrToSalaryRange.getSelectedItemPosition()).getJob_salary_value();
        }

        if (!sortbyArrayList.get(spnrSortByDate.getSelectedItemPosition()).getName().equals("All"))
            spnrSortByDate_ID = sortbyArrayList.get(spnrSortByDate.getSelectedItemPosition()).getId();


        if (chkFavorite.isChecked()) {
            favourites = "1";
        } else {
            favourites = "0";
        }

        Intent intent = new Intent();
        intent.putExtra("edtSearchTxt", edtSearchTxt);
        intent.putExtra("spnrJobCategoryTxt_ID", spnrJobCategoryTxt_ID);
        intent.putExtra("spnrJobSubCategoryTxt_ID", spnrJobSubCategoryTxt_ID);
        intent.putExtra("spnrJobTypeTxt_ID", spnrJobTypeTxt_ID);
        intent.putExtra("spnrRegionTxt_ID", spnrRegionTxt_ID);
        intent.putExtra("spnrDistrictTxt_ID", spnrDistrictTxt_ID);
        intent.putExtra("spnrSalaryRange_Type_ID", spnrSalaryRange_Type_ID);
        intent.putExtra("spnrSalaryFromRange_ID", spnrSalaryFromRange_ID);
        intent.putExtra("spnrSalaryToRange_ID", spnrSalaryToRange_ID);
        intent.putExtra("spnrSortByDate_ID", spnrSortByDate_ID);
        if (!favourites.equals("0"))
            intent.putExtra("favourites", favourites);
        else
            intent.putExtra("favourites", "");

        setResult(2, intent);
        finish();//finishing activity
    }

    private void getLocalBoardList(String name) {
        local_board_listArrayList.clear();

        Log.d(TAG, "getLocalBoardList: " + name);
        progressDialog = Utils.createProgressDialog(this);
        //     String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTU3IiwidXNlcm5hbWUiOiJzcmlkaGFyYW4ubWluZG5vdGl4QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTg2MDkwNTAsImp0aSI6IlFYVkdUVEJ3V0dOMlNGWjBkVWxVV1hKT2JEWT0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE4NjA5MDUwLCJleHAiOjE1MTkyMTM4NTB9.-1YP2zxdrvMmlc2mfH6CXyCUxdqRObTFI6GSUByJ84I";

        String token = Pref.getPreToken();


        Map<String, String> data = new HashMap<>();
        data.put("regional_id", name);
        //   data.put("is_user_id", "0");
        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


        //token = "Bearer "+ Pref.getPreToken();


        Log.d(TAG, "changePassword: token " + token);

        Call<DataLocalBoardList> call = apiService.getLocalBoardList(token, data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataLocalBoardList>() {
            @Override
            public void onResponse(Call<DataLocalBoardList> call, Response<DataLocalBoardList> response) {

                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("1")) {
                        local_board_listArrayList.addAll(response.body().getData().getLocal_board_list());

                        Local_board_list sortby = new Local_board_list();
                        sortby.setId("0");
                        sortby.setName("All");
                        local_board_listArrayList.add(0, sortby);
                        local_board_listArrayAdapter = new ArrayAdapter<Local_board_list>(JobFilterActivity.this,
                                android.R.layout.simple_spinner_item, local_board_listArrayList);
                        local_board_listArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnrDistrictCity.setAdapter(local_board_listArrayAdapter);

                        spnrDistrictCity.setSelection(Integer.parseInt(MyApplication.handleFilterPojo.getSpdistrict()));


                    } else {

                        Toast.makeText(JobFilterActivity.this, "No record available", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 304) {

                    Toast.makeText(JobFilterActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(JobFilterActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(JobFilterActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(JobFilterActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(JobFilterActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(JobFilterActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(JobFilterActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<DataLocalBoardList> call, Throwable t) {
                  progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void getSubCategory(String id) {

        Log.d(TAG, "getSubCategory: id " + id);
         progressDialog = Utils.createProgressDialog(this);
//        String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTU3IiwidXNlcm5hbWUiOiJzcmlkaGFyYW4ubWluZG5vdGl4QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTg2MDkwNTAsImp0aSI6IlFYVkdUVEJ3V0dOMlNGWjBkVWxVV1hKT2JEWT0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE4NjA5MDUwLCJleHAiOjE1MTkyMTM4NTB9.-1YP2zxdrvMmlc2mfH6CXyCUxdqRObTFI6GSUByJ84I";

        String token = Pref.getPreToken();


        Map<String, String> data = new HashMap<>();
        data.put("category", "20");

        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


        //token = "Bearer "+ Pref.getPreToken();


        Log.d(TAG, "changePassword: token " + token);

        Call<DataJobSubCategory> call = apiService.getSubCategory(token, data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataJobSubCategory>() {
            @Override
            public void onResponse(Call<DataJobSubCategory> call, Response<DataJobSubCategory> response) {

                if (progressDialog.isShowing()) {
                   progressDialog.dismiss();
                }

                if (response.code() == 200) {
                    if (response.body().getStatus().equals("1")) {

                        subcategoryviewArrayList.addAll(response.body().getData().getSubcategoryview());

                        Subcategoryview subcategoryview = new Subcategoryview();
                        subcategoryview.setId("0");
                        subcategoryview.setName("All");
                        subcategoryviewArrayList.add(0, subcategoryview);
                        subcategoryviewArrayAdapter = new ArrayAdapter<Subcategoryview>(JobFilterActivity.this,
                                android.R.layout.simple_spinner_item, subcategoryviewArrayList);
                        subcategoryviewArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnrJobSubCategory.setAdapter(subcategoryviewArrayAdapter);

                        spnrJobSubCategory.setSelection(Integer.parseInt(MyApplication.handleFilterPojo.getSpsubcategories()));


                    } else {
                        // progressDialog.dismiss();
                        Toast.makeText(JobFilterActivity.this, "No record available", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 304) {

                    Toast.makeText(JobFilterActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(JobFilterActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(JobFilterActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(JobFilterActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(JobFilterActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(JobFilterActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(JobFilterActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<DataJobSubCategory> call, Throwable t) {
                  progressDialog.dismiss();
                t.printStackTrace();
            }
        });


    }

    private void UiInitialization() {

        this.spnrSortByDate = (Spinner) findViewById(R.id.spnrSortByDate);
        this.spnrToSalaryRange = (Spinner) findViewById(R.id.spnrToSalaryRange);
        this.spnrFromSalaryRange = (Spinner) findViewById(R.id.spnrFromSalaryRange);
        this.spnrSalaryRangeType = (Spinner) findViewById(R.id.spnrSalaryRangeType);
        this.spnrDistrictCity = (Spinner) findViewById(R.id.spnrDistrictCity);
        this.spnrRegionLocation = (Spinner) findViewById(R.id.spnrRegionLocation);
        this.spnrJobTypes = (Spinner) findViewById(R.id.spnrJobTypes);
        this.spnrJobSubCategory = (Spinner) findViewById(R.id.spnrJobSubCategory);
        this.spnrJobCategory = (Spinner) findViewById(R.id.spnrJobCategory);
        this.edtJobSearch = (EditText) findViewById(R.id.edtJobSearch);
        this.txtjosss = (TextView) findViewById(R.id.txtjosss);
        this.txtReset = (TextView) findViewById(R.id.txtReset);
        this.chkFavorite = (CheckBox) findViewById(R.id.chkFavorite);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Job Filter");

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

    private void getMasterData() {


          progressDialog = Utils.createProgressDialog(this);
        //  String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTU3IiwidXNlcm5hbWUiOiJzcmlkaGFyYW4ubWluZG5vdGl4QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTg2MDkwNTAsImp0aSI6IlFYVkdUVEJ3V0dOMlNGWjBkVWxVV1hKT2JEWT0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE4NjA5MDUwLCJleHAiOjE1MTkyMTM4NTB9.-1YP2zxdrvMmlc2mfH6CXyCUxdqRObTFI6GSUByJ84I";

        String token = Pref.getPreToken();


        Map<String, String> data = new HashMap<>();
        data.put("profileid", "14557");
        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


        //token = "Bearer "+ Pref.getPreToken();


        Log.d(TAG, "changePassword: token " + token);

        Call<DataJobMasterFilter> call = apiService.getJobMasterFilter(token);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataJobMasterFilter>() {
            @Override
            public void onResponse(Call<DataJobMasterFilter> call, Response<DataJobMasterFilter> response) {

                 progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("1")) {


                        jobtypeviewArrayList.addAll(response.body().getData().getJobtypeview());
                        salarytype_anumaxArrayList.addAll(response.body().getData().getSalarytype_anumax());
                        salarytype_anuminArrayList.addAll(response.body().getData().getSalarytype_anumin());
                        categoryviewArrayList.addAll(response.body().getData().getCategoryview());
                        salarytype_hrminArrayList.addAll(response.body().getData().getSalarytype_hrmin());
                        salarytypeArrayList.addAll(response.body().getData().getSalarytype());
                        salarytype_hrmaxArrayList.addAll(response.body().getData().getSalarytype_hrmax());
                        sortbyArrayList.addAll(response.body().getData().getSortby());
                        regional_council_listArrayList.addAll(response.body().getData().getRegional_council_list());
                        categoryviewArrayAdapter.notifyDataSetChanged();
                        jobtypeviewArrayAdapter.notifyDataSetChanged();
                        regional_council_listArrayAdapter.notifyDataSetChanged();
                        salarytypeArrayAdapter.notifyDataSetChanged();

                        if (salarytype_anumaxArrayAdapter != null)
                            salarytype_anumaxArrayAdapter.notifyDataSetChanged();
                        if (salarytype_anuminArrayAdapter != null)
                            salarytype_anuminArrayAdapter.notifyDataSetChanged();
                        if (salarytype_hrminArrayAdapter != null)
                            salarytype_hrminArrayAdapter.notifyDataSetChanged();
                        if (salarytype_hrmaxArrayAdapter != null)
                            salarytype_hrmaxArrayAdapter.notifyDataSetChanged();

                        sortbyArrayAdapter.notifyDataSetChanged();

                        edtJobSearch.setText(MyApplication.handleFilterPojo.getEdtJobSearch());
                        spnrJobCategory.setSelection(Integer.parseInt(MyApplication.handleFilterPojo.getJopCatagoreyPosition()));

                        spnrFromSalaryRange.setSelection(Integer.parseInt(MyApplication.handleFilterPojo.getSpfrom()));
                        spnrJobTypes.setSelection(Integer.parseInt(MyApplication.handleFilterPojo.getSpjobtype()));
                        spnrSalaryRangeType.setSelection(Integer.parseInt(MyApplication.handleFilterPojo.getSpsalary()));
                        spnrSortByDate.setSelection(Integer.parseInt(MyApplication.handleFilterPojo.getSpsort()));
                        spnrToSalaryRange.setSelection(Integer.parseInt(MyApplication.handleFilterPojo.getSpto()));

                        spnrRegionLocation.setSelection(Integer.parseInt(MyApplication.handleFilterPojo.getSpregion()));
                        chkFavorite.setChecked(MyApplication.handleFilterPojo.isChkfav());



                    } else {

                        Toast.makeText(JobFilterActivity.this, "No record available", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 304) {

                    Toast.makeText(JobFilterActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(JobFilterActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(JobFilterActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(JobFilterActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(JobFilterActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(JobFilterActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(JobFilterActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<DataJobMasterFilter> call, Throwable t) {
                 progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }
}
