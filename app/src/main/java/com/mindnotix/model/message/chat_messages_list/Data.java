package com.mindnotix.model.message.chat_messages_list;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("chat_message_list")
  @Expose
  private List<Users_info> users_info;
  public void setUsers_info(List<Users_info> users_info){
   this.users_info=users_info;
  }
  public List<Users_info> getUsers_info(){
   return users_info;
  }
}