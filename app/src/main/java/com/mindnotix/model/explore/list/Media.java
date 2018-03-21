package com.mindnotix.model.explore.list;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Media{
  @SerializedName("content_type")
  @Expose
  private String content_type;
  @SerializedName("content_id")
  @Expose
  private String content_id;
  @SerializedName("content")
  @Expose
  private String content;
  public void setContent_type(String content_type){
   this.content_type=content_type;
  }
  public String getContent_type(){
   return content_type;
  }
  public void setContent_id(String content_id){
   this.content_id=content_id;
  }
  public String getContent_id(){
   return content_id;
  }
  public void setContent(String content){
   this.content=content;
  }
  public String getContent(){
   return content;
  }
}