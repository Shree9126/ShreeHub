package com.mindnotix.model.resume.provider;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("provider_ids")
  @Expose
  private List<Provider_ids> provider_ids;
  public void setProvider_ids(List<Provider_ids> provider_ids){
   this.provider_ids=provider_ids;
  }
  public List<Provider_ids> getProvider_ids(){
   return provider_ids;
  }
}