package com.mindnotix.youthhub;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mindnotix.adapter.AlertdialogAdapter;
import com.mindnotix.model.DataEmailVerify;
import com.mindnotix.model.DataRegSumbitResult;
import com.mindnotix.model.Local_board_list;
import com.mindnotix.model.Regional_council_list;
import com.mindnotix.model.Wishlist;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpStepThreeActivity extends BaseActivity {


    private static final String TAG = "SignUpStepThreeActivity";
    public static int districtFlag = 0;
    public static int regionFlag = 0;
    Button btnSignup;
    Spinner spnrRegion, spnrDistrict;

    MultiAutoCompleteTextView edtJobWhistlist;

    CharSequence[] wishlistArrayListStringchar;
    TextView spnrJobWishList;
    ImageView imgJobLists, imgClose;
    LinearLayout imgJobList;
    EditText edtMobile, edtPassword, edtConfirmPassword;
    String display_checked_job_wishlist;
    String display_checked_job_wishlist_id;
    ProgressDialog progressDialog;
    String name, sur_name, ethinicity, gender, LocalBoardID, email, dob;
    CircleImageView imgProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_step_three);
        UiInitialization();


        if (getIntent() != null) {
            name = getIntent().getStringExtra("name");
            sur_name = getIntent().getStringExtra("sur_name");
            ethinicity = getIntent().getStringExtra("ethinicity");
            gender = getIntent().getStringExtra("gender");
            email = getIntent().getStringExtra("email");
            dob = getIntent().getStringExtra("dob");


            Log.d(TAG, "onCreate: name " + name);
            Log.d(TAG, "onCreate: sur_name " + sur_name);
            Log.d(TAG, "onCreate: ethinicity " + ethinicity);
            Log.d(TAG, "onCreate: gender " + gender);
            Log.d(TAG, "onCreate: email " + email);
            Log.d(TAG, "onCreate: dob " + dob);


        }


        if (regionFlag == 0) {
            Regional_council_list regional_council_list = new Regional_council_list();
            regional_council_list.setId("0");
            regional_council_list.setName("Select Region");
            Constant.regionalCouncilListArrayList.add(0, regional_council_list);

            regionFlag = 1;
        }


        ArrayAdapter<Regional_council_list> dataAdapters = new ArrayAdapter<Regional_council_list>(this,
                android.R.layout.simple_spinner_item, Constant.regionalCouncilListArrayList);
        dataAdapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnrRegion.setAdapter(dataAdapters);


        spnrRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String Regional_ID = Constant.regionalCouncilListArrayList.get(position).getId();
                Log.d(TAG, "onItemSelected:Regional_ID " + Regional_ID);
                if (!Regional_ID.equals("0"))
                    getDistrict(Regional_ID);
                else {
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add("Select District");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SignUpStepThreeActivity.this,
                            android.R.layout.simple_spinner_item, arrayList);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spnrDistrict.setAdapter(dataAdapter);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkValidation();
            }
        });

        imgJobList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLangDialog();
            }
        });

       /* imgProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void UiInitialization() {

        spnrRegion = (Spinner) findViewById(R.id.spnrRegion);
        spnrDistrict = (Spinner) findViewById(R.id.spnrDistrict);
        spnrJobWishList = (TextView) findViewById(R.id.spnrJobWishList);
        imgJobList = (LinearLayout) findViewById(R.id.imgJobList);
        edtMobile = (EditText) findViewById(R.id.edtMobile);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtConfirmPassword = (EditText) findViewById(R.id.edtConfirmPassword);
        //edtJobWhistlist = (MultiAutoCompleteTextView) findViewById(R.id.edtJobWhistlists);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        //  imgProfileImage = (CircleImageView) findViewById(R.id.imgProfileImage);
        imgClose = (ImageView) findViewById(R.id.imgClose);

        wishlistArrayListStringchar = Constant.wishlistArrayListString.toArray(new CharSequence[Constant.wishlistArrayListString.size()]);

    }

    private void checkValidation() {


        if (spnrRegion.getSelectedItem().toString().equals("Select Region")) {
            Toast.makeText(this, "Select your Region", Toast.LENGTH_SHORT).show();
        } else {

            if (spnrDistrict.getSelectedItem().toString().equals("Select District")) {
                Toast.makeText(this, "Select your district", Toast.LENGTH_SHORT).show();
            } else {

                if (spnrJobWishList.getText().toString().equals("")) {
                    Toast.makeText(this, "Select your job wishlist", Toast.LENGTH_SHORT).show();
                } else {
                    if (!Utils.hasText(edtMobile)) {
                        Toast.makeText(this, "Enter your mobile number", Toast.LENGTH_SHORT).show();
                    } else {


                        if (!Utils.validateMobileNZ(edtMobile.getText().toString().trim())) {
                            Toast.makeText(this, "Enter valid mobile number", Toast.LENGTH_SHORT).show();
                        } else {
                            if (!Utils.hasText(edtPassword)) {
                                Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show();
                            } else {

                                if (!Utils.validPassword(edtPassword.getText().toString())) {
                                    Toast.makeText(this, R.string.valid_password_atleast, Toast.LENGTH_LONG).show();
                                }else{

                                    if (!Utils.hasText(edtConfirmPassword)) {
                                        Toast.makeText(this, "Enter your confirm password", Toast.LENGTH_SHORT).show();
                                    } else {

                                        if (!Utils.validPassword(edtConfirmPassword.getText().toString())) {
                                            Toast.makeText(this,  R.string.valid_password_atleast, Toast.LENGTH_LONG).show();
                                        }else{

                                            if (!edtPassword.getText().toString().trim().equals(edtConfirmPassword.getText().toString().trim())) {
                                                Toast.makeText(this, "Password mis-match", Toast.LENGTH_SHORT).show();
                                            } else {

                                                submitData();

                                            }
                                        }


                                    }
                                }

                            }
                        }
                    }
                }
            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (progressDialog != null)
            progressDialog.dismiss();

        if (!nbIsNetworkAvailable(this)) {
            nbShowNoInternet(this);
        }
    }


    private void submitData() {


        int pos = spnrRegion.getSelectedItemPosition();
        final String regional = Constant.regionalCouncilListArrayList.get(pos).getId();
        int local_board_id_pos = spnrDistrict.getSelectedItemPosition();
        String local_board_id = Constant.local_board_listArrayList.get(local_board_id_pos).getId();

        Log.d(TAG, "submitData: local_board_id_pos " + local_board_id_pos);
        Log.d(TAG, "submitData: regional " + regional);

        progressDialog = Utils.createProgressDialog(this);
        Map<String, String> data = new HashMap<>();
        data.put("user_type", "8");
        data.put("firstname", name);
        data.put("lastname", sur_name);
        data.put("email", email);
        data.put("dob", "8");
        data.put("mobile_no", edtMobile.getText().toString().trim());
        data.put("regional_id", regional);
        data.put("local_board_id", local_board_id);
        data.put("gender", gender);
        data.put("userpassword", edtConfirmPassword.getText().toString().trim());
        data.put("confirm_password", edtConfirmPassword.getText().toString().trim());
        data.put("agree", "1");
        if (Pref.getClientProfilePictureName() != null)
            data.put("profile_pic", Pref.getClientProfilePictureName());
        else
            data.put("profile_pic", "");
        data.put("wishlist", display_checked_job_wishlist_id);
        data.put("ethnicity", ethinicity);


        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("key, " + key + " value " + value);
        }
        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataRegSumbitResult> call = apiService.submitData(data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataRegSumbitResult>() {
            @Override
            public void onResponse(Call<DataRegSumbitResult> call, Response<DataRegSumbitResult> response) {


                if (response.body().getStatus().equals("1")) {

                    progressDialog.dismiss();


                    Intent intent = new Intent(SignUpStepThreeActivity.this, LoginActivity.class);
                    startActivity(intent);

                    Toast.makeText(SignUpStepThreeActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "onResponse: " + response.body().getStatus());
                    progressDialog.dismiss();
                    Toast.makeText(SignUpStepThreeActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<DataRegSumbitResult> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void getDistrict(final String regional_id) {
        Constant.local_board_listArrayList.clear();
        progressDialog = Utils.createProgressDialog(this);
        Map<String, String> data = new HashMap<>();
        data.put("regional_id", regional_id);


        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("key, " + key + " value " + value);
        }
        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataEmailVerify> call = apiService.getLocalBoardDistrict(data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataEmailVerify>() {
            @Override
            public void onResponse(Call<DataEmailVerify> call, Response<DataEmailVerify> response) {


                if (response.body().getStatus().equals("1")) {

                    progressDialog.dismiss();
                    Constant.local_board_listArrayList.addAll(response.body().getData().getLocal_board_list());
                    // Log.d(TAG, "onResponse: district "+ new Gson.toJson(response.body().getData().getLocal_board_list()));

                    Local_board_list local_board_list = new Local_board_list();
                    local_board_list.setId("0");
                    local_board_list.setName("Select District");
                    Constant.local_board_listArrayList.add(0, local_board_list);
                    ArrayAdapter<Local_board_list> dataAdapter = new ArrayAdapter<Local_board_list>(SignUpStepThreeActivity.this,
                            android.R.layout.simple_spinner_item, Constant.local_board_listArrayList);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spnrDistrict.setAdapter(dataAdapter);


                } else {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpStepThreeActivity.this, "Failed to load", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<DataEmailVerify> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }


    public void showChangeLangDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog_list, null);
        dialogBuilder.setView(dialogView);
        final ArrayList<Wishlist> wishlistArrayTempList = new ArrayList<>();


        final EditText filterText = (EditText) dialogView.findViewById(R.id.edit1);
        //  final EditText searchView1 = (EditText) dialogView.findViewById(R.id.searchView1);
        final RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.jobWishList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final AlertdialogAdapter alertdialogAdapter = new AlertdialogAdapter(Constant.wishlistArrayList);
        recyclerView.setAdapter(alertdialogAdapter);
        dialogBuilder.setTitle("Job wishlist");


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


                for (Wishlist wishlist : Constant.wishlistArrayList) {

                    if (wishlist.getChecked()) {
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

                    }

                }
                display_checked_job_wishlist_id = display_checked_id;
                display_checked_job_wishlist = display_checked_days;

                if (display_checked_job_wishlist.length() != 0)
                    spnrJobWishList.setText(display_checked_job_wishlist);
                else
                    spnrJobWishList.setText("");


            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass

               /* for (int i = 0; i < Constant.wishlistArrayList.size(); i++) {
                    if (Constant.wishlistArrayList.get(i).getStatus().equals("1")) {
                        Constant.wishlistArrayList.get(i).setStatus("0");
                    }
                }*/
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
}
