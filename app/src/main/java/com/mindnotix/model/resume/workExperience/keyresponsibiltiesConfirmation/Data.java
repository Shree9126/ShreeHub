package com.mindnotix.model.resume.workExperience.keyresponsibiltiesConfirmation;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("title")
  @Expose
  private String title;
  @SerializedName("content")
  @Expose
  private String content;
  public void setTitle(String title){
   this.title=title;
  }
  public String getTitle(){
   return title;
  }
  public void setContent(String content){
   this.content=content;
  }
  public String getContent(){
   return content;
  }
}