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

import com.mindnotix.adapter.find_connection.FindBusinessServiceTypeAdapter;
import com.mindnotix.adapter.find_connection.FindBusinessSubServiceTypeAdapter;
import com.mindnotix.adapter.find_connection.FindDistrictLocalBoardAdapter;
import com.mindnotix.adapter.find_connection.FindRegionalAdapter;
import com.mindnotix.adapter.find_connection.FindTagsAdapter;
import com.mindnotix.model.find_connection.master.Biz_partnertype;
import com.mindnotix.model.find_connection.master.DataFindConnectionMaster;
import com.mindnotix.model.find_connection.master.Local_boards;
import com.mindnotix.model.find_connection.master.Partner_tag;
import com.mindnotix.model.find_connection.master.Regionals;
import com.mindnotix.model.find_connection.master.Wishlist;
import com.mindnotix.model.find_connection.master.Ysp_partnertype;
import com.mindnotix.model.find_connection.master.sub_service_type.DataSubServiceTypeItems;
import com.mindnotix.model.find_connection.master.sub_service_type.Subcategoryview;
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
 * Created by Admin on 3/5/2018.
 */

public class FindBusinessFilter extends BaseActivity implements View.OnClickListener {

    private static final String TAG = FindConnectYouthFilter.class.getSimpleName();
    public static ArrayList<Local_boards> local_boardsArrayList;
    public static ArrayList<Partner_tag> partner_tagArrayList;
    public static ArrayList<Local_board_list> local_board_listArrayList;
    public static ArrayList<Subcategoryview> subcategoryviewArrayList;
    public static ArrayList<Wishlist> wishlistArrayList;
    public static ArrayList<Ysp_partnertype> ysp_partnertypeArrayList;
    public static ArrayList<Biz_partnertype> biz_partnertypeArrayList;
    public static ArrayList<Partner_tag> partnerTagArrayList;
    public static ArrayList<Regionals> regionalsArrayList;
    ArrayAdapter<Local_boards> local_board_listArrayAdapter;
    String name = "";
    private Toolbar toolbar;
    private TextView txtjosss;
    private TextView txtReset;
    private EditText edtJobSearch;
    private TextView txtspnrRegion;
    private LinearLayout linearSpnrRegion;
    private TextView txtspnrDistrict;
    private LinearLayout linearSpnrDistrict;
    private TextView txt_spnrServiceType;
    private TextView txt_spnrSpnrSubServiceType;
    private TextView txt_spnrTags;
    private LinearLayout linearSpnrServiceType;
    private LinearLayout linearSpnrSubServiceType;
    private LinearLayout linearSpnrTags;
    private LinearLayout linearTagName;
    private ProgressDialog progressDialog;
    private String display_checked_job_wishlist = "";
    private String display_checked_job_wishlist_id = "";
    private String display_checked_regionals = "";
    private String display_checked_regionals_ids = "";
    private String display_checked_districts = "";
    private String display_checked_districts_ids = "";
    private String display_checked_job_service_type_ids = "";
    private String display_checked_job_service_type = "";
    private String display_checked_job_tags = "";
    private String display_checked_job_tags_ids = "";
    private String display_checked_job_sub_service_type = "";
    private String display_checked_job_sub_service_type_id = "";

    private String edtSearchTxt = "";
    private String regional_ids = "";
    private String district_ids = "";
    private String service_type_ids = "";
    private String sub_service_type_ids = "";
    private String tags_ids = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activiy_find_connection_business_filter);
        if (getIntent() != null) {
            name = getIntent().getStringExtra("name");
            edtSearchTxt = getIntent().getStringExtra("edtSearchTxt");
            regional_ids = getIntent().getStringExtra("regional_ids");
            district_ids = getIntent().getStringExtra("district_ids");
            service_type_ids = getIntent().getStringExtra("service_type_ids");
            sub_service_type_ids = getIntent().getStringExtra("sub_service_type_ids");
            tags_ids = getIntent().getStringExtra("tags_ids");
        }

        UiInitialization();


        local_board_listArrayList = new ArrayList<>();
        subcategoryviewArrayList = new ArrayList<>();

        getMasterData();
        edtJobSearch.setText(edtSearchTxt);

    }

    private void getMasterData() {

        regionalsArrayList = new ArrayList<>();
        wishlistArrayList = new ArrayList<>();
        ysp_partnertypeArrayList = new ArrayList<>();
        biz_partnertypeArrayList = new ArrayList<>();
        partner_tagArrayList = new ArrayList<>();
        partnerTagArrayList = new ArrayList<>();
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

                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("1")) {

                        local_boardsArrayList.addAll(response.body().getData().getLocal_boards());

                        wishlistArrayList.addAll(response.body().getData().getWishlist());
                        biz_partnertypeArrayList.addAll(response.body().getData().getBiz_partnertype());
                        partner_tagArrayList.addAll(response.body().getData().getPartner_tag());
                        partnerTagArrayList.addAll(response.body().getData().getPartner_tag());
                        ysp_partnertypeArrayList.addAll(response.body().getData().getYsp_partnertype());
                        regionalsArrayList.addAll(response.body().getData().getRegionals());

                        Log.d(TAG, "onResponse: regionalsArrayList size " + regionalsArrayList.size());

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

                            if (display_checked_id.startsWith(","))
                                display_checked_id = display_checked_id.substring(1, display_checked_id.length());

                            getLocalBoardList(display_checked_id);

                            if (display_checked_days.startsWith(","))
                                display_checked_days = display_checked_days.substring(1, display_checked_days.length());


                            if (display_checked_days.length() != 0)
                                txtspnrRegion.setText(display_checked_days);
                            else
                                txtspnrRegion.setText("All");

                        }

                        if (service_type_ids.length() != 0) {
                            String[] regionals_idss = service_type_ids.split(",");
                            String display_checked_days = "";
                            String display_checked_id = "";
                            for (int i = 0; i < regionals_idss.length; i++) {

                                int pos = getIndexServiceType(regionals_idss[i], biz_partnertypeArrayList);

                                Log.d(TAG, "onCreate: position " + pos);
                                Log.d(TAG, "onCreate: regionalsArrayList " + biz_partnertypeArrayList.size());

                                biz_partnertypeArrayList.get(pos).setStatus("1");

                                display_checked_days = display_checked_days + "," + biz_partnertypeArrayList.get(pos).getName();
                                display_checked_id = display_checked_id + "," + biz_partnertypeArrayList.get(pos).getId();

                                //getLocalBoardList(display_checked_id);

                            }

                            if (display_checked_days.startsWith(","))
                                display_checked_days = display_checked_days.substring(1, display_checked_days.length());


                            if (display_checked_id.startsWith(","))
                                display_checked_id = display_checked_id.substring(1, display_checked_id.length());

                            getSubServiceType(display_checked_id);


                            if (display_checked_days.length() != 0)
                                txt_spnrServiceType.setText(display_checked_days);
                            else
                                txt_spnrServiceType.setText("All");

                        }


                        if (tags_ids.length() != 0) {
                            String[] regionals_idss = tags_ids.split(",");
                            String display_checked_days = "";
                            String display_checked_id = "";
                            for (int i = 0; i < regionals_idss.length; i++) {

                                int pos = getIndexTags(regionals_idss[i], partner_tagArrayList);

                                Log.d(TAG, "onCreate: position " + pos);
                                Log.d(TAG, "onCreate: regionalsArrayList " + partner_tagArrayList.size());

                                partner_tagArrayList.get(pos).setStatus("1");

                                display_checked_days = display_checked_days + "," + partner_tagArrayList.get(pos).getName();
                                display_checked_id = display_checked_id + "," + partner_tagArrayList.get(pos).getId();

                                getLocalBoardList(display_checked_id);
                            }

                            if (display_checked_days.startsWith(","))
                                display_checked_days = display_checked_days.substring(1, display_checked_days.length());

                            if (display_checked_days.length() != 0)
                                txt_spnrTags.setText(display_checked_days);
                            else
                                txt_spnrTags.setText("All");

                        }


                    } else {

                        Toast.makeText(FindBusinessFilter.this, "No record available", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 304) {

                    Toast.makeText(FindBusinessFilter.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(FindBusinessFilter.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(FindBusinessFilter.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(FindBusinessFilter.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(FindBusinessFilter.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(FindBusinessFilter.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(FindBusinessFilter.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<DataFindConnectionMaster> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    private int getIndexTags(String regionals_idss, ArrayList<Partner_tag> partnerTagArrayList) {

        for (int i = 0; i < partnerTagArrayList.size(); i++) {
            Partner_tag auction = partnerTagArrayList.get(i);
            if (regionals_idss.equals(auction.getId())) {
                return i;
            }
        }

        return -1;
    }

    private int getIndexServiceType(String regionals_idss, ArrayList<Biz_partnertype> ysp_partnertypeArrayList) {
        for (int i = 0; i < ysp_partnertypeArrayList.size(); i++) {
            Biz_partnertype auction = ysp_partnertypeArrayList.get(i);
            if (regionals_idss.equals(auction.getId())) {
                return i;
            }
        }

        return -1;
    }

    private int getIndexRegional(String regionals_idss, ArrayList<Regionals> regionalsArrayList) {

        for (int i = 0; i < regionalsArrayList.size(); i++) {
            Regionals auction = regionalsArrayList.get(i);
            if (regionals_idss.equals(auction.getId())) {
                return i;
            }
        }

        return -1;
    }

    private void getLocalBoardList(String name) {
        local_board_listArrayList = new ArrayList<>();

        Log.d(TAG, "getLocalBoardList: " + name);
        String token = Pref.getPreToken();
        progressDialog = Utils.createProgressDialog(this);

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

                try{

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

                            Toast.makeText(FindBusinessFilter.this, "No record available", Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 304) {

                        Toast.makeText(FindBusinessFilter.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(FindBusinessFilter.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(FindBusinessFilter.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(FindBusinessFilter.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(FindBusinessFilter.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(FindBusinessFilter.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(FindBusinessFilter.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }


                }catch (Exception e){
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<DataLocalBoardList> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    private int getIndexDistrict(String regionals_idss, ArrayList<Local_board_list> local_board_listArrayList) {

        for (int i = 0; i < local_board_listArrayList.size(); i++) {
            Local_board_list auction = local_board_listArrayList.get(i);
            if (regionals_idss.equals(auction.getId())) {
                return i;
            }
        }

        return -1;
    }

    private void UiInitialization() {

        this.linearSpnrServiceType = (LinearLayout) findViewById(R.id.linearSpnrServiceType);
        this.linearSpnrSubServiceType = (LinearLayout) findViewById(R.id.linearSpnrSubServiceType);
        this.txt_spnrServiceType = (TextView) findViewById(R.id.txt_spnrServiceType);
        this.txt_spnrSpnrSubServiceType = (TextView) findViewById(R.id.txt_spnrSpnrSubServiceType);
        this.linearSpnrServiceType.setOnClickListener(this);
        this.linearSpnrSubServiceType.setOnClickListener(this);


        this.linearSpnrTags = (LinearLayout) findViewById(R.id.linearSpnrTags);
        this.linearTagName = (LinearLayout) findViewById(R.id.linearTagName);
        this.txt_spnrTags = (TextView) findViewById(R.id.txt_spnrTags);
        this.linearSpnrTags.setOnClickListener(this);


        this.txt_spnrServiceType = (TextView) findViewById(R.id.txt_spnrServiceType);

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
        txtjosss.setText("Browse " + name);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(name);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
        intent.putExtra("service_type_ids", display_checked_job_service_type_ids);
        intent.putExtra("sub_service_type_ids", display_checked_job_sub_service_type_id);
        intent.putExtra("tags_ids", display_checked_job_tags_ids);
        setResult(2, intent);
        finish();//finishing activity
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearSpnrServiceType:
                showBusinessServiceTypeDialog();

                break;

            case R.id.linearSpnrTags:
                showHashTags();
                break;

            case R.id.linearSpnrSubServiceType:
                showSubServiceType();

                break;
            case R.id.linearSpnrDistrict:

                showDistrictDialogSSS();
                break;
            case R.id.linearSpnrRegion:
                showRegionalDialog();
                break;
            case R.id.txtReset:

                display_checked_regionals = "";
                display_checked_regionals_ids = "";
                display_checked_districts = "";
                display_checked_districts_ids = "";
                display_checked_job_service_type_ids = "";
                display_checked_job_service_type = "";
                display_checked_job_tags = "";
                display_checked_job_tags_ids = "";
                display_checked_job_sub_service_type = "";
                display_checked_job_sub_service_type_id = "";

                edtJobSearch.setText("");
                txtspnrRegion.setText("All");
                txtspnrDistrict.setText("All");
                txt_spnrServiceType.setText("All");
                txt_spnrSpnrSubServiceType.setText("All");
                txt_spnrTags.setText("All");

                getMasterData();

                break;
        }
    }

    private void showBusinessServiceTypeDialog() {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog_list, null);
        dialogBuilder.setView(dialogView);
        final ArrayList<Biz_partnertype> wishlistArrayTempList = new ArrayList<>();

        final EditText filterText = (EditText) dialogView.findViewById(R.id.edit1);
        final RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.jobWishList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final FindBusinessServiceTypeAdapter alertdialogAdapter = new FindBusinessServiceTypeAdapter(biz_partnertypeArrayList, this);

        recyclerView.setAdapter(alertdialogAdapter);
        dialogBuilder.setTitle("Service Type");

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

                //   ArrayList<Biz_partnertype> biz_partnertypeArrayList = alertdialogAdapter.getModifyServiceTypeList();
                String display_checked_days = "";
                String display_checked_id = "";
                for (int i = 0; i < biz_partnertypeArrayList.size(); i++) {
                    if (biz_partnertypeArrayList.get(i).getStatus().equals("1")) {
                        display_checked_days = display_checked_days + "," + biz_partnertypeArrayList.get(i).getName();
                        display_checked_id = display_checked_id + "," + biz_partnertypeArrayList.get(i).getId();
                        Log.d(TAG, "onClick: " + display_checked_id);
                        Log.d(TAG, "onClick: " + display_checked_days);
                        display_checked_job_service_type = display_checked_days.substring(1, display_checked_days.length());
                        display_checked_job_service_type_ids = display_checked_id.substring(1, display_checked_id.length());
                        Log.d(TAG, "onClick:s " + display_checked_job_service_type);
                        Log.d(TAG, "onClick:s " + display_checked_job_service_type_ids);
                    }
                }

                getSubServiceType(display_checked_job_service_type_ids);

                if (biz_partnertypeArrayList.size() != 0)
                    txt_spnrServiceType.setText(display_checked_job_service_type);
                else
                    txt_spnrServiceType.setText("All");

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

    private void getSubServiceType(String display_checked_job_service_type_ids) {


        subcategoryviewArrayList.clear();

        subcategoryviewArrayList = new ArrayList<>();

        Log.d(TAG, "getSubServiceType: " + display_checked_job_service_type_ids);
        // progressDialog = Utils.createProgressDialog(this);

        String token = Pref.getPreToken();


        Map<String, String> data = new HashMap<>();
        data.put("category", display_checked_job_service_type_ids);
        //   data.put("is_user_id", "0");
        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


        //token = "Bearer "+ Pref.getPreToken();


        Log.d(TAG, "changePassword: token " + token);

        Call<DataSubServiceTypeItems> call = apiService.getSubServiceTypeBusiness(token, data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataSubServiceTypeItems>() {
            @Override
            public void onResponse(Call<DataSubServiceTypeItems> call, Response<DataSubServiceTypeItems> response) {

                try {


                    if (response.code() == 200) {
                        if (response.body().getStatus().equals("1")) {
                            subcategoryviewArrayList.addAll(response.body().getData().getSubcategoryview());


                            if (sub_service_type_ids.length() != 0) {
                                String[] regionals_idss = sub_service_type_ids.split(",");
                                String display_checked_days = "";
                                String display_checked_id = "";
                                for (int i = 0; i < regionals_idss.length; i++) {

                                    int pos = getIndexSubServiceType(regionals_idss[i], subcategoryviewArrayList);

                                    Log.d(TAG, "onCreate: position " + pos);
                                    Log.d(TAG, "onCreate: regionalsArrayList " + subcategoryviewArrayList.size());

                                    subcategoryviewArrayList.get(pos).setStatus_chk("1");

                                    display_checked_days = display_checked_days + "," + subcategoryviewArrayList.get(pos).getName();
                                    display_checked_id = display_checked_id + "," + subcategoryviewArrayList.get(pos).getId();

                                    //getLocalBoardList(display_checked_id);
                                }

                                if (display_checked_days.startsWith(","))
                                    display_checked_days = display_checked_days.substring(1, display_checked_days.length());

                                if (display_checked_days.length() != 0)
                                    txt_spnrSpnrSubServiceType.setText(display_checked_days);
                                else
                                    txt_spnrSpnrSubServiceType.setText("All");

                            }
                        } else {

                            Toast.makeText(FindBusinessFilter.this, "No record available", Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 304) {

                        Toast.makeText(FindBusinessFilter.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(FindBusinessFilter.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(FindBusinessFilter.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(FindBusinessFilter.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(FindBusinessFilter.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(FindBusinessFilter.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(FindBusinessFilter.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {

                }
                //    progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<DataSubServiceTypeItems> call, Throwable t) {
                //    progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    private int getIndexSubServiceType(String regionals_idss, ArrayList<Subcategoryview> subcategoryviewArrayList) {

        for (int i = 0; i < subcategoryviewArrayList.size(); i++) {
            Subcategoryview auction = subcategoryviewArrayList.get(i);
            if (regionals_idss.equals(auction.getId())) {
                return i;
            }
        }

        return -1;
    }

    private void showSubServiceType() {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog_list, null);
        dialogBuilder.setView(dialogView);
        final ArrayList<Subcategoryview> wishlistArrayTempList = new ArrayList<>();

        final EditText filterText = (EditText) dialogView.findViewById(R.id.edit1);
        final RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.jobWishList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final FindBusinessSubServiceTypeAdapter alertdialogAdapter = new FindBusinessSubServiceTypeAdapter(subcategoryviewArrayList, this);

        recyclerView.setAdapter(alertdialogAdapter);
        dialogBuilder.setTitle("Sub Service Type");

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

                //   ArrayList<Subcategoryview> subcategoryviewArrayList = alertdialogAdapter.getModifyPartnerTagsList();
                String display_checked_days = "";
                String display_checked_id = "";
                for (int i = 0; i < subcategoryviewArrayList.size(); i++) {
                    if (subcategoryviewArrayList.get(i).getStatus_chk().equals("1")) {
                        display_checked_days = display_checked_days + "," + subcategoryviewArrayList.get(i).getName();
                        display_checked_id = display_checked_id + "," + subcategoryviewArrayList.get(i).getId();
                        Log.d(TAG, "onClick: " + display_checked_id);
                        Log.d(TAG, "onClick: " + display_checked_days);
                        display_checked_job_sub_service_type = display_checked_days.substring(1, display_checked_days.length());
                        display_checked_job_sub_service_type_id = display_checked_id.substring(1, display_checked_id.length());
                        Log.d(TAG, "onClick:s " + display_checked_job_sub_service_type);
                        Log.d(TAG, "onClick:s " + display_checked_job_sub_service_type_id);
                    }
                }

                if (subcategoryviewArrayList.size() != 0)
                    txt_spnrSpnrSubServiceType.setText(display_checked_job_sub_service_type);
                else
                    txt_spnrSpnrSubServiceType.setText("All");

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


    private void showHashTags() {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog_list, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        final ArrayList<Partner_tag> wishlistArrayTempList = new ArrayList<>();

        final EditText filterText = (EditText) dialogView.findViewById(R.id.edit1);
        final RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.jobWishList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final FindTagsAdapter alertdialogAdapter = new FindTagsAdapter(partner_tagArrayList, this);

        recyclerView.setAdapter(alertdialogAdapter);
        dialogBuilder.setTitle("Tags");

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

                //     ArrayList<Partner_tag> partner_tagArrayList = alertdialogAdapter.getModifyPartnerTagsList();
                String display_checked_days = "";
                String display_checked_id = "";
                for (int i = 0; i < partner_tagArrayList.size(); i++) {
                    if (partner_tagArrayList.get(i).getStatus().equals("1")) {
                        display_checked_days = display_checked_days + "," + partner_tagArrayList.get(i).getName();
                        display_checked_id = display_checked_id + "," + partner_tagArrayList.get(i).getId();
                        Log.d(TAG, "onClick: " + display_checked_id);
                        Log.d(TAG, "onClick: " + display_checked_days);
                        display_checked_job_tags = display_checked_days.substring(1, display_checked_days.length());
                        display_checked_job_tags_ids = display_checked_id.substring(1, display_checked_id.length());
                        Log.d(TAG, "onClick:s " + display_checked_job_tags);
                        Log.d(TAG, "onClick:s " + display_checked_job_tags_ids);
                    }
                }

                if (partner_tagArrayList.size() != 0)
                    txt_spnrTags.setText(display_checked_job_tags);
                else
                    txt_spnrTags.setText("All");

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

                //   ArrayList<Local_board_list> local_board_listArrayList = alertdialogAdapter.getModifyDistrictList();
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
        dialogBuilder.setTitle("Regionals");

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

                //ArrayList<Regionals> regionalsArrayList = alertdialogAdapter.getModifyRegionalList();

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
