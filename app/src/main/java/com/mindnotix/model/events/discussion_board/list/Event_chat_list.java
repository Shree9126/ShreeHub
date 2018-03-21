package com.mindnotix.model.events.discussion_board.list;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Event_chat_list{
  @SerializedName("date")
  @Expose
  private String date;
  @SerializedName("profile_image")
  @Expose
  private String profile_image;
  @SerializedName("user_id")
  @Expose
  private String user_id;
  @SerializedName("last_name")
  @Expose
  private String last_name;
  @SerializedName("time")
  @Expose
  private String time;
  @SerializedName("message")
  @Expose
  private String message;
  @SerializedName("first_name")
  @Expose
  private String first_name;
  @SerializedName("chat_id")
  @Expose
  private String chat_id;

  @SerializedName("image_path")
  @Expose
  private String image_path;


    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

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

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }
}