package com.mindnotix.model.profile.update;

/**
 * Created by Admin on 06-02-2018.
 */

public class WhatVisaHave {

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
