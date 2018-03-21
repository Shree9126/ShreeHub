package com.mindnotix.model.message.chat_history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Chat_list_users {
    @SerializedName("profile_image")
    @Expose
    private String profile_image;

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("group_id")
    @Expose
    private String group_id;

    @SerializedName("unread_count")
    @Expose
    private String unread_count;

    @SerializedName("created_date")
    @Expose
    private String created_date;


    @SerializedName("created_time")
    @Expose
    private String created_time;


    @SerializedName("last_name")
    @Expose
    private String last_name;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("first_name")
    @Expose
    private String first_name;
    @SerializedName("chat_id")
    @Expose
    private String chat_id;
    @SerializedName("isonline")
    @Expose
    private String isonline;
    @SerializedName("status")
    @Expose
    private String status;

    public String getUnread_count() {
        return unread_count;
    }

    public void setUnread_count(String unread_count) {
        this.unread_count = unread_count;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getIsonline() {
        return isonline;
    }

    public void setIsonline(String isonline) {
        this.isonline = isonline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}