package com.mindnotix.model.resume.Education.qualificationSubCatgory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Subcategory_ids{
  @SerializedName("subsubcategory")
  @Expose
  private List<Subsubcategory> subsubcategory;
  @SerializedName("subcategory")
  @Expose
  private String subcategory;
  public void setSubsubcategory(List<Subsubcategory> subsubcategory){
   this.subsubcategory=subsubcategory;
  }
  public List<Subsubcategory> getSubsubcategory(){
   return subsubcategory;
  }
  public void setSubcategory(String subcategory){
   this.subcategory=subcategory;
  }
  public String getSubcategory(){
   return subcategory;
  }
}