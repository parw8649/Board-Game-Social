package com.example.boardgamesocial.DataClasses.Relationships;

import java.util.Objects;

public class UserToUser {
    private Integer id;
    private Integer userOneId;
    private Integer userTwoId;

    public UserToUser(Integer id, Integer userOneId, Integer userTwoId) {
        this.id = id;
        this.userOneId = userOneId;
        this.userTwoId = userTwoId;
    }

    public UserToUser(Integer userOneId, Integer userTwoId) {
        this.userOneId = userOneId;
        this.userTwoId = userTwoId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserOneId() {
        return userOneId;
    }

    public void setUserOneId(Integer userOneId) {
        this.userOneId = userOneId;
    }

    public Integer getUserTwoId() {
        return userTwoId;
    }

    public void setUserTwoId(Integer userTwoId) {
        this.userTwoId = userTwoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserToUser that = (UserToUser) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getUserOneId(), that.getUserOneId())
                && Objects.equals(getUserTwoId(), that.getUserTwoId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getUserOneId(),
                getUserTwoId());
    }

    @Override
    public String toString() {
        return "UserToUser{" +
                "id=" + id +
                ", userOneId=" + userOneId +
                ", userTwoId=" + userTwoId +
                '}';
    }
}
