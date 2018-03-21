package com.mindnotix.model.contactsupport.list;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Supportlist{
  @SerializedName("ticket_status")
  @Expose
  private String ticket_status;
  @SerializedName("replies")
  @Expose
  private String replies;
  @SerializedName("user_id")
  @Expose
  private String user_id;

    public String getTicket_status() {
        return ticket_status;
    }

    public void setTicket_status(String ticket_status) {
        this.ticket_status = ticket_status;
    }

    public String getReplies() {
        return replies;
    }

    public void setReplies(String replies) {
        this.replies = replies;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTicket_status_id() {
        return ticket_status_id;
    }

    public void setTicket_status_id(String ticket_status_id) {
        this.ticket_status_id = ticket_status_id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    @SerializedName("post_date")
  @Expose
  private String post_date;
  @SerializedName("subject")
  @Expose
  private String subject;
  @SerializedName("ticket_status_id")
  @Expose
  private String ticket_status_id;
  @SerializedName("last_name")
  @Expose
  private String last_name;
  @SerializedName("ticket_id")
  @Expose
  private String ticket_id;

  @SerializedName("ticket_code")
  @Expose
  private String ticket_code;

  @SerializedName("first_name")
  @Expose
  private String first_name;

    public String getTicket_code() {
        return ticket_code;
    }

    public void setTicket_code(String ticket_code) {
        this.ticket_code = ticket_code;
    }
}