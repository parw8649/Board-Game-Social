package com.example.boardgamesocial.DataClasses;

import java.util.Objects;

public class HostedGame implements DataClass {
    private Integer id;
    private Integer eventId;
    private Integer gameId;
    private Integer seatsAvailable;

    public HostedGame(Integer id, Integer eventId, Integer gameId, Integer seatsAvailable) {
        this.id = id;
        this.eventId = eventId;
        this.gameId = gameId;
        this.seatsAvailable = seatsAvailable;
    }

    public HostedGame(Integer eventId, Integer gameId, Integer seatsAvailable) {
        this.eventId = eventId;
        this.gameId = gameId;
        this.seatsAvailable = seatsAvailable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(Integer seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    @Override
    public Integer getPrimaryKey() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HostedGame that = (HostedGame) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getEventId(), that.getEventId())
                && Objects.equals(getGameId(), that.getGameId())
                && Objects.equals(getSeatsAvailable(), that.getSeatsAvailable());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getEventId(),
                getGameId(),
                getSeatsAvailable());
    }

    @Override
    public String toString() {
        return "HostedGame{" +
                "id=" + id +
                ", eventId=" + eventId +
                ", gameId=" + gameId +
                ", seatsAvailable=" + seatsAvailable +
                '}';
    }
}
