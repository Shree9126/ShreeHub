package com.mindnotix.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Gallerylist {
    @SerializedName("pg_date")
    @Expose
    private String pg_date;
    @SerializedName("pg_title")
    @Expose
    private String pg_title;
    @SerializedName("pm_type")
    @Expose
    private String pm_type;
    @SerializedName("pg_name")
    @Expose
    private String pg_name;
    @SerializedName("pg_link")
    @Expose
    private String pg_link;
    @SerializedName("pg_type")
    @Expose
    private String pg_type;
    @SerializedName("pg_gallery_id")
    @Expose
    private String pg_gallery_id;

    public String getPg_date() {
        return pg_date;
    }

    public void setPg_date(String pg_date) {
        this.pg_date = pg_date;
    }

    public String getPg_title() {
        return pg_title;
    }

    public void setPg_title(String pg_title) {
        this.pg_title = pg_title;
    }

    public String getPm_type() {
        return pm_type;
    }

    public void setPm_type(String pm_type) {
        this.pm_type = pm_type;
    }

    public String getPg_name() {
        return pg_name;
    }

    public void setPg_name(String pg_name) {
        this.pg_name = pg_name;
    }

    public String getPg_link() {
        return pg_link;
    }

    public void setPg_link(String pg_link) {
        this.pg_link = pg_link;
    }

    public String getPg_type() {
        return pg_type;
    }

    public void setPg_type(String pg_type) {
        this.pg_type = pg_type;
    }

    public String getPg_gallery_id() {
        return pg_gallery_id;
    }

    public void setPg_gallery_id(String pg_gallery_id) {
        this.pg_gallery_id = pg_gallery_id;
    }
}