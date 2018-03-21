package com.mindnotix.model.resume.workExperience.workExperienceEdit;
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
  @SerializedName("keyresponsibilities")
  @Expose
  private List<Keyresponsibilities> keyresponsibilities;
  @SerializedName("category")
  @Expose
  private List<Category> category;
  @SerializedName("subcategory")
  @Expose
  private List<Subcategory> subcategory;
  @SerializedName("jobtype")
  @Expose
  private List<Jobtype> jobtype;
  @SerializedName("status")
  @Expose
  private List<Status> status;
  public void setQualificationsview(Qualificationsview qualificationsview){
   this.qualificationsview=qualificationsview;
  }
  public Qualificationsview getQualificationsview(){
   return qualificationsview;
  }
  public void setKeyresponsibilities(List<Keyresponsibilities> keyresponsibilities){
   this.keyresponsibilities=keyresponsibilities;
  }
  public List<Keyresponsibilities> getKeyresponsibilities(){
   return keyresponsibilities;
  }
  public void setCategory(List<Category> category){
   this.category=category;
  }
  public List<Category> getCategory(){
   return category;
  }
  public void setSubcategory(List<Subcategory> subcategory){
   this.subcategory=subcategory;
  }
  public List<Subcategory> getSubcategory(){
   return subcategory;
  }
  public void setJobtype(List<Jobtype> jobtype){
   this.jobtype=jobtype;
  }
  public List<Jobtype> getJobtype(){
   return jobtype;
  }
  public void setStatus(List<Status> status){
   this.status=status;
  }
  public List<Status> getStatus(){
   return status;
  }
}