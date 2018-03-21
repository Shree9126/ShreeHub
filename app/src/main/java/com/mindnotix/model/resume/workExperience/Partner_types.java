package com.mindnotix.model.resume.workExperience;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 * */
public class Partner_types{
  @SerializedName("image")
  @Expose
  private String image;
  @SerializedName("is_display_register")
  @Expose
  private String is_display_register;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("id")
  @Expose
  private String id;
  public void setImage(String image){
   this.image=image;
  }
  public String getImage(){
   return image;
  }
  public void setIs_display_register(String is_display_register){
   this.is_display_register=is_display_register;
  }
  public String getIs_display_register(){
   return is_display_register;
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

    @Override
    public String toString() {
        return name;
    }
}