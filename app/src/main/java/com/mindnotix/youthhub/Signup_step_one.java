package com.mindnotix.youthhub;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindnotix.model.BasicResponse;
import com.mindnotix.model.DataEmailVerify;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup_step_one extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "Signup_step_one";
    Button button;
    TextView txtTermsAndConditin, txtPrivacyPolicy;
    EditText edtEmail, edtDate;
    ImageView imgDatePicker;
    CheckBox checkBoxCustomized;
    Context context = this;
    EditText editDate;
    TextView txtLogin;
    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "dd/MM/yyyy";
    DatePickerDialog.OnDateSetListener date;

    String date_of_birth;

    ProgressDialog progressDialog;

    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.GERMANY);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_step_one);

        UiInitialization();

        // init - set date to current date
       /* long currentdate = System.currentTimeMillis();
        String dateString = sdf.format(currentdate);
        Log.d(TAG, "onCreate: dateString " + dateString);
        edtDate.setText(dateString);*/


        // set calendar date and update editDate
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

        };


        // onclick - popup datepicker
        edtDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                   /*new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();*/

                DatePickerDialog dialog = new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                Calendar minCal = Calendar.getInstance();
                minCal.set(Calendar.YEAR, minCal.get(Calendar.YEAR) - 26);
                Calendar maxCal = Calendar.getInstance();
                maxCal.set(Calendar.YEAR, maxCal.get(Calendar.YEAR) - 13);


                dialog.getDatePicker().setMinDate(minCal.getTimeInMillis());
                dialog.getDatePicker().setMaxDate(maxCal.getTimeInMillis());


                dialog.show();
            }
        });


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


    @Override
    protected void onPause() {
        super.onPause();
        if (progressDialog != null)
            progressDialog.dismiss();

    }

    private void UiInitialization() {
        button = (Button) findViewById(R.id.btnNext);
        button.setOnClickListener(this);
        edtEmail = (EditText) findViewById(R.id.edtEmailID);
        txtTermsAndConditin = (TextView) findViewById(R.id.txtTermsAndConditin);
        txtTermsAndConditin.setOnClickListener(this);
        txtPrivacyPolicy = (TextView) findViewById(R.id.txtPrivacyPolicy);
        txtPrivacyPolicy.setOnClickListener(this);
        edtDate = (EditText) findViewById(R.id.edtDate);
        imgDatePicker = (ImageView) findViewById(R.id.imgDatePicker);
        txtLogin = findViewById(R.id.txtLogin);
        txtLogin.setOnClickListener(this);
        checkBoxCustomized = (CheckBox) findViewById(R.id.checkBoxCustomized1);


    }

    private void updateDate() {
        Log.d(TAG, "updateDate: " + sdf.format(myCalendar.getTime()));
        edtDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtPrivacyPolicy:
                Intent intents = new Intent(Signup_step_one.this, TermsAndConditionActivity.class);
                intents.putExtra("type", "2");
                startActivity(intents);


                break;
            case R.id.txtTermsAndConditin:

                Intent intent = new Intent(Signup_step_one.this, TermsAndConditionActivity.class);
                intent.putExtra("type", "1");
                startActivity(intent);

                break;
            case R.id.btnNext:

                validationFields();
                break;


            case R.id.txtLogin:

                Intent txtSignup = new Intent(Signup_step_one.this, LoginActivity.class);
                startActivity(txtSignup);
                finish();
                break;
        }
    }

    public void validationFields() {
        if (!Utils.hasText(edtEmail)) {
            Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Utils.isEmailValid(edtEmail.getText().toString())) {
            Toast.makeText(this, "Enter valid email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Utils.hasText(edtDate)) {
            Toast.makeText(this, "Please pick your dob", Toast.LENGTH_SHORT).show();
            return;
        }


        if (!checkBoxCustomized.isChecked()) {
            Toast.makeText(this, R.string.accept_terms, Toast.LENGTH_SHORT).show();
            return;
        } else {
            Next();
            return;
        }
    }


    private void Next() {


        progressDialog = Utils.createProgressDialog(this);
        Map<String, String> data = new HashMap<>();
        data.put("email", edtEmail.getText().toString());


        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("key, " + key + " value " + value);
        }
        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<BasicResponse> call = apiService.getCheckEmailExist(data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                progressDialog.dismiss();

                if (response.body().getStatus().equals("1")) {

                    VerifyEmailID();


                    Toast.makeText(Signup_step_one.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                } else {


                    Toast.makeText(Signup_step_one.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void VerifyEmailID() {

        date_of_birth = edtDate.getText().toString();
        progressDialog = Utils.createProgressDialog(this);
        Map<String, String> data = new HashMap<>();
        data.put("email", edtEmail.getText().toString());
        data.put("dob", edtDate.getText().toString());


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

                progressDialog.dismiss();
                if (response.body().getStatus().equals("1")) {


                    Intent intent = new Intent(Signup_step_one.this, EmailVerifyActivity.class);
                    intent.putExtra("email", response.body().getData().getEmail());
                    intent.putExtra("dob", date_of_birth);
                    startActivity(intent);
                   // finish();
                    Toast.makeText(Signup_step_one.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(Signup_step_one.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
