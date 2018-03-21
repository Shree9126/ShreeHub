package com.mindnotix.model.jobs.filter_master;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("jobtypeview")
  @Expose
  private List<Jobtypeview> jobtypeview;
  @SerializedName("salarytype_anumax")
  @Expose
  private List<Salarytype_anumax> salarytype_anumax;
  @SerializedName("salarytype_anumin")
  @Expose
  private List<Salarytype_anumin> salarytype_anumin;
  @SerializedName("categoryview")
  @Expose
  private List<Categoryview> categoryview;
  @SerializedName("salarytype_hrmin")
  @Expose
  private List<Salarytype_hrmin> salarytype_hrmin;
  @SerializedName("salarytype_hrmax")
  @Expose
  private List<Salarytype_hrmax> salarytype_hrmax;
  @SerializedName("sortby")
  @Expose
  private List<Sortby> sortby;
  @SerializedName("salarytype")
  @Expose
  private List<Salarytype> salarytype;
  @SerializedName("regional_council_list")
  @Expose
  private List<Regional_council_list> regional_council_list;
  public void setJobtypeview(List<Jobtypeview> jobtypeview){
   this.jobtypeview=jobtypeview;
  }
  public List<Jobtypeview> getJobtypeview(){
   return jobtypeview;
  }
  public void setSalarytype_anumax(List<Salarytype_anumax> salarytype_anumax){
   this.salarytype_anumax=salarytype_anumax;
  }
  public List<Salarytype_anumax> getSalarytype_anumax(){
   return salarytype_anumax;
  }
  public void setSalarytype_anumin(List<Salarytype_anumin> salarytype_anumin){
   this.salarytype_anumin=salarytype_anumin;
  }
  public List<Salarytype_anumin> getSalarytype_anumin(){
   return salarytype_anumin;
  }
  public void setCategoryview(List<Categoryview> categoryview){
   this.categoryview=categoryview;
  }
  public List<Categoryview> getCategoryview(){
   return categoryview;
  }
  public void setSalarytype_hrmin(List<Salarytype_hrmin> salarytype_hrmin){
   this.salarytype_hrmin=salarytype_hrmin;
  }
  public List<Salarytype_hrmin> getSalarytype_hrmin(){
   return salarytype_hrmin;
  }
  public void setSalarytype_hrmax(List<Salarytype_hrmax> salarytype_hrmax){
   this.salarytype_hrmax=salarytype_hrmax;
  }
  public List<Salarytype_hrmax> getSalarytype_hrmax(){
   return salarytype_hrmax;
  }
  public void setSortby(List<Sortby> sortby){
   this.sortby=sortby;
  }
  public List<Sortby> getSortby(){
   return sortby;
  }
  public void setSalarytype(List<Salarytype> salarytype){
   this.salarytype=salarytype;
  }
  public List<Salarytype> getSalarytype(){
   return salarytype;
  }
  public void setRegional_council_list(List<Regional_council_list> regional_council_list){
   this.regional_council_list=regional_council_list;
  }
  public List<Regional_council_list> getRegional_council_list(){
   return regional_council_list;
  }
}