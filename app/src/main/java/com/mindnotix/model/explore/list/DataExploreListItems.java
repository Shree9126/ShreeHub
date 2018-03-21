package com.mindnotix.model.explore.list;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class DataExploreListItems{
  @SerializedName("nextpage")
  @Expose
  private String nextpage;
  @SerializedName("data")
  @Expose
  private Data data;
  @SerializedName("status")
  @Expose
  private String status;
  @SerializedName("token")
  @Expose
  private String token;
  public void setNextpage(String nextpage){
   this.nextpage=nextpage;
  }
  public String getNextpage(){
   return nextpage;
  }
  public void setData(Data data){
   this.data=data;
  }
  public Data getData(){
   return data;
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