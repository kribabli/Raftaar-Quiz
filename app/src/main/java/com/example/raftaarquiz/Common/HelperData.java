package com.example.raftaarquiz.Common;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class HelperData extends Application {
    private static String SHARED_PREF_NAME1 = "Raftaar";
    private SharedPreferences sharedPreferences;
    Context context;
    private SharedPreferences.Editor editor;

    public HelperData(Context context) {
        this.context = context;
    }

    public boolean getIsLogin() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("IsLoggedIn", false);
    }

    public void saveIsLogin(boolean flag) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean("IsLoggedIn", flag);
        editor.apply();
    }

    public void saveLogin(String user_id, String user_name, String email, String mobile, String refferal_code) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("user_id", user_id);
        editor.putString("user_name", user_name);
        editor.putString("email", email);
        editor.putString("mobile", mobile);
        editor.putString("refferal_code", refferal_code);
        editor.apply();
    }

    public String getUserId() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_id", "");
    }

    public String getUserName() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_name", "");
    }

    public String getUserEmail() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", "");
    }

    public String getMobile() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        return sharedPreferences.getString("mobile", "");
    }

    public String getReferalCode() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        return sharedPreferences.getString("refferal_code", "");
    }

    public void Logout() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
