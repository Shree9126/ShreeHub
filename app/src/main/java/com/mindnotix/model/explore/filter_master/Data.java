package com.mindnotix.model.explore.filter_master;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Awesome Pojo Generator
 * */
public class Data{
    public ArrayList<Course_sortby> getCourse_sortby() {
        return course_sortby;
    }

    public void setCourse_sortby(ArrayList<Course_sortby> course_sortby) {
        this.course_sortby = course_sortby;
    }

    @SerializedName("course_sortby")
  @Expose
  private ArrayList<Course_sortby> course_sortby;


}