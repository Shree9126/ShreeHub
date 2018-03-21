package com.mindnotix.model.dashboard.like_comments_contribute;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Posts {
    @SerializedName("pf_created_on")
    @Expose
    private String pf_created_on;
    @SerializedName("pf_user_id")
    @Expose
    private String pf_user_id;
    @SerializedName("pf_type")
    @Expose
    private String pf_type;
    @SerializedName("pf_feed_id")
    @Expose
    private String pf_feed_id;
    @SerializedName("pf_pm_post_id")
    @Expose
    private String pf_pm_post_id;
    @SerializedName("pf_message")
    @Expose
    private String pf_message;

    public String getPf_created_on() {
        return pf_created_on;
    }

    public void setPf_created_on(String pf_created_on) {
        this.pf_created_on = pf_created_on;
    }

    public String getPf_user_id() {
        return pf_user_id;
    }

    public void setPf_user_id(String pf_user_id) {
        this.pf_user_id = pf_user_id;
    }

    public String getPf_type() {
        return pf_type;
    }

    public void setPf_type(String pf_type) {
        this.pf_type = pf_type;
    }

    public String getPf_feed_id() {
        return pf_feed_id;
    }

    public void setPf_feed_id(String pf_feed_id) {
        this.pf_feed_id = pf_feed_id;
    }

    public String getPf_pm_post_id() {
        return pf_pm_post_id;
    }

    public void setPf_pm_post_id(String pf_pm_post_id) {
        this.pf_pm_post_id = pf_pm_post_id;
    }

    public String getPf_message() {
        return pf_message;
    }

    public void setPf_message(String pf_message) {
        this.pf_message = pf_message;
    }
}