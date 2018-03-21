package com.mindnotix.model.resume.Education.qualificationSubCatgory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("subcategory_ids")
  @Expose
  private List<Subcategory_ids> subcategory_ids;
  public void setSubcategory_ids(List<Subcategory_ids> subcategory_ids){
   this.subcategory_ids=subcategory_ids;
  }
  public List<Subcategory_ids> getSubcategory_ids(){
   return subcategory_ids;
  }
}