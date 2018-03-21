package com.mindnotix.model.jobs.filter_master.local_board;

/**
 * Awesome Pojo Generator
 */
public class Local_board_list {
    private String user_id;
    private String name;
    private String lb_longitude;
    private String regional_council_id;
    private String id;
    private String lb_latitude;
    private String trademeid;
    private String status;

    public String getStatus_chk() {
        return status_chk;
    }

    public void setStatus_chk(String status_chk) {
        this.status_chk = status_chk;
    }

    private String status_chk="0";

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
     * @return ptg_name
     */
    @Override
    public String toString() {
        return name;
    }
}