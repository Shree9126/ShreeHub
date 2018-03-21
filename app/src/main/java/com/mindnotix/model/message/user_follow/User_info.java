package com.mindnotix.model.message.user_follow;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class User_info{
  @SerializedName("user_id")
  @Expose
  private String user_id;
  @SerializedName("last_name")
  @Expose
  private String last_name;
  @SerializedName("first_name")
  @Expose
  private String first_name;
  @SerializedName("chat_id")
  @Expose
  private String chat_id;
  @SerializedName("status")
  @Expose
  private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}