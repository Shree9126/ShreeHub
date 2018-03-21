package com.mindnotix.model.jobs.my_jobs;

/**
 * Created by Admin on 15-02-2018.
 */

public class InvitedList {

    private String job_id;

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getInvited_on() {
        return invited_on;
    }

    public void setInvited_on(String invited_on) {
        this.invited_on = invited_on;
    }

    private String job_title;
    private String business_name;
    private String business_id;
    private String invited_on;

}
