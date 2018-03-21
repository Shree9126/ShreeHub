package com.mindnotix.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class DataRegSumbitResult {


    @SerializedName("thumbnail_path")
    @Expose
    private String thumbnail_path;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("medium_path")
    @Expose
    private String medium_path;
    @SerializedName("cover_path")
    @Expose
    private String cover_path;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("token")
    @Expose
    private String token;

    public String getThumbnail_path() {
        return thumbnail_path;
    }

    public void setThumbnail_path(String thumbnail_path) {
        this.thumbnail_path = thumbnail_path;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMedium_path() {
        return medium_path;
    }

    public void setMedium_path(String medium_path) {
        this.medium_path = medium_path;
    }

    public String getCover_path() {
        return cover_path;
    }

    public void setCover_path(String cover_path) {
        this.cover_path = cover_path;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}