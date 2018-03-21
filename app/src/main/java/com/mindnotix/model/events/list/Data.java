package com.mindnotix.model.events.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class Data {
    @SerializedName("path_image")
    @Expose
    private String path_image;
    @SerializedName("event_list")
    @Expose
    private List<Event_list> event_list;

    public String getPath_image() {
        return path_image;
    }

    public void setPath_image(String path_image) {
        this.path_image = path_image;
    }

    public List<Event_list> getEvent_list() {
        return event_list;
    }

    public void setEvent_list(List<Event_list> event_list) {
        this.event_list = event_list;
    }
}