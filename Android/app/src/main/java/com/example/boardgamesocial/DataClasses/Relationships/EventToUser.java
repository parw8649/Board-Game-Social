package com.example.boardgamesocial.DataClasses.Relationships;

import java.util.Objects;

public class EventToUser {
    private Integer id;
    private Integer serId;
    private Integer eventId;

    public EventToUser(Integer id, Integer serId, Integer eventId) {
        this.id = id;
        this.serId = serId;
        this.eventId = eventId;
    }

    public EventToUser(Integer serId, Integer eventId) {
        this.serId = serId;
        this.eventId = eventId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSerId() {
        return serId;
    }

    public void setSerId(Integer serId) {
        this.serId = serId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventToUser that = (EventToUser) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getSerId(), that.getSerId())
                && Objects.equals(getEventId(), that.getEventId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getSerId(),
                getEventId());
    }

    @Override
    public String toString() {
        return "EventToUser{" +
                "id=" + id +
                ", serId=" + serId +
                ", eventId=" + eventId +
                '}';
    }
}
