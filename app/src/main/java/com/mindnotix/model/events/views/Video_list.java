package com.mindnotix.model.events.views;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 2/16/2018.
 */

public class Video_list {

    @SerializedName("video_description")
    @Expose
    private String video_description;
    @SerializedName("video_url")
    @Expose
    private String video_url;
    @SerializedName("video_title")
    @Expose
    private String video_title;
    @SerializedName("video_thumb")
    @Expose
    private String video_thumb;
    @SerializedName("video_id")
    @Expose
    private String video_id;

    public String getVideo_description() {
        return video_description;
    }

    public void setVideo_description(String video_description) {
        this.video_description = video_description;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getVideo_thumb() {
        return video_thumb;
    }

    public void setVideo_thumb(String video_thumb) {
        this.video_thumb = video_thumb;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }
}
