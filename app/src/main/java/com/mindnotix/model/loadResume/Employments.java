package com.mindnotix.model.loadResume;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Employments{
  @SerializedName("end_date")
  @Expose
  private String end_date;
  @SerializedName("category_name")
  @Expose
  private String category_name;
  @SerializedName("updater_lastname")
  @Expose
  private String updater_lastname;
  @SerializedName("keyresponsibilities")
  @Expose
  private List<KeyresponsibilitiesItem> keyresponsibilities;
  @SerializedName("type_id")
  @Expose
  private String type_id;
  @SerializedName("student_id")
  @Expose
  private String student_id;
  @SerializedName("description")
  @Expose
  private String description;
  @SerializedName("subcategory_name")
  @Expose
  private String subcategory_name;
  @SerializedName("title")
  @Expose
  private String title;
  @SerializedName("type")
  @Expose
  private String type;
  @SerializedName("subsubcategory_name")
  @Expose
  private String subsubcategory_name;
  @SerializedName("subcategory_id")
  @Expose
  private String subcategory_id;
  @SerializedName("category_id")
  @Expose
  private String category_id;
  @SerializedName("jobtype_name")
  @Expose
  private String jobtype_name;
  @SerializedName("is_through_us")
  @Expose
  private String is_through_us;
  @SerializedName("creator_id")
  @Expose
  private String creator_id;
  @SerializedName("ncl_id")
  @Expose
  private String ncl_id;
  @SerializedName("category_name_pic")
  @Expose
  private String category_name_pic;
  @SerializedName("id")
  @Expose
  private String id;
  @SerializedName("provider_name")
  @Expose
  private String provider_name;
  @SerializedName("updater_firstname")
  @Expose
  private String updater_firstname;
  @SerializedName("start_date")
  @Expose
  private String start_date;
  @SerializedName("qualification_status")
  @Expose
  private String qualification_status;
  public void setEnd_date(String end_date){
   this.end_date=end_date;
  }
  public String getEnd_date(){
   return end_date;
  }
  public void setCategory_name(String category_name){
   this.category_name=category_name;
  }
  public String getCategory_name(){
   return category_name;
  }
  public void setUpdater_lastname(String updater_lastname){
   this.updater_lastname=updater_lastname;
  }
  public String getUpdater_lastname(){
   return updater_lastname;
  }
  public void setKeyresponsibilities(List<KeyresponsibilitiesItem> keyresponsibilities){
   this.keyresponsibilities=keyresponsibilities;
  }
  public List<KeyresponsibilitiesItem> getKeyresponsibilities(){
   return keyresponsibilities;
  }
  public void setType_id(String type_id){
   this.type_id=type_id;
  }
  public String getType_id(){
   return type_id;
  }
  public void setStudent_id(String student_id){
   this.student_id=student_id;
  }
  public String getStudent_id(){
   return student_id;
  }
  public void setDescription(String description){
   this.description=description;
  }
  public String getDescription(){
   return description;
  }
  public void setSubcategory_name(String subcategory_name){
   this.subcategory_name=subcategory_name;
  }
  public String getSubcategory_name(){
   return subcategory_name;
  }
  public void setTitle(String title){
   this.title=title;
  }
  public String getTitle(){
   return title;
  }
  public void setType(String type){
   this.type=type;
  }
  public String getType(){
   return type;
  }
  public void setSubsubcategory_name(String subsubcategory_name){
   this.subsubcategory_name=subsubcategory_name;
  }
  public String getSubsubcategory_name(){
   return subsubcategory_name;
  }
  public void setSubcategory_id(String subcategory_id){
   this.subcategory_id=subcategory_id;
  }
  public String getSubcategory_id(){
   return subcategory_id;
  }
  public void setCategory_id(String category_id){
   this.category_id=category_id;
  }
  public String getCategory_id(){
   return category_id;
  }
  public void setJobtype_name(String jobtype_name){
   this.jobtype_name=jobtype_name;
  }
  public String getJobtype_name(){
   return jobtype_name;
  }
  public void setIs_through_us(String is_through_us){
   this.is_through_us=is_through_us;
  }
  public String getIs_through_us(){
   return is_through_us;
  }
  public void setCreator_id(String creator_id){
   this.creator_id=creator_id;
  }
  public String getCreator_id(){
   return creator_id;
  }
  public void setNcl_id(String ncl_id){
   this.ncl_id=ncl_id;
  }
  public String getNcl_id(){
   return ncl_id;
  }
  public void setCategory_name_pic(String category_name_pic){
   this.category_name_pic=category_name_pic;
  }
  public String getCategory_name_pic(){
   return category_name_pic;
  }
  public void setId(String id){
   this.id=id;
  }
  public String getId(){
   return id;
  }
  public void setProvider_name(String provider_name){
   this.provider_name=provider_name;
  }
  public String getProvider_name(){
   return provider_name;
  }
  public void setUpdater_firstname(String updater_firstname){
   this.updater_firstname=updater_firstname;
  }
  public String getUpdater_firstname(){
   return updater_firstname;
  }
  public void setStart_date(String start_date){
   this.start_date=start_date;
  }
  public String getStart_date(){
   return start_date;
  }
  public void setQualification_status(String qualification_status){
   this.qualification_status=qualification_status;
  }
  public String getQualification_status(){
   return qualification_status;
  }
}