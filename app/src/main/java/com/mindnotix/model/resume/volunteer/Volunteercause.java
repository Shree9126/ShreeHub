package com.mindnotix.model.resume.volunteer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Volunteercause{
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("id")
  @Expose
  private String id;
  public void setName(String name){
   this.name=name;
  }
  public String getName(){
   return name;
  }
  public void setId(String id){
   this.id=id;
  }
  public String getId(){
   return id;
  }

    @Override
    public String toString() {
        return name;
    }
}