package com.mindnotix.model.youth_support;

/**
 * Created by Admin on 07-02-2018.
 */

public class ShareYouth {

    private String request_id;
    private String share_to_user_id;

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getShare_to_user_id() {
        return share_to_user_id;
    }

    public void setShare_to_user_id(String share_to_user_id) {
        this.share_to_user_id = share_to_user_id;
    }

    public String getShared_firstname() {
        return shared_firstname;
    }

    public void setShared_firstname(String shared_firstname) {
        this.shared_firstname = shared_firstname;
    }

    public String getShared_lastname() {
        return shared_lastname;
    }

    public void setShared_lastname(String shared_lastname) {
        this.shared_lastname = shared_lastname;
    }

    public String getPosted_date() {
        return posted_date;
    }

    public void setPosted_date(String posted_date) {
        this.posted_date = posted_date;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String shared_firstname;
    private String shared_lastname;
    private String posted_date;
    private String profile_pic;
    private String status;

}
