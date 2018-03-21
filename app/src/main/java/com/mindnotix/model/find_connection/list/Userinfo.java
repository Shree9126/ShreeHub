package com.mindnotix.model.find_connection.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Userinfo {
    @SerializedName("name_code")
    @Expose
    private String name_code;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("group_name")
    @Expose
    private String group_name;
    @SerializedName("isshareprofile")
    @Expose
    private String isshareprofile;
    @SerializedName("shareprofilestatus")
    @Expose
    private String shareprofilestatus;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("about_me")
    @Expose
    private String about_me;
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("subtype")
    @Expose
    private String subtype;
    @SerializedName("group_id")
    @Expose
    private String group_id;
    @SerializedName("isfollowing")
    @Expose
    private String isfollowing;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("profile_pic_url")
    @Expose
    private String profile_pic_url;
    @SerializedName("followingstatus")
    @Expose
    private String followingstatus;


    public String getName_code() {
        return name_code;
    }

    public void setName_code(String name_code) {
        this.name_code = name_code;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getIsshareprofile() {
        return isshareprofile;
    }

    public void setIsshareprofile(String isshareprofile) {
        this.isshareprofile = isshareprofile;
    }

    public String getShareprofilestatus() {
        return shareprofilestatus;
    }

    public void setShareprofilestatus(String shareprofilestatus) {
        this.shareprofilestatus = shareprofilestatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAbout_me() {
        return about_me;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getIsfollowing() {
        return isfollowing;
    }

    public void setIsfollowing(String isfollowing) {
        this.isfollowing = isfollowing;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getProfile_pic_url() {
        return profile_pic_url;
    }

    public void setProfile_pic_url(String profile_pic_url) {
        this.profile_pic_url = profile_pic_url;
    }

    public String getFollowingstatus() {
        return followingstatus;
    }

    public void setFollowingstatus(String followingstatus) {
        this.followingstatus = followingstatus;
    }
}