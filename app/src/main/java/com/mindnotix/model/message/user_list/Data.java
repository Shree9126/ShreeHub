package com.mindnotix.model.message.user_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class Data {
    @SerializedName("chat_users_list")
    @Expose
    private List<Chat_users_list> chat_users_list;
    @SerializedName("path_image")
    @Expose
    private String path_image;

    public List<Chat_users_list> getChat_users_list() {
        return chat_users_list;
    }

    public void setChat_users_list(List<Chat_users_list> chat_users_list) {
        this.chat_users_list = chat_users_list;
    }

    public String getPath_image() {
        return path_image;
    }

    public void setPath_image(String path_image) {
        this.path_image = path_image;
    }
}