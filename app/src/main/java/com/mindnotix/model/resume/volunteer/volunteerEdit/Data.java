package com.mindnotix.model.resume.volunteer.volunteerEdit;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("qualificationsview")
  @Expose
  private Qualificationsview qualificationsview;
  @SerializedName("category")
  @Expose
  private List<Category> category;
  @SerializedName("status")
  @Expose
  private List<Status> status;
  public void setQualificationsview(Qualificationsview qualificationsview){
   this.qualificationsview=qualificationsview;
  }
  public Qualificationsview getQualificationsview(){
   return qualificationsview;
  }
  public void setCategory(List<Category> category){
   this.category=category;
  }
  public List<Category> getCategory(){
   return category;
  }
  public void setStatus(List<Status> status){
   this.status=status;
  }
  public List<Status> getStatus(){
   return status;
  }
}