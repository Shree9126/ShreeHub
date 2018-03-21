package com.mindnotix.utils;

import com.mindnotix.model.DataRegSumbitResult;
import com.mindnotix.model.Ethnicity;
import com.mindnotix.model.Local_board_list;
import com.mindnotix.model.Regional_council_list;
import com.mindnotix.model.Wishlist;

import java.util.ArrayList;

/**
 * Created by Sridharan on 22/11/17.
 */
public class Constant {


    //server
    public static final String BASE_URL = "https://backend.youthhub.co.nz/api/v1/";
    public static final String BASE_URL_PROFILE_THUMBNAIL = "https://images.youthhub.co.nz/profile/thumbnail/";
    public static final String BASE_URL_PROFILE_MEDIUM = "https://images.youthhub.co.nz/profile/medium/";
    public static final String BASE_URL_PROFILE_LARGE = "https://images.youthhub.co.nz/profile/";

    //Master record
    public static ArrayList<Ethnicity> ethnicityArrayList = new ArrayList<>();
    public static ArrayList<Wishlist> wishlistArrayList = new ArrayList<>();
    public static ArrayList<String> wishlistArrayListString = new ArrayList<>();
    //  public static CharSequence[] wishlistArrayListStringchar = new CharSequence[wishlistArrayList.size()];
    public static ArrayList<Regional_council_list> regionalCouncilListArrayList = new ArrayList<>();
    public static ArrayList<Local_board_list> local_board_listArrayList = new ArrayList<>();
    public static ArrayList<DataRegSumbitResult> dataRegSumbitResultArrayList = new ArrayList<>();


    public interface httpcodes {
        int STATUS_OK = 200;
        int STATUS_CREATED = 201;
        int STATUS_NO_CONTENT = 204;
        int STATUS_BAD_REQUEST = 400;
        int STATUS_UNAUTHORIZED = 401;
        int STATUS_FORBITTEN = 403;
        int STATUS_NOT_FOUND = 404;
        int STATUS_SERVER_ERROR = 500;
    }

}
