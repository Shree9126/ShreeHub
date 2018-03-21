package com.mindnotix.model.contactsupport.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class Data {
    @SerializedName("supportlist")
    @Expose
    private List<Supportlist> supportlist;

    public void setSupportlist(List<Supportlist> supportlist) {
        this.supportlist = supportlist;
    }

    public List<Supportlist> getSupportlist() {
        return supportlist;
    }
}