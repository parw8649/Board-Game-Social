package com.example.boardgamesocial.DataClasses;

import java.util.Objects;

public class Game implements DataClass {
    private Integer id;
    private String gameTitle;
    private String genre;
    private Integer minPlayer;
    private Integer maxPlayer;
    private String description;
    private String imageUrl;
    private Integer overallPlayCount;

    public Game(Integer id, String gameTitle, String genre, Integer minPlayer, Integer maxPlayer, String description, String imageUrl, Integer overallPlayCount) {
        this.id = id;
        this.gameTitle = gameTitle;
        this.genre = genre;
        this.minPlayer = minPlayer;
        this.maxPlayer = maxPlayer;
        this.description = description;
        this.imageUrl = imageUrl;
        this.overallPlayCount = overallPlayCount;
    }

    public Game(String gameTitle, String genre, Integer minPlayer, Integer maxPlayer, String description, String imageUrl, Integer overallPlayCount) {
        this.gameTitle = gameTitle;
        this.genre = genre;
        this.minPlayer = minPlayer;
        this.maxPlayer = maxPlayer;
        this.description = description;
        this.imageUrl = imageUrl;
        this.overallPlayCount = overallPlayCount;
    }

    public Game(String gameTitle, String genre, Integer minPlayer, Integer maxPlayer) {
        this.gameTitle = gameTitle;
        this.genre = genre;
        this.minPlayer = minPlayer;
        this.maxPlayer = maxPlayer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getMinPlayer() {
        return minPlayer;
    }

    public void setMinPlayer(Integer minPlayer) {
        this.minPlayer = minPlayer;
    }

    public Integer getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(Integer maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getOverallPlayCount() {
        return overallPlayCount;
    }

    public void setOverallPlayCount(Integer overallPlayCount) {
        this.overallPlayCount = overallPlayCount;
    }

    @Override
    public Integer getPrimaryKey() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(getId(), game.getId())
                && Objects.equals(getGameTitle(), game.getGameTitle())
                && Objects.equals(getGenre(), game.getGenre())
                && Objects.equals(getMinPlayer(), game.getMinPlayer())
                && Objects.equals(getMaxPlayer(), game.getMaxPlayer())
                && Objects.equals(getDescription(), game.getDescription())
                && Objects.equals(getImageUrl(), game.getImageUrl())
                && Objects.equals(getOverallPlayCount(), game.getOverallPlayCount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getGameTitle(),
                getGenre(),
                getMinPlayer(),
                getMaxPlayer(),
                getDescription(),
                getImageUrl(),
                getOverallPlayCount());
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", gameTitle='" + gameTitle + "'" +
                ", genre='" + genre + "'" +
                ", minPlayer=" + minPlayer +
                ", maxPlayer=" + maxPlayer +
                ", description='" + description + "'" +
                ", imageUrl='" + imageUrl + "'" +
                ", overallPlayCount=" + overallPlayCount +
                '}';
    }
}
