package com.mindnotix.model.find_connection.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class Data {
    @SerializedName("userinfo")
    @Expose
    private List<Userinfo> userinfo;

    public List<Userinfo> getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(List<Userinfo> userinfo) {
        this.userinfo = userinfo;
    }
}