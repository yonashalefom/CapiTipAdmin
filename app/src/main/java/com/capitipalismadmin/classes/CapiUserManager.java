package com.capitipalismadmin.classes;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class CapiUserManager {
    private static boolean userExists;
    private static String currentUserID;
    private static String userType;

    // region Load User Preferences
    public static void loadUserData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            if (sharedPreferences.contains("user_id") && sharedPreferences.contains("user_type")) {
                currentUserID = sharedPreferences.getString("user_id", "NULL");
                userType = sharedPreferences.getString("user_type", "NULL");
                userExists = true;
            } else {
                currentUserID = null;
                userType = null;
                userExists = false;
            }
        } else {
            currentUserID = null;
            userType = null;
            userExists = false;
        }
    }
    // endregion

    // region Remove User Preferences
    public static void removeUserData(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.remove("user_id");
        sharedPreferencesEditor.remove("user_type");
        sharedPreferencesEditor.apply();
        sharedPreferencesEditor.clear();
    }
    // endregion

    //region Save User's ID to User Data Shared Preference
    public static void saveUserData(Context context, String userId, String userType) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE); //1
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_id", userId);
        editor.putString("user_type", userType);
        editor.apply();
    }
    //endregion

    // region Check User data exists
    public static boolean userDataExists() {
        return userExists;
    }
    // endregion

    // region Get current user ID
    public static String getCurrentUserID() {
        return currentUserID;
    }
    // endregion


    public static String getUserType() {
        return userType;
    }

    public static void setUserType(String userType) {
        CapiUserManager.userType = userType;
    }
}
