package com.mindnotix.model.SubPartnerType;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("subpartnertype")
  @Expose
  private List<Subpartnertype> subpartnertype;
  public void setSubpartnertype(List<Subpartnertype> subpartnertype){
   this.subpartnertype=subpartnertype;
  }
  public List<Subpartnertype> getSubpartnertype(){
   return subpartnertype;
  }
}