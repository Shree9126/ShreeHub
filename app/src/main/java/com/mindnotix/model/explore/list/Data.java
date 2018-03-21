package com.mindnotix.model.explore.list;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("explore_list")
  @Expose
  private ArrayList<Explore_list> explore_list;
  @SerializedName("path_pdf")
  @Expose
  private String path_pdf;
  @SerializedName("path_image")
  @Expose
  private String path_image;
  public void setExplore_list(ArrayList<Explore_list> explore_list){
   this.explore_list=explore_list;
  }
  public ArrayList<Explore_list> getExplore_list(){
   return explore_list;
  }
  public void setPath_pdf(String path_pdf){
   this.path_pdf=path_pdf;
  }
  public String getPath_pdf(){
   return path_pdf;
  }
  public void setPath_image(String path_image){
   this.path_image=path_image;
  }
  public String getPath_image(){
   return path_image;
  }
}