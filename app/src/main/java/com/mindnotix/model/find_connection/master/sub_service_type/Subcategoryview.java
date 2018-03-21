package com.mindnotix.model.find_connection.master.sub_service_type;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Subcategoryview {
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("partner_type_id")
    @Expose
    private String partner_type_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("livejobtotal")
    @Expose
    private String livejobtotal;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("trademeid")
    @Expose
    private String trademeid;
    @SerializedName("livevacanciestotal")
    @Expose
    private String livevacanciestotal;
    @SerializedName("status")
    @Expose
    private String status;
    private String status_chk = "0";

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPartner_type_id() {
        return partner_type_id;
    }

    public void setPartner_type_id(String partner_type_id) {
        this.partner_type_id = partner_type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLivejobtotal() {
        return livejobtotal;
    }

    public void setLivejobtotal(String livejobtotal) {
        this.livejobtotal = livejobtotal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrademeid() {
        return trademeid;
    }

    public void setTrademeid(String trademeid) {
        this.trademeid = trademeid;
    }

    public String getLivevacanciestotal() {
        return livevacanciestotal;
    }

    public void setLivevacanciestotal(String livevacanciestotal) {
        this.livevacanciestotal = livevacanciestotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_chk() {
        return status_chk;
    }

    public void setStatus_chk(String status_chk) {
        this.status_chk = status_chk;
    }
}