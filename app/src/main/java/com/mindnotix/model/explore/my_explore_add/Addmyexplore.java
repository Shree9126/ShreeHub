package com.mindnotix.model.explore.my_explore_add;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Addmyexplore {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("learning_users_sequence_id")
    @Expose
    private String learning_users_sequence_id;
    @SerializedName("status")
    @Expose
    private String status;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLearning_users_sequence_id() {
        return learning_users_sequence_id;
    }

    public void setLearning_users_sequence_id(String learning_users_sequence_id) {
        this.learning_users_sequence_id = learning_users_sequence_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}