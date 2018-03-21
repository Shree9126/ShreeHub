package com.mindnotix.model.resume.workExperience;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("job_types")
  @Expose
  private List<Job_types> job_types;
  @SerializedName("partner_types")
  @Expose
  private List<Partner_types> partner_types;
  @SerializedName("status")
  @Expose
  private List<Status> status;
  public void setJob_types(List<Job_types> job_types){
   this.job_types=job_types;
  }
  public List<Job_types> getJob_types(){
   return job_types;
  }
  public void setPartner_types(List<Partner_types> partner_types){
   this.partner_types=partner_types;
  }
  public List<Partner_types> getPartner_types(){
   return partner_types;
  }
  public void setStatus(List<Status> status){
   this.status=status;
  }
  public List<Status> getStatus(){
   return status;
  }
}