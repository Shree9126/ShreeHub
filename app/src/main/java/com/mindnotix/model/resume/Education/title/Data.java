package com.mindnotix.model.resume.Education.title;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("qualification")
  @Expose
  private List<Qualification> qualification;
  public void setQualification(List<Qualification> qualification){
   this.qualification=qualification;
  }
  public List<Qualification> getQualification(){
   return qualification;
  }
}