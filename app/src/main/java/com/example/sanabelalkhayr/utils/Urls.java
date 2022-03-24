package com.example.sanabelalkhayr.utils;

import java.util.Base64;

public class Urls {

    //MAIN REQUESTS
    public static final String BASE_URL = "http://std.scit.co/crops/public/api/";

    public static final String LOGIN_URL = BASE_URL +"login";
    public static final String REGISTER_URL = BASE_URL +"register";
    public static final String UPDATE_ACC_URL = BASE_URL +"update-profile";
    public static final String FEEDBACK_URL = BASE_URL +"";
    public static final String GET_EVENTS_FOR_USERS = BASE_URL +"";

    //NEEdY
    public static final String GET_DONATIONS = BASE_URL +"";
    public static final String ORDER_DONATION_URL = BASE_URL +"";
    public static final String GET_CATEGORIES = BASE_URL +"" ;
    public static final String GET_DONATION_ORDERS = BASE_URL +"";
    public static final String GET_SERVICES = BASE_URL + "";
    public static final String GET_SERVICE_ORDERS = BASE_URL + "";


    //DONOR
    public static final String ADD_DONATION_URL = BASE_URL +"";
    public static final String GET_REGIONS = BASE_URL + "";
    public static final String GET_ORDERS_REQUESTS = BASE_URL + "";
    public static final String GET_DONATIONS_BY_DONOR = BASE_URL + "";
    public static final String UPDATE_REQUEST_STATUS = BASE_URL + ""; //accept or reject request

    //VOLUNTEER
    public static final String ADD_SERVICE_URL = BASE_URL +"";
    public static final String GET_MY_SERVICES_URL = BASE_URL +"";



    //ADMIN

    public static final String GET_USERS_URL = BASE_URL +"";
    public static final String GET_EVENTS_URL = BASE_URL +"get-events";
    public static final String DELETE_USER_URL = BASE_URL +"";
    public static final String ADD_EVENT_URL = BASE_URL +"";

}
