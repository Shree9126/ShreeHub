package com.mindnotix.model.contactsupport.view;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("supportdetailview")
  @Expose
  private List<Supportdetailview> supportdetailview;
  @SerializedName("supportview")
  @Expose
  private List<Supportview> supportview;
  public void setSupportdetailview(List<Supportdetailview> supportdetailview){
   this.supportdetailview=supportdetailview;
  }
  public List<Supportdetailview> getSupportdetailview(){
   return supportdetailview;
  }
  public void setSupportview(List<Supportview> supportview){
   this.supportview=supportview;
  }
  public List<Supportview> getSupportview(){
   return supportview;
  }
}