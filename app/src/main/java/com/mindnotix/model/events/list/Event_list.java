package com.mindnotix.model.events.list;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Event_list{
  @SerializedName("end_date")
  @Expose
  private String end_date;
  @SerializedName("local_board_name")
  @Expose
  private String local_board_name;
  @SerializedName("post_by_firstname")
  @Expose
  private String post_by_firstname;
  @SerializedName("end_time")
  @Expose
  private String end_time;
  @SerializedName("title")
  @Expose
  private String title;
  @SerializedName("reginal_id")
  @Expose
  private String reginal_id;
  @SerializedName("latitude")
  @Expose
  private String latitude;

  @SerializedName("longitude")
  @Expose
  private String longitude;

  @SerializedName("reginal_name")
  @Expose
  private String reginal_name;
  @SerializedName("explore_logo")
  @Expose
  private String explore_logo;
  @SerializedName("start_time")
  @Expose
  private String start_time;
  @SerializedName("organisers")
  @Expose
  private String organisers;
  @SerializedName("event_id")
  @Expose
  private String event_id;
  @SerializedName("post_date")
  @Expose
  private String post_date;
  @SerializedName("local_board_id")
  @Expose
  private String local_board_id;
  @SerializedName("profile_id")
  @Expose
  private String profile_id;
  @SerializedName("total_person_attending_event")
  @Expose
  private String total_person_attending_event;
  @SerializedName("post_by_lastname")
  @Expose
  private String post_by_lastname;
  @SerializedName("start_date")
  @Expose
  private String start_date;
  @SerializedName("event_status")
  @Expose
  private String event_status;

    public Event_list(String title) {

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

    public void setEnd_date(String end_date){
   this.end_date=end_date;
  }
  public String getEnd_date(){
   return end_date;
  }
  public void setLocal_board_name(String local_board_name){
   this.local_board_name=local_board_name;
  }
  public String getLocal_board_name(){
   return local_board_name;
  }
  public void setPost_by_firstname(String post_by_firstname){
   this.post_by_firstname=post_by_firstname;
  }
  public String getPost_by_firstname(){
   return post_by_firstname;
  }
  public void setEnd_time(String end_time){
   this.end_time=end_time;
  }
  public String getEnd_time(){
   return end_time;
  }
  public void setTitle(String title){
   this.title=title;
  }
  public String getTitle(){
   return title;
  }
  public void setReginal_id(String reginal_id){
   this.reginal_id=reginal_id;
  }
  public String getReginal_id(){
   return reginal_id;
  }
  public void setReginal_name(String reginal_name){
   this.reginal_name=reginal_name;
  }
  public String getReginal_name(){
   return reginal_name;
  }
  public void setExplore_logo(String explore_logo){
   this.explore_logo=explore_logo;
  }
  public String getExplore_logo(){
   return explore_logo;
  }
  public void setStart_time(String start_time){
   this.start_time=start_time;
  }
  public String getStart_time(){
   return start_time;
  }
  public void setOrganisers(String organisers){
   this.organisers=organisers;
  }
  public String getOrganisers(){
   return organisers;
  }
  public void setEvent_id(String event_id){
   this.event_id=event_id;
  }
  public String getEvent_id(){
   return event_id;
  }
  public void setPost_date(String post_date){
   this.post_date=post_date;
  }
  public String getPost_date(){
   return post_date;
  }
  public void setLocal_board_id(String local_board_id){
   this.local_board_id=local_board_id;
  }
  public String getLocal_board_id(){
   return local_board_id;
  }
  public void setProfile_id(String profile_id){
   this.profile_id=profile_id;
  }
  public String getProfile_id(){
   return profile_id;
  }
  public void setTotal_person_attending_event(String total_person_attending_event){
   this.total_person_attending_event=total_person_attending_event;
  }
  public String getTotal_person_attending_event(){
   return total_person_attending_event;
  }
  public void setPost_by_lastname(String post_by_lastname){
   this.post_by_lastname=post_by_lastname;
  }
  public String getPost_by_lastname(){
   return post_by_lastname;
  }
  public void setStart_date(String start_date){
   this.start_date=start_date;
  }
  public String getStart_date(){
   return start_date;
  }
  public void setEvent_status(String event_status){
   this.event_status=event_status;
  }
  public String getEvent_status(){
   return event_status;
  }
}