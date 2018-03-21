package com.mindnotix.model.profile.update;

/**
 * Created by Admin on 03-02-2018.
 */

public class WorkAvailablity {

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

    private String key;
    private String name;

    /**
     * Pay attention here, you have to override the toString method as the
     * ArrayAdapter will reads the toString of the given object for the name
     *
     * @return contact_name
     */
    @Override
    public String toString() {
        return name;
    }
}
