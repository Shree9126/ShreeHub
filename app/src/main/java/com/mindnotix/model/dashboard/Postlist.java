
package com.mindnotix.model.dashboard;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Postlist {

    @SerializedName("posts")
    @Expose
    private Posts posts;
    @SerializedName("tags_name")
    @Expose
    private List<TagsName> tagsName = null;
    @SerializedName("images_count")
    @Expose
    private String imagesCount;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("post_user")
    @Expose
    private PostUser postUser;
  //  private List<PostUser> postUser = null;
    @SerializedName("post_user_activity")
    @Expose
    private PostUserActivity postUserActivity;

    public Posts getPosts() {
        return posts;
    }

    public void setPosts(Posts posts) {
        this.posts = posts;
    }

    public List<TagsName> getTagsName() {
        return tagsName;
    }

    public void setTagsName(List<TagsName> tagsName) {
        this.tagsName = tagsName;
    }

    public String getImagesCount() {
        return imagesCount;
    }

    public void setImagesCount(String imagesCount) {
        this.imagesCount = imagesCount;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

   /* public List<PostUser> getPostUser() {
        return postUser;
    }

    public void setPostUser(List<PostUser> postUser) {
        this.postUser = postUser;
    }*/

    public PostUserActivity getPostUserActivity() {
        return postUserActivity;
    }

    public void setPostUserActivity(PostUserActivity postUserActivity) {
        this.postUserActivity = postUserActivity;
    }

    public PostUser getPostUser() {
        return postUser;
    }

    public void setPostUser(PostUser postUser) {
        this.postUser = postUser;
    }
}
