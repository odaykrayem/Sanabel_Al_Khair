package com.example.sanabelalkhayr.model;

public class Donation {

    private Integer id;
    private String title;
    private String description;
    private String category;
    private String image;
    private int quantity;
    private String donorUserName;
    private String region;

    public Donation(Integer id, String title, String description, String image, String category, int quantity, String donorUserName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.quantity = quantity;
        this.category = category;
        this.donorUserName = donorUserName;
    }

    public Donation(Integer id, String title, String description, String category, String image, int quantity, String donorUserName, String region) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.image = image;
        this.quantity = quantity;
        this.donorUserName = donorUserName;
        this.region = region;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDonorUserName() {
        return donorUserName;
    }

    public void setDonorUserName(String donorUserName) {
        this.donorUserName = donorUserName;
    }
}
