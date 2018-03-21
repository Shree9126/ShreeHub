package com.mindnotix.model.explore.discussionboard.list;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("explore_chat_list")
  @Expose
  private List<Explore_chat_list> explore_chat_list;
  @SerializedName("path_image")
  @Expose
  private String path_image;
  public void setExplore_chat_list(List<Explore_chat_list> explore_chat_list){
   this.explore_chat_list=explore_chat_list;
  }
  public List<Explore_chat_list> getExplore_chat_list(){
   return explore_chat_list;
  }
  public void setPath_image(String path_image){
   this.path_image=path_image;
  }
  public String getPath_image(){
   return path_image;
  }
}