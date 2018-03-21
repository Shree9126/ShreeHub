package com.mindnotix.model.resume.loadResume;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class KeyresponsibilitiesItem {
  @SerializedName("user_id")
  @Expose
  private String user_id;
  @SerializedName("created_on")
  @Expose
  private String created_on;
  @SerializedName("id")
  @Expose
  private String id;
  @SerializedName("title")
  @Expose
  private String title;
  @SerializedName("stu_qualification_id")
  @Expose
  private String stu_qualification_id;
  @SerializedName("created_by")
  @Expose
  private String created_by;
  @SerializedName("status")
  @Expose
  private String status;
  public void setUser_id(String user_id){
   this.user_id=user_id;
  }
  public String getUser_id(){
   return user_id;
  }
  public void setCreated_on(String created_on){
   this.created_on=created_on;
  }
  public String getCreated_on(){
   return created_on;
  }
  public void setId(String id){
   this.id=id;
  }
  public String getId(){
   return id;
  }
  public void setTitle(String title){
   this.title=title;
  }
  public String getTitle(){
   return title;
  }
  public void setStu_qualification_id(String stu_qualification_id){
   this.stu_qualification_id=stu_qualification_id;
  }
  public String getStu_qualification_id(){
   return stu_qualification_id;
  }
  public void setCreated_by(String created_by){
   this.created_by=created_by;
  }
  public String getCreated_by(){
   return created_by;
  }
  public void setStatus(String status){
   this.status=status;
  }
  public String getStatus(){
   return status;
  }
}