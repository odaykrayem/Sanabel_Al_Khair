package com.example.sanabelalkhair.model;

public class Service {

    private Integer id;
    private String subject;
    private String description;
    private String region;

    public Service(Integer id, String subject, String description, String region) {
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.region = region;
    }

    public Integer getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public String getRegion() {
        return region;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
