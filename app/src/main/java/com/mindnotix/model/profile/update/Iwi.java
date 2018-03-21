package com.mindnotix.model.profile.update;



import java.util.ArrayList;

/**
 * Created by Admin on 03-02-2018.
 */

public class Iwi   {

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

    public ArrayList<Subiwi> getSubiwi() {
        return subiwi;
    }

    public void setSubiwi(ArrayList<Subiwi> subiwi) {
        this.subiwi = subiwi;
    }

    private ArrayList<Subiwi> subiwi;



}


