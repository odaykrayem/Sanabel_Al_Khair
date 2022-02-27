package com.example.sanabelalkhayr.model;

public class ServiceRequest {

    private int id;
    private int user_id;
    private String userName;
    private int donation_id;
    private String donationName;
    private int donationQuantity;
    private int service_id;
    private String date;

    public ServiceRequest(int id, int user_id, String userName, int donation_id, String donationName, int donationQuantity, int service_id, String date) {
        this.id = id;
        this.user_id = user_id;
        this.userName = userName;
        this.donation_id = donation_id;
        this.donationName = donationName;
        this.donationQuantity = donationQuantity;
        this.service_id = service_id;
        this.date = date;
    }

    public int getDonationQuantity() {
        return donationQuantity;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUserName() {
        return userName;
    }

    public int getDonation_id() {
        return donation_id;
    }

    public String getDonationName() {
        return donationName;
    }
}
