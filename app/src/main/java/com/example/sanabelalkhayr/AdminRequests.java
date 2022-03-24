package com.example.sanabelalkhayr;

import com.example.sanabelalkhayr.model.User;

import java.util.ArrayList;

public interface AdminRequests {

    /**
     * User Types:
     *
     *     int USER_TYPE_ADMIN = 0;
     *     int USER_TYPE_MAIN = 1;
     *     int USER_TYPE_DONOR = 2;
     *     int USER_TYPE_VOLUNTEER = 3;
     */
    /**
     * User:
     *     int id;
     *     String name;
     *     String userName;
     *     String password;
     *     String phone;
     *     String address;
     *     int type;
     */
    //Admin account should be already exist
    boolean log_in(String userName, String password);
    ArrayList<User> get_users();
    boolean  delete_user(int user_id);

    /**
     *  Charitable Event Table:
     *      int id;
     *      String description;
     *      String start_at;
     *      String end_at;
     *      String address;
     *      boolean interested;// this will be changed according to user default 0 or false
     */
    boolean add_event(String description, String start_at, String end_at, String address);

    //i will handle logout from android
}
