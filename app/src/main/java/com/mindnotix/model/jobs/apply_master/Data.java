package com.mindnotix.model.jobs.apply_master;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("resumeattached_msg")
  @Expose
  private String resumeattached_msg;
  @SerializedName("job_questions")
  @Expose
  private List<Job_questions> job_questions;
  @SerializedName("job_questions_count")
  @Expose
  private String job_questions_count;
  @SerializedName("resumeattached_path")
  @Expose
  private String resumeattached_path;
  @SerializedName("jobview")
  @Expose
  private Jobview jobview;
  @SerializedName("resumeattached")
  @Expose
  private List<Resumeattached> resumeattached;
  @SerializedName("resumeattached_count")
  @Expose
  private String resumeattached_count;
  public void setResumeattached_msg(String resumeattached_msg){
   this.resumeattached_msg=resumeattached_msg;
  }
  public String getResumeattached_msg(){
   return resumeattached_msg;
  }
  public void setJob_questions(List<Job_questions> job_questions){
   this.job_questions=job_questions;
  }
  public List<Job_questions> getJob_questions(){
   return job_questions;
  }
  public void setJob_questions_count(String job_questions_count){
   this.job_questions_count=job_questions_count;
  }
  public String getJob_questions_count(){
   return job_questions_count;
  }
  public void setResumeattached_path(String resumeattached_path){
   this.resumeattached_path=resumeattached_path;
  }
  public String getResumeattached_path(){
   return resumeattached_path;
  }
  public void setJobview(Jobview jobview){
   this.jobview=jobview;
  }
  public Jobview getJobview(){
   return jobview;
  }
  public void setResumeattached(List<Resumeattached> resumeattached){
   this.resumeattached=resumeattached;
  }
  public List<Resumeattached> getResumeattached(){
   return resumeattached;
  }
  public void setResumeattached_count(String resumeattached_count){
   this.resumeattached_count=resumeattached_count;
  }
  public String getResumeattached_count(){
   return resumeattached_count;
  }
}