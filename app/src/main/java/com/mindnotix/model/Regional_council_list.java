package com.mindnotix.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Regional_council_list {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("rc_latitude")
    @Expose
    private String rc_latitude;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("rc_longitude")
    @Expose
    private String rc_longitude;
    @SerializedName("trademeid")
    @Expose
    private String trademeid;
    @SerializedName("status")
    @Expose
    private String status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRc_latitude() {
        return rc_latitude;
    }

    public void setRc_latitude(String rc_latitude) {
        this.rc_latitude = rc_latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRc_longitude() {
        return rc_longitude;
    }

    public void setRc_longitude(String rc_longitude) {
        this.rc_longitude = rc_longitude;
    }

    public String getTrademeid() {
        return trademeid;
    }

    public void setTrademeid(String trademeid) {
        this.trademeid = trademeid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Pay attention here, you have to override the toString method as the
     * ArrayAdapter will reads the toString of the given object for the name
     *
     * @return contact_name
     */
    @Override
    public String toString() {
        return name;
    }
}