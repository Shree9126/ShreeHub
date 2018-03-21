package com.mindnotix.model.explore.discussionboard.add;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("date")
  @Expose
  private String date;
  @SerializedName("last_name")
  @Expose
  private String last_name;
  @SerializedName("message")
  @Expose
  private String message;
  @SerializedName("chat_id")
  @Expose
  private String chat_id;
  @SerializedName("profile_image")
  @Expose
  private String profile_image;
  @SerializedName("user_id")
  @Expose
  private String user_id;
  @SerializedName("explore_id")
  @Expose
  private String explore_id;
  @SerializedName("image_path")
  @Expose
  private String image_path;
  @SerializedName("success_message")
  @Expose
  private String success_message;
  @SerializedName("topic_id")
  @Expose
  private String topic_id;
  @SerializedName("time")
  @Expose
  private String time;
  @SerializedName("first_name")
  @Expose
  private String first_name;
  @SerializedName("status")
  @Expose
  private String status;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
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

    public String getExplore_id() {
        return explore_id;
    }

    public void setExplore_id(String explore_id) {
        this.explore_id = explore_id;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getSuccess_message() {
        return success_message;
    }

    public void setSuccess_message(String success_message) {
        this.success_message = success_message;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}