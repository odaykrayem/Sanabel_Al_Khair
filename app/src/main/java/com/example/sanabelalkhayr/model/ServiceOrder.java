package com.example.sanabelalkhayr.model;

public class ServiceOrder {
        private int id;
        private int donation_id;
         private int service_id;
         private String donation_title;
        private  String volunteer_name;
        private int status;
        private String message;
        private String createdAt;

    public ServiceOrder(int id, int donation_id,int service_id ,String donation_title, String volunteer_name, int status, String message, String createdAt) {
        this.id = id;
        this.donation_id = donation_id;
        this.service_id = service_id;
        this.donation_title = donation_title;
        this.volunteer_name = volunteer_name;
        this.status = status;
        this.message = message;
        this.createdAt = createdAt;
    }



    public int getId() {
        return id;
    }

    public int getDonation_id() {
        return donation_id;
    }

    public String getDonation_title() {
        return donation_title;
    }

    public int getService_id() {
        return service_id;
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
