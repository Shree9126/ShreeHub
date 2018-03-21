package com.mindnotix.youthhub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mindnotix.model.DataForgotPasswordRequest;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.IBaseResponseListener;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener, IBaseResponseListener {
    private static final String TAG = "ForgotPasswordActivity";
    TextView txtLogin, a;
    EditText edtEmailID;
    Toolbar toolbar;
    Button btnSubmit;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        UiInitialization();

    }

    private void UiInitialization() {

        txtLogin = (TextView) findViewById(R.id.txtLogin);
        txtLogin.setOnClickListener(this);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        a = (TextView) findViewById(R.id.a);
        edtEmailID = (EditText) findViewById(R.id.edtEmailID);
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
    protected void onResume() {
        super.onResume();
        if (!nbIsNetworkAvailable(this)) {
            nbShowNoInternet(this);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            /*case R.id.imgClose:

                onBackPressed();
                break;*/

            case R.id.btnSubmit:

                checkValidation();


                break;
            case R.id.txtLogin:

                Intent mIntent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(mIntent);
                finish();
                break;

        }
    }

    private void checkValidation() {

        if (!Utils.hasText(edtEmailID)) {
            Toast.makeText(this, "Enter your email address to reset your password", Toast.LENGTH_SHORT).show();
        } else {

            if (!Utils.isEmailValid(edtEmailID.getText().toString().trim())) {
                Toast.makeText(this, "Enter valid email address", Toast.LENGTH_SHORT).show();
            } else {
                verifyEmailID();

            }
        }
    }


    private void verifyEmailID() {


        progressDialog = Utils.createProgressDialog(this);


        Map<String, String> data = new HashMap<>();
        data.put("email", edtEmailID.getText().toString());
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

                        Intent intent = new Intent(ForgotPasswordActivity.this, ForgotPasswordEmailVerifyActivity.class);
                        intent.putExtra("email", response.body().getData().getEmail());
                        startActivity(intent);
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else if (response.code() == 304) {

                    Toast.makeText(ForgotPasswordActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(ForgotPasswordActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(ForgotPasswordActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(ForgotPasswordActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(ForgotPasswordActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(ForgotPasswordActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(ForgotPasswordActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<DataForgotPasswordRequest> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
