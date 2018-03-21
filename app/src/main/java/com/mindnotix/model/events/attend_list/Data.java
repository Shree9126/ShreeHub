package com.mindnotix.model.events.attend_list;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mindnotix.youthhub.EventDetailsActivity;

import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("image_path")
  @Expose
  private String image_path;
  @SerializedName("eventcountmelist")
  private List<Eventcountmelist> eventcountmelist;
  public void setImage_path(String image_path){
   this.image_path=image_path;
  }
  public String getImage_path(){
   return image_path;
  }
  public void setEventcountmelist(List<Eventcountmelist> eventcountmelist){
   this.eventcountmelist=eventcountmelist;
  }
  public List<Eventcountmelist> getEventcountmelist(){
   return eventcountmelist;
  }
}