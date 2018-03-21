package com.mindnotix.youthhub.find_connection;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindnotix.adapter.find_connection.FindDistrictLocalBoardAdapter;
import com.mindnotix.adapter.find_connection.FindRegionalAdapter;
import com.mindnotix.adapter.find_connection.FindWishlistadapter;
import com.mindnotix.model.find_connection.master.DataFindConnectionMaster;
import com.mindnotix.model.find_connection.master.Local_boards;
import com.mindnotix.model.find_connection.master.Regionals;
import com.mindnotix.model.find_connection.master.Wishlist;
import com.mindnotix.model.jobs.filter_master.local_board.DataLocalBoardList;
import com.mindnotix.model.jobs.filter_master.local_board.Local_board_list;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;
import com.mindnotix.youthhub.BaseActivity;
import com.mindnotix.youthhub.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 3/2/2018.
 */

public class FindConnectYouthFilter extends BaseActivity implements View.OnClickListener {

    private static final String TAG = FindConnectYouthFilter.class.getSimpleName();
    public static ArrayList<Local_boards> local_boardsArrayList;
    public static ArrayList<Local_board_list> local_board_listArrayList;
    public static ArrayList<Wishlist> wishlistArrayList;
    public static ArrayList<Regionals> regionalsArrayList;
    public ArrayList<Regionals> regionalsArrayListx;
    ArrayAdapter<Local_boards> local_board_listArrayAdapter;
    private Toolbar toolbar;
    private TextView txtjosss;
    private TextView txtReset;
    private EditText edtJobSearch;
    private TextView txtspnrRegion;
    private LinearLayout linearSpnrRegion;
    private TextView txtspnrDistrict;
    private LinearLayout linearSpnrDistrict;
    private TextView txtspnrWishlist;
    private LinearLayout linearSpnrWishlist;
    private ProgressDialog progressDialog;
    private String display_checked_job_wishlist = "";
    private String display_checked_job_wishlist_id = "";
    private String display_checked_regionals = "";
    private String display_checked_regionals_ids = "";
    private String display_checked_districts = "";
    private String display_checked_districts_ids = "";
    private String edtSearchTxt = "";
    private String regional_ids = "";
    private String district_ids = "";
    private String wishlist_ids = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_connection_youth_filter);


        if (getIntent() != null) {
            edtSearchTxt = getIntent().getStringExtra("edtSearchTxt");
            regional_ids = getIntent().getStringExtra("regional_ids");
            display_checked_regionals_ids = getIntent().getStringExtra("regional_ids");
            district_ids = getIntent().getStringExtra("district_ids");
            display_checked_districts_ids = getIntent().getStringExtra("district_ids");
            wishlist_ids = getIntent().getStringExtra("wishlist_ids");
            display_checked_job_wishlist_id = getIntent().getStringExtra("wishlist_ids");

            Log.d(TAG, "onCreate:edtSearchTxt " + edtSearchTxt);
            Log.d(TAG, "onCreate:regional_ids " + regional_ids);
            Log.d(TAG, "onCreate:district_ids " + district_ids);
            Log.d(TAG, "onCreate:wishlist_ids " + wishlist_ids);
        }
        local_board_listArrayList = new ArrayList<>();
        UiInitialization();


        getMasterData();
        edtJobSearch.setText(edtSearchTxt);


    }


    public int getIndexRegional(String id, ArrayList<Regionals> regionalsArrayListsss) {
        for (int i = 0; i < regionalsArrayListsss.size(); i++) {
            Regionals auction = regionalsArrayListsss.get(i);
            if (id.equals(auction.getId())) {
                return i;
            }
        }

        return -1;
    }

    public int getIndexDistrict(String id, ArrayList<Local_board_list> regionalsArrayListsss) {
        for (int i = 0; i < regionalsArrayListsss.size(); i++) {
            Local_board_list auction = regionalsArrayListsss.get(i);
            if (id.equals(auction.getId())) {
                return i;
            }
        }

        return -1;
    }

    public int getIndexWishList(String id, ArrayList<Wishlist> regionalsArrayListsss) {
        for (int i = 0; i < regionalsArrayListsss.size(); i++) {
            Wishlist auction = regionalsArrayListsss.get(i);
            if (id.equals(auction.getId())) {
                return i;
            }
        }

        return -1;
    }

    private void UiInitialization() {

        this.linearSpnrWishlist = (LinearLayout) findViewById(R.id.linearSpnrWishlist);
        this.linearSpnrWishlist.setOnClickListener(this);
        this.txtspnrWishlist = (TextView) findViewById(R.id.txt_spnrWishlist);

        this.linearSpnrDistrict = (LinearLayout) findViewById(R.id.linearSpnrDistrict);
        this.linearSpnrDistrict.setOnClickListener(this);
        this.txtspnrDistrict = (TextView) findViewById(R.id.txt_spnrDistrict);

        this.linearSpnrRegion = (LinearLayout) findViewById(R.id.linearSpnrRegion);
        this.linearSpnrRegion.setOnClickListener(this);
        this.txtspnrRegion = (TextView) findViewById(R.id.txt_spnrRegion);

        this.edtJobSearch = (EditText) findViewById(R.id.edtJobSearch);
        this.txtReset = (TextView) findViewById(R.id.txtReset);
        this.txtReset.setOnClickListener(this);
        this.txtjosss = (TextView) findViewById(R.id.txtjosss);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Youth");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearSpnrWishlist:
                showWishListDialog();
                break;
            case R.id.linearSpnrDistrict:
                //showDistrictDialog();
                showDistrictDialogSSS();
                break;
            case R.id.linearSpnrRegion:
                showRegionalDialog();
                break;
            case R.id.txtReset:

                display_checked_job_wishlist_id = "";
                display_checked_job_wishlist = "";
                display_checked_regionals_ids = "";
                display_checked_regionals = "";
                display_checked_districts_ids = "";
                display_checked_districts = "";

                edtSearchTxt = "";
                regional_ids = "";
                district_ids = "";
                wishlist_ids = "";

                edtJobSearch.setText("");
                txtspnrWishlist.setText("All");
                txtspnrDistrict.setText("All");
                txtspnrRegion.setText("All");

                getMasterData();

                break;
        }
    }

    private void getMasterData() {

        regionalsArrayList = new ArrayList<>();
        wishlistArrayList = new ArrayList<>();
        local_boardsArrayList = new ArrayList<>();

        progressDialog = Utils.createProgressDialog(this);
        String token = Pref.getPreToken();


        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


        Log.d(TAG, "getMasterData: token " + token);

        Call<DataFindConnectionMaster> call = apiService.getFindConnectionMasterData(token);
        Log.d(TAG, "DataFindConnectionMaster: url " + call.request().url());

        call.enqueue(new Callback<DataFindConnectionMaster>() {
            @Override
            public void onResponse(Call<DataFindConnectionMaster> call, Response<DataFindConnectionMaster> response) {

                try {

                    progressDialog.dismiss();
                    if (response.code() == 200) {
                        if (response.body().getStatus().equals("1")) {

                            local_boardsArrayList.addAll(response.body().getData().getLocal_boards());

                            wishlistArrayList.addAll(response.body().getData().getWishlist());

                            regionalsArrayList.addAll(response.body().getData().getRegionals());


                            if (regional_ids.length() != 0) {
                                String[] regionals_idss = regional_ids.split(",");
                                String display_checked_days = "";
                                String display_checked_id = "";
                                for (int i = 0; i < regionals_idss.length; i++) {

                                    int pos = getIndexRegional(regionals_idss[i], regionalsArrayList);

                                    Log.d(TAG, "onCreate: position " + pos);
                                    Log.d(TAG, "onCreate: regionalsArrayList " + regionalsArrayList.size());

                                    regionalsArrayList.get(pos).setStatus("1");

                                    display_checked_days = display_checked_days + "," + regionalsArrayList.get(pos).getName();
                                    display_checked_id = display_checked_id + "," + regionalsArrayList.get(pos).getId();


                                }

                                if (display_checked_days.startsWith(","))
                                    display_checked_days = display_checked_days.substring(1, display_checked_days.length());

                                if (display_checked_id.startsWith(","))
                                    display_checked_id = display_checked_id.substring(1, display_checked_id.length());
                                getLocalBoardList(display_checked_id);

                                if (display_checked_days.length() != 0)
                                    txtspnrRegion.setText(display_checked_days);
                                else
                                    txtspnrRegion.setText("All");

                            }

                            if (wishlist_ids.length() != 0) {
                                String[] regionals_idss = wishlist_ids.split(",");
                                String display_checked_days = "";
                                String display_checked_id = "";
                                for (int i = 0; i < regionals_idss.length; i++) {

                                    int pos = getIndexWishList(regionals_idss[i], wishlistArrayList);

                                    Log.d(TAG, "onCreate: position " + pos);
                                    Log.d(TAG, "onCreate: regionalsArrayList " + wishlistArrayList.size());

                                    wishlistArrayList.get(pos).setStatus(true);

                                    display_checked_days = display_checked_days + "," + wishlistArrayList.get(pos).getName();
                                    display_checked_id = display_checked_id + "," + wishlistArrayList.get(pos).getId();

                                }

                                if (display_checked_days.startsWith(","))
                                    display_checked_days = display_checked_days.substring(1, display_checked_days.length());

                                if (display_checked_days.length() != 0)
                                    txtspnrWishlist.setText(display_checked_days);
                                else
                                    txtspnrWishlist.setText("All");

                            }

                        } else {

                            Toast.makeText(FindConnectYouthFilter.this, "No record available", Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 304) {

                        Toast.makeText(FindConnectYouthFilter.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(FindConnectYouthFilter.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(FindConnectYouthFilter.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(FindConnectYouthFilter.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(FindConnectYouthFilter.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(FindConnectYouthFilter.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(FindConnectYouthFilter.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }


                } catch (Exception e) {

                }


            }

            @Override
            public void onFailure(Call<DataFindConnectionMaster> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });


    }

    private void getLocalBoardList(String name) {
        local_board_listArrayList = new ArrayList<>();

        Log.d(TAG, "getLocalBoardList: " + name);

        if (!progressDialog.isShowing())
            progressDialog = Utils.createProgressDialog(this);
        //     String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTU3IiwidXNlcm5hbWUiOiJzcmlkaGFyYW4ubWluZG5vdGl4QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTg2MDkwNTAsImp0aSI6IlFYVkdUVEJ3V0dOMlNGWjBkVWxVV1hKT2JEWT0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE4NjA5MDUwLCJleHAiOjE1MTkyMTM4NTB9.-1YP2zxdrvMmlc2mfH6CXyCUxdqRObTFI6GSUByJ84I";

        String token = Pref.getPreToken();


        Map<String, String> data = new HashMap<>();
        data.put("regional_id", name);
        data.put("is_multi", "1");
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

                try {


                    progressDialog.dismiss();
                    if (response.code() == 200) {
                        if (response.body().getStatus().equals("1")) {
                            local_board_listArrayList.addAll(response.body().getData().getLocal_board_list());


                            if (district_ids.length() != 0) {


                                String[] regionals_idss = district_ids.split(",");
                                String display_checked_days = "";
                                String display_checked_id = "";
                                for (int i = 0; i < regionals_idss.length; i++) {

                                    int pos = getIndexDistrict(regionals_idss[i], local_board_listArrayList);

                                    Log.d(TAG, "onCreate: position " + pos);
                                    Log.d(TAG, "onCreate: regionalsArrayList " + local_board_listArrayList.size());

                                    local_board_listArrayList.get(pos).setStatus_chk("1");

                                    display_checked_days = display_checked_days + "," + local_board_listArrayList.get(pos).getName();
                                    display_checked_id = display_checked_id + "," + local_board_listArrayList.get(pos).getId();


                                }

                                Log.d(TAG, "onResponse: display_checked_days localboard " + display_checked_days);
                                if (display_checked_days.startsWith(","))
                                    display_checked_days = display_checked_days.substring(1, display_checked_days.length());

                                if (display_checked_days.length() != 0)
                                    txtspnrDistrict.setText(display_checked_days);
                                else
                                    txtspnrDistrict.setText("All");

                            }

                        } else {

                            Toast.makeText(FindConnectYouthFilter.this, "No record available", Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 304) {

                        Toast.makeText(FindConnectYouthFilter.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(FindConnectYouthFilter.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(FindConnectYouthFilter.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(FindConnectYouthFilter.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(FindConnectYouthFilter.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(FindConnectYouthFilter.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(FindConnectYouthFilter.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }


                } catch (Exception e) {
                    progressDialog.dismiss();
                }


            }

            @Override
            public void onFailure(Call<DataLocalBoardList> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
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
                FilterSubmit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void FilterSubmit() {

        Log.d(TAG, "FilterSubmit: edtJobSearch " + edtJobSearch.getText().toString());
        Intent intent = new Intent();
        intent.putExtra("edtSearchTxt", edtJobSearch.getText().toString());
        intent.putExtra("regional_ids", display_checked_regionals_ids);
        intent.putExtra("district_ids", display_checked_districts_ids);
        intent.putExtra("wishlist_ids", display_checked_job_wishlist_id);
        setResult(2, intent);
        finish();//finishing activity
    }


    private void showWishListDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog_list, null);
        dialogBuilder.setView(dialogView);
        final ArrayList<Wishlist> wishlistArrayTempList = new ArrayList<>();

        final EditText filterText = (EditText) dialogView.findViewById(R.id.edit1);
        final RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.jobWishList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final FindWishlistadapter alertdialogAdapter = new FindWishlistadapter(wishlistArrayList);
        recyclerView.setAdapter(alertdialogAdapter);
        dialogBuilder.setTitle("Wishlist");

        filterText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                alertdialogAdapter.search(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();

                String display_checked_days = "";
                String display_checked_id = "";


                for (Wishlist wishlist : wishlistArrayList) {

                    if (wishlist.isStatus()) {
                        display_checked_days = display_checked_days + "," + wishlist.getName();
                        display_checked_id = display_checked_id + "," + wishlist.getId();
                        Log.d(TAG, "onClick: " + display_checked_id);
                        Log.d(TAG, "onClick: " + display_checked_days);
                        if (display_checked_days.startsWith(","))
                            display_checked_days = display_checked_days.substring(1, display_checked_days.length());

                        if (display_checked_id.startsWith(","))
                            display_checked_id = display_checked_id.substring(1, display_checked_id.length());
                        Log.d(TAG, "onClick:s " + display_checked_days);
                        Log.d(TAG, "onClick:s " + display_checked_id);

                    } else {
                        wishlist.setStatus(false);
                        alertdialogAdapter.notifyDataSetChanged();
                    }

                }
                display_checked_job_wishlist_id = display_checked_id;
                display_checked_job_wishlist = display_checked_days;

                if (display_checked_job_wishlist.length() != 0)
                    txtspnrWishlist.setText(display_checked_job_wishlist);
                else
                    txtspnrWishlist.setText("");
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


    private void showDistrictDialogSSS() {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog_list, null);
        dialogBuilder.setView(dialogView);
        final ArrayList<Local_board_list> wishlistArrayTempList = new ArrayList<>();

        final EditText filterText = (EditText) dialogView.findViewById(R.id.edit1);
        final RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.jobWishList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final FindDistrictLocalBoardAdapter alertdialogAdapter = new FindDistrictLocalBoardAdapter(local_board_listArrayList, this);
        recyclerView.setAdapter(alertdialogAdapter);
        dialogBuilder.setTitle("District");

        filterText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                alertdialogAdapter.search(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                //ArrayList<Local_board_list> local_board_listArrayList = alertdialogAdapter.getModifyDistrictList();
                String display_checked_days = "";
                String display_checked_id = "";
                for (int i = 0; i < local_board_listArrayList.size(); i++) {
                    if (local_board_listArrayList.get(i).getStatus_chk().equals("1")) {
                        display_checked_days = display_checked_days + "," + local_board_listArrayList.get(i).getName();
                        display_checked_id = display_checked_id + "," + local_board_listArrayList.get(i).getId();
                        Log.d(TAG, "onClick: " + display_checked_id);
                        Log.d(TAG, "onClick: " + display_checked_days);
                        display_checked_districts = display_checked_days.substring(1, display_checked_days.length());
                        display_checked_districts_ids = display_checked_id.substring(1, display_checked_id.length());
                        Log.d(TAG, "onClick:s " + display_checked_districts);
                        Log.d(TAG, "onClick:s " + display_checked_districts_ids);
                    }
                }

                if (local_board_listArrayList.size() != 0)
                    txtspnrDistrict.setText(display_checked_districts);
                else
                    txtspnrDistrict.setText("All");

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

    public void showRegionalDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog_list, null);
        dialogBuilder.setView(dialogView);
        final ArrayList<Regionals> wishlistArrayTempList = new ArrayList<>();

        final EditText filterText = (EditText) dialogView.findViewById(R.id.edit1);
        final RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.jobWishList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final FindRegionalAdapter alertdialogAdapter = new FindRegionalAdapter(regionalsArrayList, this);
        recyclerView.setAdapter(alertdialogAdapter);
        dialogBuilder.setTitle("Region");

        filterText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                alertdialogAdapter.search(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();

                //   ArrayList<Regionals> regionalsArrayList = alertdialogAdapter.getModifyRegionalList();

                String display_checked_days = "";
                String display_checked_id = "";


                for (Regionals wishlist : regionalsArrayList) {

                    if (wishlist.getStatus().equals("1")) {
                        display_checked_days = display_checked_days + "," + wishlist.getName();
                        display_checked_id = display_checked_id + "," + wishlist.getId();
                        Log.d(TAG, "onClick: " + display_checked_id);
                        Log.d(TAG, "onClick: " + display_checked_days);
                        if (display_checked_days.startsWith(","))
                            display_checked_days = display_checked_days.substring(1, display_checked_days.length());

                        if (display_checked_id.startsWith(","))
                            display_checked_id = display_checked_id.substring(1, display_checked_id.length());
                        Log.d(TAG, "onClick:s " + display_checked_days);
                        Log.d(TAG, "onClick:s " + display_checked_id);

                    } else {
                        wishlist.setStatus("0");
                        alertdialogAdapter.notifyDataSetChanged();
                    }

                }
                display_checked_regionals_ids = display_checked_id;
                display_checked_regionals = display_checked_days;

                district_ids = "";
                txtspnrDistrict.setText("All");
                getLocalBoardList(display_checked_regionals_ids);

                if (display_checked_regionals.length() != 0)
                    txtspnrRegion.setText(display_checked_regionals);
                else
                    txtspnrRegion.setText("");


               /* ArrayList<Regionals> regionalsArrayList = alertdialogAdapter.getModifyRegionalList();
                String display_checked_days = "";
                String display_checked_id = "";
                for (int i = 0; i < regionalsArrayList.size(); i++) {
                    if (regionalsArrayList.get(i).getStatus().equals("1")) {
                        display_checked_days = display_checked_days + "," + regionalsArrayList.get(i).getName();
                        display_checked_id = display_checked_id + "," + regionalsArrayList.get(i).getId();
                        Log.d(TAG, "onClick: " + display_checked_id);
                        Log.d(TAG, "onClick: " + display_checked_days);
                        display_checked_regionals = display_checked_days.substring(1, display_checked_days.length());
                        display_checked_regionals_ids = display_checked_id.substring(1, display_checked_id.length());
                        Log.d(TAG, "onClick:s " + display_checked_regionals);
                        Log.d(TAG, "onClick:s " + display_checked_regionals_ids);
                    }
                }
                getLocalBoardList(display_checked_regionals_ids);

                if (regionalsArrayList.size() != 0)
                    txtspnrRegion.setText(display_checked_regionals);
                else
                    txtspnrRegion.setText("All");*/
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
}
