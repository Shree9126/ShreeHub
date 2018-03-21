package com.mindnotix.model.explore.list;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Explore_list{
  @SerializedName("tags_name")
  @Expose
  private List<Tags_name> tags_name;
  @SerializedName("post_id")
  @Expose
  private String post_id;
  @SerializedName("update_on")
  @Expose
  private String update_on;
  @SerializedName("subject")
  @Expose
  private String subject;
  @SerializedName("post_by_firstname")
  @Expose
  private String post_by_firstname;
  @SerializedName("profile_id")
  @Expose
  private String profile_id;
  @SerializedName("description")
  @Expose
  private String description;
  @SerializedName("media")
  @Expose
  private List<Media> media;
  @SerializedName("title")
  @Expose
  private String title;
  @SerializedName("post_by_lastname")
  @Expose
  private String post_by_lastname;


  @SerializedName("ismyexplore")
  @Expose
  private String ismyexplore;

    public String getIsmyexplore() {
        return ismyexplore;
    }

    public void setIsmyexplore(String ismyexplore) {
        this.ismyexplore = ismyexplore;
    }

    public List<Tags_name> getTags_name() {
        return tags_name;
    }

    public void setTags_name(List<Tags_name> tags_name) {
        this.tags_name = tags_name;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getUpdate_on() {
        return update_on;
    }

    public void setUpdate_on(String update_on) {
        this.update_on = update_on;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPost_by_firstname() {
        return post_by_firstname;
    }

    public void setPost_by_firstname(String post_by_firstname) {
        this.post_by_firstname = post_by_firstname;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPost_by_lastname() {
        return post_by_lastname;
    }

    public void setPost_by_lastname(String post_by_lastname) {
        this.post_by_lastname = post_by_lastname;
    }

    public void setRating_id(String rating_id) {
        this.rating_id = rating_id;
    }

    public void setRating_name(String rating_name) {
        this.rating_name = rating_name;
    }

    private String rating_id;

    public String getRating_id() {
        return rating_id;
    }

    public String getRating_name() {
        return rating_name;
    }

    private String rating_name;
}

