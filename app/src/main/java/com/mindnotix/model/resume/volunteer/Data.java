package com.mindnotix.model.resume.volunteer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("volunteercause")
  @Expose
  private ArrayList<Volunteercause> volunteercause;
  @SerializedName("status")
  @Expose
  private ArrayList<Status> status;
  public void setVolunteercause(ArrayList<Volunteercause> volunteercause){
   this.volunteercause=volunteercause;
  }
  public ArrayList<Volunteercause> getVolunteercause(){
   return volunteercause;
  }
  public void setStatus(ArrayList<Status> status){
   this.status=status;
  }
  public ArrayList<Status> getStatus(){
   return status;
  }
}