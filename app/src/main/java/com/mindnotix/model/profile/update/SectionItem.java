package com.mindnotix.model.profile.update;


import com.mindnotix.listener.Item;

/**
 * Created by Admin on 05-02-2018.
 */

public class SectionItem implements Item {

    private final String title;

    public SectionItem(String title) {
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    @Override
    public boolean isSection() {
        return true;
    }

}