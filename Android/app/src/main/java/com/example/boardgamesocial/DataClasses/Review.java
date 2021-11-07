package com.example.boardgamesocial.DataClasses;

import java.util.Objects;

public class Review implements DataClass {
    private Integer id;
    private Integer userId;
    private Integer gameId;
    private String content;

    public Review(Integer id, Integer userId, Integer gameId, String content) {
        this.id = id;
        this.userId = userId;
        this.gameId = gameId;
        this.content = content;
    }

    public Review(Integer userId, Integer gameId, String content) {
        this.userId = userId;
        this.gameId = gameId;
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

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public Integer getPrimaryKey() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(getId(), review.getId())
                && Objects.equals(getUserId(), review.getUserId())
                && Objects.equals(getGameId(), review.getGameId())
                && Objects.equals(getContent(), review.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getUserId(),
                getGameId(),
                getContent());
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", userId=" + userId +
                ", gameId=" + gameId +
                ", content='" + content + '\'' +
                '}';
    }
}
