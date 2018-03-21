package com.mindnotix.model.dashboard.tags.taglist;

/**
 * Awesome Pojo Generator
 */
public class Data {
    private String ptg_name;
    private String ptg_created_on;
    private String ptg_status;
    private String status="0";
    private String ptg_created_by;
    private String ptg_tag_id;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPtg_name() {
        return ptg_name;
    }

    public void setPtg_name(String ptg_name) {
        this.ptg_name = ptg_name;
    }

    public String getPtg_created_on() {
        return ptg_created_on;
    }

    public void setPtg_created_on(String ptg_created_on) {
        this.ptg_created_on = ptg_created_on;
    }

    public String getPtg_status() {
        return ptg_status;
    }

    public void setPtg_status(String ptg_status) {
        this.ptg_status = ptg_status;
    }

    public String getPtg_created_by() {
        return ptg_created_by;
    }

    public void setPtg_created_by(String ptg_created_by) {
        this.ptg_created_by = ptg_created_by;
    }

    public String getPtg_tag_id() {
        return ptg_tag_id;
    }

    public void setPtg_tag_id(String ptg_tag_id) {
        this.ptg_tag_id = ptg_tag_id;
    }



    /**
     * Pay attention here, you have to override the toString method as the
     * ArrayAdapter will reads the toString of the given object for the name
     *
     * @return ptg_name
     */
    @Override
    public String toString() {
        return ptg_name;
    }

}