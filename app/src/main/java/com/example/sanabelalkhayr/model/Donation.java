package com.example.sanabelalkhayr.model;

public class Donation {

    private Integer id;
    private String title;
    private String description;
    private String image;
    private int quantity;

    public Donation(Integer id, String title, String description, String image, int quantity) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}