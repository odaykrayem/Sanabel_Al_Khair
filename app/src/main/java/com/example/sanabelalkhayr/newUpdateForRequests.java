package com.example.sanabelalkhayr;

import com.example.sanabelalkhayr.model.CharitableEvent;
import com.example.sanabelalkhayr.model.Donation;
import com.example.sanabelalkhayr.model.DonationOrder;
import com.example.sanabelalkhayr.model.Feedback;
import com.example.sanabelalkhayr.model.ServiceOrder;

import java.util.ArrayList;

public interface newUpdateForRequests {
    /**
     * @return Service Order
     *     int id;
     *      String donation_title;
     *      String user_name;//user who posted this service/volunteer/
     *      int status;
     *      String message;
     *      String createdAt;
     */
    //the services which user ordered
    ArrayList<ServiceOrder> get_service_orders(int user_id);//todo
    /**
     * @return Donation Order
     *      int id;
     *      int user_name; //user who posted this order /donor/
     *      String donation_title;
     *      int quantity;
     *      int status;
     *      String message;
     *      String createdAt;
     */
    //donations which user ordered
    ArrayList<DonationOrder> get_donation_orders(int user_id);//todo
    /**
     * service is not being deleted//////////
     * @return
     * status 1/0
     * status refers that operation done successfully or not
     */
    boolean delete_service(int user_id, int service_id);//todo
    //Not tested cuz i cant get donation orders
    boolean order_service(int user_id, int service_id, int donation_order_id);//todo
    //need quantity
    boolean order_donation(int user_is, int donation_id);//todo

    //change service request status by volunteer
    boolean change_service_request_status(int user_id, int service_order_id,String message, int status);//todo

    //change donation request status by donor
    boolean change_donation_request_status(int user_id, int donation_order_id,String message, int status);//todo

    //not working properly
    ArrayList<CharitableEvent> get_events();//todo

    //not tested cuz i cant get events
    boolean interested(int user_id, int event_id);//todo
    //not tested cuz i cant get donor donations

    //not return donor orders in post man
    ArrayList<Donation> get_donor_donations(int user_id);//todo
    /**
     * Donation Order:
     *      int id;
     *      int user_name; //user who ordered this donation/ normal user/needy
     *      String donation_title;
     *      int quantity; // quantity of items in order table not all donation quantity
     *      int status;
     *      String message;
     *      String createdAt;
     */
    //this return donation orders for a specific donor which were ordered from his donations
    ArrayList<DonationOrder> get_donation_requests(int user_id);//todo

    /**
     * Service Order/Request
     *     int id;
     *      String donation_title;
     *      String user_name;//user who ordered this service/normal user/needy
     *      int status;
     *      String message;
     *      String createdAt;
     */
    //this return services orders for a specific volunteer which were ordered from his services
    ArrayList<ServiceOrder> get_service_requests(int user_id);//todo

//    /**
//     * ALL Admin requests are missing in postman
//     */

//    /**
//     * Feedback/report
//     *      int id;
//     *      String title;
//     *      String content;
//     *      String user_name;//who posted this report
//     *      String date;
//     */
//    ArrayList<Feedback> get_reports();
    /**
//     *  Charitable Event Table:
//     *      int id;
//     *      String description;
//     *      String start_at;
//     *      String end_at;
//     *      String address;
//     */
//    boolean add_event(String description, String start_at, String end_at, String address);
    //get all events for admin no need for interested value
//    void get_events_for_admin();

    //  *     int USER_TYPE_ADMIN = 0;
    //Admin account should be already exist
    boolean log_in(String userName, String password);

}
