package com.mindnotix.model.message.chat_history;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("image_path")
  @Expose
  private String image_path;
  @SerializedName("chat_list_users")
  @Expose
  private List<Chat_list_users> chat_list_users;
  public void setImage_path(String image_path){
   this.image_path=image_path;
  }
  public String getImage_path(){
   return image_path;
  }
  public void setChat_list_users(List<Chat_list_users> chat_list_users){
   this.chat_list_users=chat_list_users;
  }
  public List<Chat_list_users> getChat_list_users(){
   return chat_list_users;
  }
}