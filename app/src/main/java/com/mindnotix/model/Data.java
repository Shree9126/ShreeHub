package com.mindnotix.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mindnotix.model.about.WishListTag;
import com.mindnotix.model.jobs.my_jobs.AppliedList;
import com.mindnotix.model.jobs.my_jobs.InvitedList;
import com.mindnotix.model.profile.Youth;
import com.mindnotix.model.profile.Youthuser;
import com.mindnotix.model.profile.update.CurrentQualificationType;
import com.mindnotix.model.profile.update.Gender;
import com.mindnotix.model.profile.update.IntendedDestination;
import com.mindnotix.model.profile.update.Iwi;
import com.mindnotix.model.profile.update.LicenseType;
import com.mindnotix.model.profile.update.Surub;
import com.mindnotix.model.profile.update.WhatVisaHave;
import com.mindnotix.model.profile.update.WorkAvailablity;
import com.mindnotix.model.profile.update.WorkExperience;
import com.mindnotix.model.profile.update.WorkStatus;
import com.mindnotix.model.profile.update.WorkType;
import com.mindnotix.model.youth_support.ShareYouth;

import java.util.ArrayList;
import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class Data {

    @SerializedName("email")
    @Expose
    private String email;

    private String dob;

    private String user_type;
    @SerializedName("ethnicity")
    @Expose
    private ArrayList<Ethnicity> ethnicity;
    @SerializedName("wishlist")
    @Expose
    private ArrayList<Wishlist> wishlist;
    @SerializedName("regional_council_list")
    @Expose
    private ArrayList<Regional_council_list> regional_council_list;

    @SerializedName("local_board_list")
    @Expose
    private List<Local_board_list> local_board_list;


    @SerializedName("cover_pic")
    @Expose
    private String cover_pic;
    @SerializedName("profile_pic")
    @Expose
    private String profile_pic;
    @SerializedName("last_name")
    @Expose
    private String last_name;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("first_name")
    @Expose
    private String first_name;
    @SerializedName("path_medium")
    @Expose
    private String path_medium;
    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("path_thumb")
    @Expose
    private String path_thumb;
    @SerializedName("path_large")
    @Expose
    private String path_large;
    private Youthuser youthuser;
    private Youth youth;

    public String getCover_pic() {
        return cover_pic;
    }

    public void setCover_pic(String cover_pic) {
        this.cover_pic = cover_pic;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public ArrayList<Ethnicity> getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(ArrayList<Ethnicity> ethnicity) {
        this.ethnicity = ethnicity;
    }

    //upload profile picture

    public ArrayList<Wishlist> getWishlist() {
        return wishlist;
    }

    public void setWishlist(ArrayList<Wishlist> wishlist) {
        this.wishlist = wishlist;
    }

    public ArrayList<Regional_council_list> getRegional_council_list() {
        return regional_council_list;
    }

    public void setRegional_council_list(ArrayList<Regional_council_list> regional_council_list) {
        this.regional_council_list = regional_council_list;
    }

    public List<Local_board_list> getLocal_board_list() {
        return local_board_list;
    }

    public void setLocal_board_list(List<Local_board_list> local_board_list) {
        this.local_board_list = local_board_list;
    }

    public String getPath_medium() {
        return path_medium;
    }

    public void setPath_medium(String path_medium) {
        this.path_medium = path_medium;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPath_thumb() {
        return path_thumb;
    }

    public void setPath_thumb(String path_thumb) {
        this.path_thumb = path_thumb;
    }


    //Profile

    public String getPath_large() {
        return path_large;
    }

    public void setPath_large(String path_large) {
        this.path_large = path_large;
    }

    public Youthuser getYouthuser() {
        return youthuser;
    }

    public void setYouthuser(Youthuser youthuser) {
        this.youthuser = youthuser;
    }

    public Youth getYouth() {
        return youth;
    }

    public void setYouth(Youth youth) {
        this.youth = youth;
    }

    private String following_count;

    public String getFollowing_count() {
        return following_count;
    }

    public void setFollowing_count(String following_count) {
        this.following_count = following_count;
    }

    public String getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(String followers_count) {
        this.followers_count = followers_count;
    }

    private String followers_count;


    public String getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(String cover_photo) {
        this.cover_photo = cover_photo;
    }

    private String cover_photo;


    public ArrayList<WhatVisaHave> getWhat_visa_have() {
        return what_visa_have;
    }

    public void setWhat_visa_have(ArrayList<WhatVisaHave> what_visa_have) {
        this.what_visa_have = what_visa_have;
    }

    ArrayList<WhatVisaHave>what_visa_have;

    ArrayList<Iwi>iwi;

    public ArrayList<Iwi> getIwi() {
        return iwi;
    }

    public void setIwi(ArrayList<Iwi> iwi) {
        this.iwi = iwi;
    }

    public ArrayList<Surub> getSuburbs_list() {
        return suburbs_list;
    }

    public void setSuburbs_list(ArrayList<Surub> suburbs_list) {
        this.suburbs_list = suburbs_list;
    }

    public ArrayList<Surub>suburbs_list;

    public ArrayList<WorkType> getWork_type() {
        return work_type;
    }

    public void setWork_type(ArrayList<WorkType> work_type) {
        this.work_type = work_type;
    }

    public ArrayList<WorkType>work_type;


    public ArrayList<LicenseType> getLicense_type() {
        return license_type;
    }

    public void setLicense_type(ArrayList<LicenseType> license_type) {
        this.license_type = license_type;
    }

    public ArrayList<LicenseType>license_type;


    public ArrayList<IntendedDestination> getIntended_destination() {
        return intended_destination;
    }

    public void setIntended_destination(ArrayList<IntendedDestination> intended_destination) {
        this.intended_destination = intended_destination;
    }

    public ArrayList<IntendedDestination>intended_destination;


    public ArrayList<WorkExperience> getWork_experience() {
        return work_experience;
    }

    public void setWork_experience(ArrayList<WorkExperience> work_experience) {
        this.work_experience = work_experience;
    }

    public ArrayList<WorkExperience>work_experience;


    public ArrayList<WorkAvailablity> getGeneral_availability() {
        return general_availability;
    }

    public void setGeneral_availability(ArrayList<WorkAvailablity> general_availability) {
        this.general_availability = general_availability;
    }

    public ArrayList<WorkAvailablity>general_availability;



    public ArrayList<WorkStatus> getWork_status() {
        return work_status;
    }

    public void setWork_status(ArrayList<WorkStatus> work_status) {
        this.work_status = work_status;
    }

    public ArrayList<WorkStatus>work_status;



    public ArrayList<CurrentQualificationType> getCurrent_qualification_type() {
        return current_qualification_type;
    }

    public void setCurrent_qualification_type(ArrayList<CurrentQualificationType> current_qualification_type) {
        this.current_qualification_type = current_qualification_type;
    }

    ArrayList<CurrentQualificationType> current_qualification_type;

    public ArrayList<Gender> getGender() {
        return gender;
    }

    public void setGender(ArrayList<Gender> gender) {
        this.gender = gender;
    }

    ArrayList<Gender> gender;

    public String getWork_information_helper() {
        return work_information_helper;
    }

    public void setWork_information_helper(String work_information_helper) {
        this.work_information_helper = work_information_helper;
    }

    private String work_information_helper;




    public String getCommunications_helper() {
        return communications_helper;
    }

    public void setCommunications_helper(String communications_helper) {
        this.communications_helper = communications_helper;
    }

    private String communications_helper;


    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    ArrayList<String>skills;

    public void setWishlisttag(ArrayList<WishListTag> wishlisttag) {
        this.wishlisttag = wishlisttag;
    }

    public ArrayList<WishListTag> getWishlisttag() {
        return wishlisttag;
    }

    private ArrayList<WishListTag> wishlisttag;



    public ArrayList<ShareYouth> getShare_youth() {
        return share_youth;
    }

    public void setShare_youth(ArrayList<ShareYouth> share_youth) {
        this.share_youth = share_youth;
    }

    private ArrayList<ShareYouth>share_youth;

    public String getShare_youth_count() {
        return share_youth_count;
    }

    public void setShare_youth_count(String share_youth_count) {
        this.share_youth_count = share_youth_count;
    }

    private String share_youth_count;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscretion() {
        return discretion;
    }

    public void setDiscretion(String discretion) {
        this.discretion = discretion;
    }

    private String title;
    private String discretion;

    private String selected_count;

    public String getSelected_count() {
        return selected_count;
    }

    public void setSelected_count(String selected_count) {
        this.selected_count = selected_count;
    }

    public String getShorlisted_count() {
        return shorlisted_count;
    }

    public void setShorlisted_count(String shorlisted_count) {
        this.shorlisted_count = shorlisted_count;
    }

    public String getDeclined_count() {
        return declined_count;
    }

    public void setDeclined_count(String declined_count) {
        this.declined_count = declined_count;
    }

    public String getInvited_list_count() {
        return invited_list_count;
    }

    public void setInvited_list_count(String invited_list_count) {
        this.invited_list_count = invited_list_count;
    }

    private String shorlisted_count;
    private String declined_count;
    private String invited_list_count;

    public ArrayList<AppliedList> getApplied_list() {
        return applied_list;
    }

    public void setApplied_list(ArrayList<AppliedList> applied_list) {
        this.applied_list = applied_list;
    }

    private ArrayList<AppliedList> applied_list;

    public ArrayList<InvitedList> getInvited_list() {
        return invited_list;
    }

    public void setInvited_list(ArrayList<InvitedList> invited_list) {
        this.invited_list = invited_list;
    }

    private ArrayList<InvitedList> invited_list;


    public String getApplied_list_count() {
        return applied_list_count;
    }

    public void setApplied_list_count(String applied_list_count) {
        this.applied_list_count = applied_list_count;
    }

    private String applied_list_count;




}