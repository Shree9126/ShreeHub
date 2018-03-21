package com.mindnotix.youthhub;

import android.app.Dialog;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.adapter.TicketViewSupportAdapter;
import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.DataUploadProfilePic;
import com.mindnotix.model.contactsupport.reply.DataTicketReplyITem;
import com.mindnotix.model.contactsupport.ticket_cancel.DataTicketCancelItem;
import com.mindnotix.model.contactsupport.view.DataContactSupportTicketVIew;
import com.mindnotix.model.contactsupport.view.Supportdetailview;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 2/20/2018.
 */

public class TicketViewActivity extends BaseActivity implements View.OnClickListener {


    private static final String TAG = TicketViewActivity.class.getSimpleName();
    private static final int PICK_GALLERY_IMAGE_REQUEST_CODE = 200;
    ProgressDialog progressDialog;
    String ticket_id = "";
    ArrayList<Supportdetailview> supportdetailviewArrayList = new ArrayList<>();
    RecyclerViewClickListener listener;
    TicketViewSupportAdapter ticketViewSupportAdapter;
    private android.widget.TextView txtTicketNo;
    private android.widget.TextView txtTicketCreateBy;
    private android.widget.TextView txtTicketDateTime;
    private android.widget.TextView txtReply;
    private android.widget.TextView txtCancelView;
    private android.widget.TextView TicketName;
    private android.support.v7.widget.RecyclerView recyclerView;
    private android.widget.EditText edtSubject;
    private android.widget.ImageView imgPickTicketImage;
    private android.widget.LinearLayout linearPickTicketImage;
    private android.widget.ImageView imgPickTicketImageSet;
    private android.widget.LinearLayout linearSetImage;
    private TextView txtSubmit;
    private ImageView imgReplyClose;
    private TextView txtCancel;
    private Toolbar toolbar;
    private String image_fileName = "";
    private String hmStatus;
    private View cancelView;
    private View replyView;
    private View mainTicketView;
    private static int RESULT_LOAD_IMG = 1, RESULT_LOAD_FILE = 0, RESULT_LOAD_FILE_IMG = 2;
    public int FileChoosenType, FileChoosenType_style;
    Uri selectedPdf;
    String mediaPath = "", filePath = "", FileMediaPath = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_view);
        if (getIntent() != null) {
            ticket_id = getIntent().getStringExtra("ticket_id");
            Log.d(TAG, "onCreate: ticket_id " + ticket_id);
        } else {
            ticket_id = "";
        }
        UiInitialization();
        listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {


            }
        };
        ticketViewSupportAdapter = new TicketViewSupportAdapter(this, supportdetailviewArrayList, listener);
        recyclerView.setAdapter(ticketViewSupportAdapter);

        getTicketViewDetails();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtCancelView:
                DeleteDilaog();
                break;
            case R.id.txtReply:
                DialogReply();
                break;
        }
    }

    private void DialogReply() {


        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_ticket_reply);

        txtCancel = (TextView) dialog.findViewById(R.id.txtCancel);
        txtSubmit = (TextView) dialog.findViewById(R.id.txtSubmit);
        imgReplyClose = (ImageView) dialog.findViewById(R.id.imgRegpply);
        linearSetImage = (LinearLayout) dialog.findViewById(R.id.linearSetImage);
        imgPickTicketImageSet = (ImageView) dialog.findViewById(R.id.imgPickTicketImageSet);
        linearPickTicketImage = (LinearLayout) dialog.findViewById(R.id.linearPickTicketImage);
        imgPickTicketImage = (ImageView) dialog.findViewById(R.id.imgPickTicketImage);
        edtSubject = (EditText) dialog.findViewById(R.id.edtSubject);

        //dialog.setTitle("Title...");

        imgReplyClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        imgPickTicketImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //picImage();
                selectFile();
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // if button is clicked, close the custom dialog
        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkValidation();
                dialog.dismiss();

            }
        });

        dialog.show();
    }


    private void selectFile() {

        final CharSequence[] options = {"File", "Image", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(TicketViewActivity.this);
        builder.setTitle("Attach");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("File")) {

                    FileChoosenType = 0;

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
                            //this.linearPickTicketImage.setVisibility(View.GONE);
                            // this.linearSetImage.setVisibility(View.VISIBLE);
                            Log.d(TAG, "onActivityResult: selectedImageUri " + selectedImageUri);


                            String realPath = getPath(selectedImageUri);
                            Log.i(TAG, "onActivityResult: Image Path_s_realPath : " + realPath);


                            Picasso.with(this)
                                    .load(selectedImageUri)
                                    .placeholder(R.drawable.profile_pic) //this is optional the image to display while the url image is downloading
                                    .error(R.drawable.profile_pic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                    .into(this.imgPickTicketImage);


                            uploadProfile(realPath);


                        } catch (Exception e) {

                            e.printStackTrace();
                        }

                    }
                    break;
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
                    Log.d(TAG, "onResponse: ticket reply dialog image " + new Gson().toJson(response.body()));
                    if (response.code() == 200) {


                        try {
                            DataUploadProfilePic dataUploadProfilePic = response.body();
                            if (dataUploadProfilePic.getStatus().equals("1")) {


                                image_fileName = dataUploadProfilePic.getData().getFilename();
                                Log.d(TAG, "replyTicket: image_fileName gal " + image_fileName);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else if (response.code() == 201) {
                        Toast.makeText(TicketViewActivity.this, "201 Created", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 304) {
                        Toast.makeText(TicketViewActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {
                        Toast.makeText(TicketViewActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(TicketViewActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {
                        Toast.makeText(TicketViewActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {
                        Toast.makeText(TicketViewActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {
                        Toast.makeText(TicketViewActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(TicketViewActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

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


        if (!Utils.hasText(edtSubject)) {
            Toast.makeText(this, "Enter Message", Toast.LENGTH_SHORT).show();
            return;
        } else {
            replyTicket(edtSubject.getText().toString());
        }
    }

    private void replyTicket(String message) {

        Log.d(TAG, "replyTicket: image_fileName " + image_fileName);
        progressDialog = Utils.createProgressDialog(this);
        Map<String, String> data = new HashMap<>();
        data.put("ticket_id", ticket_id);
        data.put("message", message);
        data.put("image", image_fileName);

        String token = Pref.getPreToken();
        Log.d(TAG, "logMeIn onResponse: token " + token);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataTicketReplyITem> call = apiService.replyTicket(token, data);
        Log.d(TAG, "favorioute: url " + call.request().url());

        call.enqueue(new Callback<DataTicketReplyITem>() {
            @Override
            public void onResponse(Call<DataTicketReplyITem> call, Response<DataTicketReplyITem> response) {
                progressDialog.dismiss();
                if (response.code() == 200 || response.code() == 201) {

                    if (response.body().getStatus().equals("1")) {

                        Toast.makeText(TicketViewActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        Intent mIntent = new Intent(TicketViewActivity.this, TicketViewActivity.class);
                        mIntent.putExtra("ticket_id", ticket_id);
                        startActivity(mIntent);
                        finish();
                    } else {
                        Toast.makeText(TicketViewActivity.this, "Failed, Try again later", Toast.LENGTH_SHORT).show();
                    }

                } else if (response.code() == 304) {

                    Toast.makeText(TicketViewActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(TicketViewActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(TicketViewActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(TicketViewActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(TicketViewActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(TicketViewActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(TicketViewActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<DataTicketReplyITem> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }

    private void picImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Pic"), PICK_GALLERY_IMAGE_REQUEST_CODE);

    }

    public void DeleteDilaog() {


        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_delete);
        Button btCancel = dialog.findViewById(R.id.btCancel);
        btCancel.setText("No");
        //  btCancel.setBackgroundColor(getResources().getColor(R.color.grey_500));
        //  btCancel.setTextColor(getResources().getColor(R.color.black));
        TextView txtHead = dialog.findViewById(R.id.txtTitle);
        txtHead.setText("Cancel Ticket");

        TextView txtTitle = dialog.findViewById(R.id.text_dialog);
        txtTitle.setText("Do you really want to cancel this ticket.?");
        txtTitle.setAllCaps(false);
        Button btOkay = dialog.findViewById(R.id.btOkay);
        btOkay.setText("Yes");
        // btOkay.setTextColor(getResources().getColor(R.color.white));
        //  btOkay.setBackgroundColor(getResources().getColor(R.color.medium_level_blue));

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        btOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                cancelTicket();

                dialog.dismiss();

            }
        });
        dialog.show();

    }

    private void cancelTicket() {

        progressDialog = Utils.createProgressDialog(this);
        Map<String, String> data = new HashMap<>();
        data.put("ticket_id", ticket_id);

        String token = Pref.getPreToken();
        Log.d(TAG, "logMeIn onResponse: token " + token);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataTicketCancelItem> call = apiService.cancelTicket(token, data);
        Log.d(TAG, "favorioute: url " + call.request().url());

        call.enqueue(new Callback<DataTicketCancelItem>() {
            @Override
            public void onResponse(Call<DataTicketCancelItem> call, Response<DataTicketCancelItem> response) {
                progressDialog.dismiss();
                if (response.code() == 200 || response.code() == 201) {

                    if (response.body().getStatus().equals("1")) {

                        Toast.makeText(TicketViewActivity.this, "Ticket canceled successfully", Toast.LENGTH_SHORT).show();
                        Intent mIntent = new Intent(TicketViewActivity.this, ContactSupportActivity.class);
                        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mIntent);
                        finish();
                    } else {
                        Toast.makeText(TicketViewActivity.this, "You can't cancel ticket now, Try again later", Toast.LENGTH_SHORT).show();
                    }

                } else if (response.code() == 304) {

                    Toast.makeText(TicketViewActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(TicketViewActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(TicketViewActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(TicketViewActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(TicketViewActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(TicketViewActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(TicketViewActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<DataTicketCancelItem> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }

    private void UiInitialization() {
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.txtCancelView = (TextView) findViewById(R.id.txtCancelView);
        this.txtCancelView.setOnClickListener(this);
        this.txtReply = (TextView) findViewById(R.id.txtReply);
        this.txtReply.setOnClickListener(this);
        this.txtTicketDateTime = (TextView) findViewById(R.id.txtTicketDateTime);
        this.txtTicketCreateBy = (TextView) findViewById(R.id.txtTicketCreateBy);
        this.txtTicketNo = (TextView) findViewById(R.id.txtTicketNo);
        this.TicketName = (TextView) findViewById(R.id.TicketName);
        cancelView = findViewById(R.id.cancelView);
        replyView = findViewById(R.id.replyView);
        mainTicketView = findViewById(R.id.mainTicketView);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //    getSupportActionBar().setTitle("Job list");

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

    private void getTicketViewDetails() {


        progressDialog = Utils.createProgressDialog(this);

        Map<String, String> data = new HashMap<>();
        if (ticket_id != null)
            data.put("ticket_id", ticket_id);
        else
            data.put("ticket_id", "");

        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("joblist  key, " + key + " value " + value);
        }


        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        //String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTU2IiwidXNlcm5hbWUiOiJwcm0ubW9oYW5yYWpAZ21haWwuY29tIn0sImlhdCI6MTUxOTAyOTg1NSwianRpIjoiYWpKbFpGTjFRbXN5YkZOQ1UzSlBabkpVVGtJPSIsImlzcyI6Imh0dHBzOlwvXC9iYWNrZW5kLnlvdXRoaHViLmNvLm56XC8iLCJuYmYiOjE1MTkwMjk4NTUsImV4cCI6MTUxOTYzNDY1NX0.2yS4aD808K8PXS05h0Ky-zMTOeqsY5XfsPyeSzXJOCI";

        String token = Pref.getPreToken();
        Log.d(TAG, "changePassword: token " + token);

        Call<DataContactSupportTicketVIew> call = apiService.getTicketDetails(token, data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataContactSupportTicketVIew>() {
            @Override
            public void onResponse(Call<DataContactSupportTicketVIew> call, Response<DataContactSupportTicketVIew> response) {

                Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));
                try {

                    progressDialog.dismiss();
                    if (response.code() == 200) {
                        mainTicketView.setVisibility(View.VISIBLE);
                        if (response.body().getStatus().equals("1")) {
                            TicketName.setText(response.body().getData().getSupportview().get(0).getSubject());
                            txtTicketDateTime.setText(response.body().getData().getSupportview().get(0).getDate()
                                    .concat(" " + response.body().getData().getSupportview().get(0).getTime()));
                            txtTicketCreateBy.setText(response.body().getData().getSupportview().get(0).getFirst_name()
                                    .concat(" " + response.body().getData().getSupportview().get(0).getLast_name()));
                            txtTicketNo.setText(response.body().getData().getSupportview().get(0).getTicket_id());
                            hmStatus = response.body().getData().getSupportview().get(0).getHm_status();

                            if (hmStatus.equals("5")) {
                                replyView.setVisibility(View.GONE);
                                cancelView.setVisibility(View.VISIBLE);
                                //  activity.getResources().getColor(R.color.viloet)
                            } else {
                                replyView.setVisibility(View.VISIBLE);
                                cancelView.setVisibility(View.GONE);
                            }


                            supportdetailviewArrayList.addAll(response.body().getData().getSupportdetailview());
                            ticketViewSupportAdapter.notifyDataSetChanged();
                        } else {

                            Toast.makeText(TicketViewActivity.this, "No record available", Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 304) {

                        Toast.makeText(TicketViewActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(TicketViewActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(TicketViewActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(TicketViewActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(TicketViewActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(TicketViewActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(TicketViewActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<DataContactSupportTicketVIew> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }


}
