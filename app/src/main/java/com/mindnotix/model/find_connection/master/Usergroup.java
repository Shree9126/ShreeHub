package com.mindnotix.model.find_connection.master;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Usergroup{
  @SerializedName("id")
  @Expose
  private String id;
  @SerializedName("groupname")
  @Expose
  private String groupname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
}