package com.example.sanabelalkhayr.model;

public class CharitableEvent {
    private int id;
    private String description;
    private String start_at;
    private String end_at;
    private String address;

    public CharitableEvent(int id, String description, String start_at, String end_at, String address) {
        this.id = id;
        this.description = description;
        this.start_at = start_at;
        this.end_at = end_at;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getStart_at() {
        return start_at;
    }

    public String getEnd_at() {
        return end_at;
    }

    public String getAddress() {
        return address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStart_at(String start_at) {
        this.start_at = start_at;
    }

    public void setEnd_at(String end_at) {
        this.end_at = end_at;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
