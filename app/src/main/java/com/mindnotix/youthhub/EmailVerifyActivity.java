package com.mindnotix.youthhub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.model.DataEmailVerify;
import com.mindnotix.model.DataMasterInfo;
import com.mindnotix.model.Wishlist;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailVerifyActivity extends BaseActivity implements View.OnClickListener {


    private static final String TAG = "EmailVerifyActivity";
    EditText edtVerifyCode;
    ProgressDialog progressDialog;
    Button btnVerifyEmail;
    TextView txtSendAgain;
    ImageView imgClose;
    private String email, dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verify);

        if (getIntent() != null) {

            email = getIntent().getStringExtra("email");
            dob = getIntent().getStringExtra("dob");


            Log.d(TAG, "onCreate: email " + email);
            Log.d(TAG, "onCreate: dob " + dob);


        }
        UiInitialization();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!nbIsNetworkAvailable(this)) {
            nbShowNoInternet(this);
        }


    }


    private void UiInitialization() {
        edtVerifyCode = (EditText) findViewById(R.id.edtVerifyCode);
        btnVerifyEmail = (Button) findViewById(R.id.btnVerifyEmail);
        txtSendAgain = (TextView) findViewById(R.id.txtLogin);
        txtSendAgain.setOnClickListener(this);
        imgClose = (ImageView) findViewById(R.id.imgClose);
        imgClose.setOnClickListener(this);
        //  txtSendAgain.setOnClickListener(this);
        btnVerifyEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnVerifyEmail:

                if (!Utils.hasText(edtVerifyCode)) {
                    Toast.makeText(this, "Enter your unique code", Toast.LENGTH_SHORT).show();
                } else {
                    //getMasterInfo();

                    VerifyEmailID();
                }

                break;
            case R.id.txtLogin:


                VerifyEmailIDRetry();
                break;
            case R.id.imgClose:
                onBackPressed();
                break;
        }

    }

    private void VerifyEmailID() {

        progressDialog = Utils.createProgressDialog(this);
        Map<String, String> data = new HashMap<>();
        data.put("email", email);
        data.put("passcode", edtVerifyCode.getText().toString());


        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("key, " + key + " value " + value);
        }
        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataEmailVerify> call = apiService.getEmailVerificationPasscode(data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataEmailVerify>() {
            @Override
            public void onResponse(Call<DataEmailVerify> call, Response<DataEmailVerify> response) {


                if (response.body().getStatus().equals("1")) {

                    progressDialog.dismiss();

                    getMasterInfo();

                    Toast.makeText(EmailVerifyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(EmailVerifyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<DataEmailVerify> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void getMasterInfo() {

        progressDialog = Utils.createProgressDialog(this);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataMasterInfo> call = apiService.getMasterData();
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataMasterInfo>() {
            @Override
            public void onResponse(Call<DataMasterInfo> call, Response<DataMasterInfo> response) {


                Log.d(TAG, "onResponse: arrar aaaaa " + new Gson().toJson(response.body().getData()));
                if (response.body().getStatus() == 1) {

                    progressDialog.dismiss();
                    Constant.ethnicityArrayList.clear();
                    Constant.regionalCouncilListArrayList.clear();
                    Constant.wishlistArrayList.clear();

                    Constant.ethnicityArrayList = response.body().getData().getEthnicity();
                    Constant.regionalCouncilListArrayList = response.body().getData().getRegional_council_list();
                    Constant.wishlistArrayList = response.body().getData().getWishlist();

                    for (Wishlist a : Constant.wishlistArrayList) {
                        Constant.wishlistArrayListString.add(a.getName());


                    }
                    Log.d(TAG, "onResponse:ethnicityArrayList " + Constant.ethnicityArrayList.size());
                    Log.d(TAG, "onResponse:regionalCouncilListArrayList " + Constant.regionalCouncilListArrayList.size());
                    Log.d(TAG, "onResponse:wishlistArrayList " + Constant.wishlistArrayList.size());

                    Intent intent = new Intent(EmailVerifyActivity.this, SignupStepTwoActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("dob", dob);
                    startActivity(intent);


                } else {

                    progressDialog.dismiss();

                }


            }

            @Override
            public void onFailure(Call<DataMasterInfo> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });

    }


    private void VerifyEmailIDRetry() {

        progressDialog = Utils.createProgressDialog(this);
        Map<String, String> data = new HashMap<>();
        data.put("email", email);
        data.put("dob", dob);


        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("key, " + key + " value " + value);
        }
        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataEmailVerify> call = apiService.getEmailVerification(data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataEmailVerify>() {
            @Override
            public void onResponse(Call<DataEmailVerify> call, Response<DataEmailVerify> response) {

                edtVerifyCode.setText("");
                if (response.body().getStatus().equals("1")) {

                    progressDialog.dismiss();

                    Toast.makeText(EmailVerifyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    edtVerifyCode.setText("");

                    Toast.makeText(EmailVerifyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<DataEmailVerify> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }
}
