package com.example.boardgamesocial.DataClasses;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Post implements DataClass {
    @SerializedName("id")
    private Integer id;
    @SerializedName("userId")
    private Integer userId;
    @SerializedName("postBody")
    private String postBody;
    @SerializedName("postType")
    private String postType;
    @SerializedName("private")
    private Boolean private_;
    @SerializedName("likes")
    private Integer likes;

    public Post(Integer id, Integer userId, String postBody, String postType, Boolean private_, Integer likes) {
        this.id = id;
        this.userId = userId;
        this.postBody = postBody;
        this.postType = postType;
        this.private_ = private_;
        this.likes = likes;
    }

    public Post(Integer userId, String postBody, String postType, Boolean private_, Integer likes) {
        this.userId = userId;
        this.postBody = postBody;
        this.postType = postType;
        this.private_ = private_;
        this.likes = likes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public Boolean getPrivate_() {
        return private_;
    }

    public void setPrivate_(Boolean private_) {
        this.private_ = private_;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @Override
    public Integer getPrimaryKey() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(getId(), post.getId())
                && Objects.equals(getUserId(), post.getUserId())
                && Objects.equals(getPostBody(), post.getPostBody())
                && Objects.equals(getPostType(), post.getPostType())
                && Objects.equals(getPrivate_(), post.getPrivate_())
                && Objects.equals(getLikes(), post.getLikes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getUserId(),
                getPostBody(),
                getPostType(),
                getPrivate_(),
                getLikes());
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userId=" + userId +
                ", postBody='" + postBody + '\'' +
                ", postType='" + postType + '\'' +
                ", private_=" + private_ +
                ", likes=" + likes +
                '}';
    }
}
