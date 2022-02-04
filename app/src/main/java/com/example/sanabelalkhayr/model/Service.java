package com.example.sanabelalkhayr.model;

public class Service {

    private int id;
    private String volunteerName;
    private String description;
    private String region;
    private int status;
    private int donationId;

    public void setvolunteerName(String volunteerName) {
        this.volunteerName = volunteerName;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getvolunteerName() {
        return volunteerName;
    }

    public int getStatus() {
        return status;
    }

    public Service(int id, String volunteerName, String description, String region, int status, int donationId) {
        this.id = id;
        this.volunteerName = volunteerName;
        this.description = description;
        this.region = region;
        this.status = status;
        this.donationId = donationId;
    }

    public Service(int id, String volunteerName, String description, String region, int donationId) {
        this.id = id;
        this.volunteerName = volunteerName;
        this.description = description;
        this.region = region;
        this.donationId = donationId;
    }

    public int getId() {
        return id;
    }

    public String getSubject() {
        return volunteerName;
    }

    public String getDescription() {
        return description;
    }

    public String getRegion() {
        return region;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSubject(String subject) {
        this.volunteerName = subject;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
