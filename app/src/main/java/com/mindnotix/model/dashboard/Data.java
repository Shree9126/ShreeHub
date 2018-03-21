
package com.mindnotix.model.dashboard;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("postlist")
    @Expose
    private List<Postlist> postlist = null;
    @SerializedName("path_large")
    @Expose
    private String pathLarge;
    @SerializedName("path_medium")
    @Expose
    private String pathMedium;
    @SerializedName("path_small")
    @Expose
    private String pathSmall;
    @SerializedName("path_thumb")
    @Expose
    private String pathThumb;

    public List<Postlist> getPostlist() {
        return postlist;
    }

    public void setPostlist(List<Postlist> postlist) {
        this.postlist = postlist;
    }

    public String getPathLarge() {
        return pathLarge;
    }

    public void setPathLarge(String pathLarge) {
        this.pathLarge = pathLarge;
    }

    public String getPathMedium() {
        return pathMedium;
    }

    public void setPathMedium(String pathMedium) {
        this.pathMedium = pathMedium;
    }

    public String getPathSmall() {
        return pathSmall;
    }

    public void setPathSmall(String pathSmall) {
        this.pathSmall = pathSmall;
    }

    public String getPathThumb() {
        return pathThumb;
    }

    public void setPathThumb(String pathThumb) {
        this.pathThumb = pathThumb;
    }

}
