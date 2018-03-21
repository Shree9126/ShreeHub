package com.mindnotix.model.explore.my_explore_remove;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Data {
    @SerializedName("removeid")
    @Expose
    private String removeid;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public String getRemoveid() {
        return removeid;
    }

    public void setRemoveid(String removeid) {
        this.removeid = removeid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}