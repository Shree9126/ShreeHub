package com.mindnotix.model.resume.extraActivites;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class ExtraActivities{
  @SerializedName("message")
  @Expose
  private String message;
  @SerializedName("status")
  @Expose
  private String status;
  @SerializedName("token")
  @Expose
  private String token;
  public void setMessage(String message){
   this.message=message;
  }
  public String getMessage(){
   return message;
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