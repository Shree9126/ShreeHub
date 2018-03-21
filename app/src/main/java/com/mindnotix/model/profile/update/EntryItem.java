package com.mindnotix.model.profile.update;


import com.mindnotix.listener.Item;

/**
 * Created by Admin on 05-02-2018.
 */

public class EntryItem implements Item {


    public final String title;

    public String getId() {
        return id;
    }

    public final String id;

    public Boolean getSetchecked() {
        return setchecked;
    }

    public void setSetchecked(Boolean setchecked) {
        this.setchecked = setchecked;
    }

    public  Boolean setchecked=false;


    public EntryItem(String title,String id) {
        this.title = title;
        this.id = id;

    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean isSection() {
        return false;
    }
}