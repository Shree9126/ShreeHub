package com.mindnotix.model.explore.explorerView;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Exploreview{
  @SerializedName("post_id")
  @Expose
  private String post_id;
  @SerializedName("update_on")
  @Expose
  private String update_on;
  @SerializedName("post_date")
  @Expose
  private String post_date;
  @SerializedName("post_by_firstname")
  @Expose
  private String post_by_firstname;
  @SerializedName("profile_id")
  @Expose
  private String profile_id;
  @SerializedName("rating")
  @Expose
  private String rating;
  @SerializedName("description")
  @Expose
  private String description;
  @SerializedName("page_view_count")
  @Expose
  private String page_view_count;
  @SerializedName("rating_name")
  @Expose
  private String rating_name;
  @SerializedName("title")
  @Expose
  private String title;
  @SerializedName("post_by_lastname")
  @Expose
  private String post_by_lastname;
  public void setPost_id(String post_id){
   this.post_id=post_id;
  }
  public String getPost_id(){
   return post_id;
  }
  public void setUpdate_on(String update_on){
   this.update_on=update_on;
  }
  public String getUpdate_on(){
   return update_on;
  }
  public void setPost_date(String post_date){
   this.post_date=post_date;
  }
  public String getPost_date(){
   return post_date;
  }
  public void setPost_by_firstname(String post_by_firstname){
   this.post_by_firstname=post_by_firstname;
  }
  public String getPost_by_firstname(){
   return post_by_firstname;
  }
  public void setProfile_id(String profile_id){
   this.profile_id=profile_id;
  }
  public String getProfile_id(){
   return profile_id;
  }
  public void setRating(String rating){
   this.rating=rating;
  }
  public String getRating(){
   return rating;
  }
  public void setDescription(String description){
   this.description=description;
  }
  public String getDescription(){
   return description;
  }
  public void setPage_view_count(String page_view_count){
   this.page_view_count=page_view_count;
  }
  public String getPage_view_count(){
   return page_view_count;
  }
  public void setRating_name(String rating_name){
   this.rating_name=rating_name;
  }
  public String getRating_name(){
   return rating_name;
  }
  public void setTitle(String title){
   this.title=title;
  }
  public String getTitle(){
   return title;
  }
  public void setPost_by_lastname(String post_by_lastname){
   this.post_by_lastname=post_by_lastname;
  }
  public String getPost_by_lastname(){
   return post_by_lastname;
  }
}