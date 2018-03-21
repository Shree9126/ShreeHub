package com.mindnotix.model.explore.rating;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("date")
  @Expose
  private String date;
  @SerializedName("post_id")
  @Expose
  private String post_id;
  @SerializedName("rating_id")
  @Expose
  private String rating_id;

  @SerializedName("rating_name")
  @Expose
  private String rating_name;
  @SerializedName("user_id")
  @Expose
  private String user_id;
  @SerializedName("rating_value")
  @Expose
  private String rating_value;
  @SerializedName("time")
  @Expose
  private String time;

    public String getDate() {
        return date;
    }


    public String getRating_name() {
        return rating_name;
    }

    public void setRating_name(String rating_name) {
        this.rating_name = rating_name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getRating_id() {
        return rating_id;
    }

    public void setRating_id(String rating_id) {
        this.rating_id = rating_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRating_value() {
        return rating_value;
    }

    public void setRating_value(String rating_value) {
        this.rating_value = rating_value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}