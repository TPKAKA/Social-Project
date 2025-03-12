package com.example.demo.models;

public class Comment {
    private int id;
    private String content;
    private int postId;
    private int userId;
    private String dateTime;
    private String firstName;
    private String lastName;

    public Comment() {}

    public Comment(int id, String content, int postId, int userId, String dateTime, String firstName, String lastName) {
        this.id = id;
        this.content = content;
        this.postId = postId;
        this.userId = userId;
        this.dateTime = dateTime;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getDateTime() { return dateTime; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
}