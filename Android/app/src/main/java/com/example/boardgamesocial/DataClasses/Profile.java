package com.example.boardgamesocial.DataClasses;

import java.util.Objects;

public class Profile implements DataClass{
    private Integer id;
    private Integer userId;
    private String bio;
    private String iconUrl;

    public Profile(Integer id, Integer userId, String bio, String iconUrl) {
        this.id = id;
        this.userId = userId;
        this.bio = bio;
        this.iconUrl = iconUrl;
    }

    public Profile(Integer userId, String bio, String iconUrl) {
        this.userId = userId;
        this.bio = bio;
        this.iconUrl = iconUrl;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @Override
    public Integer getPrimaryKey() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(getId(), profile.getId()) && Objects.equals(getUserId(), profile.getUserId()) && Objects.equals(getBio(), profile.getBio()) && Objects.equals(getIconUrl(), profile.getIconUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserId(), getBio(), getIconUrl());
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", userId=" + userId +
                ", bio='" + bio + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                '}';
    }
}
