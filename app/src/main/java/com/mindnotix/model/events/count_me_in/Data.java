package com.mindnotix.model.events.count_me_in;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Data {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("countmeout")
    @Expose
    private String countmeout;


    @SerializedName("countmeid")
    @Expose
    private String countmeid;

    public String getCountmeout() {
        return countmeout;
    }

    public void setCountmeout(String countmeout) {
        this.countmeout = countmeout;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCountmeid() {
        return countmeid;
    }

    public void setCountmeid(String countmeid) {
        this.countmeid = countmeid;
    }
}