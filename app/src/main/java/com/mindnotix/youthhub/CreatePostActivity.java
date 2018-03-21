package com.mindnotix.youthhub;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.adapter.CustomTagsAdapter;
import com.mindnotix.adapter.ImageAdapter;
import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.dashboard.DataDashboardTimeLine;
import com.mindnotix.model.dashboard.image_upload.DataMultiImgSingleImgUpload;
import com.mindnotix.model.dashboard.tags.addtags.DataAddNewTag;
import com.mindnotix.model.dashboard.tags.taglist.Data;
import com.mindnotix.model.dashboard.tags.taglist.DataPostTagList;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.CompressImage;
import com.mindnotix.utils.ImageFilePath;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;
import com.squareup.picasso.Picasso;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.filter.entity.ImageFile;
import com.vincent.filepicker.filter.entity.VideoFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vincent.filepicker.activity.ImagePickActivity.IS_NEED_CAMERA;

/**
 * Created by Sridharan on 2/2/2018.
 */

public class CreatePostActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = CreatePostActivity.class.getSimpleName();
    private static final int CAMERA_REQUEST = 1888;
    ImageAdapter imageAdapter = null;
    ProgressDialog progressDialog;
    ArrayList<Data> dataPostTagListArrayList = new ArrayList<>();
    ArrayList<Data> dataPostTagListArrayTempList = new ArrayList<>();
    CustomTagsAdapter customTagsAdapter;
    String selected_tags_ID;
    String tags_id = "";
    String images_name_picker_gallery = "";
    ArrayList<String> image_name_gallery_list = new ArrayList<>();
    String images_name_picker_camera = "";
    ArrayList<String> arrayListTagNames = new ArrayList<>();
    ArrayList<String> arrayListTagNamess = new ArrayList<>();
    ArrayList<String> arrayListTagNamessTempRemove = new ArrayList<>();
    ArrayList<String> arrayListTagNamesSample = new ArrayList<>();
    int arrayListTagNamesSampleRemove = 0;
    ArrayList<String> arrayListTagsID = new ArrayList<>();
    ArrayList<String> arrayListTagsIDD = new ArrayList<>();
    ArrayList<String> arrayListTagsIDDTempRemove = new ArrayList<>();
    int tagEventsFlag = 0;
    int tagTrainingFlag = 0;
    int tagMyInterestFlag = 0;
    int tagEmploymentFlag = 0;
    int tagVolunteeringFlag = 0;
    int tagNewCreateFlag = 0;
    int tagEducationFlag = 0;
    int tagCommunityFlag = 0;
    ArrayAdapter<String> adapter;
    ArrayList<String> filePaths = new ArrayList<>();
    RecyclerViewClickListener listener;
    ArrayList<String> uploadedPaths = new ArrayList<>();
    private Toolbar toolbar;
    private CircleImageView imgProfileImage;
    private EditText edtPost;
    private ImageView imgVideoImageThumbnail;
    private LinearLayout linearCameraVideo;
    private LinearLayout linearAddTags;
    private ImageView imgTakeImage;
    private ImageView image_view;
    private LinearLayout linearCameraImages;
    private LinearLayout linearCustomTagsList;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewCustomTagList;
    private LinearLayout linearImages;
    private TextView tagEducation;
    private TextView txtAddNewTag;
    private TextView tagTraining;
    private TextView tagEmployment;
    private TextView tagVolunteering;
    private TextView tagCommunity;
    private TextView tagNewCreate;
    private TextView tagAddCustomTag;
    private TextView tagMyInterest;
    private TextView txtCustomTag;
    private TextView tagEvents;
    private EditText edtAddTags;
    private android.widget.MultiAutoCompleteTextView multiAutoTextViewTag;
    private EditText edtAddTagsList;
    private LinearLayout addTags;
    private LinearLayout linearTags;
    private LinearLayout linearPickTags;
    private LinearLayout linearPickImages;
    private ImageView ImgMediaImage;
    private LinearLayout linearPickCamera;
    private LinearLayout linearPickVideos;
    private Button btnCreatePost;
    private View addNewTagView;
    private boolean isCameraUpload = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        UiInitalization();

        getPostTagList();


    }

    private void getPostTagList() {

        progressDialog = Utils.createProgressDialog(this);
        //String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTQyIiwidXNlcm5hbWUiOiJnb3d0aGFtODc2QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTc1NTQxNjksImp0aSI6IlR6UkhkWE5MYjI1blQwSkllbkZpY0VwTVdtUT0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE3NTU0MTY5LCJleHAiOjE1MTgxNTg5Njl9.Irk5s-f_i2AmK9y7xF_nhsExKckfpdK_GyC7Xt0ypAs";
        String token = Pref.getPreToken();
        // Log.d(TAG, "getPostTagList: "+token);
        //RXRetro.getInstance().retrofitEnque(ApiClient.getInterface().getPostTagList(token), this, 2);

        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


        //token = "Bearer "+ Pref.getPreToken();


        Log.d(TAG, "changePassword: token " + token);

        Call<DataPostTagList> call = apiService.getPostTagList(token);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataPostTagList>() {
            @Override
            public void onResponse(Call<DataPostTagList> call, Response<DataPostTagList> response) {

                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("1")) {

                        //progressDialog.dismiss();
                        dataPostTagListArrayList.addAll(response.body().getData());

                        customTagsAdapter = new CustomTagsAdapter(dataPostTagListArrayList);
                        recyclerViewCustomTagList.setAdapter(customTagsAdapter);
                        for (int i = 0; i < dataPostTagListArrayList.size(); i++) {

                            arrayListTagNames.add(dataPostTagListArrayList.get(i).getPtg_name());
                            arrayListTagsID.add(dataPostTagListArrayList.get(i).getPtg_tag_id());

                        }


                    } else {
                        //  progressDialog.dismiss();
                        Toast.makeText(CreatePostActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 304) {
                    /// progressDialog.dismiss();
                    Toast.makeText(CreatePostActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    // progressDialog.dismiss();
                    Toast.makeText(CreatePostActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    // progressDialog.dismiss();
                    Toast.makeText(CreatePostActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    // progressDialog.dismiss();
                    Toast.makeText(CreatePostActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    //  progressDialog.dismiss();
                    Toast.makeText(CreatePostActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {
                    // progressDialog.dismiss();
                    Toast.makeText(CreatePostActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {
                    // progressDialog.dismiss();
                    Toast.makeText(CreatePostActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<DataPostTagList> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }

    public void showChangeLangDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog_list, null);
        dialogBuilder.setView(dialogView);

        final EditText filterText = (EditText) dialogView.findViewById(R.id.edit1);
        final RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.jobWishList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customTagsAdapter = new CustomTagsAdapter(dataPostTagListArrayList);
        recyclerView.setAdapter(customTagsAdapter);
        dialogBuilder.setTitle("Select tags");

        filterText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.d(TAG, "onTextChanged: char " + s);
                dataPostTagListArrayTempList.clear();
                //something here
                for (Data d : dataPostTagListArrayList) {
                    if (d.getPtg_name() != null && d.getPtg_name().contains(s)) {
                        dataPostTagListArrayTempList.add(d);
                    }

                }
                Log.d(TAG, "onTextChanged:size  " + dataPostTagListArrayTempList.size());
                customTagsAdapter = new CustomTagsAdapter(dataPostTagListArrayTempList);
                recyclerView.setAdapter(customTagsAdapter);
                customTagsAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();

                String display_checked_days = "";
                String display_checked_id = "";
                for (int i = 0; i < dataPostTagListArrayList.size(); i++) {
                    if (dataPostTagListArrayList.get(i).getStatus().equals("1")) {

                        display_checked_days = display_checked_days + dataPostTagListArrayList.get(i).getPtg_name() + ", ";

                        display_checked_id = display_checked_id + "," + dataPostTagListArrayList.get(i).getPtg_tag_id();
                        Log.d(TAG, "ssssssonClick: " + display_checked_id);
                        Log.d(TAG, "ssssssonClick: " + display_checked_days);

                        if (!arrayListTagNamesSample.contains(dataPostTagListArrayList.get(i).getPtg_name()))
                            arrayListTagNamesSample.add(dataPostTagListArrayList.get(i).getPtg_name());

                        Log.d(TAG, "onClick:s " + tags_id);
                        Log.d(TAG, "onClick:s " + tags_id);
                    } else {
                        // txtCustomTag.setText("");
                    }
                }


                if (dataPostTagListArrayList.size() != 0) {
                    addNewTagView.setVisibility(View.VISIBLE);
                    String txtCustomTagsss = "";

                    for (String items : arrayListTagNamesSample) {

                        Log.d(TAG, "onClick:dataPostTagListArrayList items " + items);

                        txtCustomTagsss = txtCustomTagsss + items + ", ";


                    }
                    addNewTagView.setVisibility(View.VISIBLE);

                    if (txtCustomTagsss.length() > 0 && txtCustomTagsss.charAt(txtCustomTagsss.length() - 2) == ',') {
                        txtCustomTagsss = txtCustomTagsss.substring(0, txtCustomTagsss.length() - 2);
                    }
                    txtCustomTag.setText(txtCustomTagsss + ".");

                    Log.d(TAG, "onClick: dataPostTagListArrayList if " + txtCustomTagsss);
                } else {
                    addNewTagView.setVisibility(View.GONE);
                    txtCustomTag.setText("");
                    Log.d(TAG, "onClick: dataPostTagListArrayList if else");
                }


            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
                if (txtCustomTag.getText().toString().length() > 0) {

                    addNewTagView.setVisibility(View.VISIBLE);
                } else {
                    addNewTagView.setVisibility(View.GONE);
                }
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void UiInitalization() {

        this.btnCreatePost = (Button) findViewById(R.id.btnCreatePost);
        this.btnCreatePost.setOnClickListener(this);

        this.linearPickVideos = (LinearLayout) findViewById(R.id.linearPickVideos);
        this.linearPickVideos.setOnClickListener(this);
        this.linearPickCamera = (LinearLayout) findViewById(R.id.linearPickCamera);
        this.linearPickCamera.setOnClickListener(this);

        this.ImgMediaImage = (ImageView) findViewById(R.id.ImgMediaImage);
        this.linearPickImages = (LinearLayout) findViewById(R.id.linearPickImages);
        this.linearPickImages.setOnClickListener(this);
        this.ImgMediaImage.setOnClickListener(this);

        this.linearAddTags = (LinearLayout) findViewById(R.id.linearAddTags);
        this.linearAddTags.setOnClickListener(this);

        this.linearPickTags = (LinearLayout) findViewById(R.id.linearPickTags);
        this.linearPickTags.setOnClickListener(this);

        this.linearCustomTagsList = (LinearLayout) findViewById(R.id.linearCustomTagsList);
        this.linearCustomTagsList.setOnClickListener(this);

        this.linearTags = (LinearLayout) findViewById(R.id.linearTags);
        //this.addTags = (LinearLayout) findViewById(R.id.addTags);
        this.edtAddTags = (EditText) findViewById(R.id.edtAddTags);


        this.tagEvents = (TextView) findViewById(R.id.tagEvents);
        this.tagEvents.setOnClickListener(this);
        this.txtCustomTag = (TextView) findViewById(R.id.txtCustomTag);

        this.tagMyInterest = (TextView) findViewById(R.id.tagMyInterest);
        this.tagMyInterest.setOnClickListener(this);
        this.tagCommunity = (TextView) findViewById(R.id.tagCommunity);
        this.tagCommunity.setOnClickListener(this);

        this.tagNewCreate = (TextView) findViewById(R.id.tagNewCreate);
        this.tagNewCreate.setOnClickListener(this);
        this.tagAddCustomTag = (TextView) findViewById(R.id.tagAddCustomTag);
        this.tagAddCustomTag.setOnClickListener(this);


        this.tagVolunteering = (TextView) findViewById(R.id.tagVolunteering);
        this.tagVolunteering.setOnClickListener(this);
        this.tagEmployment = (TextView) findViewById(R.id.tagEmployment);
        this.tagEmployment.setOnClickListener(this);
        this.tagTraining = (TextView) findViewById(R.id.tagTraining);
        this.tagTraining.setOnClickListener(this);
        this.tagEducation = (TextView) findViewById(R.id.tagEducation);
        this.tagEducation.setOnClickListener(this);
        this.txtAddNewTag = (TextView) findViewById(R.id.txtAddNewTag);
        this.txtAddNewTag.setOnClickListener(this);
        this.linearImages = (LinearLayout) findViewById(R.id.linearImages);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.recyclerViewCustomTagList = (RecyclerView) findViewById(R.id.recyclerViewCustomTagList);
        this.recyclerViewCustomTagList.setLayoutManager(new LinearLayoutManager(this));
        this.linearCameraImages = (LinearLayout) findViewById(R.id.linearCameraImages);
        this.imgTakeImage = (ImageView) findViewById(R.id.imgTakeImage);
        this.image_view = (ImageView) findViewById(R.id.image_view);
        this.linearCameraVideo = (LinearLayout) findViewById(R.id.linearCameraVideo);
        this.imgVideoImageThumbnail = (ImageView) findViewById(R.id.imgVideoImageThumbnail);
        this.edtPost = (EditText) findViewById(R.id.edtPost);
        this.imgProfileImage = (CircleImageView) findViewById(R.id.imgProfileImage);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.addNewTagView = findViewById(R.id.addNewTagView);

        Log.d(TAG, "onCreateView: profile path = " + Pref.getClientImageThumbnailPath().concat(Pref.getClientProfilePictureName()));
        Picasso.with(this)
                .load(Pref.getClientImageThumbnailPath().concat(Pref.getClientProfilePictureName()))
                .placeholder(R.drawable.profile_pic) //this is optional the image to display while the url image is downloading
                .error(R.drawable.profile_pic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                .into(imgProfileImage);


        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Create Post");

        } catch (Exception e) {
            e.printStackTrace();
        }


        edtPost.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    nbHideKeyboard();
                }
            }
        });

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

            case R.id.tagCommunity:

                if (tagCommunityFlag == 0) {

                    arrayListTagNamesSample.add("Community");
                    tagCommunity.setBackgroundResource(R.color.Aqua);
                    tagCommunityFlag = 1;
                } else {

                    arrayListTagNamesSample.remove("Community");
                    tagCommunity.setBackgroundColor(Color.LTGRAY);
                    tagCommunityFlag = 0;
                }

                break;

            case R.id.tagEducation:
                if (tagEducationFlag == 0) {

                    arrayListTagNamesSample.add("Education");
                    tagEducation.setBackgroundResource(R.color.Aqua);
                    tagEducationFlag = 1;

                } else {
                    arrayListTagNamesSample.remove("Education");

                    tagEducation.setBackgroundColor(Color.LTGRAY);
                    tagEducationFlag = 0;
                }
                break;

            case R.id.tagEmployment:

                if (tagEmploymentFlag == 0) {
                    arrayListTagNamesSample.add("Employment");
                    tagEmployment.setBackgroundResource(R.color.Aqua);
                    tagEmploymentFlag = 1;
                } else {

                    arrayListTagNamesSample.remove("Employment");
                    tagEmployment.setBackgroundColor(Color.LTGRAY);
                    tagEmploymentFlag = 0;
                }
                break;

            case R.id.tagEvents:

                if (tagEventsFlag == 0) {
                    arrayListTagNamesSample.add("Events");
                    tagEvents.setBackgroundResource(R.color.Aqua);
                    tagEventsFlag = 1;
                } else {
                    arrayListTagNamesSample.remove("Events");
                    tagEvents.setBackgroundColor(Color.LTGRAY);
                    tagEventsFlag = 0;
                }
                break;

            case R.id.tagMyInterest:

                if (tagMyInterestFlag == 0) {
                    arrayListTagNamesSample.add("My Interests");
                    tagMyInterestFlag = 1;
                    tagMyInterest.setBackgroundResource(R.color.Aqua);
                } else {
                    tagMyInterestFlag = 0;
                    arrayListTagNamesSample.remove("My Interests");

                    tagMyInterest.setBackgroundColor(Color.LTGRAY);
                }
                break;
            case R.id.tagTraining:

                if (tagTrainingFlag == 0) {
                    tagTrainingFlag = 1;
                    arrayListTagNamesSample.add("Training");
                    tagTraining.setBackgroundResource(R.color.Aqua);
                } else {
                    tagTrainingFlag = 0;
                    arrayListTagNamesSample.remove("Training");

                    tagTraining.setBackgroundColor(Color.LTGRAY);
                }

                break;

            case R.id.tagVolunteering:

                if (tagVolunteeringFlag == 0) {
                    tagVolunteeringFlag = 1;
                    arrayListTagNamesSample.add("Volunteer");
                    tagVolunteering.setBackgroundResource(R.color.Aqua);
                } else {
                    tagVolunteeringFlag = 0;
                    arrayListTagNamesSample.remove("Volunteer");

                    tagVolunteering.setBackgroundColor(Color.LTGRAY);
                }
                break;

            case R.id.txtAddNewTag:
                if (!Utils.hasText(edtAddTags)) {
                    Toast.makeText(this, "Enter your tag", Toast.LENGTH_SHORT).show();
                } else {
                    addTags();
                }

                break;

            case R.id.tagNewCreate:
                linearAddTags.setVisibility(View.VISIBLE);

                break;
            case R.id.linearPickTags:

                linearTags.setVisibility(View.VISIBLE);
                linearImages.setVisibility(View.GONE);
                linearCameraImages.setVisibility(View.GONE);
                linearCameraVideo.setVisibility(View.GONE);
                break;

            case R.id.linearPickVideos:

                linearTags.setVisibility(View.GONE);
                linearImages.setVisibility(View.VISIBLE);
                linearCameraImages.setVisibility(View.GONE);
                linearCameraVideo.setVisibility(View.GONE);

                /*Intent intent2 = new Intent(this, VideoPickActivity.class);
                intent2.putExtra(IS_NEED_CAMERA, true);
                intent2.putExtra(Constant.MAX_NUMBER, 9);
                startActivityForResult(intent2, Constant.REQUEST_CODE_PICK_VIDEO);*/

                break;


            case R.id.ImgMediaImage:

                linearTags.setVisibility(View.GONE);
                linearImages.setVisibility(View.VISIBLE);
                linearCameraImages.setVisibility(View.GONE);
                linearCameraVideo.setVisibility(View.GONE);

                Intent intent2 = new Intent(this, ImagePickActivity.class);
                intent2.putExtra(IS_NEED_CAMERA, true);
                intent2.putExtra(Constant.MAX_NUMBER, 9);
                startActivityForResult(intent2, Constant.REQUEST_CODE_PICK_IMAGE);

                break;
            case R.id.linearPickImages:
                linearTags.setVisibility(View.GONE);
                linearImages.setVisibility(View.VISIBLE);
                linearCameraImages.setVisibility(View.GONE);
                linearCameraVideo.setVisibility(View.GONE);

                Intent intent1 = new Intent(this, ImagePickActivity.class);
                intent1.putExtra(IS_NEED_CAMERA, true);
                intent1.putExtra(Constant.MAX_NUMBER, 9);
                startActivityForResult(intent1, Constant.REQUEST_CODE_PICK_IMAGE);

                break;

            case R.id.linearPickCamera:

                linearTags.setVisibility(View.GONE);
                linearImages.setVisibility(View.GONE);
                linearCameraImages.setVisibility(View.VISIBLE);
                linearCameraVideo.setVisibility(View.GONE);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST);
                break;

            case R.id.tagAddCustomTag:

                showChangeLangDialog();
                break;

            case R.id.btnCreatePost:
                tags_id = "";
                images_name_picker_gallery = "";

                for (int i = 0; i < arrayListTagNamesSample.size(); i++) {
                    int pos = arrayListTagNames.indexOf(arrayListTagNamesSample.get(i));

                    tags_id = tags_id + arrayListTagsID.get(pos) + ",";

                }

                for (int i = 0; i < image_name_gallery_list.size(); i++) {
                    images_name_picker_gallery = images_name_picker_gallery + image_name_gallery_list.get(i) + ",";
                }


                String pm_description = edtPost.getText().toString().trim();
                String images = "";
                String pm_type = "1";
                String pm_timeline = "1";
                String pm_step = "0";
                String video = "";

                Log.d(TAG, "onClick:images_name_picker_gallery  pm_description " + pm_description);
                Log.d(TAG, "onClick: images_name_picker_gallery " + images_name_picker_gallery);
                Log.d(TAG, "onClick: images_name_picker_gallery tags_id " + tags_id);

                if (tags_id.equals("")) {
                    Toast toast = Toast.makeText(CreatePostActivity.this, "Select tags regard your post", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    if (tags_id != null && tags_id.length() > 0 && tags_id.charAt(tags_id.length() - 1) == ',') {
                        tags_id = tags_id.substring(0, tags_id.length() - 1);
                    }
                    if (images_name_picker_gallery != null && images_name_picker_gallery.length() > 0 && images_name_picker_gallery.charAt(images_name_picker_gallery.length() - 1) == ',') {
                        images_name_picker_gallery = images_name_picker_gallery.substring(0, images_name_picker_gallery.length() - 1);
                    }
                    if (!pm_description.equals("")) {
                        submitPost(tags_id, pm_description, images_name_picker_gallery, pm_type, pm_timeline, pm_step, video);
                    } else {
                        Toast.makeText(this, "Enter description regard your post", Toast.LENGTH_SHORT).show();
                    }


                }


                break;

        }
    }

    private void submitPost(String tags_id, String pm_description, String images, String pm_type, String pm_timeline,
                            String pm_step, String video) {


        progressDialog = Utils.createProgressDialog(this);

        Map<String, String> data = new HashMap<>();
        data.put("pm_tags", tags_id);
        data.put("pm_description", pm_description);
        data.put("images", images);
        data.put("pm_type", pm_type);
        data.put("pm_timeline", pm_timeline);
        data.put("pm_step", pm_step);
        data.put("video", video);

        String token = Pref.getPreToken();
        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


        //token = "Bearer "+ Pref.getPreToken();


        Log.d(TAG, "changePassword: token " + token);

        Call<DataDashboardTimeLine> call = apiService.submitNewPost(token, data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataDashboardTimeLine>() {
            @Override
            public void onResponse(Call<DataDashboardTimeLine> call, Response<DataDashboardTimeLine> response) {
                progressDialog.dismiss();

                if (response.code() == 200) {
                    if (response.body().getStatus().equals("1")) {

                        Pref.setdevicetoken("Bearer " + response.body().getToken());
                        Intent mIntent = new Intent(CreatePostActivity.this, NavDrawerActivity.class);
                        startActivity(mIntent);
                        finish();
                        /*Intent mIntent = new Intent(CreatePostActivity.this, DashBoardActivity.class);
                        startActivity(mIntent);*/
                        Toast.makeText(CreatePostActivity.this, "You have post successfully!", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(CreatePostActivity.this, "You have post failure!", Toast.LENGTH_SHORT).show();

                    }
                } else if (response.code() == 304) {

                    Toast.makeText(CreatePostActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(CreatePostActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(CreatePostActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(CreatePostActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(CreatePostActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(CreatePostActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(CreatePostActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<DataDashboardTimeLine> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void addTags() {

        progressDialog = Utils.createProgressDialog(this);

        Map<String, String> data = new HashMap<>();
        data.put("tag", edtAddTags.getText().toString().trim());
        //String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTQyIiwidXNlcm5hbWUiOiJnb3d0aGFtODc2QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTc1NTQxNjksImp0aSI6IlR6UkhkWE5MYjI1blQwSkllbkZpY0VwTVdtUT0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE3NTU0MTY5LCJleHAiOjE1MTgxNTg5Njl9.Irk5s-f_i2AmK9y7xF_nhsExKckfpdK_GyC7Xt0ypAs";
        String token = Pref.getPreToken();
        //  RXRetro.getInstance().retrofitEnque(ApiClient.getInterface().postNewTag(token, data), this, 1);


        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


        //token = "Bearer "+ Pref.getPreToken();


        Log.d(TAG, "changePassword: token " + token);

        Call<DataAddNewTag> call = apiService.postNewTag(token, data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataAddNewTag>() {
            @Override
            public void onResponse(Call<DataAddNewTag> call, Response<DataAddNewTag> response) {
                progressDialog.dismiss();

                if (response.code() == 200) {

                    edtAddTags.setText("");


                    if (response.body().getStatus().equals("1")) {

                        Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));

                        Log.d(TAG, "   name " + response.body().getData().getTag());
                        Log.d(TAG, "onSuccess: tag id " + response.body().getData().getTag_id());
                        int pos = dataPostTagListArrayList.size();
                        // dataPostTagListArrayList.get(pos++).setPtg_name(response.body().getData().getTag());
                        // dataPostTagListArrayList.get(pos++).setPtg_tag_id(response.body().getData().getTag_id());
                        arrayListTagNamesSample.add(response.body().getData().getTag());
                        arrayListTagNames.add(response.body().getData().getTag());
                        arrayListTagNamess.add(response.body().getData().getTag());
                        arrayListTagsID.add(response.body().getData().getTag_id());
                        arrayListTagsIDD.add(response.body().getData().getTag_id());
                        arrayListTagsIDDTempRemove.add(response.body().getData().getTag_id());


                        String txtCustomTagsss = " ";
                        for (String a : arrayListTagNamesSample
                                ) {

                            //txtCustomTagsss.concat(a.concat(", "));

                            txtCustomTagsss = txtCustomTagsss + a + ", ";

                        }


                        if (txtCustomTagsss.length() != 0) {

                            if (txtCustomTagsss.length() > 0 && txtCustomTagsss.charAt(txtCustomTagsss.length() - 2) == ',') {
                                txtCustomTagsss = txtCustomTagsss.substring(0, txtCustomTagsss.length() - 2);
                            }
                            Log.d(TAG, "onResponse: asdfsadfasdfasdf");
                            txtCustomTag.setText(new StringBuilder().append(txtCustomTagsss).append(".").toString());
                            addNewTagView.setVisibility(View.VISIBLE);
                        } else {
                            Log.d(TAG, "onResponse: asdfsadfasdfasdf else ");
                            addNewTagView.setVisibility(View.GONE);
                        }


                    } else {
                        Toast.makeText(CreatePostActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else if (response.code() == 304) {

                    Toast.makeText(CreatePostActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(CreatePostActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(CreatePostActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(CreatePostActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(CreatePostActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(CreatePostActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(CreatePostActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<DataAddNewTag> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case CAMERA_REQUEST:

                if (resultCode == Activity.RESULT_OK) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    imgTakeImage.setImageBitmap(photo);

                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    Uri selectedImage = getImageUri(this, photo);
                    Log.d(TAG, "onActivityResult: selectedImage " + selectedImage);
                    String realPath = getPath(selectedImage);
                    Log.d(TAG, "onActivityResult:realPath " + realPath);
                    Log.d(TAG, "onActivityResult: selectedImage realPath size " + getReadableFileSize(realPath.length()));
                    //new DashboardMyStep.UploadImages(realPath).execute();

                    isCameraUpload = false;
                    String compressPath = decodeFile(realPath);
                    Log.d(TAG, "onActivityResult: compressPath " + compressPath);
                    Log.d(TAG, "onActivityResult: selectedImage compressPath size " + getReadableFileSize(realPath.length()));
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add(compressPath);
                    if (arrayList.size() > 0) {
                        new UploadImages(arrayList).execute();
                    }

                }
                break;
            case Constant.REQUEST_CODE_PICK_IMAGE:

                if (resultCode == RESULT_OK) {
                    ArrayList<ImageFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
                    StringBuilder builder = new StringBuilder();

                    File compressedFile = null;
                    ArrayList<String> arrayList = new ArrayList<>();
                    for (ImageFile file : list) {
                        final String path = file.getPath();

                        final String str = CompressImage.getInstance().compressImage(this, path);

                        final File compressedFiles = new File(str);

                        arrayList.add(compressedFiles.getPath());
                        Log.d(TAG, "doUploadPic: file.exists " + compressedFiles.exists());

                        Log.d(TAG, "doUploadPic:original file " + ImageFilePath.getFileSize(new File(path)) + " compressedFile " + ImageFilePath.getFileSize(compressedFiles));

                    }

                    if (arrayList.size() > 0) {
                        new UploadImages(arrayList).execute();
                    }


                }


                break;
            case Constant.REQUEST_CODE_PICK_VIDEO:
                if (resultCode == RESULT_OK) {
                    ArrayList<VideoFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_VIDEO);
                    StringBuilder builder = new StringBuilder();
                    ArrayList<String> arrayList = new ArrayList<>();
                    for (VideoFile file : list) {
                        String path = file.getPath();
                        builder.append(path + "\n");
                        arrayList.add(path);
                    }


                    Log.d("ssssss", "onActivityResult:v " + builder.toString());
                    //if (arrayList.size() > 0)
                    // addThemToView(arrayList);
                    // new UploadImages(arrayList).execute();

                }
                break;


        }
    }


    private void addThemToView(ArrayList<String> imagePaths, String path_thumb) {
        final ArrayList<String> filePaths = new ArrayList<>();
        if (imagePaths != null)
            filePaths.addAll(imagePaths);


        Log.d(TAG, "addThemToView: filePaths size " + filePaths.size());

        if (filePaths.size() == 1) {

            recyclerView.setVisibility(View.GONE);
            image_view.setVisibility(View.VISIBLE);
            Log.d(TAG, "addThemToView:filePaths " + filePaths.get(0));

            Picasso.with(this)
                    .load(path_thumb.concat(filePaths.get(0)))
                    .placeholder(R.drawable.no_preview) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.no_preview)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(image_view);


        } else {


            recyclerView.setVisibility(View.VISIBLE);
            image_view.setVisibility(View.GONE);
            if (recyclerView != null) {

                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL);
                layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
                recyclerView.setLayoutManager(layoutManager);
                listener = new RecyclerViewClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        try {

                            filePaths.remove(position);
                            uploadedPaths.remove(position);
                            image_name_gallery_list.remove(position);

                            if (imageAdapter != null)
                                imageAdapter.notifyDataSetChanged();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                };

                imageAdapter = new ImageAdapter(CreatePostActivity.this, filePaths, listener, "2", path_thumb);

                recyclerView.setAdapter(imageAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
            }

        }


    }

    private class UploadImages extends AsyncTask<Void, Void, String> {
        int position;
        View view;
        ArrayList<String> filePaths;

        ImageAdapter imageAdapter;

        ProgressDialog progressDialog;

      /*  public UploadImages(ArrayList<String> filePaths, ImageAdapter imageAdapter) {
            this.filePaths = filePaths;
            this.imageAdapter = imageAdapter;
            progressDialog = Utils.createProgressDialog(CreatePostActivity.this);

            if (!isCameraUpload) {

            } else {
                image_name_gallery_list.clear();
            }
        }*/

        public UploadImages(ArrayList<String> arrayList) {
            this.filePaths = arrayList;
            progressDialog = Utils.createProgressDialog(CreatePostActivity.this);
        }

        @Override
        protected String doInBackground(Void... voids) {

            for (int i = 0; i < filePaths.size(); i++) {
                Log.d(TAG, "doInBackground: filePaths " + filePaths.get(i));
            }
            final Map<String, ArrayList<String>> data = new HashMap<>();
            if (filePaths != null) {

                data.put("userfile[]", filePaths);
            }


            //     String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTQyIiwidXNlcm5hbWUiOiJnb3d0aGFtODc2QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTY2MTQzNDgsImp0aSI6ImJHZHpVVVJRWlhSUmVqbDNNelp0YjFnMFdGST0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE2NjE0MzQ4LCJleHAiOjE1MTcyMTkxNDh9.BYBvL9AFaq3hAQMPUDt77ANgwcfeBzpdTbK7gd-S3N0";
            //   RXRetro.getInstance().retrofitEnque(ApiClient.getInterface().getTimeLinePostLists(token,data), this, 1);
            String token = Pref.getPreToken();
            Log.d(TAG, "logMeIn onResponse: token " + token);


            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            // Map is used to multipart the file using okhttp3.RequestBody
            // Multiple Images
            for (int i = 0; i < filePaths.size(); i++) {
                File file = new File(filePaths.get(i));
                builder.addFormDataPart("userfile[]", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
            }

            final File file = new File("");
            MultipartBody requestBody = builder.build();

            YouthHubApi apiService =
                    ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);

            Call<DataMultiImgSingleImgUpload> call = apiService.uploadImage(token, requestBody);
            Log.d(TAG, "logMeIn: url " + call.request().url());

            call.enqueue(new Callback<DataMultiImgSingleImgUpload>() {
                @Override
                public void onResponse(Call<DataMultiImgSingleImgUpload> call, Response<DataMultiImgSingleImgUpload> response) {
                    progressDialog.dismiss();

                    try {

                        if (response.code() == 200 || response.code() == 201) {

                            //  filePaths.clear();
                            Log.d(TAG, "onResponse:response.body " + new Gson().toJson(response.body()));
                            Log.d(TAG, "onResponse: statussss " + response.body().getStatus());
                            if (response.body().getStatus().equals("1")) {


                                int size = response.body().getData().getFilename().size();
                                int failedSize = response.body().getData().getFilenameuploadfield().size();


                                Log.d(TAG, "onResponse:image size = " + size);
                                for (int i = 0; i < size; i++) {
                                    uploadedPaths.add(response.body().getData().getFilename().get(i));
                                    filePaths.add(response.body().getData().getFilename().get(i));

                                    image_name_gallery_list.add(response.body().getData().getFilename().get(i));

                                }

                                if (failedSize > 0)
                                    Toast.makeText(CreatePostActivity.this, "Some files are failed to upload. Try again later", Toast.LENGTH_SHORT).show();

                                addThemToView(uploadedPaths, response.body().getData().getPath_thumb());
                                //   addThemToView(filePaths, response.body().getData().getPath_thumb());

                                Log.d(TAG, "onResponse: image_name_gallery_list " + image_name_gallery_list.size());

                            } else {
                                imageAdapter.notifyDataSetChanged();
                            }
                        } else if (response.code() == 304) {

                            Toast.makeText(CreatePostActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 400) {

                            Toast.makeText(CreatePostActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 401) {

                            Toast.makeText(CreatePostActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 403) {

                            Toast.makeText(CreatePostActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 404) {

                            Toast.makeText(CreatePostActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                        } else if (response.code() == 422) {

                            Toast.makeText(CreatePostActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(CreatePostActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<DataMultiImgSingleImgUpload> call, Throwable t) {

                    t.printStackTrace();
                    progressDialog.dismiss();
                }
            });
            return null;
        }


    }
}
