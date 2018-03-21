package com.mindnotix.model.jobs.filter_master;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Jobtypeview{
  @SerializedName("image_name")
  @Expose
  private String image_name;
  @SerializedName("total")
  @Expose
  private String total;
  @SerializedName("trademe_type")
  @Expose
  private String trademe_type;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("livejobtotal")
  @Expose
  private String livejobtotal;
  @SerializedName("id")
  @Expose
  private String id;
  @SerializedName("livevacanciestotal")
  @Expose
  private String livevacanciestotal;
  @SerializedName("status")
  @Expose
  private String status;
  public void setImage_name(String image_name){
   this.image_name=image_name;
  }
  public String getImage_name(){
   return image_name;
  }
  public void setTotal(String total){
   this.total=total;
  }
  public String getTotal(){
   return total;
  }
  public void setTrademe_type(String trademe_type){
   this.trademe_type=trademe_type;
  }
  public String getTrademe_type(){
   return trademe_type;
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