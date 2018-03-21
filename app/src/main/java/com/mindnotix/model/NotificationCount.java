package com.mindnotix.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class NotificationCount{
  @SerializedName("unreadcount")
  @Expose
  private String unreadcount;
  @SerializedName("status")
  @Expose
  private String status;
  @SerializedName("token")
  @Expose
  private String token;
  public void setUnreadcount(String unreadcount){
   this.unreadcount=unreadcount;
  }
  public String getUnreadcount(){
   return unreadcount;
  }
  public void setStatus(String status){
   this.status=status;
  }
  public String getStatus(){
   return status;
  }
  public void setToken(String token){
   this.token=token;
  }
  public String getToken(){
   return token;
  }
}