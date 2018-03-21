package com.mindnotix.model.resume.Education;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("NCEAlevel")
  @Expose
  private List<NCEAlevel> NCEAlevel;
  @SerializedName("regionals")
  @Expose
  private List<Regionals> regionals;
  @SerializedName("qualificationcategory")
  @Expose
  private List<Qualificationcategory> qualificationcategory;
  @SerializedName("organizationcategory")
  @Expose
  private List<Organizationcategory> organizationcategory;
  @SerializedName("status")
  @Expose
  private List<Status> status;
  public void setNCEAlevel(List<NCEAlevel> NCEAlevel){
   this.NCEAlevel=NCEAlevel;
  }
  public List<NCEAlevel> getNCEAlevel(){
   return NCEAlevel;
  }
  public void setRegionals(List<Regionals> regionals){
   this.regionals=regionals;
  }
  public List<Regionals> getRegionals(){
   return regionals;
  }
  public void setQualificationcategory(List<Qualificationcategory> qualificationcategory){
   this.qualificationcategory=qualificationcategory;
  }
  public List<Qualificationcategory> getQualificationcategory(){
   return qualificationcategory;
  }
  public void setOrganizationcategory(List<Organizationcategory> organizationcategory){
   this.organizationcategory=organizationcategory;
  }
  public List<Organizationcategory> getOrganizationcategory(){
   return organizationcategory;
  }
  public void setStatus(List<Status> status){
   this.status=status;
  }
  public List<Status> getStatus(){
   return status;
  }
}