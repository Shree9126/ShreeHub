package com.mindnotix.model.find_connection.master;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class Data {
    @SerializedName("local_boards")
    @Expose
    private List<Local_boards> local_boards;
    @SerializedName("local_board_current_id")
    @Expose
    private String local_board_current_id;
    @SerializedName("ysp_partnertype")
    @Expose
    private List<Ysp_partnertype> ysp_partnertype;
    @SerializedName("wishlist")
    @Expose
    private List<Wishlist> wishlist;
    @SerializedName("regional_current_id")
    @Expose
    private String regional_current_id;
    @SerializedName("usergroup")
    @Expose
    private List<Usergroup> usergroup;
    @SerializedName("regionals")
    @Expose
    private List<Regionals> regionals;
    @SerializedName("biz_partnertype")
    @Expose
    private List<Biz_partnertype> biz_partnertype;
    @SerializedName("partner_tag")
    @Expose
    private List<Partner_tag> partner_tag;


    public List<Local_boards> getLocal_boards() {
        return local_boards;
    }

    public void setLocal_boards(List<Local_boards> local_boards) {
        this.local_boards = local_boards;
    }

    public String getLocal_board_current_id() {
        return local_board_current_id;
    }

    public void setLocal_board_current_id(String local_board_current_id) {
        this.local_board_current_id = local_board_current_id;
    }

    public List<Ysp_partnertype> getYsp_partnertype() {
        return ysp_partnertype;
    }

    public void setYsp_partnertype(List<Ysp_partnertype> ysp_partnertype) {
        this.ysp_partnertype = ysp_partnertype;
    }

    public List<Wishlist> getWishlist() {
        return wishlist;
    }

    public void setWishlist(List<Wishlist> wishlist) {
        this.wishlist = wishlist;
    }

    public String getRegional_current_id() {
        return regional_current_id;
    }

    public void setRegional_current_id(String regional_current_id) {
        this.regional_current_id = regional_current_id;
    }

    public List<Usergroup> getUsergroup() {
        return usergroup;
    }

    public void setUsergroup(List<Usergroup> usergroup) {
        this.usergroup = usergroup;
    }

    public List<Regionals> getRegionals() {
        return regionals;
    }

    public void setRegionals(List<Regionals> regionals) {
        this.regionals = regionals;
    }

    public List<Biz_partnertype> getBiz_partnertype() {
        return biz_partnertype;
    }

    public void setBiz_partnertype(List<Biz_partnertype> biz_partnertype) {
        this.biz_partnertype = biz_partnertype;
    }

    public List<Partner_tag> getPartner_tag() {
        return partner_tag;
    }

    public void setPartner_tag(List<Partner_tag> partner_tag) {
        this.partner_tag = partner_tag;
    }
}