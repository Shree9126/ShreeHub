package com.mindnotix.model.message.add_message;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("chat_message_list")
  @Expose
  private List<Chat_message_list> chat_message_list;
  public void setChat_message_list(List<Chat_message_list> chat_message_list){
   this.chat_message_list=chat_message_list;
  }
  public List<Chat_message_list> getChat_message_list(){
   return chat_message_list;
  }
}