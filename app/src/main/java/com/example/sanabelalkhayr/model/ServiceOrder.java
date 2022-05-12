package com.example.sanabelalkhayr.model;

public class ServiceOrder {

    private int id;
    private String donation_title;
    private  String user_name;// user who ordered this or/ user who posted this
    private int status;
    private String message;
    private String createdAt;

    public ServiceOrder(int id, String donation_title, String user_name, int status, String message, String createdAt) {
        this.id = id;
        this.donation_title = donation_title;
        this.user_name = user_name;
        this.status = status;
        this.message = message;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }


    public String getDonation_title() {
        return donation_title;
    }


    public String getUser_name() {
        return user_name;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
