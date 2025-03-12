package com.example.demo.models;

public class Post {
    private int id;
    private String content;
    private int userId;
    private String dateTime;
    private String firstName;
    private String lastName;
    private int likeCount;

    public Post() {}

    public Post(int id, String content, int userId, String dateTime, String firstName, String lastName) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.dateTime = dateTime;
        this.firstName = firstName;
        this.lastName = lastName;
        this.likeCount = 0;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getDateTime() { return dateTime; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public int getLikeCount() { return likeCount; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }
}