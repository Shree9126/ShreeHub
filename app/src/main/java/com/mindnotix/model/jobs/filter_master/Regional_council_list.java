package com.mindnotix.model.jobs.filter_master;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Regional_council_list{
  @SerializedName("user_id")
  @Expose
  private String user_id;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("id")
  @Expose
  private String id;
  public void setUser_id(String user_id){
   this.user_id=user_id;
  }
  public String getUser_id(){
   return user_id;
  }
  public void setName(String name){
   this.name=name;
  }
  public String getName(){
   return name;
  }
  public void setId(String id){
   this.id=id;
  }
  public String getId(){
   return id;
  }

    /**
     * Pay attention here, you have to override the toString method as the
     * ArrayAdapter will reads the toString of the given object for the name
     *
     * @return ptg_name
     */
    @Override
    public String toString() {
        return name;
    }
}