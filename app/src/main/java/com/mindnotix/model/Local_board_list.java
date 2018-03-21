package com.mindnotix.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Local_board_list {
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lb_longitude")
    @Expose
    private String lb_longitude;
    @SerializedName("regional_council_id")
    @Expose
    private String regional_council_id;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("lb_latitude")
    @Expose
    private String lb_latitude;
    @SerializedName("trademeid")
    @Expose
    private String trademeid;
    @SerializedName("status")
    @Expose
    private String status;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLb_longitude() {
        return lb_longitude;
    }

    public void setLb_longitude(String lb_longitude) {
        this.lb_longitude = lb_longitude;
    }

    public String getRegional_council_id() {
        return regional_council_id;
    }

    public void setRegional_council_id(String regional_council_id) {
        this.regional_council_id = regional_council_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLb_latitude() {
        return lb_latitude;
    }

    public void setLb_latitude(String lb_latitude) {
        this.lb_latitude = lb_latitude;
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
     * @return name
     */
    @Override
    public String toString() {
        return name;
    }
}