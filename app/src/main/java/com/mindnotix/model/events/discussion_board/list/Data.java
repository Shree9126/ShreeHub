package com.mindnotix.model.events.discussion_board.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class Data {
    @SerializedName("event_chat_list")
    @Expose
    private List<Event_chat_list> event_chat_list;
    @SerializedName("path_image")
    @Expose
    private String path_image;

    public List<Event_chat_list> getEvent_chat_list() {
        return event_chat_list;
    }

    public void setEvent_chat_list(List<Event_chat_list> event_chat_list) {
        this.event_chat_list = event_chat_list;
    }

    public String getPath_image() {
        return path_image;
    }

    public void setPath_image(String path_image) {
        this.path_image = path_image;
    }
}