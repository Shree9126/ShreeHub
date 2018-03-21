package com.mindnotix.youthhub;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mindnotix.model.explore.filter_master.Course_sortby;
import com.mindnotix.model.explore.filter_master.DataExploreFilterItems;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sridharan on 2/19/2018.
 */

public class ExploreFilterActivity extends BaseActivity {


    private static final String TAG = ExploreFilterActivity.class.getSimpleName();
    protected ProgressDialog progressDialog;
    private Toolbar toolbar;
    private TextView txtjosss;
    private TextView txtReset;
    private EditText edtEventSearch;
    private EditText edtPostBy;
    private Spinner spnrSortByRating;
    protected ArrayList<Course_sortby> course_sortby;
    private static String sortByRatingID = " ";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_filter);
        UiInitialization();

        exploreFilterMaster();
    }

    private void UiInitialization() {
        this.spnrSortByRating = findViewById(R.id.spnrSortByRating);
        this.edtPostBy = findViewById(R.id.edtPostBy);
        this.edtEventSearch = findViewById(R.id.edtEventSearch);
        this.txtReset = findViewById(R.id.txtReset);
        this.txtjosss = findViewById(R.id.txtjosss);
        this.toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Explore Filter");

        } catch (Exception e) {
            e.printStackTrace();
        }

        course_sortby = new ArrayList<>();

        spnrSortByRating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sort_by_rating_id = course_sortby.get(position).getId();

                if (!sort_by_rating_id.equals("0")) {
                    sortByRatingID = sort_by_rating_id;

                } else {
                    sortByRatingID = " ";
                }


            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void exploreFilterMaster() {

        progressDialog = Utils.createProgressDialog(this);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataExploreFilterItems> call = apiService.getExploreMasterFilter(Pref.getPreToken());

        call.enqueue(new Callback<DataExploreFilterItems>() {
            @Override
            public void onResponse(Call<DataExploreFilterItems> call, Response<DataExploreFilterItems> response) {

                progressDialog.dismiss();

                try {
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body().getStatus().equals("1")) {

                            Pref.setdevicetoken("Bearer " + response.body().getToken());

                            course_sortby = response.body().getData().getCourse_sortby();

                            setSpinner();

                        } else {

                            Toast.makeText(ExploreFilterActivity.this, "No record available", Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 304) {

                        Toast.makeText(ExploreFilterActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(ExploreFilterActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(ExploreFilterActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(ExploreFilterActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(ExploreFilterActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(ExploreFilterActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(ExploreFilterActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<DataExploreFilterItems> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void setSpinner() {

        Course_sortby genders = new Course_sortby();
        genders.setId("0");
        genders.setName("All");
        course_sortby.add(genders);

        Collections.reverse(course_sortby);

        ArrayAdapter<Course_sortby> dataAdapters = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, course_sortby);
        dataAdapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnrSortByRating.setAdapter(dataAdapters);
        spnrSortByRating.setSelection(0);
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
                Toast.makeText(getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void filterSubmit() {
    }
}
