package com.mindnotix.model.jobs.list;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("company_name")
  @Expose
  private String company_name;

  @SerializedName("title")
  @Expose
  private String title;

  @SerializedName("id")
  @Expose
  private String id;

  @SerializedName("end_date")
  @Expose
  private String end_date;

  @SerializedName("start_date")
  @Expose
  private String start_date;

  @SerializedName("description")
  @Expose
  private String description;

  @SerializedName("localboardname")
  @Expose
  private String localboardname;


  @SerializedName("regionalname")
  @Expose
  private String regionalname;

  @SerializedName("jobtypename")
  @Expose
  private String jobtypename;

  @SerializedName("post_date")
  @Expose
  private String post_date;

  @SerializedName("latitude")
  @Expose
  private String latitude;
  @SerializedName("longitude")
  @Expose
  private String longitude;

  @SerializedName("isfavourtejob")
  @Expose
  private String isfavourtejob;

  public String getIsfavourtejob() {
    return isfavourtejob;
  }

  public void setIsfavourtejob(String isfavourtejob) {
    this.isfavourtejob = isfavourtejob;
  }

  public String getRegionalname() {
    return regionalname;
  }

  public void setRegionalname(String regionalname) {
    this.regionalname = regionalname;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getCompany_name() {
    return company_name;
  }

  public void setCompany_name(String company_name) {
    this.company_name = company_name;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEnd_date() {
    return end_date;
  }

  public void setEnd_date(String end_date) {
    this.end_date = end_date;
  }

  public String getStart_date() {
    return start_date;
  }

  public void setStart_date(String start_date) {
    this.start_date = start_date;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLocalboardname() {
    return localboardname;
  }

  public void setLocalboardname(String localboardname) {
    this.localboardname = localboardname;
  }

  public String getJobtypename() {
    return jobtypename;
  }

  public void setJobtypename(String jobtypename) {
    this.jobtypename = jobtypename;
  }

  public String getPost_date() {
    return post_date;
  }

  public void setPost_date(String post_date) {
    this.post_date = post_date;
  }
}