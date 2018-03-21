package com.mindnotix.model.resume.loadResume;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class Data {
    @SerializedName("volunteer")
    @Expose
    private List<Volunteer> volunteer;
    @SerializedName("languages")
    @Expose
    private List<Languages> languages;
    @SerializedName("educations")
    @Expose
    private List<Educations> educations;
    @SerializedName("employments")
    @Expose
    private List<Employments> employments;
    @SerializedName("technicalskills")
    @Expose
    private List<Technicalskills> technicalskills;
    @SerializedName("interests")
    @Expose
    private List<Interests> interests;
    @SerializedName("honorsawards")
    @Expose
    private List<Honorsawards> honorsawards;

    public void setVolunteer(List<Volunteer> volunteer) {
        this.volunteer = volunteer;
    }

    public List<Volunteer> getVolunteer() {
        return volunteer;
    }

    public void setLanguages(List<Languages> languages) {
        this.languages = languages;
    }

    public List<Languages> getLanguages() {
        return languages;
    }

    public void setEducations(List<Educations> educations) {
        this.educations = educations;
    }

    public List<Educations> getEducations() {
        return educations;
    }

    public void setEmployments(List<Employments> employments) {
        this.employments = employments;
    }

    public List<Employments> getEmployments() {
        return employments;
    }

    public void setTechnicalskills(List<Technicalskills> technicalskills) {
        this.technicalskills = technicalskills;
    }

    public List<Technicalskills> getTechnicalskills() {
        return technicalskills;
    }

    public void setInterests(List<Interests> interests) {
        this.interests = interests;
    }

    public List<Interests> getInterests() {
        return interests;
    }

    public void setHonorsawards(List<Honorsawards> honorsawards) {
        this.honorsawards = honorsawards;
    }

    public List<Honorsawards> getHonorsawards() {
        return honorsawards;
    }
// }
}