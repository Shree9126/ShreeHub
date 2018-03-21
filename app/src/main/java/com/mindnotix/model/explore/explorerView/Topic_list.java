package com.mindnotix.model.explore.explorerView;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Topic_list{
  @SerializedName("topic_name")
  @Expose
  private String topic_name;
  @SerializedName("topic_id")
  @Expose
  private String topic_id;
  public void setTopic_name(String topic_name){
   this.topic_name=topic_name;
  }
  public String getTopic_name(){
   return topic_name;
  }
  public void setTopic_id(String topic_id){
   this.topic_id=topic_id;
  }
  public String getTopic_id(){
   return topic_id;
  }
}