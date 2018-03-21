package com.mindnotix.model.jobs.filter_master.sub_category;
/**
 * Awesome Pojo Generator
 * */
public class Subcategoryview{
  private String total;
  private String partner_type_id;
  private String name;
  private String livejobtotal;
  private String id;
  private String trademeid;
  private String livevacanciestotal;
  private String status;
  public void setTotal(String total){
   this.total=total;
  }
  public String getTotal(){
   return total;
  }
  public void setPartner_type_id(String partner_type_id){
   this.partner_type_id=partner_type_id;
  }
  public String getPartner_type_id(){
   return partner_type_id;
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