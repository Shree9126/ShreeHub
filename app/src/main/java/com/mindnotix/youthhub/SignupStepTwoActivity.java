package com.mindnotix.youthhub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mindnotix.model.DataUploadProfilePic;
import com.mindnotix.model.Ethnicity;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupStepTwoActivity extends BaseActivity implements View.OnClickListener {


    private static final int PICK_GALLERY_IMAGE_REQUEST_CODE = 200;
    private static final String TAG = "SignupStepTwoActivity";
    private final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 300;
    Spinner spnrEtnicity, spnrGender;
    Button button;
    ImageView imgClose;
    EditText edtName, edtSurName;
    ProgressDialog progressDialog;
    TextView txtLogin;
    String Ethinicity_ID;
    CircleImageView imgProfileImage;
    String email, dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_step_two);
        UiInitialization();


        if (getIntent() != null) {

            email = getIntent().getStringExtra("email");
            dob = getIntent().getStringExtra("dob");


            Log.d(TAG, "onCreate: email " + email);
            Log.d(TAG, "onCreate: dob " + dob);


        }

        List<String> list = new ArrayList<String>();
        list.add("Select Gender");
        list.add("Male");
        list.add("Female");
        list.add("Other");

        ArrayAdapter<String> dataAdapterGender = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrGender.setAdapter(dataAdapterGender);


        Ethnicity ethnicity = new Ethnicity();
        ethnicity.setKey("0");
        ethnicity.setName("Select Ethnicity");
        Constant.ethnicityArrayList.add(0, ethnicity);


        ArrayAdapter<Ethnicity> dataAdapter = new ArrayAdapter<Ethnicity>(this,
                android.R.layout.simple_spinner_dropdown_item, Constant.ethnicityArrayList);
        // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // dataAdapter.setDropDownViewTheme(R.style.spinnerDropDownItemStyle);

        spnrEtnicity.setAdapter(dataAdapter);

        spnrEtnicity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                Ethinicity_ID = Constant.ethnicityArrayList.get(position).getKey();
                Log.d(TAG, "onItemSelected:Regional_ID " + Ethinicity_ID);
                ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }

    private void UiInitialization() {

        button = (Button) findViewById(R.id.btnNext);
        imgClose = (ImageView) findViewById(R.id.imgClose);
        imgClose.setOnClickListener(this);
        button.setOnClickListener(this);
        spnrEtnicity = (Spinner) findViewById(R.id.spnrEtnicity);
        spnrGender = (Spinner) findViewById(R.id.spnrGender);
        edtName = (EditText) findViewById(R.id.edtName);
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        txtLogin.setOnClickListener(this);
        edtSurName = (EditText) findViewById(R.id.edtSurName);
        imgProfileImage = (CircleImageView) findViewById(R.id.imgProfileImage);
        imgProfileImage.setOnClickListener(this);

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtLogin:
                Intent intent = new Intent(SignupStepTwoActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnNext:

                checkValidation();

                break;
            case R.id.imgProfileImage:

                UploadProfilePic();
                break;

            case R.id.imgClose:

                onBackPressed();
                break;
        }
    }

    @Override
    public void onSuccess(String completed, int requestId) {
        super.onSuccess(completed, requestId);
        switch (requestId) {

            case 2:
                DataUploadProfilePic postResponse = getGson().fromJson(completed, DataUploadProfilePic.class);
                Log.d(TAG, "onSuccess: " + postResponse);

                if (postResponse.getStatus().equals("1")) {

                    String thumb = postResponse.getData().getPath_thumb() + postResponse.getData().getFilename();
                    String medium = postResponse.getData().getPath_medium() + postResponse.getData().getFilename();
                    String large = postResponse.getData().getPath_large() + postResponse.getData().getFilename();
                    Log.d(TAG, "onSuccess:thumb " + thumb);
                    Log.d(TAG, "onSuccess:medium " + medium);
                    Log.d(TAG, "onSuccess:large " + large);

                    Pref.saveProfileImagePathReg(thumb,
                            medium, large);
                    Toast.makeText(this, postResponse.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, postResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }

    @Override
    public void onFailure(Throwable t, int requestId) {
        super.onFailure(t, requestId);
    }

    private void UploadProfilePic() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Pic"), PICK_GALLERY_IMAGE_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case PICK_GALLERY_IMAGE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {

                    Uri selectedImageUri = data.getData();
                    if (null != selectedImageUri) {

                        try {

                            Log.d(TAG, "onActivityResult: selectedImageUri " + selectedImageUri);


                            String realPath = getPath(selectedImageUri);
                            Log.i(TAG, "onActivityResult: Image Path_s_realPath : " + realPath);


                            Picasso.with(this)
                                    .load(selectedImageUri)
                                    .placeholder(R.drawable.profile_pic) //this is optional the image to display while the url image is downloading
                                    .error(R.drawable.profile_pic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                    .into(this.imgProfileImage);


                            uploadProfile(realPath);


                        } catch (Exception e) {

                            e.printStackTrace();
                        }

                    }
                    break;
                }
        }

    }

    private void uploadProfile(final String realPath) {

        if (!realPath.equals("") && !realPath.equals(null)) {


            progressDialog = Utils.createProgressDialog(this);

            Map<String, RequestBody> map = new HashMap<>();

            Log.v("befyebfgeuhfguyh", realPath);

            File file = new File(realPath);


            Log.v("befyebfgeuhfguyh", file.getName());

            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("userfile", file.getName(), requestBody);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

            //  RXRetro.getInstance().retrofitEnque(ApiClient.getInterface().uploadMUCProfileImagePost(fileToUpload), this, 2);

            YouthHubApi apiService =
                    ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

            Call<DataUploadProfilePic> call = apiService.uploadMUCProfileImagePosts(fileToUpload);
            Log.d(TAG, "logMeIn: url " + call.request().url());

            call.enqueue(new Callback<DataUploadProfilePic>() {
                @Override
                public void onResponse(Call<DataUploadProfilePic> call, Response<DataUploadProfilePic> response) {


                    if (response.body().getStatus().equals("1")) {

                        progressDialog.dismiss();


                        Log.d(TAG, "onSuccess: " + response.body());

                        if (response.body().getStatus().equals("1")) {

                            String thumb = response.body().getData().getPath_thumb() + response.body().getData().getFilename();
                            String medium = response.body().getData().getPath_medium() + response.body().getData().getFilename();
                            String large = response.body().getData().getPath_large() + response.body().getData().getFilename();
                            Log.d(TAG, "onSuccess:thumb " + thumb);
                            Log.d(TAG, "onSuccess:medium " + medium);
                            Log.d(TAG, "onSuccess:large " + large);

                            Pref.setClientProfilePictureName(response.body().getData().getFilename());

                            Pref.saveProfileImagePathReg(thumb,
                                    medium, large);

                        } else {


                        }


                       // Toast.makeText(SignupStepTwoActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                      //  Toast.makeText(SignupStepTwoActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onFailure(Call<DataUploadProfilePic> call, Throwable t) {
                    progressDialog.dismiss();
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(this, "Please, pick your profile picture.", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkValidation() {

        if (!Utils.hasText(edtName)) {
            Toast.makeText(this, "Enter your Name", Toast.LENGTH_SHORT).show();
        } else {


            if (!Utils.hasText(edtSurName)) {
                Toast.makeText(this, "Enter your Surname", Toast.LENGTH_SHORT).show();
            } else {

                if (spnrGender.getSelectedItem().toString().equals("Select Gender")) {
                    Toast.makeText(this, "Select your gender", Toast.LENGTH_SHORT).show();
                } else {
                    if (spnrEtnicity.getSelectedItem().toString().equals("Select Ethnicity")) {
                        Toast.makeText(this, "Select your Ethnicity", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(SignupStepTwoActivity.this, SignUpStepThreeActivity.class);
                        intent.putExtra("gender", spnrGender.getSelectedItem().toString());
                        intent.putExtra("ethinicity", spnrEtnicity.getSelectedItem().toString());
                        intent.putExtra("name", edtName.getText().toString());
                        intent.putExtra("sur_name", edtSurName.getText().toString());
                        intent.putExtra("email", email);
                        intent.putExtra("dob", dob);

                        startActivity(intent);
                    }
                }
            }
        }


    }


}
