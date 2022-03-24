package com.example.sanabelalkhayr.model;

public class Feedback
{
    private int id;
    private String title;
    private String content;
    private int userId;
    private String date;

    public Feedback(int id, String title, String content, int userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public Feedback(int id, String title, String content, int userId, String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getUserId() {
        return userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
