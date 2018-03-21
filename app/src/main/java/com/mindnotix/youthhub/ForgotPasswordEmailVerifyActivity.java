package com.mindnotix.youthhub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindnotix.model.DataEmailVerify;
import com.mindnotix.model.DataForgotPasswordRequest;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 1/20/2018.
 */

public class ForgotPasswordEmailVerifyActivity extends BaseActivity implements View.OnClickListener {

    private final String TAG = ForgotPasswordEmailVerifyActivity.this.getClass().getSimpleName();
    ;
    TextView txtLogin, a;
    Button btnVerify;
    EditText edtEmailVerifyCode;
    Toolbar toolbar;
    ImageView imgClose;
    ProgressDialog progressDialog;
    String email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pass_email_verify);

        UiInitialization();

        if (getIntent() != null)
            email = getIntent().getStringExtra("email");

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!nbIsNetworkAvailable(this)) {
            nbShowNoInternet(this);
        }
    }


    private void UiInitialization() {

        txtLogin = (TextView) findViewById(R.id.txtLogin);
        imgClose = (ImageView) findViewById(R.id.imgClose);
        imgClose.setOnClickListener(this);
        txtLogin.setOnClickListener(this);
        a = (TextView) findViewById(R.id.a);
        btnVerify = (Button) findViewById(R.id.btnVerify);
        btnVerify.setOnClickListener(this);
        edtEmailVerifyCode = (EditText) findViewById(R.id.edtEmailVerifyCode);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnVerify:

                checkValidation();

                break;
            case R.id.txtLogin:

                verifyEmailID();
                break;

            case R.id.imgClose:

                onBackPressed();
                break;
        }
    }


    private void verifyEmailID() {


        progressDialog = Utils.createProgressDialog(this);


        Map<String, String> data = new HashMap<>();
        data.put("email", email);
        //   data.put("passcode", edtVerifyCode.getText().toString());


        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("key, " + key + " value " + value);
        }

        // RXRetro.getInstance().retrofitEnque(ApiClient.getInterface().getForgotPassRequest(data), this, 2);


        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


        Call<DataForgotPasswordRequest> call = apiService.getForgotPassRequest(data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataForgotPasswordRequest>() {
            @Override
            public void onResponse(Call<DataForgotPasswordRequest> call, Response<DataForgotPasswordRequest> response) {
                progressDialog.dismiss();

                if (response.code() == 200) {


                    if (response.body().getStatus().equals("1")) {

                        edtEmailVerifyCode.setText("");
                    } else {
                        edtEmailVerifyCode.setText("");
                        Toast.makeText(ForgotPasswordEmailVerifyActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else if (response.code() == 304) {

                    Toast.makeText(ForgotPasswordEmailVerifyActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(ForgotPasswordEmailVerifyActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(ForgotPasswordEmailVerifyActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(ForgotPasswordEmailVerifyActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(ForgotPasswordEmailVerifyActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(ForgotPasswordEmailVerifyActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(ForgotPasswordEmailVerifyActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<DataForgotPasswordRequest> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void checkValidation() {

        if (!Utils.hasText(edtEmailVerifyCode)) {
            Toast.makeText(this, "Please enter unique code", Toast.LENGTH_SHORT).show();
        } else {
            passcodeVerify();
        }
    }

    private void passcodeVerify() {


        progressDialog = Utils.createProgressDialog(this);
        Map<String, String> data = new HashMap<>();
        data.put("email", email);
        data.put("passcode", edtEmailVerifyCode.getText().toString().trim());
        //   data.put("passcode", edtVerifyCode.getText().toString());


        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("key, " + key + " value " + value);
        }
        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataEmailVerify> call = apiService.getForgotPassCodeVerify(data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataEmailVerify>() {
            @Override
            public void onResponse(Call<DataEmailVerify> call, Response<DataEmailVerify> response) {
                progressDialog.dismiss();

                if (response.code() == 500) {


                    Toast.makeText(ForgotPasswordEmailVerifyActivity.this, "Internal server error.", Toast.LENGTH_SHORT).show();

                } else {

                    if (response.body().getStatus().equals("1")) {


                        Intent intent = new Intent(ForgotPasswordEmailVerifyActivity.this, ResetPasswordActivity.class);
                        intent.putExtra("email", response.body().getData().getEmail());
                        startActivity(intent);
                        Toast.makeText(ForgotPasswordEmailVerifyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {

                        edtEmailVerifyCode.setText("");
                        Toast.makeText(ForgotPasswordEmailVerifyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
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
