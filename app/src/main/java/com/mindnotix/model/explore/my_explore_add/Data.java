package com.mindnotix.model.explore.my_explore_add;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("addmyexplore")
  @Expose
  private List<Addmyexplore> addmyexplore;
  public void setAddmyexplore(List<Addmyexplore> addmyexplore){
   this.addmyexplore=addmyexplore;
  }
  public List<Addmyexplore> getAddmyexplore(){
   return addmyexplore;
  }
}