package com.mindnotix.model.resume.Education.qualificationSubCatgory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Subsubcategory{
  @SerializedName("id")
  @Expose
  private String id;
  @SerializedName("subcategory")
  @Expose
  private String subcategory;
  public void setId(String id){
   this.id=id;
  }
  public String getId(){
   return id;
  }
  public void setSubcategory(String subcategory){
   this.subcategory=subcategory;
  }
  public String getSubcategory(){
   return subcategory;
  }
}