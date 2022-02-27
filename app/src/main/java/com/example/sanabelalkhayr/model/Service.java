package com.example.sanabelalkhayr.model;

public class Service {

    private int id;
    private String volunteerName;
    private String description;
    private String region;

    public void setvolunteerName(String volunteerName) {
        this.volunteerName = volunteerName;
    }

    public String getvolunteerName() {
        return volunteerName;
    }


    public Service(int id, String volunteerName, String description, String region) {
        this.id = id;
        this.volunteerName = volunteerName;
        this.description = description;
        this.region = region;
    }

    //Volunteer services
    public Service(int id, String description, String region) {
        this.id = id;
        this.description = description;
        this.region = region;
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
