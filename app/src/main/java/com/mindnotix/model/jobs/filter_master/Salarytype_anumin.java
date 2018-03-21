package com.mindnotix.model.jobs.filter_master;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Salarytype_anumin{
  @SerializedName("job_salary_value")
  @Expose
  private String job_salary_value;
  @SerializedName("job_salary_amount")
  @Expose
  private String job_salary_amount;
  public void setJob_salary_value(String job_salary_value){
   this.job_salary_value=job_salary_value;
  }
  public String getJob_salary_value(){
   return job_salary_value;
  }
  public void setJob_salary_amount(String job_salary_amount){
   this.job_salary_amount=job_salary_amount;
  }
  public String getJob_salary_amount(){
   return job_salary_amount;
  }


    /**
     * Pay attention here, you have to override the toString method as the
     * ArrayAdapter will reads the toString of the given object for the name
     *
     * @return ptg_name
     */
    @Override
    public String toString() {
        return job_salary_amount;
    }
}