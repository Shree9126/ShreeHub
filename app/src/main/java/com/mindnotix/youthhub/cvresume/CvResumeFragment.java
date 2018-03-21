package com.mindnotix.youthhub.cvresume;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mindnotix.adapter.resumeAdapter.InterestsAdapter;
import com.mindnotix.adapter.resumeAdapter.technicalSkills.TechnicalskillsAdapter;
import com.mindnotix.adapter.resumeAdapter.VolunterAdapter;
import com.mindnotix.adapter.resumeAdapter.AchieveMentsAdapter;
import com.mindnotix.adapter.resumeAdapter.EducationAdapter;
import com.mindnotix.adapter.resumeAdapter.workExperience.KeyResponsiblitesLoadAdapter;
import com.mindnotix.adapter.resumeAdapter.LanguagesAdapter;
import com.mindnotix.adapter.resumeAdapter.workExperience.ResumeWorkExperienceAdapter;
import com.mindnotix.model.resume.loadResume.Educations;
import com.mindnotix.model.resume.loadResume.Honorsawards;
import com.mindnotix.model.resume.loadResume.Interests;
import com.mindnotix.model.resume.loadResume.Languages;
import com.mindnotix.model.resume.loadResume.Technicalskills;
import com.mindnotix.model.resume.loadResume.Volunteer;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.youthhub.R;
import com.mindnotix.model.resume.loadResume.Employments;
import com.mindnotix.model.resume.loadResume.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 26-02-2018.
 */

public class CvResumeFragment extends Fragment {

    protected View addEditCvLayout;
    protected RecyclerView recyclerViewWorkExperience;
    protected RecyclerView recyclerViewKeyPoints;
    protected RecyclerView recyclerEducation;
    protected RecyclerView recyclerVolunter;
    protected RecyclerView recyclerTechnicalSkills;
    protected RecyclerView recyclerLanguages;
    protected RecyclerView recyclerInterests;
    protected RecyclerView recyclerAchievementAwards;
    protected ResumeWorkExperienceAdapter resumeWorkExperienceAdapter;
    protected KeyResponsiblitesLoadAdapter keyResponsiblitesLoadAdapter;
    protected EducationAdapter educationAdapter;
    protected VolunterAdapter volunterAdapter;
    protected AchieveMentsAdapter achieveMentsAdapter;
    protected TechnicalskillsAdapter technicalskillsAdapter;
    protected InterestsAdapter interestsAdapter;
    protected LanguagesAdapter languagesAdapter;
    ArrayList<Employments> employments;
    ArrayList<Educations> educations;
    ArrayList<Volunteer> volunteerArrayList;
    ArrayList<Honorsawards> honorsawards;
    ArrayList<Interests> interestsArrayList;
    ArrayList<Technicalskills> technicalskills;
    ArrayList<Languages> languagesArrayList;

    LinearLayoutManager linearLayoutManager;
    ProgressDialog progressDialog;
    View mainLoadView;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_cv_resume, container, false);
        InitUi(view);
        return view;
    }

    private void InitUi(View view) {


        recyclerViewWorkExperience = view.findViewById(R.id.recyclerViewWorkExperience);
        recyclerEducation = view.findViewById(R.id.recyclerEducation);
        recyclerVolunter = view.findViewById(R.id.recyclerVolunter);
        recyclerAchievementAwards = view.findViewById(R.id.recyclerAchievementAwards);
        recyclerTechnicalSkills = view.findViewById(R.id.recyclerTechnicalSkills);
        recyclerLanguages = view.findViewById(R.id.recyclerLanguages);
        recyclerInterests = view.findViewById(R.id.recyclerInterests);
        mainLoadView = view.findViewById(R.id.mainLoadView);
        progressBar = view.findViewById(R.id.progressBar);


    }

    Call<Resume> loadResumeData;

    public void loadResumeData() {


        employments = new ArrayList<>();
        educations = new ArrayList<>();
        volunteerArrayList = new ArrayList<>();
        honorsawards = new ArrayList<>();
        interestsArrayList = new ArrayList<>();
        technicalskills = new ArrayList<>();
        languagesArrayList = new ArrayList<>();



        Map<String, String> data = new HashMap<>();
        data.put("profileid", Pref.getClientId());

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        loadResumeData = apiService.loadResume(Pref.getPreToken(), data);

        loadResumeData.enqueue(new Callback<Resume>() {
            @Override
            public void onResponse(@NonNull Call<Resume> call, @NonNull Response<Resume> response) {

                progressBar.setVisibility(View.GONE);
                mainLoadView.setVisibility(View.VISIBLE);
                if (response.code() == 200 || response.code() == 201) {

                    Resume resume = response.body();

                    assert resume != null;
                    if (resume.getStatus().equals("1")) {


                        try {

                            Pref.setdevicetoken("Bearer " + resume.getToken());

                            if (resume.getData().getEmployments() != null) {
                                employments.addAll(resume.getData().getEmployments());
                            }

                            if(resume.getData().getEducations()!=null){
                                educations.addAll(resume.getData().getEducations());
                            }

                            if(resume.getData().getVolunteer()!=null){
                                volunteerArrayList.addAll(resume.getData().getVolunteer());
                            }

                            if(resume.getData().getHonorsawards()!=null){
                                honorsawards.addAll(resume.getData().getHonorsawards());
                            }

                            if(resume.getData().getInterests()!=null){
                                interestsArrayList.addAll(resume.getData().getInterests());
                            }

                            if(resume.getData().getTechnicalskills()!=null){
                                technicalskills.addAll(resume.getData().getTechnicalskills());
                            }
                            if(resume.getData().getLanguages()!=null){
                                languagesArrayList.addAll(resume.getData().getLanguages());
                            }


                            resumeWorkExperienceAdapter = new ResumeWorkExperienceAdapter(getActivity(), employments);
                            recyclerViewWorkExperience.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerViewWorkExperience.setAdapter(resumeWorkExperienceAdapter);
                            recyclerViewWorkExperience.setNestedScrollingEnabled(false);

                            educationAdapter = new EducationAdapter(getActivity(), educations);
                            recyclerEducation.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerEducation.setAdapter(educationAdapter);
                            recyclerEducation.setNestedScrollingEnabled(false);

                            volunterAdapter = new VolunterAdapter(getActivity(), volunteerArrayList);
                            recyclerVolunter.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerVolunter.setAdapter(volunterAdapter);
                            recyclerVolunter.setNestedScrollingEnabled(false);


                            achieveMentsAdapter = new AchieveMentsAdapter(getActivity(), honorsawards);
                            recyclerAchievementAwards.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerAchievementAwards.setAdapter(achieveMentsAdapter);
                            recyclerAchievementAwards.setNestedScrollingEnabled(false);

                            technicalskillsAdapter = new TechnicalskillsAdapter(getActivity(), technicalskills);
                            recyclerTechnicalSkills.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerTechnicalSkills.setAdapter(technicalskillsAdapter);
                            recyclerTechnicalSkills.setNestedScrollingEnabled(false);

                            interestsAdapter = new InterestsAdapter(getActivity(), interestsArrayList);
                            recyclerInterests.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerInterests.setAdapter(interestsAdapter);
                            recyclerInterests.setNestedScrollingEnabled(false);

                            languagesAdapter = new LanguagesAdapter(getActivity(), languagesArrayList);
                            recyclerLanguages.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerLanguages.setAdapter(languagesAdapter);
                            recyclerLanguages.setNestedScrollingEnabled(false);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getActivity(), "failed to load data", Toast.LENGTH_SHORT).show();

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
            public void onFailure(@NonNull Call<Resume> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                t.printStackTrace();

            }
        });


    }


    @Override
    public void onResume() {
        loadResumeData();
        super.onResume();
    }
}
