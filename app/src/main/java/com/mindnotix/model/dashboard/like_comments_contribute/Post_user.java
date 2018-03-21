package com.mindnotix.model.dashboard.like_comments_contribute;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Post_user {
    @SerializedName("profile_pic")
    @Expose
    private String profile_pic;
    @SerializedName("last_name")
    @Expose
    private String last_name;
    @SerializedName("first_name")
    @Expose
    private String first_name;

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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
}