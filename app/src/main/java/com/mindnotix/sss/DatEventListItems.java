package com.mindnotix.sss;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class DatEventListItems{
  @SerializedName("data")
  @Expose
  private Data data;
  @SerializedName("status")
  @Expose
  private Integer status;
  @SerializedName("token")
  @Expose
  private String token;
  public void setData(Data data){
   this.data=data;
  }
  public Data getData(){
   return data;
  }
  public void setStatus(Integer status){
   this.status=status;
  }
  public Integer getStatus(){
   return status;
  }
  public void setToken(String token){
   this.token=token;
  }
  public String getToken(){
   return token;
  }
}