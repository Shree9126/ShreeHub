package com.mindnotix.model.explore.list;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Tags_name{
  @SerializedName("tag_name")
  @Expose
  private String tag_name;
  @SerializedName("tag_id")
  @Expose
  private Integer tag_id;
  public void setTag_name(String tag_name){
   this.tag_name=tag_name;
  }
  public String getTag_name(){
   return tag_name;
  }
  public void setTag_id(Integer tag_id){
   this.tag_id=tag_id;
  }
  public Integer getTag_id(){
   return tag_id;
  }
}