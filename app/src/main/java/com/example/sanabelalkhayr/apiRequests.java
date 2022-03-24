package com.example.sanabelalkhayr;

import com.example.sanabelalkhayr.model.CharitableEvent;
import com.example.sanabelalkhayr.model.Donation;
import com.example.sanabelalkhayr.model.DonationOrder;
import com.example.sanabelalkhayr.model.Service;
import com.example.sanabelalkhayr.model.ServiceOrder;
import com.example.sanabelalkhayr.model.ServiceRequest;
import com.example.sanabelalkhayr.model.User;

import java.util.ArrayList;

public interface apiRequests {


    /**
     *User types:
     * int USER_TYPE_ADMIN = 0;
     * int USER_TYPE_MAIN = 1;
     *  int USER_TYPE_DONOR = 2;
     * int USER_TYPE_VOLUNTEER = 3;
     */
    /**
     * Status types :
     * int REQUEST_STATUS_PROCESSING = 1;
     * int REQUEST_STATUS_ACCEPTED = 2;
     *  int REQUEST_STATUS_REJECTED = 0;
     */

    // ================================= All users ==========================
    User login(String user_name, String password);
    User register(String name,String user_name, String password, String phone, String address, int type);
    User update_profile(int user_id, String name, String password,  String phone, String address);
    ArrayList<CharitableEvent> get_events();
    boolean interested(int user_id, int event_id);
    boolean send_report(int user_id, String title, String content);

    ArrayList<String> get_categories();
    ArrayList<String> get_regions();

    //======================== Donor =========================================

    boolean change_donation_status(int user_id, int donation_id,String message, int status);
    ArrayList<Donation> get_donor_donations(int user_id);
    boolean add_donation(int user_id,
                         String title,
                         String description,
                         String category, String region, String image_url, int quantity);
    /**
     *      Donation request:
     *      int request_id;
     *      int user_id;
     *      int donation_id;
     *      String donation_title;
     *      int quantity;
     *      int status;
     *      String message;
     *      String createdAt;
     */

    ArrayList<DonationOrder> get_donation_requests(int user_id);

    //===================== Volunteer ===========================
    boolean add_service(int user_id, String title, String description, String region);

    /**
     *
     *
     */
    ArrayList<Service> get_volunteer_services(int user_id);
    /**
     *     Service Request:
     *      int request_id;
     *      int user_id;
     *      int donation_id;
     *      String donation_title;
     *      int quantity;
     *      int status;
     *      String message;
     *      String createdAt;
     */

    ArrayList<ServiceRequest> get_service_requests(int user_id, int donation_id, String donation_title, int quantity, int status, String message, String created_at);
    boolean change__service_status(int user_id, int service_id,String message, int status);
    boolean delete_service(int user_id, int service_id);

    // ================================    User  "Needy" =====================================

    ArrayList<Donation> get_donations();
    boolean order_donation(int user_is, int donation_id);

    /**
     *   Donation Order:
     *     int id;
     *     int user_id;
     *     int donation_id;
     *     String donation_title;
     *     int quantity;
     *     int status;
     *     String message;
     *     String createdAt;
     */
    ArrayList<DonationOrder> get_donation_orders(int user_id);

    ArrayList<Service> get_services();

    /**
     * This should be stored in service requests table
     * and quantity will be got from donation request id
     *
     */
    boolean order_service(int user_id, int service_id, int donation_order_id);
    /**
     *  Service Order:
     *    int id;
     *    int donation_id;
     *    int service_id;
     *    String donation_title;
     *     String volunteer_name;
     *    int status;
     *    String message;
     *    String createdAt;
     */
     ArrayList<ServiceOrder> get_service_orders(int user_id);

}
