package com.mindnotix.model.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostUserActivity {

    @SerializedName("is_encourage_user")
    @Expose
    private String isEncourageUser;
    @SerializedName("is_report_user")
    @Expose
    private String isReportUser;


    @SerializedName("encourage_feed_id")
    @Expose
    private String encourageFeedId;

    @SerializedName("is_favour_user")
    @Expose
    private String is_favour_user;

    @SerializedName("favour_id")
    @Expose
    private String favour_id;


    public String getEncourageFeedId() {
        return encourageFeedId;
    }

    public void setEncourageFeedId(String encourageFeedId) {
        this.encourageFeedId = encourageFeedId;
    }

    public String getIsEncourageUser() {
        return isEncourageUser;
    }

    public void setIsEncourageUser(String isEncourageUser) {
        this.isEncourageUser = isEncourageUser;
    }

    public String getIsReportUser() {
        return isReportUser;
    }

    public void setIsReportUser(String isReportUser) {
        this.isReportUser = isReportUser;
    }

    public String getIs_favour_user() {
        return is_favour_user;
    }

    public void setIs_favour_user(String is_favour_user) {
        this.is_favour_user = is_favour_user;
    }

    public String getFavour_id() {
        return favour_id;
    }

    public void setFavour_id(String favour_id) {
        this.favour_id = favour_id;
    }
}
