package com.mindnotix.youthhub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindnotix.model.BasicResponse;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private final String TAG = ResetPasswordActivity.this.getClass().getSimpleName();
    TextView txtLogin, a;
    EditText edtConfirmNewPassword, edtNewPassword;
    Toolbar toolbar;
    Button btnSubmit;
    ProgressDialog progressDialog;
    String email;
    ImageView imgClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
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
        imgClose = (ImageView) findViewById(R.id.imgClose);
        imgClose.setOnClickListener(this);
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        a = (TextView) findViewById(R.id.a);
        edtConfirmNewPassword = (EditText) findViewById(R.id.edtConfirmNewPassword);
        edtNewPassword = (EditText) findViewById(R.id.edtNewPassword);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        txtLogin.setOnClickListener(this);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

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
            case R.id.btnSubmit:

                checkValidation();

                break;
            case R.id.txtLogin:
                Intent mIntent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                startActivity(mIntent);
                finish();
                break;
            case R.id.imgClose:

                onBackPressed();
                break;
        }
    }

    private void checkValidation() {

        if (!Utils.hasText(edtNewPassword)) {
            Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show();
        } else {

            if (!Utils.validPassword(edtNewPassword.getText().toString())) {
                Toast.makeText(this,  R.string.valid_password_atleast, Toast.LENGTH_SHORT).show();
            } else {

                if (!Utils.hasText(edtConfirmNewPassword)) {
                    Toast.makeText(this, "Enter your confirm password", Toast.LENGTH_SHORT).show();
                } else {

                    if (!Utils.validPassword(edtConfirmNewPassword.getText().toString())) {
                        Toast.makeText(this,  R.string.valid_password_atleast, Toast.LENGTH_SHORT).show();
                    } else {

                        if (!edtNewPassword.getText().toString().trim().equals(edtConfirmNewPassword.getText().toString().trim())) {
                            Toast.makeText(this, "Password is mismatch", Toast.LENGTH_SHORT).show();
                        } else {
                            resetPassword();
                        }
                    }


                }
            }

        }
    }

    private void resetPassword() {

        progressDialog = Utils.createProgressDialog(this);
        Map<String, String> data = new HashMap<>();
        data.put("email", email);
        data.put("password", edtNewPassword.getText().toString().trim());
        data.put("repassword", edtConfirmNewPassword.getText().toString().trim());


        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("key, " + key + " value " + value);
        }
        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<BasicResponse> call = apiService.getResetPassword(data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {


                if (response.body().getStatus().equals("1")) {

                    progressDialog.dismiss();

                    Intent mIntent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                    startActivity(mIntent);

                    Toast.makeText(ResetPasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ResetPasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
