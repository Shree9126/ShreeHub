package com.mindnotix.model.contactsupport.master;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("ticket_status")
  @Expose
  private List<Ticket_status> ticket_status;
  public void setTicket_status(List<Ticket_status> ticket_status){
   this.ticket_status=ticket_status;
  }
  public List<Ticket_status> getTicket_status(){
   return ticket_status;
  }
}