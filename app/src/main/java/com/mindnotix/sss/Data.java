package com.mindnotix.sss;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("photo_list")
  @Expose
  private List<Photo_list> photo_list;
  @SerializedName("eventview")
  @Expose
  private List<Eventview> eventview;
  @SerializedName("video_list")
  @Expose
  private List<Video_list> video_list;
  public void setPhoto_list(List<Photo_list> photo_list){
   this.photo_list=photo_list;
  }
  public List<Photo_list> getPhoto_list(){
   return photo_list;
  }
  public void setEventview(List<Eventview> eventview){
   this.eventview=eventview;
  }
  public List<Eventview> getEventview(){
   return eventview;
  }
  public void setVideo_list(List<Video_list> video_list){
   this.video_list=video_list;
  }
  public List<Video_list> getVideo_list(){
   return video_list;
  }
}