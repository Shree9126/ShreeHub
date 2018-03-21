package com.mindnotix.youthhub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.model.DataLoginAuthorize;
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

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    TextView txtForgotPassword, txtSignup;
    Button button;
    EditText edtPassword, edtEmailID;
    Call<DataLoginAuthorize> callLogin;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UiIntialization();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!nbIsNetworkAvailable(this)) {
            nbShowNoInternet(this);
        }
    }


    private void UiIntialization() {
        txtForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);
        txtSignup = (TextView) findViewById(R.id.txtSignup);
        txtForgotPassword.setOnClickListener(this);
        txtSignup.setOnClickListener(this);
        button = (Button) findViewById(R.id.btnLogin);
        button.setOnClickListener(this);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtEmailID = (EditText) findViewById(R.id.edtEmailID);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnLogin:


                checkLoginCredentials();

                break;
            case R.id.txtForgotPassword:

                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.txtSignup:

                Intent txtSignup = new Intent(LoginActivity.this, Signup_step_one.class);
                startActivity(txtSignup);
                finish();
                break;

        }

    }

    public void checkLoginCredentials() {
        if (!Utils.hasText(edtEmailID)) {
            Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Utils.hasText(edtPassword)) {
            Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Utils.isValidPassword(edtPassword)) {
            Toast.makeText(this, "Enter valid password", Toast.LENGTH_SHORT).show();
            return;
        } else {
            logMeIn();
            return;
        }
    }

    private void logMeIn() {
        progressDialog = Utils.createProgressDialog(this);
        Pref.clear();

        final String username = edtEmailID.getText().toString();
        final String password = edtPassword.getText().toString();
        Map<String, String> data = new HashMap<>();
        data.put("username", edtEmailID.getText().toString());
        data.put("password", edtPassword.getText().toString());


        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("key, " + key + " value " + value);
        }
        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataLoginAuthorize> call = apiService.getAuthorizeLogin(data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataLoginAuthorize>() {
            @Override
            public void onResponse(Call<DataLoginAuthorize> call, Response<DataLoginAuthorize> response) {
                progressDialog.dismiss();
                DataLoginAuthorize dataLoginAuthorize = response.body();

                Log.d(TAG, "onResponse: " + response.code());
                Log.d(TAG, "onResponse: " + response.message());


                assert dataLoginAuthorize != null;
                Log.d(TAG, "onResponse: " + new Gson().toJson(dataLoginAuthorize));


                if (response.code() == 200 || response.code() == 201) {


                    Log.d(TAG, "onResponse:response.body " + new Gson().toJson(response.body()));
                    Log.d(TAG, "onResponse: statussss " + response.body().getStatus());

                    if (response.body().getStatus().equals("1")) {

                        Log.d(TAG, "onResponse: getToken    - " + "Bearer " + dataLoginAuthorize.getToken());

                        Log.d(TAG, "onResponse: profile_pic name =" + dataLoginAuthorize.getProfile_pic());
                        Pref.setClientID(dataLoginAuthorize.getId());
                        Pref.setClientFirstname(dataLoginAuthorize.getUser_name());
                        Pref.setClientProfilePictureName(dataLoginAuthorize.getProfile_pic());
                        Pref.saveProfileImagePath(dataLoginAuthorize.getThumbnail_path(),
                                dataLoginAuthorize.getMedium_path(), dataLoginAuthorize.getCover_path());
                        Pref.setdevicetoken("Bearer " + dataLoginAuthorize.getToken());
                        Pref.savePreference(dataLoginAuthorize.getUser_name(), password, dataLoginAuthorize.getId());
                       // Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                        Intent intent = new Intent(LoginActivity.this, NavDrawerActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                        Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid login", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 304) {

                    Toast.makeText(LoginActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(LoginActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(LoginActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(LoginActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(LoginActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(LoginActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(LoginActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<DataLoginAuthorize> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }


}
