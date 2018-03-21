package com.mindnotix.model.jobs.jobview;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("jobview")
  @Expose
  private Jobview jobview;
  @SerializedName("photo_detail")
  @Expose
  private List<Photo_detail> photo_detail;
  @SerializedName("video_detail")
  @Expose
  private List<Video_detail> video_detail;
  @SerializedName("selected_category")
  @Expose
  private Selected_category selected_category;
  public void setJobview(Jobview jobview){
   this.jobview=jobview;
  }
  public Jobview getJobview(){
   return jobview;
  }
  public void setPhoto_detail(List<Photo_detail> photo_detail){
   this.photo_detail=photo_detail;
  }
  public List<Photo_detail> getPhoto_detail(){
   return photo_detail;
  }
  public void setVideo_detail(List<Video_detail> video_detail){
   this.video_detail=video_detail;
  }
  public List<Video_detail> getVideo_detail(){
   return video_detail;
  }
  public void setSelected_category(Selected_category selected_category){
   this.selected_category=selected_category;
  }
  public Selected_category getSelected_category(){
   return selected_category;
  }
}