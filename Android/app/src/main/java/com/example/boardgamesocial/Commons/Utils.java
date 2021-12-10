package com.example.boardgamesocial.Commons;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.boardgamesocial.LoginAndSignUp.LoginAndSignUpActivity;
import com.example.boardgamesocial.MainApp.MainAppActivity;

import java.util.Objects;

public class Utils {

    private static SharedPreferences mPreferences;
    private static int userId;
    private static int profileId;

    public static void checkForUser(Context context) {
        boolean isLoginPage = context.getClass().getSimpleName().equals(LoginAndSignUpActivity.class.getSimpleName());
        if(Objects.nonNull(userId)) {
            if(isLoginPage) switchToMainAppActivity(context);
            return;
        }
        if(mPreferences == null) {
            getPrefs(context);
        }
        userId = mPreferences.getInt(Constants.USER_ID, -1);
        if(Objects.nonNull(userId)) {
            if(isLoginPage) switchToMainAppActivity(context);
            return;
        }
        Intent goToLoginActivity = LoginAndSignUpActivity.getIntent(context);
        context.startActivity(goToLoginActivity);
    }

    public static void getPrefs(Context context) {
        mPreferences = context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE);
    }

    public static void addUserIdToPreferences(Context context, Integer userId) {
        if(mPreferences == null) { getPrefs(context); }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(Constants.USER_ID, userId);
        editor.apply();
    }

    public static void addProfileIdToPreferences(Context context, Integer profileId) {
        if(mPreferences == null) { getPrefs(context); }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(Constants.PROFILE_ID, profileId);
        editor.apply();
    }

    public static Integer getUserId() {
        return mPreferences.getInt(Constants.USER_ID, -1);
    }

    public static Integer getProfileId() {
        return mPreferences.getInt(Constants.PROFILE_ID, -1);
    }

    private static void switchToMainAppActivity(Context context) {
        Intent goToLoginActivity = MainAppActivity.getIntent(context);
        context.startActivity(goToLoginActivity);
    }
}
