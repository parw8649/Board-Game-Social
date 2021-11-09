package com.example.boardgamesocial.DataClasses.Relationships;

import com.example.boardgamesocial.DataClasses.DataClass;

import java.util.Objects;

public class EventToUser implements DataClass {
    private Integer id;
    private Integer userId;
    private Integer eventId;

    public EventToUser(Integer id, Integer userId, Integer eventId) {
        this.id = id;
        this.userId = userId;
        this.eventId = eventId;
    }

    public EventToUser(Integer userId, Integer eventId) {
        this.userId = userId;
        this.eventId = eventId;
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

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    @Override
    public Integer getPrimaryKey() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventToUser that = (EventToUser) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getUserId(), that.getUserId())
                && Objects.equals(getEventId(), that.getEventId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getUserId(),
                getEventId());
    }

    @Override
    public String toString() {
        return "EventToUser{" +
                "id=" + id +
                ", userId=" + userId +
                ", eventId=" + eventId +
                '}';
    }
}
