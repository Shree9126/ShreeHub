package com.mindnotix.model.jobs.apply_master;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Job_questions{
  @SerializedName("question_details")
  @Expose
  private String question_details;
  @SerializedName("job_id")
  @Expose
  private String job_id;
  @SerializedName("id")
  @Expose
  private String id;

  @SerializedName("edt_value")
  @Expose
  private String edt_value="";

    public String getEdt_value() {
        return edt_value;
    }

    public void setEdt_value(String edt_value) {
        this.edt_value = edt_value;
    }

    public void setQuestion_details(String question_details){
   this.question_details=question_details;
  }
  public String getQuestion_details(){
   return question_details;
  }
  public void setJob_id(String job_id){
   this.job_id=job_id;
  }
  public String getJob_id(){
   return job_id;
  }
  public void setId(String id){
   this.id=id;
  }
  public String getId(){
   return id;
  }
}