package com.mindnotix.model.contactsupport.ticket_cancel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Data {
    @SerializedName("cancelticket")
    @Expose
    private String cancelticket;

    public String getCancelticket() {
        return cancelticket;
    }

    public void setCancelticket(String cancelticket) {
        this.cancelticket = cancelticket;
    }
}