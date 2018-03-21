package com.mindnotix.model.contactsupport.reply;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Data {
    @SerializedName("replyticket_id")
    @Expose
    private String replyticket_id;

    public String getReplyticket_id() {
        return replyticket_id;
    }

    public void setReplyticket_id(String replyticket_id) {
        this.replyticket_id = replyticket_id;
    }
}