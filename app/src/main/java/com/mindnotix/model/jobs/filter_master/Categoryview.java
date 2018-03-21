package com.mindnotix.model.jobs.filter_master;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Categoryview{
  @SerializedName("image")
  @Expose
  private String image;
  @SerializedName("total")
  @Expose
  private String total;
  @SerializedName("is_display_register")
  @Expose
  private String is_display_register;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("livejobtotal")
  @Expose
  private String livejobtotal;
  @SerializedName("id")
  @Expose
  private String id;
  @SerializedName("trademeid")
  @Expose
  private String trademeid;
  @SerializedName("livevacanciestotal")
  @Expose
  private String livevacanciestotal;
  @SerializedName("status")
  @Expose
  private String status;
  public void setImage(String image){
   this.image=image;
  }
  public String getImage(){
   return image;
  }
  public void setTotal(String total){
   this.total=total;
  }
  public String getTotal(){
   return total;
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
  public void setLivejobtotal(String livejobtotal){
   this.livejobtotal=livejobtotal;
  }
  public String getLivejobtotal(){
   return livejobtotal;
  }
  public void setId(String id){
   this.id=id;
  }
  public String getId(){
   return id;
  }
  public void setTrademeid(String trademeid){
   this.trademeid=trademeid;
  }
  public String getTrademeid(){
   return trademeid;
  }
  public void setLivevacanciestotal(String livevacanciestotal){
   this.livevacanciestotal=livevacanciestotal;
  }
  public String getLivevacanciestotal(){
   return livevacanciestotal;
  }
  public void setStatus(String status){
   this.status=status;
  }
  public String getStatus(){
   return status;
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