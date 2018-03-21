package com.mindnotix.model.contactsupport.view;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Supportview {
    @SerializedName("master_id")
    @Expose
    private String master_id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("hm_status")
    @Expose
    private String hm_status;
    @SerializedName("last_name")
    @Expose
    private String last_name;
    @SerializedName("ticket_id")
    @Expose
    private String ticket_id;
    @SerializedName("first_name")
    @Expose
    private String first_name;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;


    public String getMaster_id() {
        return master_id;
    }

    public void setMaster_id(String master_id) {
        this.master_id = master_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHm_status() {
        return hm_status;
    }

    public void setHm_status(String hm_status) {
        this.hm_status = hm_status;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
}