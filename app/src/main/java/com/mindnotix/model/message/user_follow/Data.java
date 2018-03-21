package com.mindnotix.model.message.user_follow;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("user_info")
  @Expose
  private List<User_info> user_info;
  public void setUser_info(List<User_info> user_info){
   this.user_info=user_info;
  }
  public List<User_info> getUser_info(){
   return user_info;
  }
}