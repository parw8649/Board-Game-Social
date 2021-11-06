package com.example.boardgamesocial.DataClasses;

import java.util.Objects;

public class Review {
    private Integer id;
    private Integer serId;
    private Integer ameId;
    private String content;

    public Review(Integer id, Integer serId, Integer ameId, String content) {
        this.id = id;
        this.serId = serId;
        this.ameId = ameId;
        this.content = content;
    }

    public Review(Integer serId, Integer ameId, String content) {
        this.serId = serId;
        this.ameId = ameId;
        this.content = content;
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

    public Integer getAmeId() {
        return ameId;
    }

    public void setAmeId(Integer ameId) {
        this.ameId = ameId;
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
        Review review = (Review) o;
        return Objects.equals(getId(), review.getId())
                && Objects.equals(getSerId(), review.getSerId())
                && Objects.equals(getAmeId(), review.getAmeId())
                && Objects.equals(getContent(), review.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getSerId(),
                getAmeId(),
                getContent());
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", serId=" + serId +
                ", ameId=" + ameId +
                ", content='" + content + '\'' +
                '}';
    }
}
