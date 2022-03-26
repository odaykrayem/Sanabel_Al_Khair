package com.example.sanabelalkhayr.utils;

public class Urls {

    //MAIN REQUESTS
    public static final String BASE_URL = "http://std.scit.co/crops/public/api/";

    public static final String IMAGE_BASE_URL = "http://std.scit.co/crops/public/";

    public static final String LOGIN_URL = BASE_URL + "login";
    public static final String REGISTER_URL = BASE_URL + "register";
    public static final String UPDATE_ACC_URL = BASE_URL + "update-profile";
    public static final String FEEDBACK_URL = BASE_URL + "send-report";
    public static final String GET_EVENTS_FOR_USERS = BASE_URL + "";//TODO

    //NEEdY

    public static final String GET_CATEGORIES = BASE_URL + "get-categories";
    public static final String GET_DONATIONS = BASE_URL + "get-donations";
    public static final String ORDER_DONATION_URL = BASE_URL + "order-donation";
    public static final String GET_DONATION_ORDERS = BASE_URL + "get-donation-orders";
    public static final String GET_SERVICES = BASE_URL + "";//TODO
    public static final String GET_SERVICE_ORDERS = BASE_URL + "";//TODO
    public static final String ORDER_SERVICE_URL = BASE_URL + "order-service";

    //DONOR
    public static final String ADD_DONATION_URL = BASE_URL + "add-donation";
    public static final String GET_REGIONS = BASE_URL + "get-areas";
    public static final String GET_ORDERS_REQUESTS = BASE_URL + "";//TODO
    public static final String GET_DONATIONS_BY_DONOR = BASE_URL + "get-donor-donations";
    public static final String UPDATE_DONATION_REQUEST_STATUS = BASE_URL + "change-donation-status";

    //VOLUNTEER
    public static final String ADD_SERVICE_URL = BASE_URL + "add-service";
    public static final String DELETE_SERVICE_URL = BASE_URL + "delete-service";
    public static final String GET_VOLUNTEER_SERVICES_URL = BASE_URL + "get-volunteer-services";
    public static final String UPDATE_SERVICE_REQUEST_STATUS = BASE_URL + "change-service-status";

    //ADMIN
    public static final String GET_USERS_URL = BASE_URL + "get-users";
    public static final String DELETE_USER_URL = BASE_URL + "delete-user";

    public static final String GET_EVENTS_FOR_ADMIN = BASE_URL + "";//TODO
    public static final String DELETE_EVENT_URL = BASE_URL + "delete-event";


    public static final String ADD_EVENT_URL = BASE_URL + "add-event";
    public static final String ADD_AREA_URL = BASE_URL + "add-area";
    public static final String ADD_CATEGORY_URL = BASE_URL + "add-category";

}