package com.mindnotix.model.explore.discussionboard.list;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Explore_chat_list{
  @SerializedName("date")
  @Expose
  private String date;
  @SerializedName("profile_image")
  @Expose
  private String profile_image;
  @SerializedName("user_id")
  @Expose
  private String user_id;
  @SerializedName("post_by_firstname")
  @Expose
  private String post_by_firstname;
  @SerializedName("message")
  @Expose
  private String message;
  @SerializedName("post_by_lastname")
  @Expose
  private String post_by_lastname;
  @SerializedName("chat_id")
  @Expose
  private String chat_id;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPost_by_firstname() {
        return post_by_firstname;
    }

    public void setPost_by_firstname(String post_by_firstname) {
        this.post_by_firstname = post_by_firstname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPost_by_lastname() {
        return post_by_lastname;
    }

    public void setPost_by_lastname(String post_by_lastname) {
        this.post_by_lastname = post_by_lastname;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }
}