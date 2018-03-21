package com.mindnotix.youthhub;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindnotix.adapter.SkillsAdapter;
import com.mindnotix.adapter.WishListAdapter;
import com.mindnotix.model.BasicResponse;
import com.mindnotix.model.Wishlist;
import com.mindnotix.model.about.WishListTag;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import mabbas007.tagsedittext.TagsEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AboutFragment extends Fragment implements View.OnClickListener, TagsEditText.TagsEditListener {
    private static final String TAG = AboutFragment.class.getSimpleName();
    protected TextView tvAdd;
    protected TextView tvRegion;
    protected TextView tvCity;
    protected TextView tvGender;
    protected TextView tvDestination;
    protected TextView tvWorkExperience;
    protected TextView tvLicense;
    protected TextView tvCurrentWorkStatus;
    protected TextView tvWorkHours;
    protected TextView tvShareLink;
    protected View workDetailsView;
    protected TextView tvAbout;
    protected View workInfo;
    protected String workHelper;
    protected RecyclerView recyclerView;
    protected SkillsAdapter skillsAdapter;
    protected WishListAdapter wishListAdapter;
    protected ArrayList<String> skills;
    protected ArrayList<Wishlist> whislist;
    protected ArrayList<WishListTag> whislistMaster;
    protected View skillListView;
    protected View emptyViewSkills;
    protected RecyclerView recyclerViewWhistlist;

    protected ImageView ImageTwitter;
    protected ImageView ImageTwitterColor;
    protected ImageView ImageFacebook;
    protected ImageView ImageFacebookColor;
    protected ImageView ImageGoogleplus;
    protected ImageView ImageGoogleplusColor;
    protected ImageView ImageLinkedIn;
    protected ImageView ImageLinkedInColor;
    protected ImageView ImageGithub;
    protected ImageView ImageGithubColor;
    protected ImageView ImageBehance;
    protected ImageView ImageBehanceColor;
    protected ImageView ImageInstagram;
    protected ImageView ImageInstagramColor;

    protected String twitter;
    protected String facebook;
    protected String gooleplus;
    protected String linkedin;
    protected String github;
    protected String instagram;
    protected String behance;
    protected AppCompatActivity appCompatActivity;
    protected TextView btCopy;
    protected View mainLoadView;
    protected View progressLayout;
    protected View layoutIntent;
    protected View layoutAboutsub;
    protected View layoutAbout;
    protected View layoutRegion;
    protected View layoutGender;
    protected View layoutDistrict;
    protected View layoutLicenseView;
    protected View layoutCurrentWorkView;
    protected View layoutWorkAvailable;
    protected View layoutWhislist;
    protected TextView tvSkillsTextColor;
    protected View skillsTab;
    protected TagsEditText mTagsEditText;
    protected View shareLinkContent;
    protected View shareLinkHeading;
    ArrayList<String> arrayListTagNames = new ArrayList<>();
    ArrayList<String> arrayListTagNamesSample = new ArrayList<>();
    ArrayList<String> arrayListTagsID = new ArrayList<>();

    ProgressDialog progressDialog;
    ArrayAdapter<String> adapter;
    Call<BasicResponse> loadprofileapicall;
    Call<BasicResponse> addWhishlist;
    Call<BasicResponse> loadWhishlistMaster;
    Call<BasicResponse> addWishlist;
    private String whishlistAddId = " ";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_about_fragment, container, false);
        intiUi(view);
        return view;
    }

    private void intiUi(View view) {
        tvAdd = view.findViewById(R.id.tvAdd);
        tvRegion = view.findViewById(R.id.tvRegion);
        tvCity = view.findViewById(R.id.tvCity);
        tvGender = view.findViewById(R.id.tvGender);
        tvDestination = view.findViewById(R.id.tvDestination);
        tvWorkExperience = view.findViewById(R.id.tvWorkExperience);
        tvLicense = view.findViewById(R.id.tvLicense);
        tvCurrentWorkStatus = view.findViewById(R.id.tvCurrentWorkStatus);
        tvWorkHours = view.findViewById(R.id.tvWorkHours);
        workDetailsView = view.findViewById(R.id.workDetailsView);
        tvAbout = view.findViewById(R.id.tvAbout);
        tvShareLink = view.findViewById(R.id.tvShareLink);
        workInfo = view.findViewById(R.id.workInfo);
        recyclerView = view.findViewById(R.id.recyclerView);
        emptyViewSkills = view.findViewById(R.id.emptyViewSkills);
        skillListView = view.findViewById(R.id.skillListView);
        recyclerViewWhistlist = view.findViewById(R.id.recyclerViewWhistlist);

        ImageTwitter = view.findViewById(R.id.ImageTwitter);
        ImageTwitterColor = view.findViewById(R.id.ImageTwitterColor);
        ImageFacebook = view.findViewById(R.id.ImageFacebook);
        ImageFacebookColor = view.findViewById(R.id.ImageFacebookColor);
        ImageGoogleplus = view.findViewById(R.id.ImageGoogleplus);
        ImageGoogleplusColor = view.findViewById(R.id.ImageGoogleplusColor);
        ImageLinkedIn = view.findViewById(R.id.ImageLinkedIn);
        ImageLinkedInColor = view.findViewById(R.id.ImageLinkedInColor);
        ImageGithub = view.findViewById(R.id.ImageGithub);
        ImageBehance = view.findViewById(R.id.ImageBehance);
        ImageBehanceColor = view.findViewById(R.id.ImageBehanceColor);
        ImageInstagram = view.findViewById(R.id.ImageInstagram);
        ImageInstagramColor = view.findViewById(R.id.ImageInstagramColor);
        ImageGithubColor = view.findViewById(R.id.ImageGithubColor);
        btCopy = view.findViewById(R.id.btCopy);
        mainLoadView = view.findViewById(R.id.mainLoadView);
        progressLayout = view.findViewById(R.id.progressBar);
        layoutIntent = view.findViewById(R.id.layoutIntent);
        layoutAboutsub = view.findViewById(R.id.layoutAboutsub);
        layoutAbout = view.findViewById(R.id.layoutAbout);
        layoutRegion = view.findViewById(R.id.layoutRegion);
        layoutGender = view.findViewById(R.id.layoutGender);
        layoutDistrict = view.findViewById(R.id.layoutDistrict);
        layoutLicenseView = view.findViewById(R.id.layoutLicenseView);
        layoutCurrentWorkView = view.findViewById(R.id.layoutCurrentWorkView);
        layoutWorkAvailable = view.findViewById(R.id.layoutWorkAvailable);
        layoutWhislist = view.findViewById(R.id.layoutWhislist);
        tvSkillsTextColor = view.findViewById(R.id.tvSkillsTextColor);
        skillsTab = view.findViewById(R.id.skillsTab);
        shareLinkContent = view.findViewById(R.id.shareLinkContent);
        shareLinkHeading = view.findViewById(R.id.shareLinkHeading);

        String first = "Request a testimonial to be endrosed for your skill.";
        String next = "<font color='#027aff'> Request Testimonial</font>";
        tvSkillsTextColor.setText(Html.fromHtml(first + next));

        whislist = new ArrayList<>();
        skills = new ArrayList<>();
        whislistMaster = new ArrayList<>();

        workInfo.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        btCopy.setOnClickListener(this);
        mainLoadView.setOnClickListener(this);
        emptyViewSkills.setOnClickListener(this);
        ImageTwitterColor.setOnClickListener(this);
        ImageFacebookColor.setOnClickListener(this);
        ImageGoogleplusColor.setOnClickListener(this);
        ImageLinkedInColor.setOnClickListener(this);
        ImageBehanceColor.setOnClickListener(this);
        ImageInstagramColor.setOnClickListener(this);
        skillsTab.setOnClickListener(this);
        tvSkillsTextColor.setOnClickListener(this);

        appCompatActivity = new AppCompatActivity();



    }

    @Override
    public void onResume() {
        super.onResume();
        loadProfileData();
    }

    public void loadProfileData() {


        whislist = new ArrayList<>();
        whislist.clear();

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

                        try {

                            Pref.setdevicetoken("Bearer " + basicResponse.getToken());

                            progressLayout.setVisibility(View.GONE);
                            mainLoadView.setVisibility(View.VISIBLE);


                            if (!basicResponse.getData().getYouth().getSharelink().equals("") &&
                                    !basicResponse.getData().getYouth().getSharelink().isEmpty() &&
                                    basicResponse.getData().getYouth().getSharelink() != null) {

                                shareLinkContent.setVisibility(View.VISIBLE);
                                shareLinkHeading.setVisibility(View.VISIBLE);

                                tvShareLink.setText(basicResponse.getData().getYouth().getSharelink());

                            } else {
                                shareLinkContent.setVisibility(View.GONE);
                                shareLinkHeading.setVisibility(View.GONE);


                            }


                            if (!basicResponse.getData().getYouth().getRegional_name().equals("") &&
                                    !basicResponse.getData().getYouth().getRegional_name().isEmpty()) {
                                layoutRegion.setVisibility(View.VISIBLE);
                                tvRegion.setText(basicResponse.getData().getYouth().getRegional_name());

                            } else {
                                layoutRegion.setVisibility(View.GONE);

                            }


                            if (!basicResponse.getData().getYouth().getLocal_board_name().equals("") &&
                                    !basicResponse.getData().getYouth().getLocal_board_name().isEmpty()) {
                                layoutDistrict.setVisibility(View.VISIBLE);
                                tvCity.setText(basicResponse.getData().getYouth().getLocal_board_name());

                            } else {
                                layoutDistrict.setVisibility(View.GONE);

                            }


                            if (!basicResponse.getData().getYouth().getGender().equals("") &&
                                    !basicResponse.getData().getYouth().getGender().isEmpty()) {
                                layoutGender.setVisibility(View.VISIBLE);
                                tvGender.setText(basicResponse.getData().getYouth().getGender());

                            } else {
                                layoutGender.setVisibility(View.GONE);

                            }


                            if (!basicResponse.getData().getYouthuser().getAbout_me().equals("") &&
                                    !basicResponse.getData().getYouthuser().getAbout_me().isEmpty()) {
                                tvAbout.setText(basicResponse.getData().getYouthuser().getAbout_me());
                                layoutAbout.setVisibility(View.VISIBLE);
                                layoutAboutsub.setVisibility(View.VISIBLE);

                            } else {

                                layoutAbout.setVisibility(View.GONE);
                                layoutAboutsub.setVisibility(View.GONE);

                            }


                            if (!basicResponse.getData().getYouth().getIntended_destination_name().equals("") &&
                                    !basicResponse.getData().getYouth().getIntended_destination_name().isEmpty()) {
                                layoutIntent.setVisibility(View.VISIBLE);
                                tvDestination.setText(basicResponse.getData().getYouth().getIntended_destination_name());
                            } else {

                                layoutIntent.setVisibility(View.GONE);
                            }


                            if (basicResponse.getData().getYouth().getWork_experience_name() != null) {
                                if (!basicResponse.getData().getYouth().getWork_experience_name().equals("")) {

                                    if (!basicResponse.getData().getYouth().getWork_experience_name().isEmpty()) {
                                        workDetailsView.setVisibility(View.VISIBLE);
                                        tvWorkExperience.setText(basicResponse.getData().getYouth().getWork_experience_name());
                                    }

                                }
                            } else {
                                workDetailsView.setVisibility(View.GONE);

                            }

                            if (!basicResponse.getData().getYouth().getLicense_type_name().equals("") &&
                                    !basicResponse.getData().getYouth().getLicense_type_name().isEmpty()) {
                                layoutLicenseView.setVisibility(View.VISIBLE);
                                tvLicense.setText(basicResponse.getData().getYouth().getLicense_type_name());
                            } else {

                                layoutLicenseView.setVisibility(View.GONE);
                            }

                            if (!basicResponse.getData().getYouth().getCurrent_work_status_name().equals("") &&
                                    !basicResponse.getData().getYouth().getCurrent_work_status_name().isEmpty()) {
                                layoutCurrentWorkView.setVisibility(View.VISIBLE);
                                tvCurrentWorkStatus.setText(basicResponse.getData().getYouth().getCurrent_work_status_name().trim());
                            } else {

                                layoutCurrentWorkView.setVisibility(View.GONE);
                            }

                            if (!basicResponse.getData().getYouth().getHours_required_name().equals("") &&
                                    !basicResponse.getData().getYouth().getHours_required_name().isEmpty()) {
                                layoutWorkAvailable.setVisibility(View.VISIBLE);
                                tvWorkHours.setText(basicResponse.getData().getYouth().getHours_required_name());
                            } else {

                                layoutWorkAvailable.setVisibility(View.GONE);
                            }


                            workHelper = basicResponse.getData().getWork_information_helper();

                            for (int i = 0; i < basicResponse.getData().getSkills().size(); i++) {
                                skills.add(basicResponse.getData().getSkills().get(i));

                            }

                            if (skills.size() > 0) {
                                emptyViewSkills.setVisibility(View.GONE);
                                skillListView.setVisibility(View.VISIBLE);
                                skillsAdapter = new SkillsAdapter(getActivity(), skills);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setNestedScrollingEnabled(false);
                                recyclerView.setAdapter(skillsAdapter);
                            } else {
                                emptyViewSkills.setVisibility(View.VISIBLE);
                                skillListView.setVisibility(View.GONE);
                            }


                            if (basicResponse.getData().getWishlist() != null && !basicResponse.getData().getWishlist().isEmpty()) {
                                whislist.addAll(basicResponse.getData().getWishlist());

                                if (whislist.size() > 0) {

                                    wishListAdapter = new WishListAdapter(getActivity(), whislist);
                                    recyclerViewWhistlist.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    recyclerViewWhistlist.setNestedScrollingEnabled(false);
                                    recyclerViewWhistlist.setAdapter(wishListAdapter);
                                    layoutWhislist.setVisibility(View.VISIBLE);
                                } else {
                                    layoutWhislist.setVisibility(View.GONE);
                                }
                            } else {
                                layoutWhislist.setVisibility(View.GONE);
                            }


                            if (basicResponse.getData().getYouth().getIs_facebook().equals("1")) {
                                ImageFacebookColor.setVisibility(View.VISIBLE);
                                ImageFacebook.setVisibility(View.GONE);
                            } else {
                                ImageFacebookColor.setVisibility(View.GONE);
                                ImageFacebook.setVisibility(View.VISIBLE);
                            }

                            if (basicResponse.getData().getYouth().getIs_twitter().equals("1")) {
                                ImageTwitterColor.setVisibility(View.VISIBLE);
                                ImageTwitter.setVisibility(View.GONE);

                            } else {
                                ImageTwitterColor.setVisibility(View.GONE);
                                ImageTwitter.setVisibility(View.VISIBLE);

                            }

                            if (basicResponse.getData().getYouth().getIs_linkedin().equals("1")) {
                                ImageLinkedInColor.setVisibility(View.VISIBLE);
                                ImageLinkedIn.setVisibility(View.GONE);
                            } else {
                                ImageLinkedInColor.setVisibility(View.GONE);
                                ImageLinkedIn.setVisibility(View.VISIBLE);
                            }

                            if (basicResponse.getData().getYouth().getIs_github().equals("1")) {
                                ImageGithubColor.setVisibility(View.VISIBLE);
                                ImageGithub.setVisibility(View.GONE);
                            } else {
                                ImageGithubColor.setVisibility(View.GONE);
                                ImageGithub.setVisibility(View.VISIBLE);
                            }

                            if (basicResponse.getData().getYouth().getIs_behance_url().equals("1")) {
                                ImageBehanceColor.setVisibility(View.VISIBLE);
                                ImageBehance.setVisibility(View.GONE);

                            } else {
                                ImageBehanceColor.setVisibility(View.GONE);
                                ImageBehance.setVisibility(View.VISIBLE);

                            }

                            if (basicResponse.getData().getYouth().getIs_instagram_url().equals("1")) {
                                ImageInstagramColor.setVisibility(View.VISIBLE);
                                ImageInstagram.setVisibility(View.GONE);
                            } else {
                                ImageInstagramColor.setVisibility(View.GONE);
                                ImageInstagram.setVisibility(View.VISIBLE);
                            }

                            twitter = basicResponse.getData().getYouth().getTwitter();
                            facebook = basicResponse.getData().getYouth().getFacebook();
                            gooleplus = basicResponse.getData().getYouth().getGoogleplus();
                            linkedin = basicResponse.getData().getYouth().getLinkedin();
                            github = basicResponse.getData().getYouth().getGithub();
                            instagram = basicResponse.getData().getYouth().getInstagram_url();
                            behance = basicResponse.getData().getYouth().getBehance_url();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                } else if (response.code() == 304) {
                    Toast.makeText(getActivity(), "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    Toast.makeText(getActivity(), "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    Toast.makeText(getActivity(), "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    Toast.makeText(getActivity(), "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    Toast.makeText(getActivity(), "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {
                    Toast.makeText(getActivity(), "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<BasicResponse> call, @NonNull Throwable t) {
                t.printStackTrace();

            }
        });


    }

    public void deleteWhishlist(final int adapterPosition, final ArrayList<Wishlist> whislist, final Dialog dialog) {


        Map<String, String> data = new HashMap<>();
        data.put("profileid", Pref.getClientId());
        data.put("wishlistid", whislist.get(adapterPosition).getWishlistid());

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Log.v("efefefefe", " " + Constant.BASE_URL + data);

        addWhishlist = apiService.deleteWhishlist(Pref.getPreToken(), data);

        addWhishlist.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(@NonNull Call<BasicResponse> call, @NonNull Response<BasicResponse> response) {

                try {


                    if (response.code() == 200 || response.code() == 201) {

                        BasicResponse basicResponse = response.body();

                        if (basicResponse.getStatus().equals("1")) {

                            try {
                                //    Pref.setdevicetoken("Bearer " + basicResponse.getToken());


                                if (whislist.size() > 0) {
                                    layoutWhislist.setVisibility(View.VISIBLE);
                                } else {
                                    layoutWhislist.setVisibility(View.GONE);
                                }
                                dialog.dismiss();

                                Toast.makeText(getActivity(), basicResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                    } else if (response.code() == 304) {
                        Toast.makeText(getActivity(), "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {
                        Toast.makeText(getActivity(), "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {
                        Toast.makeText(getActivity(), "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {
                        Toast.makeText(getActivity(), "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {
                        Toast.makeText(getActivity(), "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {
                        Toast.makeText(getActivity(), "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<BasicResponse> call, @NonNull Throwable t) {
                t.printStackTrace();

            }
        });


    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        Intent intent;
        switch (id) {

            case R.id.workInfo:
                loadDilaog();
                break;

            case R.id.tvAdd:
                loadWhishlist();

                break;
            case R.id.emptyViewSkills:
                //v.setCurrentItem(pageIndex);
                break;
            case R.id.ImageTwitterColor:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", twitter);
                startActivity(intent);
                break;
            case R.id.ImageFacebookColor:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", facebook);
                startActivity(intent);
                break;
            case R.id.ImageGoogleplusColor:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", gooleplus);
                startActivity(intent);

                break;
            case R.id.ImageLinkedInColor:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", linkedin);
                startActivity(intent);
                break;
            case R.id.ImageGithubColor:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", github);
                startActivity(intent);
                break;
            case R.id.ImageBehanceColor:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", behance);
                startActivity(intent);

                break;
            case R.id.ImageInstagramColor:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", instagram);
                startActivity(intent);
                break;

            case R.id.btCopy:
                try {
                    ClipboardManager cManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData cData = ClipData.newPlainText("text", tvShareLink.getText().toString());
                    cManager.setPrimaryClip(cData);
                    Toast.makeText(getActivity(), "Link Copied", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            case R.id.skillsTab:

                //     switchTabInActivity(2);

                break;

            case R.id.tvSkillsTextColor:

                switchTabInActivity(4);

                break;


        }


    }

    private void loadWhishlist() {

        whislistMaster.clear();
        arrayListTagNames.clear();
        arrayListTagsID.clear();

        progressDialog = Utils.createProgressDialog(getActivity());

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        loadWhishlistMaster = apiService.loadWishListMaster(Pref.getPreToken());

        loadWhishlistMaster.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(@NonNull Call<BasicResponse> call, @NonNull Response<BasicResponse> response) {

                if (response.code() == 200 || response.code() == 201) {

                    BasicResponse basicResponse = response.body();

                    if (basicResponse.getStatus().equals("1")) {

                        try {
                            Pref.setdevicetoken("Bearer " + basicResponse.getToken());

                            progressDialog.dismiss();

                            whislistMaster.addAll(basicResponse.getData().getWishlisttag());
                            for (int i = 0; i < whislistMaster.size(); i++) {

                                arrayListTagNames.add(whislistMaster.get(i).getName());
                                arrayListTagsID.add(whislistMaster.get(i).getId());
                            }

                            dialogAddWishlistMaster();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), basicResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else if (response.code() == 304) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<BasicResponse> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();

            }
        });


    }

    private void dialogAddWishlistMaster() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add_whishlist);

        Button btSaveChages = dialog.findViewById(R.id.btSaveChages);
        Button btCancel = dialog.findViewById(R.id.btCancel);

        mTagsEditText = dialog.findViewById(R.id.tagsEditText);
        mTagsEditText.setHint("Enter names of tags");
        mTagsEditText.setTagsListener(this);
        mTagsEditText.setTagsWithSpacesEnabled(true);
        mTagsEditText.setTagsTextColor(R.color.black);
        mTagsEditText.setTagsBackground(R.drawable.square);
        mTagsEditText.setCloseDrawablePadding(R.dimen.dim15);
        mTagsEditText.setCloseDrawableRight(R.drawable.tag_close);

        View imageView = dialog.findViewById(R.id.icClose);

        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, arrayListTagNames);

        mTagsEditText.setThreshold(1);


        mTagsEditText.setAdapter(adapter);


        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        btSaveChages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {

                    Log.d(TAG, "onClick:arrayListTagNamesSample size " + arrayListTagNamesSample.size());
                    Log.d(TAG, "onClick:arrayListTagNamesSample arrayListTagNames size " + arrayListTagNames.size());
                    for (int i = 0; i < arrayListTagNamesSample.size(); i++) {
                        int pos = arrayListTagNames.indexOf(arrayListTagNamesSample.get(i));

                        whishlistAddId="";

                        whishlistAddId = whishlistAddId + arrayListTagsID.get(pos) + ",";

                    }
                    Log.d(TAG, "onClick:arrayListTagNamesSample whishlistAddId " + whishlistAddId);
                    if (whishlistAddId != null && whishlistAddId.length() > 0
                            && whishlistAddId.charAt(whishlistAddId.length() - 1) == ',') {
                        whishlistAddId = whishlistAddId.substring(0, whishlistAddId.length() - 1);

                        Log.d(TAG, "onClick:arrayListTagNamesSample whishlistAddId remove comma - " + whishlistAddId);

                        addWhishlist(whishlistAddId, mTagsEditText, dialog);


                    } else {

                        Toast.makeText(getActivity(), "Please add the wishlist", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }


            }
        });


        dialog.show();


    }

    private void addWhishlist(String whishlistAddId, final TagsEditText mTagsEditText, final Dialog dialog) {

        progressDialog = Utils.createProgressDialog(getActivity());


        Map<String, String> data = new HashMap<>();
        data.put("wishlist", whishlistAddId);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        addWishlist = apiService.addWhishlistMaster(Pref.getPreToken(), data);

        addWishlist.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(@NonNull Call<BasicResponse> call, @NonNull Response<BasicResponse> response) {

                if (response.code() == 200 || response.code() == 201) {

                    BasicResponse basicResponse = response.body();

                    if (basicResponse.getStatus().equals("1")) {

                        try {
                            Pref.setdevicetoken("Bearer " + basicResponse.getToken());

                            mTagsEditText.setTags(" ");
                            dialog.dismiss();
                            progressDialog.dismiss();

                            Toast.makeText(getActivity(), " Wishlist added sucessfully", Toast.LENGTH_SHORT).show();

                            loadProfileData();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), basicResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else if (response.code() == 304) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<BasicResponse> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();

            }
        });


    }


    private void loadDilaog() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_work_info);

        TextView text = dialog.findViewById(R.id.text_dialog);
        ImageView imageView = dialog.findViewById(R.id.icClose);
        text.setText(workHelper);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void switchTabInActivity(int indexTabToSwitchTo) {

        ((ProfileActivity) getActivity()).switchTab(indexTabToSwitchTo);

    }

    @Override
    public void onTagsChanged(Collection<String> tags) {

        Log.v("Tags_changed: ", " " + tags.iterator());


        arrayListTagNamesSample.clear();
        arrayListTagNamesSample.addAll(tags);
        Log.d(TAG, "onTagsChanged: " + arrayListTagNamesSample.size());


    }

    @Override
    public void onEditingFinished() {

    }


}
