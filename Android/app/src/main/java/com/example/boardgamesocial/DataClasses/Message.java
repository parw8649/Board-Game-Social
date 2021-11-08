package com.example.boardgamesocial.DataClasses;

import java.util.Objects;

public class Message implements DataClass {
    private Integer id;
    private Integer senderId;
    private Integer receiverId;
    private String content;

    public Message(Integer id, Integer senderId, Integer receiverId, String content) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
    }

    public Message(Integer senderId, Integer receiverId, String content) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
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
        Message message = (Message) o;
        return Objects.equals(getId(), message.getId())
                && Objects.equals(getSenderId(), message.getSenderId())
                && Objects.equals(getReceiverId(), message.getReceiverId())
                && Objects.equals(getContent(), message.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getSenderId(),
                getReceiverId(),
                getContent());
    }
}
