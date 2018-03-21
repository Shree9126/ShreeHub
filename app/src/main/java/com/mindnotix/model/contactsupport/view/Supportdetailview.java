package com.mindnotix.model.contactsupport.view;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Supportdetailview{
  @SerializedName("profile_image")
  @Expose
  private String profile_image;
  @SerializedName("image_download")
  @Expose
  private String image_download;
  @SerializedName("user_id")
  @Expose
  private String user_id;
  @SerializedName("message_date")
  @Expose
  private String message_date;
  @SerializedName("image_path")
  @Expose
  private String image_path;
  @SerializedName("last_name")
  @Expose
  private String last_name;
  @SerializedName("detail_id")
  @Expose
  private String detail_id;
  @SerializedName("message")
  @Expose
  private String message;
  @SerializedName("download_file")
  @Expose
  private String download_file;
  @SerializedName("first_name")
  @Expose
  private String first_name;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;



    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getImage_download() {
        return image_download;
    }

    public void setImage_download(String image_download) {
        this.image_download = image_download;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMessage_date() {
        return message_date;
    }

    public void setMessage_date(String message_date) {
        this.message_date = message_date;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(String detail_id) {
        this.detail_id = detail_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDownload_file() {
        return download_file;
    }

    public void setDownload_file(String download_file) {
        this.download_file = download_file;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
}