package com.mindnotix.youthhub;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.developers.imagezipper.ImageZipper;
import com.mindnotix.adapter.ViewPagerAdapter;
import com.mindnotix.model.BasicResponse;
import com.mindnotix.model.DataUploadProfilePic;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.youthhub.cvresume.CvResumeFragment;
import com.squareup.picasso.Picasso;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.filter.entity.ImageFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vincent.filepicker.activity.ImagePickActivity.IS_NEED_CAMERA;


public class ProfileActivity extends BaseActivity implements View.OnClickListener {

    protected static final String TAG = "ScrollingActivity";
    private static final int PICK_GALLERY_IMAGE_REQUEST_CODE = 200;
    private static int PROFILE_PICTURE_UPLOAD = 100;
    protected ViewPager viewPager;
    protected TabLayout tabLayout;
    protected ImageView imgRight;
    protected ImageView CoverPicture;
    protected ImageView profile_image;
    protected ImageView profile_image1;
    protected ImageView imgLeft;
    protected TextView tvName;
    protected TextView tvWorker;
    protected Toolbar toolbar;
    protected ProgressBar progressBar;
    protected AppBarLayout appbar;
    protected TextView tvFollwers;
    protected TextView tvFollwing;
    protected String email;
    protected String first_name;
    protected String last_name;
    protected String company;
    protected String about_me;
    protected String profile_pic;
    protected String profile_path_medium;
    protected String profile_path_thumb;
    protected String cover_pic;
    protected String cover_path;
    protected View ViewUpload;
    protected ProgressBar imageUploadProgress;
    protected ProgressBar CoverProgress;
    protected ImageView cameraIcon;
    protected View removeProfilePicture;
    protected AppBarLayout dummyAppbar;
    protected View mainLayout;
    protected View tabLayoutView;
    AboutFragment aboutFragment;
    FragmentProfilePostStories fragmentProfilePostStories;
    FragmentProfileSeeMyStep fragmentProfileSeeMyStep;
    CvResumeFragment cvResumeFragment;
    Call<BasicResponse> loadprofileapicall;
    Call<BasicResponse> profilePictureDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setToolbar();
        initUi();
        loadProfileData();

    }

    private void setToolbar() {
        try {

            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUi() {
        tvFollwing = findViewById(R.id.tvFollwing);
        tvFollwers = findViewById(R.id.tvFollwers);
        tvName = findViewById(R.id.tvName);
        progressBar = findViewById(R.id.progressBar);
        appbar = findViewById(R.id.appbar);
        tvWorker = findViewById(R.id.tvWorker);
        CoverPicture = findViewById(R.id.CoverPicture);
        profile_image = findViewById(R.id.profile_image);
        profile_image1 = findViewById(R.id.profile_image1);
        imgRight = findViewById(R.id.left);
        imgLeft = findViewById(R.id.right);
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        ViewUpload = findViewById(R.id.ViewUpload);
        imageUploadProgress = findViewById(R.id.imageUploadProgress);
        cameraIcon = findViewById(R.id.cameraIcon);
        CoverProgress = findViewById(R.id.CoverProgress);
        removeProfilePicture = findViewById(R.id.removeProfilePicture);
        //  dummyAppbar = findViewById(R.id.dummyAppbar);
        mainLayout = findViewById(R.id.mainLayout);
        tabLayoutView = findViewById(R.id.tabLayoutView);

        imgRight.setOnClickListener(this);
        imgLeft.setOnClickListener(this);
        ViewUpload.setOnClickListener(this);
        cameraIcon.setOnClickListener(this);
        removeProfilePicture.setOnClickListener(this);


        /*for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            LinearLayout linearLayout = (LinearLayout)
                    LayoutInflater.from(this).inflate(R.layout.tab_view_layout, tabLayout, false);

            TextView tabTextView =  linearLayout.findViewById(R.id.text);
            tabTextView.setText(tab.getText());
            tab.setCustomView(linearLayout);

        }*/


    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        int tab_position;

        switch (id) {

            case R.id.left:

                tab_position = tabLayout.getSelectedTabPosition();

                Log.d(TAG, "onClick:tab_position right " + tab_position);
                Log.d(TAG, "onClick:tab_position left " + tab_position--);
                if (tab_position < 7)
                    viewPager.setCurrentItem(tab_position--);

                break;
            case R.id.right:
                tab_position = tabLayout.getSelectedTabPosition();
                Log.d(TAG, "onClick:tab_position left " + tab_position);
                Log.d(TAG, "onClick:tab_position left " + tab_position++);
                if (tab_position >= 0)
                    viewPager.setCurrentItem(tab_position++);
                break;
            case R.id.ViewUpload:
                PROFILE_PICTURE_UPLOAD = 100;
                UploadProfilePic();
                break;

            case R.id.cameraIcon:
                PROFILE_PICTURE_UPLOAD = 101;
                UploadProfilePic();
                break;
            case R.id.removeProfilePicture:

                profilePictureDelete("profile_pic");
                break;


        }

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        aboutFragment = new AboutFragment();
        fragmentProfilePostStories = new FragmentProfilePostStories();
        fragmentProfileSeeMyStep = new FragmentProfileSeeMyStep();
        cvResumeFragment = new CvResumeFragment();
        adapter.addFragment(aboutFragment, "About Me");
        adapter.addFragment(fragmentProfilePostStories, "Posted Stories");
        adapter.addFragment(fragmentProfileSeeMyStep, "See My Steps");
        adapter.addFragment(new CvResumeFragment(), "CV/Resume");
        adapter.addFragment(new CV_ResumeFragment(), "Testimonials");
        adapter.addFragment(new CV_ResumeFragment(), "Projects");
        adapter.addFragment(new FragmentProfileGallery(), "Gallery");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!nbIsNetworkAvailable(this)) {
            nbShowNoInternet(this);
        }
    }

    public void loadProfileData() {

        progressBar.isShown();

        Map<String, String> data = new HashMap<>();
        data.put("profileid", Pref.getClientId());

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        loadprofileapicall = apiService.loadProfile(Pref.getPreToken(), data);

        loadprofileapicall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(@NonNull Call<BasicResponse> call, @NonNull Response<BasicResponse> response) {

                if (response.code() == 200 || response.code() == 201) {

                    BasicResponse basicResponse = response.body();

                    if (basicResponse.getStatus().equals("1")) {

                        mainLayout.setVisibility(View.VISIBLE);
                        tabLayoutView.setVisibility(View.VISIBLE);

                        try {

                            Pref.setdevicetoken("Bearer " + basicResponse.getToken());

                            //   dummyAppbar.setVisibility(View.GONE);
                            appbar.setVisibility(View.VISIBLE);
                            viewPager.setVisibility(View.VISIBLE);

                            first_name = basicResponse.getData().getYouthuser().getFirst_name();
                            last_name = basicResponse.getData().getYouthuser().getLast_name();
                            company = basicResponse.getData().getYouthuser().getCompany();
                            profile_pic = basicResponse.getData().getYouthuser().getProfile_pic();
                            profile_path_medium = basicResponse.getData().getYouthuser().getProfile_path_medium();
                            cover_pic = basicResponse.getData().getYouthuser().getCover_pic();
                            cover_path = basicResponse.getData().getYouthuser().getCover_path();

                            tvName.setText(first_name + " " + last_name);


                            Picasso.with(ProfileActivity.this).
                                    load(cover_path + cover_pic)
                                    .into(CoverPicture, new com.squareup.picasso.Callback() {
                                        @Override
                                        public void onSuccess() {
                                            CoverProgress.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onError() {
                                            CoverProgress.setVisibility(View.GONE);
                                        }
                                    });


                            Log.v("jfbefedefefe", profile_path_medium + profile_pic);

                            if (!profile_pic.trim().equals("")) {
                                profile_image1.setVisibility(View.GONE);
                                profile_image.setVisibility(View.VISIBLE);

                                Picasso.with(ProfileActivity.this).
                                        load(profile_path_medium + profile_pic).
                                        into(profile_image, new com.squareup.picasso.Callback() {
                                            @Override
                                            public void onSuccess() {
                                                imageUploadProgress.setVisibility(View.GONE);
                                            }

                                            @Override
                                            public void onError() {
                                                imageUploadProgress.setVisibility(View.GONE);
                                            }
                                        });


                                Pref.setProfileImage(profile_path_medium + profile_pic);
                                Pref.setClientProfilePictureName(profile_pic);


                            } else {
                                Pref.setProfileImage(" ");
                                Pref.setClientProfilePictureName(" ");
                                loadTextDrawable();


                            }
                            tvFollwing.setText(basicResponse.getData().getFollowing_count());
                            tvFollwers.setText(basicResponse.getData().getFollowers_count());
                            if (!basicResponse.getData().getYouth().getCurrent_work_status_name().equals("") &&
                                    !basicResponse.getData().getYouth().getCurrent_work_status_name().isEmpty() &&
                                    basicResponse.getData().getYouth().getCurrent_work_status_name() != null &&
                                    !basicResponse.getData().getYouth().getCurrent_work_status_name().equals("None")) {
                                tvWorker.setVisibility(View.VISIBLE);
                                tvWorker.setText(basicResponse.getData().getYouth().getCurrent_work_status_name());
                            } else {
                                tvWorker.setVisibility(View.GONE);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        mainLayout.setVisibility(View.VISIBLE);
                        tabLayoutView.setVisibility(View.VISIBLE);

                        Toast.makeText(ProfileActivity.this, basicResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }


                } else if (response.code() == 304) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<BasicResponse> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                t.printStackTrace();

            }
        });


    }

    private void loadTextDrawable() {
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(String.valueOf(first_name.charAt(0)).toUpperCase(), getResources().getColor(R.color.medium_level_blue));

        profile_image1.setImageDrawable(drawable);




    }

    private void UploadProfilePic() {

        Intent intent1 = new Intent(this, ImagePickActivity.class);
        intent1.putExtra(IS_NEED_CAMERA, true);
        intent1.putExtra(com.vincent.filepicker.Constant.MAX_NUMBER, 1);
        startActivityForResult(intent1, com.vincent.filepicker.Constant.REQUEST_CODE_PICK_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case com.vincent.filepicker.Constant.REQUEST_CODE_PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    ArrayList<ImageFile> list = data.getParcelableArrayListExtra(com.vincent.filepicker.Constant.RESULT_PICK_IMAGE);
                    StringBuilder builder = new StringBuilder();

                    ArrayList<String> arrayList = new ArrayList<>();
                    for (ImageFile file : list) {
                        String path = file.getPath();
                        builder.append(path + "\n");
                        arrayList.add(path);
                        if (PROFILE_PICTURE_UPLOAD == 101) {
                            try {
                                File file1 = new File(path);
                                getReadableFileSize(file1.length());


                                Log.v("actualsize", "efbefefefuef" + getReadableFileSize(file1.length()));
                                File imageZipperFile = new ImageZipper(this).compressToFile(new File(path));
                                //  File compressedImageFile = new Compressor(this).compressToFile(new File(path));

                                coverPictureUpload(imageZipperFile.getPath());
                                // File file2=new File(imageZipperFile.getPath());
                                Log.v("actuaklatersize", "efbefefefuef" + imageZipperFile.getPath().length());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            try {
                                File imageZipperFile = new ImageZipper(this).compressToFile(new File(path));
                                uploadProfile(imageZipperFile.getPath());
                                Log.v("size", "efbefefefuef" + imageZipperFile.length());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                    Log.d("ssssss", "onActivityResult:i " + builder.toString());


                }


        }
    }

    public String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    private void uploadProfile(final String realPath) {

        if (!realPath.equals("") && !realPath.equals(null)) {


            imageUploadProgress.setVisibility(View.VISIBLE);

            File file = new File(realPath);

            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("userfile", file.getName(), requestBody);

            YouthHubApi apiService =
                    ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

            Call<DataUploadProfilePic> call = apiService.uploadProfileImage(Pref.getPreToken(), fileToUpload);
            Log.d(TAG, "logMeIn: url " + call.request().url());

            call.enqueue(new Callback<DataUploadProfilePic>() {
                @Override
                public void onResponse(Call<DataUploadProfilePic> call, Response<DataUploadProfilePic> response) {


                    if (response.code() == 200) {

                        DataUploadProfilePic dataUploadProfilePic = response.body();

                        if (dataUploadProfilePic.getStatus().equals("1")) {




                            try {

                                imageUploadProgress.setVisibility(View.GONE);

                                if (response.body().getStatus().equals("1")) {
                                    String thumb = dataUploadProfilePic.getData().getPath_thumb() + response.body().getData().getFilename();
                                    String medium = response.body().getData().getPath_medium() + response.body().getData().getFilename();
                                    String large = response.body().getData().getPath_large() + response.body().getData().getFilename();

                                   Pref.setClientProfilePictureName(response.body().getData().getFilename());


                                    Picasso.with(ProfileActivity.this).
                                            load(medium)
                                            .into(profile_image);
                                    profile_image1.setVisibility(View.GONE);
                                    profile_image.setVisibility(View.VISIBLE);


                                    Log.d(TAG, "onSuccess:thumb " + thumb);
                                    Log.d(TAG, "onSuccess:medium " + medium);
                                    Log.d(TAG, "onSuccess:large " + large);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else if (response.code() == 201) {
                        Toast.makeText(ProfileActivity.this, "201 Created", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 304) {
                        Toast.makeText(ProfileActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {
                        Toast.makeText(ProfileActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {
                        Toast.makeText(ProfileActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {
                        Toast.makeText(ProfileActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {
                        Toast.makeText(ProfileActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {
                        Toast.makeText(ProfileActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(ProfileActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }


                }

                @Override
                public void onFailure(Call<DataUploadProfilePic> call, Throwable t) {
                    //progressDialog.dismiss();
                    t.printStackTrace();
                }
            });
        } else

        {
            Toast.makeText(this, "Please, pick your profile picture.", Toast.LENGTH_SHORT).show();
        }
    }

    private void coverPictureUpload(final String realPath) {

        CoverProgress.setVisibility(View.VISIBLE);

        if (!realPath.equals("") && !realPath.equals(null)) {

            File file = new File(realPath);

            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("userfile", file.getName(), requestBody);

            YouthHubApi apiService =
                    ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

            Call<DataUploadProfilePic> call = apiService.uploadCoverImage(Pref.getPreToken(), fileToUpload);

            call.enqueue(new Callback<DataUploadProfilePic>() {
                @Override
                public void onResponse(Call<DataUploadProfilePic> call, Response<DataUploadProfilePic> response) {


                    if (response.code() == 200) {

                        DataUploadProfilePic dataUploadProfilePic = response.body();

                        if (dataUploadProfilePic.getStatus().equals("1")) {

                            try {

                                if (response.body().getStatus().equals("1")) {
                                    Log.v("fefduefuefeuif", response.body().getToken());

                                    String coverPhotoPath = dataUploadProfilePic.getData().getCover_photo();
                                    String filePath = response.body().getData().getFilename();

                                    CoverProgress.setVisibility(View.GONE);

                                    Picasso.with(ProfileActivity.this).
                                            load(coverPhotoPath + filePath)
                                            .into(CoverPicture);


                                    Log.d(TAG, "onSuccess:thumb " + coverPhotoPath);
                                    Log.d(TAG, "onSuccess:medium " + filePath);
                                    Log.d(TAG, "onSuccess:large " + filePath + coverPhotoPath);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else if (response.code() == 201) {
                        CoverProgress.setVisibility(View.GONE);
                        Toast.makeText(ProfileActivity.this, "201 Created", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 304) {
                        CoverProgress.setVisibility(View.GONE);
                        Toast.makeText(ProfileActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {
                        CoverProgress.setVisibility(View.GONE);
                        Toast.makeText(ProfileActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {
                        CoverProgress.setVisibility(View.GONE);
                        Toast.makeText(ProfileActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {
                        CoverProgress.setVisibility(View.GONE);
                        Toast.makeText(ProfileActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {
                        CoverProgress.setVisibility(View.GONE);
                        Toast.makeText(ProfileActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {
                        CoverProgress.setVisibility(View.GONE);
                        Toast.makeText(ProfileActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {
                        CoverProgress.setVisibility(View.GONE);
                        Toast.makeText(ProfileActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }


                }

                @Override
                public void onFailure(Call<DataUploadProfilePic> call, Throwable t) {
                    CoverProgress.setVisibility(View.GONE);
                    t.printStackTrace();
                }
            });
        } else

        {
            Toast.makeText(this, "Please, pick your profile picture.", Toast.LENGTH_SHORT).show();
        }
    }

    void showPopupWindow(final View view) {
        PopupMenu popup = new PopupMenu(ProfileActivity.this, view);
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popup.getMenuInflater().inflate(R.menu.profile_menu_settings, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle().equals("Remove Cover Photo")) {
                    profilePictureDelete("cover_pic");


                } else if (item.getTitle().equals("Profile Update")) {
                    Intent intent = new Intent(ProfileActivity.this, ProfileUpdateActivity.class);
                    startActivity(intent);



                } else {
                    Intent intent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
                    startActivity(intent);
                }
                return true;

            }
        });
        popup.show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                showPopupWindow(findViewById(R.id.action_settings));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void profilePictureDelete(final String picture) {

        if (picture.equals("profile_pic")) {
            imageUploadProgress.setVisibility(View.VISIBLE);
        } else {
            CoverProgress.setVisibility(View.VISIBLE);

        }


        Map<String, String> data = new HashMap<>();
        data.put("profileid", Pref.getClientId());
        data.put("type", picture);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        profilePictureDelete = apiService.profilePictureDelete(Pref.getPreToken(), data);

        profilePictureDelete.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(@NonNull Call<BasicResponse> call, @NonNull Response<BasicResponse> response) {

                if (response.code() == 200) {

                    BasicResponse basicResponse = response.body();

                    if (basicResponse.getStatus().equals("1")) {

                        try {

                            if (picture.equals("cover_pic")) {

                                Pref.setClientProfilePictureName("");




                                CoverProgress.setVisibility(View.GONE);
                                Toast.makeText(ProfileActivity.this, basicResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                Log.v("fefjefefefeif", cover_path + basicResponse.getPic_name());
                                Log.v("fefjefefefeif", basicResponse.getPic_name());
                                Picasso.with(ProfileActivity.this).
                                        load(cover_path + basicResponse.getPic_name()).
                                        into(CoverPicture);


                            } else {

                                Pref.setClientProfilePictureName("");


                                imageUploadProgress.setVisibility(View.GONE);
                                profile_image1.setVisibility(View.VISIBLE);
                                profile_image.setVisibility(View.GONE);

                                loadTextDrawable();



                                Toast.makeText(ProfileActivity.this, basicResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        if (picture.equals("cover_pic")) {

                            CoverProgress.setVisibility(View.GONE);
                            Toast.makeText(ProfileActivity.this, basicResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {

                            imageUploadProgress.setVisibility(View.GONE);
                            profile_image1.setVisibility(View.VISIBLE);
                            profile_image.setVisibility(View.GONE);
                            Toast.makeText(ProfileActivity.this, basicResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                } else if (response.code() == 201) {
                    Toast.makeText(ProfileActivity.this, "201 Created", Toast.LENGTH_SHORT).show();
                    imageUploadProgress.setVisibility(View.GONE);
                    CoverProgress.setVisibility(View.GONE);
                } else if (response.code() == 304) {
                    Toast.makeText(ProfileActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    imageUploadProgress.setVisibility(View.GONE);
                    CoverProgress.setVisibility(View.GONE);
                } else if (response.code() == 400) {
                    Toast.makeText(ProfileActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    imageUploadProgress.setVisibility(View.GONE);
                    CoverProgress.setVisibility(View.GONE);
                } else if (response.code() == 401) {
                    Toast.makeText(ProfileActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    imageUploadProgress.setVisibility(View.GONE);
                    CoverProgress.setVisibility(View.GONE);
                } else if (response.code() == 403) {
                    Toast.makeText(ProfileActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    imageUploadProgress.setVisibility(View.GONE);
                    CoverProgress.setVisibility(View.GONE);
                } else if (response.code() == 404) {
                    Toast.makeText(ProfileActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();
                    imageUploadProgress.setVisibility(View.GONE);
                    CoverProgress.setVisibility(View.GONE);

                } else if (response.code() == 422) {
                    Toast.makeText(ProfileActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();
                    imageUploadProgress.setVisibility(View.GONE);
                    CoverProgress.setVisibility(View.GONE);

                } else {
                    Toast.makeText(ProfileActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();
                    imageUploadProgress.setVisibility(View.GONE);
                    CoverProgress.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(@NonNull Call<BasicResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                CoverProgress.setVisibility(View.GONE);
                imageUploadProgress.setVisibility(View.GONE);
            }
        });


    }

    public void switchTab(int tab) {
        viewPager.setCurrentItem(tab);
    }


}




