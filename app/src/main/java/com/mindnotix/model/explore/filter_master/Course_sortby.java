package com.mindnotix.model.explore.filter_master;

/**
 * Awesome Pojo Generator
 * */
public class Course_sortby{

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;


    @Override
    public String toString() {
        return name;
    }
}