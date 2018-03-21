package com.mindnotix.model.loadResume;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Technicalskills{
  @SerializedName("user_id")
  @Expose
  private String user_id;
  @SerializedName("level")
  @Expose
  private String level;
  @SerializedName("created_on")
  @Expose
  private String created_on;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("id")
  @Expose
  private String id;
  public void setUser_id(String user_id){
   this.user_id=user_id;
  }
  public String getUser_id(){
   return user_id;
  }
  public void setLevel(String level){
   this.level=level;
  }
  public String getLevel(){
   return level;
  }
  public void setCreated_on(String created_on){
   this.created_on=created_on;
  }
  public String getCreated_on(){
   return created_on;
  }
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
}