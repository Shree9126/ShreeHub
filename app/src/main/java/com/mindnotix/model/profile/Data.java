package com.mindnotix.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class Data {


    @SerializedName("path_medium")
    @Expose
    private String path_medium;
    @SerializedName("vidimg_path")
    @Expose
    private String vidimg_path;
    @SerializedName("path_thumb")
    @Expose
    private String path_thumb;
    @SerializedName("path_large")
    @Expose
    private String path_large;
    @SerializedName("path_small")
    @Expose
    private String path_small;
    @SerializedName("gallerylist")
    @Expose
    private List<Gallerylist> gallerylist;
    @SerializedName("vid_path")
    @Expose
    private String vid_path;

    public String getPath_medium() {
        return path_medium;
    }

    public void setPath_medium(String path_medium) {
        this.path_medium = path_medium;
    }

    public String getVidimg_path() {
        return vidimg_path;
    }

    public void setVidimg_path(String vidimg_path) {
        this.vidimg_path = vidimg_path;
    }

    public String getPath_thumb() {
        return path_thumb;
    }

    public void setPath_thumb(String path_thumb) {
        this.path_thumb = path_thumb;
    }

    public String getPath_large() {
        return path_large;
    }

    public void setPath_large(String path_large) {
        this.path_large = path_large;
    }

    public String getPath_small() {
        return path_small;
    }

    public void setPath_small(String path_small) {
        this.path_small = path_small;
    }

    public List<Gallerylist> getGallerylist() {
        return gallerylist;
    }

    public void setGallerylist(List<Gallerylist> gallerylist) {
        this.gallerylist = gallerylist;
    }

    public String getVid_path() {
        return vid_path;
    }

    public void setVid_path(String vid_path) {
        this.vid_path = vid_path;
    }
}