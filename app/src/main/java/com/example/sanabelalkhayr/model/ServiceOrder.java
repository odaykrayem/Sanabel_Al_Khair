package com.example.sanabelalkhayr.model;

public class ServiceOrder {

    private int id;
    private int donation_order_id;
    private String donation_title;
    private  String volunteer_name;
    private int status;
    private String message;
    private String createdAt;

    public ServiceOrder(int id, String donation_title, String volunteer_name, int status, String message, String createdAt) {
        this.id = id;
        this.donation_title = donation_title;
        this.volunteer_name = volunteer_name;
        this.status = status;
        this.message = message;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public int getDonation_order_id() {
        return donation_order_id;
    }

    public String getDonation_title() {
        return donation_title;
    }


    public String getVolunteer_name() {
        return volunteer_name;
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
