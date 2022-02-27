package com.example.sanabelalkhayr.model;

public class ServiceRequest {

    private int id;
    private int user_id;
    private String userName;
    private int donation_id;
    private String donationName;
    private int donationQuantity;
    private int service_id;
    private int status;
    private String date;

    public ServiceRequest(int id, int user_id, String userName, int donation_id, String donationName, int donationQuantity, int service_id, String date, int status) {
        this.id = id;
        this.status = status;
        this.user_id = user_id;
        this.userName = userName;
        this.donation_id = donation_id;
        this.donationName = donationName;
        this.donationQuantity = donationQuantity;
        this.service_id = service_id;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDonation_id(int donation_id) {
        this.donation_id = donation_id;
    }

    public void setDonationName(String donationName) {
        this.donationName = donationName;
    }

    public void setDonationQuantity(int donationQuantity) {
        this.donationQuantity = donationQuantity;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDate(String date) {
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
