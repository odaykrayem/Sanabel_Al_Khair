package com.example.sanabelalkhayr.model;

public class DonationOrder {
    private int id;
    private int user_id;
    private int donation_id;
    private String donation_title;
    private int quantity;
    private int status;
    private String message;
    private String createdAt;

    public DonationOrder(int id, int user_id, int donation_id, int quantity, int status, String message) {
        this.id = id;
        this.user_id = user_id;
        this.donation_id = donation_id;
        this.quantity = quantity;
        this.status = status;
        this.message = message;
    }

//constructor for order list
    public DonationOrder(int id, int donation_id, String donation_title, int quantity, int status, String message, String createdAt) {
        this.id = id;
        this.donation_id = donation_id;
        this.donation_title = donation_title;
        this.quantity = quantity;
        this.status = status;
        this.message = message;
        this.createdAt = createdAt;
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

    public int getUser_id() {
        return user_id;
    }

    public int getDonation_id() {
        return donation_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setDonation_id(int donation_id) {
        this.donation_id = donation_id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
