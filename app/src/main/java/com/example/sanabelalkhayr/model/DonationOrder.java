package com.example.sanabelalkhayr.model;

public class DonationOrder {

    private int id;
    private String userName;// user who order this //or user who post this
    private String donation_title;
    private int quantity;
    private int status;
    private String message;
    private String createdAt;

    public DonationOrder(int id, String userName, String donation_title, int quantity, int status, String message, String createdAt) {
        this.id = id;
        this.userName = userName;
        this.donation_title = donation_title;
        this.quantity = quantity;
        this.status = status;
        this.message = message;
        this.createdAt = createdAt;
    }

    public String getUserName() {
        return userName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDonation_title() {
        return donation_title;
    }

    public void setDonation_title(String donation_title) {
        this.donation_title = donation_title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
