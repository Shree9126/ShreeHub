package com.mindnotix.model.resume.Education;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Subsubcategory{
  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("subcategory")
  @Expose
  private String subcategory;
  public void setId(Integer id){
   this.id=id;
  }
  public Integer getId(){
   return id;
  }
  public void setSubcategory(String subcategory){
   this.subcategory=subcategory;
  }
  public String getSubcategory(){
   return subcategory;
  }
}