package com.mindnotix.model.jobs.apply_master;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Jobview{
  @SerializedName("isyouth")
  @Expose
  private Integer isyouth;
  @SerializedName("code")
  @Expose
  private Long code;
  @SerializedName("isapplied")
  @Expose
  private Integer isapplied;
  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("title")
  @Expose
  private String title;
  public void setIsyouth(Integer isyouth){
   this.isyouth=isyouth;
  }
  public Integer getIsyouth(){
   return isyouth;
  }
  public void setCode(Long code){
   this.code=code;
  }
  public Long getCode(){
   return code;
  }
  public void setIsapplied(Integer isapplied){
   this.isapplied=isapplied;
  }
  public Integer getIsapplied(){
   return isapplied;
  }
  public void setId(Integer id){
   this.id=id;
  }
  public Integer getId(){
   return id;
  }
  public void setTitle(String title){
   this.title=title;
  }
  public String getTitle(){
   return title;
  }
}