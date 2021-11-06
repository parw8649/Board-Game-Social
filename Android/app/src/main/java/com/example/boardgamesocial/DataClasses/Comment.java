package com.example.boardgamesocial.DataClasses;

import java.util.Objects;

public class Comment {
    private Integer id;
    private Integer userId;
    private Integer postId;
    private String content;

    public Comment(Integer id, Integer userId, Integer postId, String content) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.content = content;
    }

    public Comment(Integer userId, Integer postId, String content) {
        this.userId = userId;
        this.postId = postId;
        this.content = content;
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

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(getId(), comment.getId())
                && Objects.equals(getUserId(), comment.getUserId())
                && Objects.equals(getPostId(), comment.getPostId())
                && Objects.equals(getContent(), comment.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getUserId(),
                getPostId(),
                getContent());
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", postId=" + postId +
                ", content='" + content + '\'' +
                '}';
    }
}
