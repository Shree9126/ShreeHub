package com.mindnotix.model.dashboard.image_upload;

import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class Data {
    private String path_medium;
    private List<String> filename;
    private List<String> filenameuploadfield;
    private String path_thumb;
    private String path_large;
    private String path_small;

    public String getPath_medium() {
        return path_medium;
    }


    public void setPath_medium(String path_medium) {
        this.path_medium = path_medium;
    }

    public List<String> getFilename() {
        return filename;
    }   public List<String> getFilenameuploadfield() {
        return filenameuploadfield;
    }

    public void setFilename(List<String> filename) {
        this.filename = filename;
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
}