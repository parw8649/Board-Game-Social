package com.example.boardgamesocial.DataClasses.Relationships;

import com.example.boardgamesocial.DataClasses.DataClass;

import java.util.Objects;

public class HostedGameToUser implements DataClass {
    private Integer id;
    private Integer userId;
    private Integer gameId;

    public HostedGameToUser(Integer id, Integer userId, Integer gameId) {
        this.id = id;
        this.userId = userId;
        this.gameId = gameId;
    }

    public HostedGameToUser(Integer userId, Integer gameId) {
        this.userId = userId;
        this.gameId = gameId;
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

    @Override
    public Integer getPrimaryKey() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HostedGameToUser that = (HostedGameToUser) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getUserId(), that.getUserId())
                && Objects.equals(getGameId(), that.getGameId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getUserId(),
                getGameId());
    }

    @Override
    public String toString() {
        return "HostedGameToUser{" +
                "id=" + id +
                ", userId=" + userId +
                ", gameId=" + gameId +
                '}';
    }
}
