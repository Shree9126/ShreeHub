package com.mindnotix.model.find_connection.master.sub_service_type;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("subcategoryview")
  @Expose
  private List<Subcategoryview> subcategoryview;
  public void setSubcategoryview(List<Subcategoryview> subcategoryview){
   this.subcategoryview=subcategoryview;
  }
  public List<Subcategoryview> getSubcategoryview(){
   return subcategoryview;
  }
}