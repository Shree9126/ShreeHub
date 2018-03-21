package com.mindnotix.model.dashboard.like_comments_contribute;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Tweaks {
    @SerializedName("post_user")
    @Expose
    private Post_user post_user;
    @SerializedName("posts")
    @Expose
    private Posts posts;

    public Tweaks(Posts posts ,Post_user post_user) {
        this.posts = posts;
        this.post_user = post_user;
    }


    public Post_user getPost_user() {
        return post_user;
    }

    public void setPost_user(Post_user post_user) {
        this.post_user = post_user;
    }

    public Posts getPosts() {
        return posts;
    }

    public void setPosts(Posts posts) {
        this.posts = posts;
    }
}