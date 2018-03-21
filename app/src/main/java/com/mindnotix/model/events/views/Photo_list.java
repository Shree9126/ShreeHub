package com.mindnotix.model.events.views;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 2/16/2018.
 */

public class Photo_list {


    @SerializedName("photo_id")
    @Expose
    private String photo_id;
    @SerializedName("photo_name")
    @Expose
    private String photo_name;
    @SerializedName("image_path")
    @Expose
    private String image_path;
    @SerializedName("photo_title")
    @Expose
    private String photo_title;
    public void setPhoto_id(String photo_id){
        this.photo_id=photo_id;
    }
    public String getPhoto_id(){
        return photo_id;
    }
    public void setPhoto_name(String photo_name){
        this.photo_name=photo_name;
    }
    public String getPhoto_name(){
        return photo_name;
    }
    public void setImage_path(String image_path){
        this.image_path=image_path;
    }
    public String getImage_path(){
        return image_path;
    }
    public void setPhoto_title(String photo_title){
        this.photo_title=photo_title;
    }
    public String getPhoto_title(){
        return photo_title;
    }
}
