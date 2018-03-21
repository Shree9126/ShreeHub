package com.mindnotix.youthhub;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindnotix.model.DataEmailVerify;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 1/20/2018.
 */

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ChangePasswordActivity";
    TextView txtLogin, a, welcome;
    Toolbar toolbar;
    EditText edtRepassword, edtNewPassword, edtOldPassword;
    Button btnLogin;
    ConstraintLayout constrainlayout;
    ProgressDialog progressDialog;
    ImageView imgClose;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_activity);

        UiInitialization();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!nbIsNetworkAvailable(this)){
            nbShowNoInternet(this);
        }
    }

    private void UiInitialization() {
        imgClose = (ImageView) findViewById(R.id.imgClose);
        imgClose.setOnClickListener(this);
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        a = (TextView) findViewById(R.id.a);
        //constrainlayout = (ConstraintLayout) findViewById(R.id.constrainlayout);
        btnLogin = (Button) findViewById(R.id.btnSubmit);
        btnLogin.setOnClickListener(this);
        edtRepassword = (EditText) findViewById(R.id.edtRe_password);
        edtNewPassword = (EditText) findViewById(R.id.edtNewPassword);
        edtOldPassword = (EditText) findViewById(R.id.edtOldPassword);
        welcome = (TextView) findViewById(R.id.welcome);
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

            case R.id.btnSubmit:

                checkValidation();


                break;

            case R.id.imgClose:

                onBackPressed();
                break;
        }
    }

    private void checkValidation() {

        if (!Utils.hasText(edtOldPassword)) {
            Toast.makeText(this, "Enter your old password", Toast.LENGTH_SHORT).show();
            return;
        }

        /*if (!Utils.validPassword(edtOldPassword.getText().toString())) {
            Toast.makeText(this, R.string.valid_password_atleast, Toast.LENGTH_SHORT).show();
            return;
        }*/

        if (!Utils.hasText(edtNewPassword)) {
            Toast.makeText(this, "Enter your new password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Utils.validPassword(edtNewPassword.getText().toString())) {
            Toast.makeText(this,  R.string.valid_password_atleast, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Utils.hasText(edtRepassword)) {
            Toast.makeText(this, "Enter your confirm password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Utils.validPassword(edtRepassword.getText().toString())) {
            Toast.makeText(this,  R.string.valid_password_atleast, Toast.LENGTH_SHORT).show();
            return;
        }else{

            changePassword();
        }
    }

    private void changePassword() {

        progressDialog = Utils.createProgressDialog(this);
        Map<String, String> data = new HashMap<>();
        data.put("oldpassword", edtOldPassword.getText().toString());
        data.put("password", edtNewPassword.getText().toString());
        data.put("repassword", edtRepassword.getText().toString());
        //   data.put("passcode", edtVerifyCode.getText().toString());


        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("key, " + key + " value " + value);
        }
        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);
        String token = "";

        //token = "Bearer "+ Pref.getPreToken();


        Log.d(TAG, "changePassword: token " + token);

        Call<DataEmailVerify> call = apiService.getChangePassword(Pref.getPreToken(), data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataEmailVerify>() {
            @Override
            public void onResponse(Call<DataEmailVerify> call, Response<DataEmailVerify> response) {


                if (response.body().getStatus().equals("1")) {

                    progressDialog.dismiss();


                    Toast.makeText(ChangePasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ChangePasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<DataEmailVerify> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
