package com.mindnotix.model.explore.explorerView;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("path_pdf")
  @Expose
  private String path_pdf;
  @SerializedName("exploreview")
  @Expose
  private List<Exploreview> exploreview;
  @SerializedName("content_list")
  @Expose
  private List<Content_list> content_list;
  @SerializedName("path_image")
  @Expose
  private String path_image;
  @SerializedName("topic_list")
  @Expose
  private List<Topic_list> topic_list;
  public void setPath_pdf(String path_pdf){
   this.path_pdf=path_pdf;
  }
  public String getPath_pdf(){
   return path_pdf;
  }
  public void setExploreview(List<Exploreview> exploreview){
   this.exploreview=exploreview;
  }
  public List<Exploreview> getExploreview(){
   return exploreview;
  }
  public void setContent_list(List<Content_list> content_list){
   this.content_list=content_list;
  }
  public List<Content_list> getContent_list(){
   return content_list;
  }
  public void setPath_image(String path_image){
   this.path_image=path_image;
  }
  public String getPath_image(){
   return path_image;
  }
  public void setTopic_list(List<Topic_list> topic_list){
   this.topic_list=topic_list;
  }
  public List<Topic_list> getTopic_list(){
   return topic_list;
  }
}