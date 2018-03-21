package com.mindnotix.model.events.attend_list;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Eventcountmelist{
  @SerializedName("profile_image")
  @Expose
  private String profile_image;
  @SerializedName("event_id")
  @Expose
  private String event_id;
  @SerializedName("user_id")
  @Expose
  private String user_id;
  @SerializedName("count_id")
  @Expose
  private String count_id;
  @SerializedName("last_name")
  @Expose
  private String last_name;

  @SerializedName("group_name")
  @Expose
  private String group_name;

  @SerializedName("group_id")
  @Expose
  private String group_id;

  @SerializedName("first_name")
  @Expose
  private String first_name;

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCount_id() {
        return count_id;
    }

    public void setCount_id(String count_id) {
        this.count_id = count_id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
}