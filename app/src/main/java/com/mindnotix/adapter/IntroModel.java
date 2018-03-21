package com.mindnotix.adapter;

/**
 * Created by Admin on 1/16/2018.
 */

public class IntroModel {

    public  int image;
    public String content;

    public IntroModel(int one, String hello) {
        this.image =one;
        this.content = hello;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
