package com.mindnotix.model.message.chat_messages_list;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Users_info{
  @SerializedName("is_attached")
  @Expose
  private String is_attached;

  @SerializedName("attachment")
  @Expose
  private String attachment;
  @SerializedName("user_id")
  @Expose
  private String user_id;
  @SerializedName("group_id")
  @Expose
  private String group_id;
  @SerializedName("post_date")
  @Expose
  private String post_date;
  @SerializedName("last_name")
  @Expose
  private String last_name;
  @SerializedName("fisrt_name")
  @Expose
  private String fisrt_name;
  @SerializedName("message_id")
  @Expose
  private String message_id;
  @SerializedName("message")
  @Expose
  private String message;
  @SerializedName("post_time")
  @Expose
  private String post_time;
  @SerializedName("chat_id")
  @Expose
  private String chat_id;
  @SerializedName("status")
  @Expose
  private String status;


    public String getIs_attached() {
        return is_attached;
    }

    public void setIs_attached(String is_attached) {
        this.is_attached = is_attached;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFisrt_name() {
        return fisrt_name;
    }

    public void setFisrt_name(String fisrt_name) {
        this.fisrt_name = fisrt_name;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
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