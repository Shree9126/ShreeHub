package com.mindnotix.utils;

import android.content.SharedPreferences;

import com.mindnotix.application.MyApplication;


public class Pref {


    public static final String PRE_TOKEN = "token";
    public static final String CLIENT_ACCOUNT_UNAME = "CLIENT_ACCOUNT_UNAME";
    public static final String CLIENT_ACCOUNT_PASS = "CLIENT_ACCOUNT_PASS";
    public static final String CLIENT_ACCOUNT_ID = "CLIENT_ACCOUNT_ID";
    public static final String CLIENT_IMAGE_THUMBNAIL_PATH = "CLIENT_IMAGE_THUMBNAIL";
    public static final String CLIENT_IMAGE_MEDIUM_PATH = "CLIENT_IMAGE_MEDIUM_PATH";
    public static final String CLIENT_IMAGE_LARGE_PATH = "CLIENT_IMAGE_LARGE_PATH";
    public static final String CLIENT_IMAGE_COVER_PATH = "CLIENT_IMAGE_COVER_PATH";
    public static final String CLIENT_ID = "CLIENT_ID";
    public static final String CLIENT_FIRSTNAME = "CLIENT_FIRSTNAME";
    public static final String CLIENT_LASTNAME = "CLIENT_LASTNAME";
    public static final String CLIENT_PROFILE_PICTURE_NAME = "CLIENT_PROFILE_PICTURE_NAME";
    public static final String CLIENT_COVER_PICTURE = "CLIENT_COVER_PICTURE";
    public static final String CLIENT_COUNT = "CLIENT_COUNT";
    public static final String PROFILE_IMAGE = "PROFILE_IMAGE";
    public static int APP_FIRST_BOOT = 0;
    static SharedPreferences preferences = MyApplication.sharedpreferences;

    public static int getAPP_FIRST_BOOT() {
        return APP_FIRST_BOOT;
    }

    public static void setAPP_FIRST_BOOT(int APP_FIRST_BOOTX) {
        APP_FIRST_BOOT = APP_FIRST_BOOTX;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("APP_FIRST_BOOT", APP_FIRST_BOOTX);

        editor.commit();
    }

    public static void setAccountId(int account_id) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(CLIENT_ACCOUNT_UNAME, account_id);

        editor.commit();
    }

    public static void setdevicetoken(String token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PRE_TOKEN, token);
        editor.commit();
    }

    public static String getPreToken() {
        return preferences.getString(PRE_TOKEN, null);
    }

    public static void savePreference(String mKey, String mValue, String id) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CLIENT_ACCOUNT_UNAME, mKey);
        editor.putString(CLIENT_ACCOUNT_PASS, mValue);
        editor.putString(CLIENT_ACCOUNT_ID, id);

        editor.commit();
    }

    public static void saveProfileImagePath(String thumnail, String image, String large) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CLIENT_IMAGE_THUMBNAIL_PATH, thumnail);
        editor.putString(CLIENT_IMAGE_MEDIUM_PATH, image);
        editor.putString(CLIENT_IMAGE_COVER_PATH, large);

        editor.commit();
    }

    public static void saveProfileImagePathReg(String thumnail, String image, String large) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CLIENT_IMAGE_THUMBNAIL_PATH, thumnail);
        editor.putString(CLIENT_IMAGE_MEDIUM_PATH, image);
        editor.putString(CLIENT_IMAGE_LARGE_PATH, large);

        editor.commit();
    }

    public static String getClientImageLargePath() {
        return preferences.getString(CLIENT_IMAGE_LARGE_PATH, null);
    }

    public static String getClientImageThumbnailPath() {
        return preferences.getString(CLIENT_IMAGE_THUMBNAIL_PATH, null);
    }

    public static String getClientImageMediumPath() {
        return preferences.getString(CLIENT_IMAGE_MEDIUM_PATH, null);
    }

    public static String getClientImageCoverPath() {

        return preferences.getString(CLIENT_IMAGE_COVER_PATH, null);
    }

    public static String getPreferenceUser() {
        return preferences.getString(CLIENT_ACCOUNT_UNAME, null);

    }

    public static String getPreferencePass() {
        return preferences.getString(CLIENT_ACCOUNT_PASS, null);

    }

    public static String getPreferenceUserID() {
        return preferences.getString(CLIENT_ACCOUNT_ID, null);

    }

    public static void clear() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public static void setClientID(String token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CLIENT_ID, token);
        editor.commit();
    }

    public static String getClientId() {
        return preferences.getString(CLIENT_ID, null);
    }

    public static String getClientFirstname() {
        return
                preferences.getString(CLIENT_FIRSTNAME, null);
    }

    public static void setClientFirstname(String token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CLIENT_FIRSTNAME, token);
        editor.commit();
    }

    public static String getClientLastname() {
        return preferences.getString(CLIENT_LASTNAME, null);
    }

    public static void setClientLastname(String token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CLIENT_LASTNAME, token);
        editor.commit();
    }

    public static String getClientProfilePictureName() {
        return preferences.getString(CLIENT_PROFILE_PICTURE_NAME, null);
    }

    public static void setClientProfilePictureName(String token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CLIENT_PROFILE_PICTURE_NAME, token);
        editor.commit();
    }

    public static String getClientCoverPicture() {
        return preferences.getString(CLIENT_COVER_PICTURE, null);
    }

    public static void setClientCoverPicture(String token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CLIENT_COVER_PICTURE, token);
        editor.commit();
    }

    public static String getClientCount() {
        return preferences.getString(CLIENT_COUNT, null);
    }


    //Profile

    public static void setClientCount(String token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CLIENT_COUNT, token);
        editor.commit();
    }

    public static String getProfileImage() {
        return preferences.getString(PROFILE_IMAGE, null);
    }

    public static void setProfileImage(String profileImage) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PROFILE_IMAGE, profileImage);
        editor.commit();
    }


}
