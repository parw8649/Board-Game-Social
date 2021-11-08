package com.example.boardgamesocial.DataClasses.Relationships;

import com.example.boardgamesocial.DataClasses.DataClass;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class GameToUser implements DataClass {
    @SerializedName("id")
    private Integer id;
    @SerializedName("userId")
    private Integer userId;
    @SerializedName("gameId")
    private Integer gameId;
    @SerializedName("private")
    private Boolean private_;

    public GameToUser(Integer id, Integer userId, Integer gameId, Boolean private_) {
        this.id = id;
        this.userId = userId;
        this.gameId = gameId;
        this.private_ = private_;
    }

    public GameToUser(Integer userId, Integer gameId, Boolean private_) {
        this.userId = userId;
        this.gameId = gameId;
        this.private_ = private_;
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

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Boolean getPrivate_() {
        return private_;
    }

    public void setPrivate_(Boolean private_) {
        this.private_ = private_;
    }

    @Override
    public Integer getPrimaryKey() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameToUser that = (GameToUser) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getUserId(), that.getUserId())
                && Objects.equals(getGameId(), that.getGameId())
                && Objects.equals(getPrivate_(), that.getPrivate_());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getUserId(),
                getGameId(),
                getPrivate_());
    }

    @Override
    public String toString() {
        return "GameToUser{" +
                "id=" + id +
                ", userId=" + userId +
                ", gameId=" + gameId +
                ", private_=" + private_ +
                '}';
    }
}
