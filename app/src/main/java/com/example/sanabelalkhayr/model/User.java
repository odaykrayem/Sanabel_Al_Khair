package com.example.sanabelalkhayr.model;

public class User {

    //all users have:
    private int id;
    private String name;
    private String userName;
    private String userEmail;
    private String password;
    private String phone;
    private String address;
    private int type;
    private int numOfResponses;

    public User(int id, String name, String userName, String phone, String address, int type) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.phone = phone;
        this.address = address;
        this.type = type;
    }

    public void setNumOfResponses(int numOfResponses) {
        this.numOfResponses = numOfResponses;
    }

    public int getNumOfResponses() {
        return numOfResponses;
    }



    public User(int id, String name, String userName, String email,String phone, String address, int type, int numOfResponses) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.userEmail = email;
        this.phone = phone;
        this.address = address;
        this.type = type;
        this.numOfResponses = numOfResponses;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public int getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setType(int type) {
        this.type = type;
    }
}
