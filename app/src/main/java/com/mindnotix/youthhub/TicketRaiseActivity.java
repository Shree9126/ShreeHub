package com.mindnotix.youthhub;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindnotix.model.DataUploadProfilePic;
import com.mindnotix.model.contactsupport.add_ticket.DataTicketRaiseITem;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 20-02-2018.
 */

public class TicketRaiseActivity extends BaseActivity implements View.OnClickListener {
    private static final int PICK_GALLERY_IMAGE_REQUEST_CODE = 200;

    private static final String TAG = TicketRaiseActivity.class.getSimpleName();
    private static int RESULT_LOAD_IMG = 1, RESULT_LOAD_FILE = 0, RESULT_LOAD_FILE_IMG = 2;
    public int FileChoosenType, FileChoosenType_style;
    ProgressDialog progressDialog;
    LinearLayout linearSetImage, linearPickTicketImage;
    Uri selectedPdf;
    String mediaPath = "", filePath = "", FileMediaPath = "";
    private android.widget.EditText edtSubject;
    private android.widget.EditText edtMessages;
    private android.widget.ImageView imgPickTicketImage;
    private android.widget.ImageView imgPickTicketImageSet;
    private android.widget.TextView txtSubmit;
    private android.widget.TextView txtCancel;
    private String filename_string = "";
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dilaog_raise_ticket);

        UiInitialization();
    }

    private void UiInitialization() {

        this.txtCancel = (TextView) findViewById(R.id.txtCancel);
        this.linearPickTicketImage = (LinearLayout) findViewById(R.id.linearPickTicketImage);
        this.linearSetImage = (LinearLayout) findViewById(R.id.linearSetImage);
        this.linearSetImage.setVisibility(View.GONE);
        this.linearPickTicketImage.setVisibility(View.VISIBLE);
        this.txtCancel.setOnClickListener(this);
        this.txtSubmit = (TextView) findViewById(R.id.txtSubmit);
        this.txtSubmit.setOnClickListener(this);
        this.imgPickTicketImage = (ImageView) findViewById(R.id.imgPickTicketImage);
        this.imgPickTicketImageSet = (ImageView) findViewById(R.id.imgPickTicketImageSet);
        this.imgPickTicketImageSet.setOnClickListener(this);
        this.imgPickTicketImage.setOnClickListener(this);
        this.edtMessages = (EditText) findViewById(R.id.edtMessages);
        this.edtSubject = (EditText) findViewById(R.id.edtSubject);

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Raise Ticket");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.txtCancel:

                break;
            case R.id.txtSubmit:
                checkValidation();
                break;

            case R.id.imgPickTicketImage:
                UploadProfilePic();
                break;
            case R.id.imgPickTicketImageSet:
                UploadProfilePic();
                break;

        }

    }

    private void checkValidation() {


        if (!Utils.hasText(edtSubject)) {
            Toast.makeText(this, "Enter subject", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Utils.hasText(edtMessages)) {
            Toast.makeText(this, "Enter Message", Toast.LENGTH_SHORT).show();
            return;
        } else {
            submitTicket();
        }
    }

    private void submitTicket() {


        progressDialog = Utils.createProgressDialog(this);
        Map<String, String> data = new HashMap<>();
        data.put("subject", edtSubject.getText().toString());
        data.put("message", edtMessages.getText().toString());
        data.put("image", filename_string);

        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("joblist  key, " + key + " value " + value);
        }


        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        // String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTU2IiwidXNlcm5hbWUiOiJwcm0ubW9oYW5yYWpAZ21haWwuY29tIn0sImlhdCI6MTUxOTAyOTg1NSwianRpIjoiYWpKbFpGTjFRbXN5YkZOQ1UzSlBabkpVVGtJPSIsImlzcyI6Imh0dHBzOlwvXC9iYWNrZW5kLnlvdXRoaHViLmNvLm56XC8iLCJuYmYiOjE1MTkwMjk4NTUsImV4cCI6MTUxOTYzNDY1NX0.2yS4aD808K8PXS05h0Ky-zMTOeqsY5XfsPyeSzXJOCI";

        String token = Pref.getPreToken();
        Log.d(TAG, "changePassword: token " + token);

        Call<DataTicketRaiseITem> call = apiService.sumbitTicketCreate(token, data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataTicketRaiseITem>() {
            @Override
            public void onResponse(Call<DataTicketRaiseITem> call, Response<DataTicketRaiseITem> response) {

                try {

                    progressDialog.dismiss();
                    if (response.code() == 200) {
                        if (response.body().getStatus().equals("1")) {

                           /* Intent returnIntent = new Intent();
                            returnIntent.putExtra("ticket_id", response.body().getData().getTicket_id());
                            returnIntent.putExtra("post_date", response.body().getData().getPost_date());
                            returnIntent.putExtra("subject", response.body().getData().getSubject());
                            returnIntent.putExtra("replies", response.body().getData().getReplies());
                            returnIntent.putExtra("ticket_status_id", response.body().getData().getTicket_status_id());
                            returnIntent.putExtra("ticket_status", response.body().getData().getTicket_status());
                            returnIntent.putExtra("user_id", response.body().getData().getUser_id());
                            returnIntent.putExtra("first_name", response.body().getData().getFirst_name());
                            returnIntent.putExtra("last_name", response.body().getData().getLast_name());
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
*/
                            Intent mIntent = new Intent(TicketRaiseActivity.this, ContactSupportActivity.class);
                            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mIntent);
                            finish();

                        } else {
                            Toast.makeText(TicketRaiseActivity.this, "No record available", Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 304) {

                        Toast.makeText(TicketRaiseActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(TicketRaiseActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(TicketRaiseActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(TicketRaiseActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(TicketRaiseActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(TicketRaiseActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(TicketRaiseActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<DataTicketRaiseITem> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void UploadProfilePic() {

        selectFile();
        //  Intent intent = new Intent();
        //  intent.setType("*/*");
        //  intent.setAction(Intent.ACTION_GET_CONTENT);
        //  startActivityForResult(Intent.createChooser(intent, "Select your file"), PICK_GALLERY_IMAGE_REQUEST_CODE);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!nbIsNetworkAvailable(this)) {
            nbShowNoInternet(this);
        }
    }

    private void selectFile() {

        final CharSequence[] options = {"File", "Image", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(TicketRaiseActivity.this);
        builder.setTitle("Attach");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("File")) {

                    FileChoosenType = 0;
                //    Intent i = new Intent();
                 //   i.setType("*/*");
                 //   i.setAction(Intent.ACTION_GET_CONTENT);
                 //   i.addCategory(Intent.CATEGORY_OPENABLE);
                  //  startActivityForResult(Intent.createChooser(i, "abc"), RESULT_LOAD_FILE);
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    startActivityForResult(intent,RESULT_LOAD_FILE);

                } else if (options[item].equals("Image")) {
                    FileChoosenType = 1;
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, RESULT_LOAD_FILE_IMG);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            if (requestCode == RESULT_LOAD_FILE_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                FileMediaPath = cursor.getString(columnIndex);
                cursor.close();

                // Set the Image in ImageView after decoding the String
                imgPickTicketImage.setImageBitmap(BitmapFactory
                        .decodeFile(FileMediaPath));

                uploadProfile(FileMediaPath);

            } else if (requestCode == RESULT_LOAD_FILE && resultCode == RESULT_OK
                    && null != data) {
                selectedPdf = data.getData();
                System.out.println("Uri of selected pdf URI ---->" + selectedPdf);
                System.out.println("Uri of selected pdf---->" + selectedPdf.getPath());
                File myFile = new File(selectedPdf.getPath());
                String path = myFile.getAbsolutePath();
                System.out.println("Uri of selected pdf path ---->" +path);
                if (selectedPdf.getPath().endsWith(".docx")) {

                    FileChoosenType_style = 0;
                    // Set the Image in ImageView after decoding the String
                 //   imgPickTicketImageSet.setImageResource(R.drawable.docx_icon);
                    imgPickTicketImage.setImageResource(R.drawable.docx_icon);
                    System.out.println("Uri of selected pdf---->" + selectedPdf.getPath());

                    File file = new File(selectedPdf.getPath());
                    filePath = file.getPath();
                    uploadProfile(filePath);


                } else if (selectedPdf.getPath().endsWith(".pdf")) {

                    FileChoosenType_style = 1;
                    // Set the Image in ImageView after decoding the String
                  //  imgPickTicketImageSet.setImageResource(R.drawable.pdf_thumb);
                    imgPickTicketImage.setImageResource(R.drawable.pdf_thumb);
                    System.out.println("Uri of selected pdf---->" + selectedPdf.getPath());

                    File file = new File(selectedPdf.getPath());
                    filePath = file.getPath();
                    uploadProfile(filePath);

                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Invalid file type", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Invalid file types", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
        /*switch (requestCode) {
            case PICK_GALLERY_IMAGE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {

                    Uri selectedImageUri = data.getData();
                    if (null != selectedImageUri) {

                        try {
                            this.linearPickTicketImage.setVisibility(View.GONE);
                            this.linearSetImage.setVisibility(View.VISIBLE);
                            Log.d(TAG, "onActivityResult: selectedImageUri " + selectedImageUri);


                            String realPath = getPath(selectedImageUri);
                            Log.i(TAG, "onActivityResult: Image Path_s_realPath : " + realPath);


                            Picasso.with(this)
                                    .load(selectedImageUri)
                                    .placeholder(R.drawable.profile_pic) //this is optional the image to display while the url image is downloading
                                    .error(R.drawable.profile_pic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                    .into(this.imgPickTicketImageSet);


                            uploadProfile(realPath);


                        } catch (Exception e) {

                            e.printStackTrace();
                        }

                    }
                    break;
                } else if (requestCode == RESULT_LOAD_FILE && resultCode == RESULT_OK
                        && null != data) {
                    selectedPdf = data.getData();
                    System.out.println("Uri of selected pdf---->" + selectedPdf.getPath().toString());
                    //   PdfSelected.setVisibility(View.VISIBLE);
                    if (selectedPdf.getPath().endsWith(".pdf")) {

                        FileChoosenType_style =1;
                        // Set the Image in ImageView after decoding the String
                        imgPickTicketImageSet.setImageResource(R.drawable.pdf_thumb);

                        System.out.println("Uri of selected pdf---->" + selectedPdf.getPath().toString());


                        uploadProfile(selectedPdf.getPath());

                    }else{
                        Toast.makeText(this, "Select pdf document", Toast.LENGTH_SHORT).show();
                    }

                }
        }*/

    }

    private void uploadProfile(String realPath) {


        if (!realPath.equals("") && !realPath.equals(null)) {
            progressDialog = Utils.createProgressDialog(this);
            File file = new File(realPath);

            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

            YouthHubApi apiService =
                    ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

            Call<DataUploadProfilePic> call = apiService.uploadSupportImage(Pref.getPreToken(), fileToUpload);
            // Call<DataImageUploadSupport> call = apiService.uploadSupportImage(fileToUpload);
            call.enqueue(new Callback<DataUploadProfilePic>() {
                @Override
                public void onResponse(Call<DataUploadProfilePic> call, Response<DataUploadProfilePic> response) {

                    progressDialog.dismiss();
                    if (response.code() == 200) {

                        DataUploadProfilePic dataUploadProfilePic = response.body();

                        if (dataUploadProfilePic.getStatus().equals("1")) {

                            try {

                                if (response.body().getStatus().equals("1")) {
                                    filename_string = response.body().getData().getFilename();

                                    Log.d(TAG, "onResponse: filename_string " + filename_string);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else if (response.code() == 201) {
                        Toast.makeText(TicketRaiseActivity.this, "201 Created", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 304) {
                        Toast.makeText(TicketRaiseActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {
                        Toast.makeText(TicketRaiseActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(TicketRaiseActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {
                        Toast.makeText(TicketRaiseActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {
                        Toast.makeText(TicketRaiseActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {
                        Toast.makeText(TicketRaiseActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(TicketRaiseActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

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

}
