package com.example.boardgamesocial.DataClasses;


import java.util.Date;
import java.util.Objects;

public class Event {
    /**
     * The dateTime field should be like one of these:
     * ['%Y-%m-%d %H:%M:%S',    # '2006-10-25 14:30:59'
     *  '%Y-%m-%d %H:%M',       # '2006-10-25 14:30'
     *  '%Y-%m-%d',             # '2006-10-25'
     *  '%m/%d/%Y %H:%M:%S',    # '10/25/2006 14:30:59'
     *  '%m/%d/%Y %H:%M',       # '10/25/2006 14:30'
     *  '%m/%d/%Y',             # '10/25/2006'
     *  '%m/%d/%y %H:%M:%S',    # '10/25/06 14:30:59'
     *  '%m/%d/%y %H:%M',       # '10/25/06 14:30'
     *  '%m/%d/%y']             # '10/25/06'
     */
    private Integer id;
    private String name;
    private Integer hostUserId;
    private Date dateTime;
    private String description;
    private Integer hostedGames;

    public Event(Integer id, String name, Integer hostUserId, Date dateTime, String description, Integer hostedGames) {
        this.id = id;
        this.name = name;
        this.hostUserId = hostUserId;
        this.dateTime = dateTime;
        this.description = description;
        this.hostedGames = hostedGames;
    }

    public Event(String name, Integer hostUserId, Date dateTime, String description, Integer hostedGames) {
        this.name = name;
        this.hostUserId = hostUserId;
        this.dateTime = dateTime;
        this.description = description;
        this.hostedGames = hostedGames;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHostUserId() {
        return hostUserId;
    }

    public void setHostUserId(Integer hostUserId) {
        this.hostUserId = hostUserId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getHostedGames() {
        return hostedGames;
    }

    public void setHostedGames(Integer hostedGames) {
        this.hostedGames = hostedGames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(getId(), event.getId())
                && Objects.equals(getName(), event.getName())
                && Objects.equals(getHostUserId(), event.getHostUserId())
                && Objects.equals(getDateTime(), event.getDateTime())
                && Objects.equals(getDescription(), event.getDescription())
                && Objects.equals(getHostedGames(), event.getHostedGames());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getName(),
                getHostUserId(),
                getDateTime(),
                getDescription(),
                getHostedGames());
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hostUserId=" + hostUserId +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", hostedGames=" + hostedGames +
                '}';
    }
}
