package com.example.sanabelalkhayr;

import com.example.sanabelalkhayr.model.CharitableEvent;
import com.example.sanabelalkhayr.model.Donation;
import com.example.sanabelalkhayr.model.DonationOrder;
import com.example.sanabelalkhayr.model.Feedback;
import com.example.sanabelalkhayr.model.Service;
import com.example.sanabelalkhayr.model.ServiceOrder;
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
    User login(String user_name, String password);//done
    User register(String name,String user_name, String password, String phone, String address, int type);//done
    User update_profile(int user_id, String name, String password,  String phone, String address);//done
    boolean send_report(int user_id, String title, String content);//done
    ArrayList<String> get_categories();//done
    ArrayList<String> get_regions();//done

    //======================== Donor =========================================

    boolean add_donation(int user_id,
                         String title,
                         String description,
                         String category, String region, String image_url, int quantity);//done
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
    //===================== Volunteer ===========================
    boolean add_service(int user_id, String title, String description, String region);//done

    /**
     *
     *
     */
    ArrayList<Service> get_volunteer_services(int user_id);//done
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
    // ================================    User  "Needy" =====================================

    ArrayList<Donation> get_donations();//done
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
    ArrayList<Service> get_services();//done
    /**
     * This should be stored in service requests table
     * and quantity will be got from donation request id
     *
     */

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
//////////////////////////////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\


}
